package com.java.application.rabbitmq;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TestBean implements Serializable{
	private String messageId;
	private int priority;

	public TestBean() {

	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public byte[] getBytes() {
        byte[]bytes;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.flush();
            oos.reset();
            bytes = baos.toByteArray();
            oos.close();
            baos.close();
        } catch(IOException e){
            bytes = new byte[] {};
            e.printStackTrace();
        }
        return bytes;
    }

    public static TestBean fromBytes(byte[] body) {
    	TestBean obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(body);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = (TestBean) ois.readObject();
            ois.close();
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

	@Override
	public String toString() {
		return "TestBean [messageId=" + messageId + ", priority=" + priority + "]";
	}
    
    

}
