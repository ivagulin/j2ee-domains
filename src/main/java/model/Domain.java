package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="dns_zones", 
	uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
@Cacheable
public class Domain implements Serializable {
	private static final long serialVersionUID = -4188406502809926438L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="zone_id")
	private Integer id;
	
	@Column(name="name")
	private String domainName;
	
	@Column(name="mbox")
	private String email;
	
	private Integer expire;
	private Integer refresh;
	private Integer retry;

	@Column(name="min_ttl")
	private Integer minTtl;

	/*
	@OneToMany(mappedBy = "domainId")
	@JsonView(Views.Internal.class)
	private List<DnsRecord> records;
	public List<DnsRecord> getRecords() {
		return records;
	}
	public void setRecords(List<DnsRecord> records) {
		this.records = records;
	}
	*/

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
	public Integer getMinTtl() {
		return minTtl;
	}
	public void setMinTtl(Integer serial) {
		this.minTtl = serial;
	}
}
