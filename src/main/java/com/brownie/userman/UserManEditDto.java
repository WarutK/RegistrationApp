package com.brownie.userman;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.brownie.constraint.FieldMatch;

public class UserManEditDto {
	
    private Long id;

	@Size(min = 3, max = 30, message = "Your username must between 3 and 30 characters")
    @NotEmpty
    private String userName;
	
    private String firstName;

    private String lastName;

    @Email
    private String email;
    
    private Boolean roleAdministrator;
    private Boolean roleOperator;
    private Boolean roleUser;
    
    private Boolean enabled;
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public Boolean getRoleAdministrator() {
		return roleAdministrator;
	}

	public void setRoleAdministrator(Boolean roleAdministrator) {
		this.roleAdministrator = roleAdministrator;
	}

	public Boolean getRoleOperator() {
		return roleOperator;
	}

	public void setRoleOperator(Boolean roleOperator) {
		this.roleOperator = roleOperator;
	}

	public Boolean getRoleUser() {
		return roleUser;
	}

	public void setRoleUser(Boolean roleUser) {
		this.roleUser = roleUser;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}



}