package br.com.drianodev.payment.system.controller;

import br.com.drianodev.payment.system.dto.UserCreateRequest;
import br.com.drianodev.payment.system.dto.UserResponse;
import br.com.drianodev.payment.system.entity.User;
import br.com.drianodev.payment.system.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserCreateRequest userCreateRequest) throws MessagingException, UnsupportedEncodingException {
        User user = userCreateRequest.toModel();
        UserResponse userSaved = userService.registerUser(user);
        return ResponseEntity.ok().body(userSaved);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code){

        if(userService.verify(code)){
            return "verify_success";
        }

        return "verify_fail";
    }

    @GetMapping("/teste")
    public String teste(){
        return "você está logado";
    }
}
