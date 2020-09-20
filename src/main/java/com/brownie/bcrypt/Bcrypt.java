package com.brownie.bcrypt;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Bcrypt {

    //@NotNull(message="Please enter some text")
    @Pattern(regexp = "^.+$", message="Please enter some text")
    private String data;
    
    private String crypted;
    
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public String getCrypted() {
		return crypted;
	}
	public void setCrypted(String crypted) {
		this.crypted = crypted;
	}



}