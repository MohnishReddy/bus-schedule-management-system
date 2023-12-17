package com.example.bsms.controllers;

import com.example.bsms.annotations.ForAdminOnly;
import com.example.bsms.exceptions.UserNotFoundException;
import com.example.bsms.models.UserDetails;
import com.example.bsms.models.entities.UserDetailsE;
import com.example.bsms.models.requests.AddUserDetailsReq;
import com.example.bsms.models.responses.BasicResponse;
import com.example.bsms.models.responses.UserDetailsRes;
import com.example.bsms.repositories.UserDetailsRepository;
import com.example.bsms.services.UserService;
import com.example.bsms.utils.ReflectionUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.example.bsms.constants.AuthConstants.CONTEXT_ROLE_KEY;
import static com.example.bsms.constants.AuthConstants.CONTEXT_USERNAME_KEY;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserDetailsController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getSelfDetails(HttpServletRequest request) {
        String username = (String) request.getAttribute(CONTEXT_USERNAME_KEY);
        try {
            UserDetails userDetails = userService.getUserDetails(username);
            UserDetailsRes userDetailsRes = new UserDetailsRes();
            ReflectionUtils.mapProperties(userDetails, userDetailsRes, true);
            userDetailsRes.setRole((String) request.getAttribute(CONTEXT_ROLE_KEY));
            return new ResponseEntity<>(userDetailsRes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updateSelfDetails(HttpServletRequest request, @RequestBody AddUserDetailsReq addUserDetailsReq) {
        String username = (String) request.getAttribute(CONTEXT_USERNAME_KEY);
        try {
            UserDetails userDetails = new UserDetails();
            userDetails.setUsername(username);
            userDetails.setName(addUserDetailsReq.getName());
            userDetails.setPhoneNumber(addUserDetailsReq.getPhoneNumber());
            userDetails.setEmail(addUserDetailsReq.getEmail());
            userService.addUserDetails(userDetails);
            return new ResponseEntity<>(new BasicResponse() {{
                setMessage("User details added");
            }}, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage("Unable to add user details : "+e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ForAdminOnly
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserDetailsFromUsername(HttpServletRequest request, @PathVariable String username) {
        try {
            UserDetails userDetails = userService.getUserDetails(username);
            UserDetailsRes userDetailsRes = new UserDetailsRes();
            ReflectionUtils.mapProperties(userDetails, userDetailsRes, true);
            userDetailsRes.setRole((String) request.getAttribute(CONTEXT_ROLE_KEY));
            return new ResponseEntity<>(userDetailsRes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteSelfAccount(HttpServletRequest request) {
        String username = (String) request.getAttribute(CONTEXT_USERNAME_KEY);
        try {
            userService.deleteUser(username);
            return new ResponseEntity<>(new BasicResponse() {{
                setMessage("User account deleted successfully");
            }}, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage("Cannot delete account : "+e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ForAdminOnly
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUserAccount(HttpServletRequest request, @PathVariable String username) {
        try {
            userService.deleteUser(username);
            return new ResponseEntity<>(new BasicResponse() {{
                setMessage("User account deleted successfully");
            }}, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage("Cannot delete account : "+e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ForAdminOnly
    @PutMapping("/{username}/update-role/{role}")
    public ResponseEntity<?> updateRoleOfUser(HttpServletRequest request, @PathVariable String username, @PathVariable String role) {
        // Don't allow self update
        String loggedInUsername = (String) request.getAttribute(CONTEXT_USERNAME_KEY);
        if(loggedInUsername.equals(username))
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage("Admin cannot update his own role");
            }}, HttpStatus.BAD_REQUEST);

        try {
            userService.updateRoleOfUser(username, role);
            return new ResponseEntity<>(new BasicResponse() {{
                setMessage("Role for "+username+" has been updated!");
            }}, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
