package stevenlan.bookstore1.jwt.service;

import stevenlan.bookstore1.jwt.model.AuthenticationResponse;
import stevenlan.bookstore1.jwt.model.Token;
import stevenlan.bookstore1.jwt.repository.TokenRepository;
import stevenlan.bookstore1.user.User;
import stevenlan.bookstore1.user.UserRepository;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    

   

    public AuthenticationService(UserRepository userRepository, 
                                PasswordEncoder passwordEncoder, 
                                JwtService jwtService,
                                AuthenticationManager authenticationManager, 
                                TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    //註冊
    public AuthenticationResponse register(User request) {

        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            return new AuthenticationResponse(null, "此帳號已存在");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        user.setRole(request.getRole());

        user = userRepository.save(user);

        String jwt = jwtService.generateToken(user);

        

        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "註冊成功");

    }

    //登入
    public AuthenticationResponse authenticate(User request) {
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getUsername(),
                       request.getPassword()
               )
       );

       User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
       String token = jwtService.generateToken(user);

       setAllOldTokenLoggedout(user);

       saveUserToken(token, user);

       return new AuthenticationResponse(token, "登入成功");
    }
    
    //存token
    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedout(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    //在產生新token前將舊的設定為loggedout
    private void setAllOldTokenLoggedout(User user) {
        List<Token> validTokensList = tokenRepository.findAllTokenByUser(user.getId());
        if(!validTokensList.isEmpty()){
            validTokensList.forEach(t->{
                t.setLoggedout(true);
            });
        }
        tokenRepository.saveAll(validTokensList);
    }
}
