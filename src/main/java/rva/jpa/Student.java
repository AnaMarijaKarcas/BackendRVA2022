package rva.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the student database table.
 * 
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@NamedQuery(name="Student.findAll", query="SELECT s FROM Student s")
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STUDENT_ID_GENERATOR", sequenceName="STUDENT_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STUDENT_ID_GENERATOR")
	private Integer id;

	@Column(name="broj_indeksa")
	private String broj_indeksa;

	private String ime;

	private String prezime;

	//bi-directional many-to-one association to Departman
	
	@ManyToOne
	@JoinColumn(name="departman")
	private Departman departman;

	//bi-directional many-to-one association to Status
	
	@ManyToOne
	@JoinColumn(name="status")
	private Status status;

	public Student() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrojIndeksa() {
		return this.broj_indeksa;
	}

	public void setBrojIndeksa(String brojIndeksa) {
		this.broj_indeksa = brojIndeksa;
	}

	public String getIme() {
		return this.ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return this.prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Departman getDepartman() {
		return this.departman;
	}

	public void setDepartman(Departman departman) {
		this.departman = departman;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}