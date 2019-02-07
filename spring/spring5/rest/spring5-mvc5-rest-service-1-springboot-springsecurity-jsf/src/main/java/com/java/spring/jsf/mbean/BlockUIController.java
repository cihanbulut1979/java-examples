package com.java.spring.jsf.mbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "blockUIController")  
@ViewScoped  
public class BlockUIController implements Serializable {  
	
	public BlockUIController() {
		System.out.println("");
	}
	
	@PostConstruct
	public void init(){
		System.out.println("");
	}
  
    private static final long serialVersionUID = 20130903L;  
  
    public void doSomething() {  
        try {  
            Thread.sleep(1200);  
        } catch (Exception e) {  
            // ignore  
        }  
    }  
}  