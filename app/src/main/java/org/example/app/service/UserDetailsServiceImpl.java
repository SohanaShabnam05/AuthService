package org.example.app.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.el.util.Validation;
import org.example.app.entities.UserInfo;
import org.example.app.model.UserInfoDto;
import org.example.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        log.debug("Entering in loadUserByUsername Method...");
        UserInfo user = userRepository.findByUsername(username);
        if(user == null)
        {
            log.error("Username not found: " + username);
            throw new UsernameNotFoundException("Could not found user...!!");
        }

        log.info("User Authenticated Successfully..!!");
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserAlreadyExist(UserInfoDto userInfoDto)
    {
        return userRepository.findByUsername(userInfoDto.getUsername());
    }

    public Boolean signupUser(UserInfoDto userInfoDto)
    {
        ValidationUtil.validateUserAttributes(userInfoDto);

        userInfoDto.setPassword(passwordEncoder.encode(unserInfoDto.getPassword()));

        if(Objects.nonNull(checkIfUserAlreadyExist(userInfoDto)))
        {
            return false;
        }

        String userId = UUID.randomUUID().toString();
        userRepository.save(new UserInfo(userId, userInfoDto.getUsername(), userInfoDto.getPassword(), new HashSet<>()));

        // pushEventToQueue
        return true;
    }
}
