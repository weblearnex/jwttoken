package com.authValidation.jwttoken.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.authValidation.jwttoken.exception.JwtException;
import com.authValidation.jwttoken.model.entity.Role;
import com.authValidation.jwttoken.model.entity.TokenDetails;
import com.authValidation.jwttoken.model.entity.User;
import com.authValidation.jwttoken.reposatory.JwtTokenRepository;
import com.authValidation.jwttoken.reposatory.UserRepository;
import com.authValidation.jwttoken.security.JwtTokenProvider;
import com.authValidation.jwttoken.service.inf.JwtTokenServiceInf;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class JwtTokenServiceImpl implements JwtTokenServiceInf{

		@Autowired
	    private PasswordEncoder passwordEncoder;
	    @Autowired
	    private JwtTokenProvider jwtTokenProvider;
	    @Autowired
	    private AuthenticationManager authenticationManager;
	    @Autowired
	    private UserRepository userRepository;
	    @Autowired
	    private JwtTokenRepository jwtTokenRepository;

	    @Override
	    public String login(String username, String password) {
	        try {
	            authenticationManager.authenticate(new  UsernamePasswordAuthenticationToken(username,password));
	            User user = userRepository.findByEmail(username);
	            System.out.println(user.getName()+" :::"+user.getRole().size());
	            if (user == null || user.getRole()==null || user.getRole().isEmpty()) {
	                throw new JwtException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
	            }
	            String token =  jwtTokenProvider.createToken(username, user.getRole().stream().map((Role role)-> "ROLE_"+role.getUserRole()).filter(Objects::nonNull).collect(Collectors.toList()));
	            return token;

	        } catch (AuthenticationException e) {
	        	e.printStackTrace();
	            throw new JwtException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
	        }
	    }

	    @Override
	    public User save(User user) {
	        user.setPassword(passwordEncoder.encode(user.getPassword()) );
	        return userRepository.save(user);
	        
	    }

	    @Override
	    public boolean logout(String token) {
	         jwtTokenRepository.delete(new TokenDetails(token));
	         return true;
	    }

	    @Override
	    public Boolean checkTokenIsValid(String token) {
	        return jwtTokenProvider.validateToken(token);
	    }

	    @Override
	    public String createToken(String token) {
	        String name = jwtTokenProvider.getUsername(token);
	        List<String> roleList = jwtTokenProvider.getRoleList(token);
	        String newToken =  jwtTokenProvider.createToken(name,roleList);
	        return newToken;
	    }

		@Override
		public User getUser(String username) {
			 	return userRepository.findByEmail(username);
		}

		@Override
		public User findById(Long id) {
			Optional<User> user= userRepository.findById(id);
			if(user.isPresent())
				return user.get();
			else
				return null;
			
		}

}
