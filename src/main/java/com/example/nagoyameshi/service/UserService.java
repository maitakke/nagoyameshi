package com.example.nagoyameshi.service;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
    public User createUser(SignupForm signupForm) {
        User user = new User();
        Role role = roleRepository.findByName("ROLE_FREE_MEMBER");

        user.setName(signupForm.getName());
        user.setFurigana(signupForm.getFurigana());
        user.setPostalCode(signupForm.getPostalCode());
        user.setAddress(signupForm.getAddress());
        user.setPhoneNumber(signupForm.getPhoneNumber());
        user.setEmail(signupForm.getEmail());
        user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
        user.setRole(role);
        user.setEnabled(true);
        
        String birthdayString = signupForm.getBirthday();
        
        if (birthdayString != null && !birthdayString.isEmpty()) {
            // "yyyyMMdd" の形式（例: "19950101"）をLocalDateに変換する設定
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate birthday = LocalDate.parse(birthdayString, formatter);
            user.setBirthday(birthday);
        } else {
            user.setBirthday(null);
        }
        
        String occupationString = signupForm.getOccupation();
        if (occupationString != null && !occupationString.isEmpty()) {
        		user.setOccupation(signupForm.getOccupation());
        }else {
        		user.setOccupation(null);
        }

        return userRepository.save(user);
	}
	
    public boolean isEmailRegistered(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    } 
    
    public boolean isSamePassword(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }
    
}
