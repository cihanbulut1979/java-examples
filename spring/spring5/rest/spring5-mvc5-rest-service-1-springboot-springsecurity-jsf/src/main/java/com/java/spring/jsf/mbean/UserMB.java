package com.java.spring.jsf.mbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@SuppressWarnings("serial")
@ManagedBean(name = "userMB")
public class UserMB implements Serializable {

	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		try {
			
			String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			
			FacesContext.getCurrentInstance().getExternalContext().redirect(path + "/logout");
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
