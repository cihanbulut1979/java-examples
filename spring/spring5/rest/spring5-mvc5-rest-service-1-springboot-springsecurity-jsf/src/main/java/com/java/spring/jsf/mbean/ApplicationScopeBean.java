package com.java.spring.jsf.mbean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
@ApplicationScoped
public class ApplicationScopeBean {

   public void preRenderView() {
      FacesContext.getCurrentInstance().getExternalContext().getSession( true );
   }
}