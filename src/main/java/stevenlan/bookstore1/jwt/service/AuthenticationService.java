package stevenlan.bookstore1.jwt.service;

import stevenlan.bookstore1.jwt.model.AuthenticationResponse;
import stevenlan.bookstore1.jwt.model.Token;
import stevenlan.bookstore1.jwt.repository.TokenRepository;
import stevenlan.bookstore1.user.User;
import stevenlan.bookstore1.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    

   

    public AuthenticationService(UserRepository repository, 
                                PasswordEncoder passwordEncoder, 
                                JwtService jwtService,
                                AuthenticationManager authenticationManager, 
                                TokenRepository tokenRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    public AuthenticationResponse register(User request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        user.setRole(request.getRole());

        user = repository.save(user);

        String jwt = jwtService.generateToken(user);

        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt);

    }
    public AuthenticationResponse authenticate(User request) {
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getUsername(),
                       request.getPassword()
               )
       );

       User user = repository.findByUsername(request.getUsername()).orElseThrow();
       String token = jwtService.generateToken(user);
       saveUserToken(token, user);

       return new AuthenticationResponse(token);
    }
    
    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedout(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
