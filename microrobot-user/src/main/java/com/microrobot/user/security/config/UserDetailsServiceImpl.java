package com.microrobot.user.security.config;

import com.microrobot.user.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.microrobot.user.persistence.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Asegúrate de tener este repositorio

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Usamos email en lugar de username
                user.getPassword(),
                user.getAuthorities() // Asegúrate de que `getAuthorities()` devuelve los roles correctamente
        );
    }
}