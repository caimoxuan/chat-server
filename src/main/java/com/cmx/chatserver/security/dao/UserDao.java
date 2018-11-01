package com.cmx.chatserver.security.dao;

import com.cmx.chatserver.security.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    User query(User user);

}
