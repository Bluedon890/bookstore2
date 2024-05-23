package stevenlan.bookstore1.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import stevenlan.bookstore1.jwt.model.AuthenticationResponse;
import stevenlan.bookstore1.jwt.service.AuthenticationService;
import stevenlan.bookstore1.user.User;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request){
        
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>login(
            @RequestBody User request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    
}
