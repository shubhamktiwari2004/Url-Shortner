package com.Url_Shortner.SnapURL.Service;

import com.Url_Shortner.SnapURL.DTO.UserResponse;
import com.Url_Shortner.SnapURL.Entity.User;
import com.Url_Shortner.SnapURL.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    public UserResponse loginUser(String email,String password) {

        UserResponse user = userRepository.findByEmail(email)
                .map(usr -> new UserResponse(
                        usr.getId(),
                        usr.getName(),
                        usr.getEmail()
                ))
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return user;



    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->new UsernameNotFoundException("User not found"));

        System.out.println("USER FOUND = " + user.getEmail());

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .authorities("USER")
                .build();
    }

    public String verify(User user){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getEmail());
        }
        return "Fail";
    }
}
