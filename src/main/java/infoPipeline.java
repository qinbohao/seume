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

		/*	boolean flag=false;
	Info oldinfo=new Info();
		if(channel.equals("����")){

			oldinfo.setType(type);
			String statement="sql.mybatis.Infomapper.getdate";
			oldinfo=(Info)session.selectOne(statement,oldinfo);		
		}else{
			
		}*/

		int index=0;
		for(String tmp:lists[0]){
			
		/*	if(tmp.equals(oldinfo.getTxt())&&flag==false){	
				flag=true;
				index++;
				continue;
			}*/
			//if(true){	
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
		return result;		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void process(ResultItems resultItems, Task task) {
		int resulet=0;
		List<String> infoxkjs=(List<String>)resultItems.get("infoxkjs");
		List<String> infometitle=(List<String>)resultItems.get("infometitle"); //����
		List<String> infomedate=(List<String>)resultItems.get("infomedate");  //����


		/**
		 * �������ݿ�
		 */
		//	String resource = "conf.xml"; //ʹ�������������mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���
		//	InputStream is = SeuProcessor.class.getClassLoader().getResourceAsStream(resource);//����sqlSession�Ĺ���
		//	SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		//	SqlSession session = sessionFactory.openSession();
		/**
		 * ѧ�ƾ���
		 */
		if(infoxkjs!=null){
			Collections.reverse(infoxkjs);
			/*boolean flag=false;
			Info oldinfo=new Info();
			oldinfo.setChannel("����");
			String statement="sql.mybatis.Infomapper.getdate";
			oldinfo=(Info)session.selectOne(statement,oldinfo);
			for(String tmp:infoxkjs){		
				if(tmp.equals(oldinfo.getTxt())&&flag==false){	
					flag=true;
					continue;
				}
				if(flag){	
					Info newinfo=new Info();
					newinfo.setType("ѧ�ƾ���");
					newinfo.setChannel("����");
					newinfo.setTxt(tmp);
					String stat = "sql.mybatis.Infomapper.addInfo";//ӳ��sql�ı�ʶ�ַ���
					resulet=session.insert(stat, newinfo);
				}
			}
			System.out.println(flag);
			session.commit();*/
			System.out.println(save("ѧ�ƾ���","����",infoxkjs));
		}

		/**
		 * ��еѧԺ
		 */
		if(infometitle!=null&&infomedate!=null&&infometitle.size()==infomedate.size()){
			Collections.reverse(infometitle);
			Collections.reverse(infomedate);
			/*boolean flag=false;

			int i=0;
			for(String tmp:infometitle){
				Info newinfo=new Info();
				newinfo.setChannel("��еѧԺ");
				newinfo.setTxt(tmp);
				newinfo.setDate(infomedate.get(i));
				switch(i%6){
				case 0:
					newinfo.setType("ѧԺ����");break;
				case 1:
					newinfo.setType("֪ͨ����");break;
				case 2:
					newinfo.setType("����������");break;
				case 3:
					newinfo.setType("ѧ������");break;
				case 4:
					newinfo.setType("�о�������");break;
				case 5:
					newinfo.setType("ѧ����̳");break;
				}
				i++;
				String statement="sql.mybatis.Infomapper.getdate";
				newinfo=(Info)session.selectOne(statement,newinfo);
				if(tmp.equals(newinfo.getTxt())&&flag==false){	
					flag=true;
					continue;
				}
				if(flag){	
					Info info=new Info();
					info.setType("ѧ�ƾ���");
					info.setTxt(tmp);
					String stat = "sql.mybatis.Infomapper.addInfo";//ӳ��sql�ı�ʶ�ַ���
					resulet=session.insert(stat, info);
				}
			}
			System.out.println(flag);
			session.commit();
			System.out.println(resulet);
		}
		session.close();*/
			System.out.println(save("no","��еѧԺ",infometitle,infomedate));
		}
	}
}
