package com.itwill.post.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.post.model.User;
import com.itwill.post.repository.UserDao;

public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    
    private final UserDao userDao = UserDao.getInstance(); // repository 
    
    private static UserService instance = null; // service 
    
    private UserService() {}
    
    public static UserService getInstance() {
        if(instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    
    // sing-in: 로그인, sign-up: 회원 가입.
    
    // username과 password가 일치하면 null이 아닌 User 객체, 일치하지 않으면 null을 리턴.
    public User signIn(String username, String password) {
        log.info("signIn(username = {}, password = {}", username, password);
        
        User test = new User(0, username, password, null, 0); // model 
        User result = userDao.selectByUsernameAndPassword(test);  // repository 
        
        return result;
    }
    
}
