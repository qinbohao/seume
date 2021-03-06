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
			page.putField("infometitle", infometitle);
			page.putField("infomedate", infomedate);

		}
		
		/**
		 * 课表
		 */
		else if(page.getUrl().toString().contains("http://xk.urp.seu.edu.cn/")){
			
			List<String> info=page.getHtml().xpath("//td[@rowspan='5']").all(); 
			List<String> infolesson=new ArrayList<String>();
			for(String tmp:info){		
				Document doc=Jsoup.parse(tmp);
				if(doc.text().trim().contains("上午")||doc.text().trim().contains("下午")){
					continue;
				}else
				{
					infolesson.add(doc.text().trim());
				}
				
			}
			page.putField("infolesson", infolesson);
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) throws IOException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				GetSeuCookie cookie=new GetSeuCookie();
				SeuProcessor spc=new SeuProcessor();	
				while(true){
					try{
						
						Map<String,String> ck=cookie.getcookie();
					
						spc.getSite().addHeader("Cookie","ASP.NET_SessionId=" + ck.get("ASP.NET_SessionId"))
						.addHeader("Cookie", ".ASPXAUTH="+ck.get(".ASPXAUTH"));
						Spider.create(spc)
						.addUrl("http://10.1.30.98:8080/competition/c_stu_default.aspx")
						.addUrl("http://me.seu.edu.cn/")
						.addUrl("http://xk.urp.seu.edu.cn/jw_service/service/stuCurriculum.action?queryStudentId=213150192&queryAcademicYear=16-17-2")
						.addPipeline(new infoPipeline())
						.run();
						Thread.sleep(1000*60*20);
					}catch(Exception e){

					}
				}
			}
		},"seu-me-listener").start();;
	}
}