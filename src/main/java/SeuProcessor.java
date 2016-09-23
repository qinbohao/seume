package main.java;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author code4crafter@gmail.com <br>
 */
public class SeuProcessor implements PageProcessor {

String URL_LIST="http://218.94.50.12/sunflower/candidate/candidateEnter.action?act=licensePrint&systemFlag=1&candidateId=";
    private Site site = Site
            .me()
            .setSleepTime(10)
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
            ;
    @Override
    public void process(Page page) {
        //¡–±Ì“≥
    	//Info info=new Info();
    	List<String> list=page.getHtml().xpath("//a[contains(@id,'ctl00_ContentPlaceHolder1_gvleader_ct')]").all(); 
    	List<String> info=new ArrayList<String>();
    	for(String tmp:list){
    		Document doc=Jsoup.parse(tmp);
    		Elements ele=doc.getElementsByTag("a");
    		info.add(ele.html());
    	}
    	page.putField("infojwc", info);
    	
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
              .addPipeline(new infoPipeline())
              .run();   
    }
}