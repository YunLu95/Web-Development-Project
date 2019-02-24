package org.umsl.ylnf2.entity;

public class Admin {
	private String adminusername;
	private String adminpassword;
	public String getAdminusername() {
		return adminusername;
	}
	public void setAdminusername(String adminusername) {
		this.adminusername = adminusername;
	}
	public String getAdminpassword() {
		return adminpassword;
	}
	public void setAdminpassword(String adminpassword) {
		this.adminpassword = adminpassword;
	}
	
	public boolean isAdmin() {
		if(this.adminpassword == null || this.adminusername == null) return false;
		if(this.adminusername.equals("admin") && this.adminpassword.equals("admin")) return true;
		return false;
	}
	@Override
	public String toString() {
		return "Admin [adminusername=" + this.adminusername + ", adminpassword=" + this.adminpassword + "]";
	}
	
	
}
