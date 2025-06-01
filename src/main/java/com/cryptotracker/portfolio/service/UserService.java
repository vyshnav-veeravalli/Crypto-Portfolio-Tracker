package com.cryptotracker.portfolio.service;

import com.cryptotracker.portfolio.DTO.LoginDTO;
import com.cryptotracker.portfolio.DTO.UserDTO;
import com.cryptotracker.portfolio.Exception.EmailAlreadyExistsException;
import com.cryptotracker.portfolio.Exception.EmailEmptyException;
import com.cryptotracker.portfolio.Exception.NameEmptyException;
import com.cryptotracker.portfolio.Exception.PasswordEmptyException;
import com.cryptotracker.portfolio.entity.User;
import com.cryptotracker.portfolio.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
     UserRepo userRepo;

    @Autowired SessionManager sessionManager;


    public User register(UserDTO userDTO){
        if(userRepo.existsByEmail(userDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email already exist");
        }
        if (userDTO.getName() == null || userDTO.getName().trim().isEmpty()) {
            throw new NameEmptyException("Name cannot be empty");
        }
        if(userDTO.getEmail() == null){
            throw new EmailEmptyException("please enter your email");
        }
        if(userDTO.getPassword() == null){
            throw new PasswordEmptyException("please enter your password");
        }


        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole("User");

        return userRepo.save(user);

    }

    public boolean login(LoginDTO loginDTO){
        Optional<User> userOptional = userRepo.findByEmail(loginDTO.getEmail());
        if(userOptional.isEmpty()){
            return false;
        }

        User user = userOptional.get();

        if(loginDTO.getPassword().equals(user.getPassword())){
            sessionManager.login(loginDTO.getEmail());
            return true;
        }
        return false;
    }





}
