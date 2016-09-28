package sql.java;

import java.util.List;

public class lesson {
	private String id;
	private String monA;
	private String monB;
	private String tueA;
	private String tueB;
	private String wedA;
	private String wedB;
	private String thrA;
	private String thrB;
	private String friA;
	private String friB;
	private String time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMonA() {
		return monA;
	}
	public void setMonA(String monA) {
		this.monA = monA;
	}
	public String getMonB() {
		return monB;
	}
	public void setMonB(String monB) {
		this.monB = monB;
	}
	public String getTueA() {
		return tueA;
	}
	public void setTueA(String tueA) {
		this.tueA = tueA;
	}
	public String getTueB() {
		return tueB;
	}
	public void setTueB(String tueB) {
		this.tueB = tueB;
	}
	public String getWedA() {
		return wedA;
	}
	public void setWedA(String wedA) {
		this.wedA = wedA;
	}
	public String getWedB() {
		return wedB;
	}
	public void setWedB(String wedB) {
		this.wedB = wedB;
	}
	public String getThrA() {
		return thrA;
	}
	public void setThrA(String thrA) {
		this.thrA = thrA;
	}
	public String getThrB() {
		return thrB;
	}
	public void setThrB(String thrB) {
		this.thrB = thrB;
	}
	public String getFriA() {
		return friA;
	}
	public void setFriA(String friA) {
		this.friA = friA;
	}
	public String getFriB() {
		return friB;
	}
	public void setFriB(String friB) {
		this.friB = friB;
	}
	public void setAll(String id,List<String> lesson,String time){
		this.id=id;
		this.monA=lesson.get(0);
		this.tueA=monB=lesson.get(1);
		this.wedA=lesson.get(2);
		this.thrA=lesson.get(3);
		this.friA=lesson.get(4);
		this.monB=lesson.get(5);
		this.tueB=lesson.get(6);
		this.wedB=lesson.get(7);
		this.thrB=lesson.get(8);
		this.friB=lesson.get(9);
		this.time=time;
		
	}
}
