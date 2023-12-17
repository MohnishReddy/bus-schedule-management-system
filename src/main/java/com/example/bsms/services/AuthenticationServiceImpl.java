package com.example.bsms.services;

import com.example.bsms.constants.RoleConstants;
import com.example.bsms.exceptions.UserNotFoundException;
import com.example.bsms.exceptions.UsernameAlreadyExistsException;
import com.example.bsms.models.entities.Users;
import com.example.bsms.repositories.UserRepository;
import com.example.bsms.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl extends JwtAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String handleLogin(@NonNull String username, @NonNull String password) throws Exception {
        String hashPassword = hashPassword(password);
        Optional<Users> userOpt = userRepository.findByUserNameAndHashedPassword(username, hashPassword);
        if(userOpt.isEmpty())
            throw new UserNotFoundException();

        return createAuthorizationToken(username);
    }

    @Override
    public String handleRegistration(@NonNull String username, @NonNull String password) throws Exception {
        String hashedPassword = hashPassword(password);

        Users user = new Users();
        user.setUserName(username);
        user.setHashedPassword(hashedPassword);
        user.setRole(RoleConstants.USER);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UsernameAlreadyExistsException();
        }

        return createAuthorizationToken(username);
    }

    @Override
    public String getRoleFromUsername(String username) throws Exception {
        Optional<Users> userOpt = userRepository.findOneByUserName(username);
        if(userOpt.isEmpty())
            throw new UserNotFoundException();
        return userOpt.get().getRole();
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        return SecurityUtils.hashString("|||"+password+"|||");
    }
}
