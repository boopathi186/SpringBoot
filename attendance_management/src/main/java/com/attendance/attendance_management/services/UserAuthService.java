package com.attendance.attendance_management.services;

import com.attendance.attendance_management.repository.UserAuthRepository;
import com.attendance.attendance_management.table.UserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {
    private final UserAuthRepository userAuthRepository;

    public String registerUser(UserAuth user) {
        userAuthRepository.save(user);
        return "Success";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserAuth userAuth = userAuthRepository.findByUserName(username);
       if(userAuth==null) {
           throw new UsernameNotFoundException("User Not Found");
       }
       return new UserAuth(userAuth);
    }

    public List<UserAuth> getRegisterUser() {
       return userAuthRepository.findAll();
    }
}
