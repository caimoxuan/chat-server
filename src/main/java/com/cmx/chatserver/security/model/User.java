package com.cmx.chatserver.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable{

    private Long id;
    private String username;
    private String password;
    private String salt;
    private Boolean locked = false;

    public User(String username) {
        this.username = username;
    }

    public String getCredentialsSalt(){
        return username + salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}
        if (o == null || getClass() != o.getClass()){ return false;}

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null){ return false;}

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}
