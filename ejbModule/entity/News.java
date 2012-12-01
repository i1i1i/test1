package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;

@Entity
public class News {

	@Id
	@Column(name = "start")
	private Date start;

	@Column(name = "planed_term")
	private Date term;

	@Column(name = "stop")
	private Date end;

	@Column(name = "planed")
	private Date planed;

	@Column(name = "done")
	private Date done;

	@Column(name = "bp")
	private String process;

	@Column(name = "name")
	private String employee;

	@Column(name = "term")
	private Integer bpTerm;

	@Column(name = "comment")
	private String comment;

	public Integer getBpTerm() {
		return bpTerm;
	}

	public void setBpTerm(Integer bpTerm) {
		this.bpTerm = bpTerm;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getTerm() {
		return term;
	}

	public void setTerm(Date term) {
		this.term = term;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public Date getPlaned() {
		return planed;
	}

	public void setPlaned(Date planed) {
		this.planed = planed;
	}

	public Date getDone() {
		return done;
	}

	public void setDone(Date done) {
		this.done = done;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public float getDuration() {
		float hours = (done == null || planed.after(done)) ? 0 : Minutes.minutesBetween(new DateTime(planed),
				new DateTime(done)).getMinutes() / 60; 
		return hours;
	}

	public float getVariance() {
		float hours = done == null ? 0 : Minutes.minutesBetween(new DateTime(term),
				new DateTime(done)).getMinutes() / 60; 
		return hours;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
