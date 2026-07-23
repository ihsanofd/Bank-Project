package com.Bank.Controller;

import com.Bank.Dto.UserRequest;
import com.Bank.Dto.UserResponse;
import com.Bank.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest request) {
        UserResponse response = userService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/user")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest request){

        UserResponse user=userService.addUser(request);
        return new ResponseEntity<>(user , HttpStatus.CREATED);
    }
}
