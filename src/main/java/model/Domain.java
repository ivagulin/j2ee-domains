package model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="dns_zones", 
	uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
@Cacheable
public class Domain implements Serializable {
	private static final long serialVersionUID = -4188406502809926438L;

	@Id
	@SequenceGenerator(name="domains_seq", sequenceName="domains_serial_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="domains_seq")
	@Column(name="zone_id")
	private Integer id;
	
	@Column(name="name")
	private String domainName;
	
	@Column(name="mbox")
	private String email;
	
	@Column(name="default_ttl")
	private String ttl;
	
	private Integer expire;
	private Integer refresh;
	private Integer retry;
	private Integer serial;
	
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String name) {
		this.domainName = name;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTtl() {
		return ttl;
	}
	public void setTtl(String ttl) {
		this.ttl = ttl;
	}
	public Integer getExpire() {
		return expire;
	}
	public void setExpire(Integer expire) {
		this.expire = expire;
	}
	public Integer getRefresh() {
		return refresh;
	}
	public void setRefresh(Integer refresh) {
		this.refresh = refresh;
	}
	public Integer getRetry() {
		return retry;
	}
	public void setRetry(Integer retry) {
		this.retry = retry;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
}
