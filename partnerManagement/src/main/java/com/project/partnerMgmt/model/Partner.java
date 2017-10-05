package com.project.partnerMgmt.model;

import org.springframework.data.annotation.Id;
public class Partner {
	@Id
	private long id;
	
	private String partnerName;
	
	private String address;
	
	private String email;
	
	public Partner(){
		id=0;
	}
	
	public Partner(long id, String partnerName, String address, String email){
		this.id = id;
		this.partnerName= partnerName;
		this.address = address;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName= partnerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Partner))
			return false;
		Partner other = (Partner) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Partner [id=" + id + ", name=" + partnerName+ ", address=" + address
				+ ", email=" + email + "]";
	}
	

	
}
