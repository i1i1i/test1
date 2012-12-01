package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "work")
public class BussinesProcess {
	
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "term")
	private int bpTerm;

	@Column(name = "name")
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBpTerm() {
		return bpTerm;
	}

	public void setBpTerm(int bpTerm) {
		this.bpTerm = bpTerm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
