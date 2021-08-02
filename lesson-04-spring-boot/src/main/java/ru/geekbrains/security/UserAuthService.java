package ru.geekbrains.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.UserRepository;

import java.util.Collections;


@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;


    @Autowired
    public UserAuthService(UserRepository _userRepository) {
        this.userRepository = _userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String _userName) throws UsernameNotFoundException {
        return userRepository.findByName(_userName)
                .map(user -> new User(
                        user.getName()
                        , user.getPassword()
                        , Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))
                )).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
