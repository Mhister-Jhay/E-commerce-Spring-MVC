package com.task.service;

import com.task.dto.StoreUsersDTO;
import com.task.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    boolean getUserRegistered(StoreUsersDTO storeUsersDTO);
    boolean getUserLoggedIn(StoreUsersDTO storeUserDTO);
    User findUserByEmail(String email);

}
