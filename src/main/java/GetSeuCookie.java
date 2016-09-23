package main.java;

import java.io.IOException;
import java.util.*;

import org.jsoup.*;
import org.jsoup.Connection.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GetSeuCookie {
	Map<String,String> map=new HashMap<String,String>();
	public GetSeuCookie(){
		
		map.put("tbname", "213150192");
		map.put("tbpsw", "qinaideyang48");
		map.put("ImageButton1.x", "0");
		map.put("ImageButton1.y", "0");
		
	}
	public void setHeader(Connection conn){
		conn.header("(Request-Line)", "GET /cgi-bin/login?lang=zh_CN HTTP/1.1");
		conn.header("Accept", "application/json, text/javascript, */*; q=0.01");
		conn.header("Accept-Encoding", "gzip, deflate");
		conn.header("Accept-Language", "zh-cn");
		conn.header("Cache-Control", "no-cache");
		conn.header("Connection", "Keep-Alive");
		conn.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		conn.header("Host", "10.1.30.98:8080");
		conn.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; MALC)");
	}
	public  Map<String, String> getcookie() throws IOException{
		Connection conn=Jsoup.connect("http://10.1.30.98:8080/competition/login.aspx");
	//	this.setHeader(conn);
		Response response = conn.ignoreContentType(false).method(Method.GET).execute();
		Document doc=Jsoup.parse(response.body());
		Element content = doc.getElementById("__VIEWSTATE");
		map.put("__VIEWSTATE", content.attr("value"));
		content=doc.getElementById("__EVENTVALIDATION");
		map.put("__EVENTVALIDATION", content.attr("value"));
		
		

		response=conn.ignoreContentType(true).method(Method.POST).data(map).execute();
		//System.out.println(response.body());
		Map<String, String> cookies = response.cookies();
		for (Map.Entry<String, String> entry : cookies.entrySet()) {
			
    		conn.cookie(entry.getKey(), entry.getValue());
    		
		}
		
		return cookies;
	}
}
