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

	public String getStudentNo() {
		return student_no;
	}
	public void setStudentNo(String student_no) {
		this.student_no = student_no;
	}
	public String getSubjectCd() {
		return subject_cd;
	}
	public void setSubjectCd(String subject_cd) {
		this.subject_cd = subject_cd;
	}
	public String getSchoolCd() {
		return school_cd;
	}
	public void setSchoolCd(String school_cd) {
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
	public String getClassNum() {
		return class_num;
	}
	public void setClassNum(String class_num) {
		this.class_num = class_num;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
}
