package model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="dns_resource_records")
@Cacheable
public class DnsRecord implements Serializable {
	private static final long serialVersionUID = 8149445530227201829L;

	@Id
	@SequenceGenerator(name = "records_seq", sequenceName = "dns_resource_records_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "records_seq")
	@Column(name="rr_id")
	private Integer id;
	
	@Column(name="domain_id")
	private Integer domainId;
	
	@Column(name="host")
	private String host;

	@Column(name="data")
	private String data;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getDomainId() {
		return domainId;
	}

	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
	}

}
