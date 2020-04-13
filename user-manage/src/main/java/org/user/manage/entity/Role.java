package org.user.manage.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "t_role")
public class Role implements java.io.Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="created",columnDefinition="timestamp default current_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@ManyToMany(cascade= {},fetch=FetchType.EAGER)
    @JoinTable(name = "user_resource",joinColumns = @JoinColumn(name = "role_id"),inverseJoinColumns = @JoinColumn(name = "resource_id"))
	private List<Resource> resources;
					
	public Role() {
		super();
	}
		
	public Role( String name, Date created, List<Resource> resources) {
		super();
		this.name = name;
		this.created = created;
		this.resources = resources;
	}



	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
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
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	
}
