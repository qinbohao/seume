package main.java;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author code4crafter@gmail.com <br>
 */
public class SeuProcessor implements PageProcessor {


	private Site site = Site
			.me()
			.setSleepTime(10)
			.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
			.addHeader("Accept-Encoding", "gzip, deflate")
			.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
			.addHeader("Connection","keep-alive")
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
			;
	@Override
	public void process(Page page) {

		/**
		 * 学科竞赛
		 */
		if(page.getUrl().toString().contains("http://10.1.30.98:8080/")){
			List<String> list=page.getHtml().xpath("//a[contains(@id,'ctl00_ContentPlaceHolder1_gvleader_ct')]").all(); 
			List<String> info=new ArrayList<String>();
			for(String tmp:list){
				Document doc=Jsoup.parse(tmp);
				Elements ele=doc.getElementsByTag("a");
				info.add(ele.html());
			}
			page.putField("infoxkjs", info);

		}
		/**
		 * 机械工程学院
		 */

		else if(page.getUrl().toString().contains("http://me.seu.edu.cn/")){
			List<String> infotitle=page.getHtml().xpath("//a[contains(@target,'_blank')]").all(); 
			List<String> infodate=page.getHtml().xpath("//td[(@align='left') and (@width='30px')]").all(); 
			List<String> infometitle=new ArrayList<String>();
			List<String> infomedate=new ArrayList<String>();


			for(String tmp:infotitle){

				Document doc=Jsoup.parse(tmp);
				Elements ele=doc.getElementsByTag("a");
				infometitle.add(ele.attr("title"));
				
			}
			for(String tmp:infodate){
				
				Document doc=Jsoup.parse(tmp);
				Elements ele=doc.getElementsByTag("div");
				infomedate.add(ele.html());
			
			}
			/*Document doc=Jsoup.parse(page.getHtml().toString());
			Elements ele=doc.select("tr:has(a[target=_blank]");

			//Elements ele=pele.select("tbody tr");
			System.out.println(ele.html());
			@SuppressWarnings("unchecked")
			ListIterator<Element> elelist=(ListIterator<Element>) ele.listIterator();
			List<String> infotitle=new ArrayList<String>();
			List<String> infodate=new ArrayList<String>();
			while(elelist.hasNext()){
				System.out.println(elelist.next().select(">tr>tbody td a").attr("title").replaceAll(" ", ""));
				//infotitle.add(elelist.next().select("td a").attr("title").replaceAll(" ", ""));
				//infodate.add(elelist.next().select("td div").html().replaceAll(" ", ""));*/
			//}
			page.putField("infometitle", infometitle);
			page.putField("infomedate", infomedate);

		}


	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) throws IOException {
		GetSeuCookie cookie=new GetSeuCookie();
		Map<String,String> ck=cookie.getcookie();
		SeuProcessor spc=new SeuProcessor();	
		spc.getSite().addHeader("Cookie","ASP.NET_SessionId=" + ck.get("ASP.NET_SessionId"))
		.addHeader("Cookie", ".ASPXAUTH="+ck.get(".ASPXAUTH"));
		Spider.create(spc)
		.addUrl("http://10.1.30.98:8080/competition/c_stu_default.aspx")
		.addUrl("http://me.seu.edu.cn/")
		.addPipeline(new infoPipeline())
		.run();   
	}
}