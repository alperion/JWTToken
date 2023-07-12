package com.example.demo.auth;


import com.example.demo.config.JwtService;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {

        HashMap<String,String >data=new HashMap<String,String>();
        List<User>names=repository.findAll();
        names.forEach(e->{data.put(e.getEmail(),"");});

        if(data.containsKey(request.getEmail())){
           return null;
        }

      else {


            var user = User.builder()
//                .firstname(request.getFirstname())
//                .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).build();

        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
       );
       var user=repository.findByEmail(request.getEmail()).orElseThrow();
        HashMap claimssetter=new HashMap();
        claimssetter.put("Role",user.getRole());
        var jwtToken=jwtService.generateToken(claimssetter,user);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }
}
