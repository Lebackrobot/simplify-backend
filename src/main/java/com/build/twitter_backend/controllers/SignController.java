package com.build.twitter_backend.controllers;

import com.build.twitter_backend.dtos.SigninDto;
import com.build.twitter_backend.dtos.SignupDto;
import com.build.twitter_backend.dtos.TokenDto;
import com.build.twitter_backend.models.UserModel;
import com.build.twitter_backend.services.TokenService;
import com.build.twitter_backend.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/noauth")
public class SignController {

    private static Logger logger = LoggerFactory.getLogger(SignController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/signin")
    public ResponseEntity<TokenDto> signin(@RequestBody @Valid SigninDto signinDto) throws ResponseStatusException {
        var authToken = new UsernamePasswordAuthenticationToken(signinDto.username(), signinDto.password());
        var authentication = authenticationManager.authenticate(authToken);
        var token = tokenService.generateToken((UserModel) authentication.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new TokenDto(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDetails> signup(@RequestBody @Valid SignupDto signupDto) throws ResponseStatusException {
        var user = userService.getByUsername(signupDto.username());

        if (user != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User alredy exists!");
        }

        var newUser = userService.create(new UserModel(
            signupDto.username(),
            passwordEncoder.encode(signupDto.password())
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
