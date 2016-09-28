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
		 * 连接数据库
		 */
		String resource = "conf.xml"; //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
		InputStream is = SeuProcessor.class.getClassLoader().getResourceAsStream(resource);//构建sqlSession的工厂
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		SqlSession session = sessionFactory.openSession();
		/**
		 * 获取数据库中最新的结果
		 */
		int index=0;
		for(String tmp:lists[0]){
				Info newinfo=new Info();
				newinfo.setType(type);
				newinfo.setChannel(channel);
				newinfo.setTxt(tmp);
				if(channel.equals("机械学院")){
					newinfo.setDate(lists[1].get(index));
					switch(index/6){
					case 5:
						newinfo.setType("学院新闻");break;
					case 4:
						newinfo.setType("通知公告");break;
					case 3:
						newinfo.setType("本科生教务");break;
					case 2:
						newinfo.setType("学生工作");break;
					case 1:
						newinfo.setType("研究生教务");break;
					case 0:
						newinfo.setType("学术论坛");break;
					}
				}

				String stat = "sql.mybatis.Infomapper.addInfo";//映射sql的标识字符串
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
		String resource = "conf.xml"; //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
		InputStream is = SeuProcessor.class.getClassLoader().getResourceAsStream(resource);//构建sqlSession的工厂
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		SqlSession session = sessionFactory.openSession();
		lesson les=new lesson();
		les.setAll(id, lesson, time);
		String stat = "sql.mybatis.lessonmapper.addlesson";//映射sql的标识字符串
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
		List<String> infometitle=(List<String>)resultItems.get("infometitle"); //标题
		List<String> infomedate=(List<String>)resultItems.get("infomedate");  //日期
		List<String> infolesson=(List<String>)resultItems.get("infolesson");  
		/**
		 * 学科竞赛
		 */
		if(infoxkjs!=null){
			Collections.reverse(infoxkjs);
			System.out.println(save("学科竞赛","教务处",infoxkjs));
		}

		/**
		 * 机械学院
		 */
		if(infometitle!=null&&infomedate!=null&&infometitle.size()==infomedate.size()){
			Collections.reverse(infometitle);
			Collections.reverse(infomedate);
			System.out.println(save("no","机械学院",infometitle,infomedate));
		}
		/**
		 * 课表
		 */
		if(infolesson!=null){
			System.out.println(save("213150192",infolesson,"16-17-2"));
		}
		
	}
}
