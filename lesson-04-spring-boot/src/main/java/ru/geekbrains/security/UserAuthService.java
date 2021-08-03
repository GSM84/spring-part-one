package ru.geekbrains.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.UserController;
import ru.geekbrains.persist.UserRepository;

import java.util.Collections;
import java.util.stream.Collectors;


@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserAuthService.class);

    @Autowired
    public UserAuthService(UserRepository _userRepository) {
        this.userRepository = _userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String _userName) throws UsernameNotFoundException {
        ru.geekbrains.persist.User usr = userRepository.findByName(_userName).get();

        usr.getRoles().stream().forEach(r -> logger.info("Role name is " + r.getName()));

        return userRepository.findByName(_userName)
                .map(user -> new User(
                        user.getName()
                        , user.getPassword()
                        , user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet())
                )).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
