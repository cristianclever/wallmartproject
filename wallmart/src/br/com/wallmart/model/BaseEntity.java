package br.com.wallmart.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@javax.persistence.MappedSuperclass
public class BaseEntity implements Serializable{
	
	protected static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}	
	
	
	
	@Version
	@Column(name="version",nullable=false)
	private Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	

}
