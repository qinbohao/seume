package main.java;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import sql.java.Info;
import sql.java.lesson;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class infoPipeline implements Pipeline{

	private int save(String type,String channel,List<String>...lists){

		int result=0;
		/**
		 * �������ݿ�
		 */
		String resource = "conf.xml"; //ʹ�������������mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���
		InputStream is = SeuProcessor.class.getClassLoader().getResourceAsStream(resource);//����sqlSession�Ĺ���
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		SqlSession session = sessionFactory.openSession();
		/**
		 * ��ȡ���ݿ������µĽ��
		 */
		int index=0;
		for(String tmp:lists[0]){
				Info newinfo=new Info();
				newinfo.setType(type);
				newinfo.setChannel(channel);
				newinfo.setTxt(tmp);
				if(channel.equals("��еѧԺ")){
					newinfo.setDate(lists[1].get(index));
					switch(index/6){
					case 5:
						newinfo.setType("ѧԺ����");break;
					case 4:
						newinfo.setType("֪ͨ����");break;
					case 3:
						newinfo.setType("����������");break;
					case 2:
						newinfo.setType("ѧ������");break;
					case 1:
						newinfo.setType("�о�������");break;
					case 0:
						newinfo.setType("ѧ����̳");break;
					}
				}

				String stat = "sql.mybatis.Infomapper.addInfo";//ӳ��sql�ı�ʶ�ַ���
				result=session.insert(stat, newinfo);
				index++;
			//}
		}	
		session.commit();
		session.close();
		return result;		
	}
	private int save(String id,List<String> lesson ,String time){
		int result=0;
		String resource = "conf.xml"; //ʹ�������������mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���
		InputStream is = SeuProcessor.class.getClassLoader().getResourceAsStream(resource);//����sqlSession�Ĺ���
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		SqlSession session = sessionFactory.openSession();
		lesson les=new lesson();
		les.setAll(id, lesson, time);
		String stat = "sql.mybatis.lessonmapper.addlesson";//ӳ��sql�ı�ʶ�ַ���
		result=session.insert(stat, les);
		session.commit();
		session.close();
		return result;	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void process(ResultItems resultItems, Task task) {
		int resulet=0;
		List<String> infoxkjs=(List<String>)resultItems.get("infoxkjs");
		List<String> infometitle=(List<String>)resultItems.get("infometitle"); //����
		List<String> infomedate=(List<String>)resultItems.get("infomedate");  //����
		List<String> infolesson=(List<String>)resultItems.get("infolesson");  
		/**
		 * ѧ�ƾ���
		 */
		if(infoxkjs!=null){
			Collections.reverse(infoxkjs);
			System.out.println(save("ѧ�ƾ���","����",infoxkjs));
		}

		/**
		 * ��еѧԺ
		 */
		if(infometitle!=null&&infomedate!=null&&infometitle.size()==infomedate.size()){
			Collections.reverse(infometitle);
			Collections.reverse(infomedate);
			System.out.println(save("no","��еѧԺ",infometitle,infomedate));
		}
		/**
		 * �α�
		 */
		if(infolesson!=null){
			System.out.println(save("213150192",infolesson,"16-17-2"));
		}
		
	}
}
