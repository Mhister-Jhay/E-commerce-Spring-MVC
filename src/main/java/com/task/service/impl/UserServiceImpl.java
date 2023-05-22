package com.task.service.impl;

import com.task.dto.StoreUsersDTO;
import com.task.model.User;
import com.task.repository.UserRepository;
import com.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public boolean getUserRegistered(StoreUsersDTO storeUsersDTO) {
        boolean isRegistered = false;
        User user = User.builder()
                .firstName(storeUsersDTO.getFirstName())
                .lastName(storeUsersDTO.getLastName())
                .email(storeUsersDTO.getEmail())
                .phoneNumber(storeUsersDTO.getPhoneNumber())
                .password(storeUsersDTO.getPassword())
                .build();
        if(findUserByEmail(user.getEmail())== null){
            userRepository.save(user);
            isRegistered = true;
        }
        return isRegistered;
    }

    @Override
    public boolean getUserLoggedIn(StoreUsersDTO storeUsersDTO){
        boolean isLoggedIn = false;
        User user = findUserByEmail(storeUsersDTO.getEmail());
        if(user != null && user.getPassword().equals(storeUsersDTO.getPassword())){
            isLoggedIn = true;
        }
        return isLoggedIn;
    }

    @Override
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }
}
