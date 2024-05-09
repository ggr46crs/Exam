package bean;

import java.io.Serializable;

public class Test implements Serializable {
	private String student_no;
	private String subject_cd;
	private String school_cd;
	private Integer no;
	private Integer point;
	private String class_num;
	private School school;

	public String getStudent_no() {
		return student_no;
	}
	public void setStudent_no(String student_no) {
		this.student_no = student_no;
	}
	public String getSubject_cd() {
		return subject_cd;
	}
	public void setSubject_cd(String subject_cd) {
		this.subject_cd = subject_cd;
	}
	public String getSchool_cd() {
		return school_cd;
	}
	public void setSchool_cd(String school_cd) {
		this.school_cd = school_cd;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public String getClass_num() {
		return class_num;
	}
	public void setClass_num(String class_num) {
		this.class_num = class_num;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
}
