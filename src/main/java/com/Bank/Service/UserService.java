package com.Bank.Service;


import com.Bank.Dto.UserRequest;
import com.Bank.Dto.UserResponse;
import com.Bank.Enum.Role;
import com.Bank.Repository.UserRepository;
import com.Bank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserResponse addUser(UserRequest request) {

     if (userRepo.existsByUsername(request.getUsername())) {
        throw new IllegalArgumentException("Username already exists");
          }
        User user=new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        User saved=userRepo.save(user);

        UserResponse dto=new UserResponse();
        dto.setUsername(saved.getUsername());
        dto.setRole(saved.getRole());
        return dto;
    }

    public UserResponse login(UserRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserResponse dto = new UserResponse();
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }
}
