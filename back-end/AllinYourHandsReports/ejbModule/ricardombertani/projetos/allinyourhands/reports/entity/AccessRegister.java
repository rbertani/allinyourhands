package ricardombertani.projetos.allinyourhands.reports.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ACCESS_REGISTER")
public class AccessRegister {

	@Id	
	@Column(name = "REGISTER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "REGISTER_NAME", nullable=false)
	private String name;
	
	@Column(name = "REGISTER_TYPE", nullable=false)
	private String type;
	
	@Column(name = "REGISTER_ARTIST", nullable=false)
	private String artist;
	
	@Column(name = "REGISTER_EXTRA_INFO", nullable=false)
	private int extraInformation;
	
	@Column(name = "REGISTER_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public AccessRegister() {
		
		this.id = null;
		this.name = "";
		this.type = "";
		this.artist = "";
		this.extraInformation = 0;
		this.date = null;
	}
	
	public AccessRegister(Long id, String name, String type, String artist,
			int extraInformation, String date) {
		
		this.id = id;
		this.name = name;
		this.type = type;
		this.artist = artist;
		this.extraInformation = extraInformation;
		this.date = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public int getExtraInformation() {
		return extraInformation;
	}

	public void setExtraInformation(int extraInformation) {
		this.extraInformation = extraInformation;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
