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
		// TODO �Զ����ɵķ������
		
		int resulet=0;
		@SuppressWarnings("unchecked")
		List<String> infojwc=(List<String>)resultItems.get("infojwc");
		
		if(infojwc!=null){
		Collections.reverse(infojwc);
		String resource = "conf.xml"; //ʹ�������������mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���
	    InputStream is = SeuProcessor.class.getClassLoader().getResourceAsStream(resource);//����sqlSession�Ĺ���
	    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
	    SqlSession session = sessionFactory.openSession();
	    for(String tmp:infojwc){
	    	Info newinfo=new Info();
	    	//newinfo.setId(1);
	    	newinfo.setType("����");
	    	newinfo.setTxt(tmp);
	    	 String statement = "sql.mybatis.Infomapper.addInfo";//ӳ��sql�ı�ʶ�ַ���
	    	// System.out.println(((Info)session.selectOne(statement, newinfo)).getType());
	    	 resulet=session.insert(statement, newinfo);
	    	 
	    }
	   
	     session.commit();
	     System.out.println(resulet);
	     session.close();
		}
	}

}
