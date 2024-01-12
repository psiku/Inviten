package com.inviten.api.authorization.controller;


import com.inviten.api.authorization.auth.JwtUtil;
import com.inviten.api.authorization.converter.TimeConverter;
import com.inviten.api.authorization.hashing.PhoneHash;
import com.inviten.api.authorization.model.request.LoginReq;
import com.inviten.api.authorization.model.response.ErrorRes;
import com.inviten.api.authorization.model.response.LoginRes;
import com.inviten.api.features.meetings.Meeting;
import com.inviten.api.features.users.IUserMeetingsRepository;
import com.inviten.api.features.users.User;
import com.inviten.api.features.users.UserMeetingsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


@Controller
@RequestMapping("/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final IUserMeetingsRepository userRepository;


    private JwtUtil jwtUtil;
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, IUserMeetingsRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;

    }


    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody LoginReq loginReq)  {

        PhoneHash phoneHash = new PhoneHash();
        TimeConverter timeConverter = new TimeConverter();

        try {
            // pobierz LogReq
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getPhoneNumber(), loginReq.getPassword()));
            String phoneNumber = authentication.getName(); // pobiera numer telefonu
            String newPhoneNumber = phoneHash.hashPhoneNumber(phoneNumber);
            // sprawdź czy istnieje jak nie to stwórz jak nie pobierz z bazy ! ! !
            User user = userRepository.findUserByID(newPhoneNumber);
            userRepository.create(user);
            String token = jwtUtil.createToken(user);

            // parser tokenu
            Claims claims = Jwts.parser()
                    .setSigningKey("mysecretkey")
                    .parseClaimsJws(token)
                    .getBody();

            long expirationTimestamp = claims.getExpiration().getTime();

            String expirationTimestampAsString = timeConverter.convertTimestampToIso8601(expirationTimestamp);

            LoginRes loginRes = new LoginRes(newPhoneNumber,token, expirationTimestampAsString);

            loginRes.setTokenValidity(expirationTimestampAsString);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}