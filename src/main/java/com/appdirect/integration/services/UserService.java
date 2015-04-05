package com.appdirect.integration.services;

import com.appdirect.integration.models.User;
import com.appdirect.integration.repositories.UserRepository;
import com.appdirect.integration.utils.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private IdGenerator idGenerator;

    @Autowired
    public UserService(UserRepository userRepository, IdGenerator idGenerator) {
        this.userRepository = userRepository;
        this.idGenerator = idGenerator;
    }

    public String save(User user) {
        user.setId(idGenerator.generateId());
        userRepository.save(user);
        return user.getId();
    }

    public List<User> getUsers() {
        return newArrayList(userRepository.findAll());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user with openId {}", username);
        return userRepository.findByOpenId(username);
    }
}
