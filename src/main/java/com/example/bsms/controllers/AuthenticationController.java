package com.example.bsms.controllers;

import com.example.bsms.exceptions.UserNotFoundException;
import com.example.bsms.exceptions.UsernameAlreadyExistsException;
import com.example.bsms.models.requests.LoginReq;
import com.example.bsms.models.requests.RegisterUserReq;
import com.example.bsms.models.responses.BasicResponse;
import com.example.bsms.models.responses.LoginRes;
import com.example.bsms.services.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.bsms.constants.AuthConstants.AUTH_PREFIX;

@RestController()
@AllArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(path = "/register", consumes = "application/json")
    public @ResponseBody ResponseEntity<?> register(@RequestBody RegisterUserReq registerReq) {
        if( !registerReq.getPassword().equals(registerReq.getRePassword()) )
            return new ResponseEntity<BasicResponse>(new BasicResponse() {{
                setErrMessage("The entered passwords do not match");
            }}, HttpStatus.BAD_REQUEST);

        String authToken;
        try {
            authToken = authenticationService.handleRegistration(registerReq.getUsername(), registerReq.getPassword());
        } catch (UsernameAlreadyExistsException ue) {
            return new ResponseEntity<BasicResponse>(new BasicResponse() {{
                setErrMessage(ue.ErrorMessage);
            }}, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<BasicResponse>(new BasicResponse() {{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<LoginRes>(new LoginRes() {{
            setAuthPrefix(AUTH_PREFIX);
            setAuthToken(authToken);
        }}, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login", consumes = "application/json")
    public @ResponseBody ResponseEntity<?> login(@RequestBody LoginReq loginReq) {
        if(loginReq.getUserName() == null || loginReq.getPassword() == null)
            return new ResponseEntity<BasicResponse>(new BasicResponse(){{
                setErrMessage("Username/Password cannot be null");
            }}, HttpStatus.BAD_REQUEST);

        String authToken;
        try {
            authToken = authenticationService.handleLogin(loginReq.getUserName(), loginReq.getPassword());
        } catch (UserNotFoundException e) {
            return new ResponseEntity<BasicResponse>(new BasicResponse(){{
                setErrMessage("Username/Password provided is incorrect!");
            }}, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<BasicResponse>(new BasicResponse(){{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<LoginRes>(new LoginRes() {{
            setAuthPrefix(AUTH_PREFIX);
            setAuthToken(authToken);
        }}, HttpStatus.OK);

    }

}
