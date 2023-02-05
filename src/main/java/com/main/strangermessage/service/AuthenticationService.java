package com.main.strangermessage.service;

import com.main.strangermessage.controller.AuthenticationResponse;
import com.main.strangermessage.model.Role;
import com.main.strangermessage.model.User;
import com.main.strangermessage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final JwtService jwtService;

    public AuthenticationResponse register() {
        List<User> userList = repository.findByCreatedAtLessThanOrderByIdAsc(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 24));
        User user = null;
        if(userList.size() > 0) {
            user = userList.get(0);
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        } else {
            user = User.builder()
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .role(Role.USER)
                    .build();
        }
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}