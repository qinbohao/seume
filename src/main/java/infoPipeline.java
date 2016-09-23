package main.java;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import sql.java.Info;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class infoPipeline implements Pipeline{

	@Override
	public void process(ResultItems resultItems, Task task) {
		// TODO 自动生成的方法存根
		
		int resulet=0;
		@SuppressWarnings("unchecked")
		List<String> infojwc=(List<String>)resultItems.get("infojwc");
		
		if(infojwc!=null){
		Collections.reverse(infojwc);
		String resource = "conf.xml"; //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
	    InputStream is = SeuProcessor.class.getClassLoader().getResourceAsStream(resource);//构建sqlSession的工厂
	    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
	    SqlSession session = sessionFactory.openSession();
	    for(String tmp:infojwc){
	    	Info newinfo=new Info();
	    	//newinfo.setId(1);
	    	newinfo.setType("教务处");
	    	newinfo.setTxt(tmp);
	    	 String statement = "sql.mybatis.Infomapper.addInfo";//映射sql的标识字符串
	    	// System.out.println(((Info)session.selectOne(statement, newinfo)).getType());
	    	 resulet=session.insert(statement, newinfo);
	    	 
	    }
	   
	     session.commit();
	     System.out.println(resulet);
	     session.close();
		}
	}

}
