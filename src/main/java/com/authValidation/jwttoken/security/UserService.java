package com.authValidation.jwttoken.security;


import com.authValidation.jwttoken.exception.JwtException;
import com.authValidation.jwttoken.model.entity.Role;
import com.authValidation.jwttoken.model.entity.User;
import com.authValidation.jwttoken.reposatory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null || user.getRole() == null || user.getRole().isEmpty()) {
            throw new JwtException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        }
        Set<Role> roles = user.getRole();
        List<GrantedAuthority> grantedAuthorities = roles.stream().map(r -> {return new SimpleGrantedAuthority(r.getUserRole());
		}).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),grantedAuthorities);
    }


}
