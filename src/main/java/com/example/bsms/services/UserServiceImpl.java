package com.example.bsms.services;

import com.example.bsms.exceptions.UserNotFoundException;
import com.example.bsms.models.UserDetails;
import com.example.bsms.models.entities.UserDetailsE;
import com.example.bsms.models.entities.Users;
import com.example.bsms.repositories.UserDetailsRepository;
import com.example.bsms.repositories.UserRepository;
import com.example.bsms.utils.ReflectionUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public void addUserDetails(UserDetails userDetails) throws Exception {
        Optional<UserDetailsE> userDetailsEOpt = userDetailsRepository.findOneByUsername(userDetails.getUsername());

        UserDetailsE userDetailsE;
        userDetailsE = userDetailsEOpt.orElseGet(UserDetailsE::new);

        ReflectionUtils.mapProperties(userDetails, userDetailsE, true);

        userDetailsRepository.save(userDetailsE);
    }

    @Override
    public UserDetails getUserDetails(String username) throws Exception {
        Optional<UserDetailsE> userDetailsEOpt = userDetailsRepository.findOneByUsername(username);

        if(userDetailsEOpt.isEmpty())
            throw new UserNotFoundException();

        UserDetailsE userDetailsE = userDetailsEOpt.get();
        UserDetails userDetails = new UserDetails();
        userDetails.setUsername(username);
        ReflectionUtils.mapProperties(userDetailsE, userDetails, true);
        return userDetails;
    }

    @Override
    public void deleteUser(String username) throws Exception {
        Optional<Users> usersOpt = userRepository.findOneByUserName(username);
        Optional<UserDetailsE> userDetailsEOpt = userDetailsRepository.findOneByUsername(username);

        usersOpt.ifPresent(users -> userRepository.deleteById(users.getId()));
        userDetailsEOpt.ifPresent(userDetails -> userDetailsRepository.deleteById(userDetails.getId()));
    }

    @Override
    public List<UserDetails> getAllUsers() {
        return null;
    }

    @Override
    public void updateRoleOfUser(String username, String role) throws Exception {
        Optional<Users> userOpt = userRepository.findOneByUserName(username);

        if(userOpt.isEmpty())
            throw new UserNotFoundException();

        Users user = new Users();
        ReflectionUtils.mapProperties(userOpt.get(), user, true);
        user.setRole(role);

        userRepository.save(user);
    }
}
