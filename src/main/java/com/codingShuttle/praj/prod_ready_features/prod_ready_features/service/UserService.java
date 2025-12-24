package com.codingShuttle.praj.prod_ready_features.prod_ready_features.service;

import com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto.SignUpDTO;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto.UserDto;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.User;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.exception.ResourceNotFoundException;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    //This is the service layer where all the logic will be written..
    private final UserRepository userRepository;
    private ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with email "+username+" not found"));
    }

    //@Override
    public User getUsrByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    //User service is used to create a new user and then store it in the DB, so that later we can authenticate it .
    public UserDto signUp(SignUpDTO signUpDto) {
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
        if(user.isPresent()) {
            throw new BadCredentialsException("User with email already exits "+ signUpDto.getEmail());
        }

        User toBeCreatedUser = modelMapper.map(signUpDto, User.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword())); //Getting the password and encoding it .

        User savedUser = userRepository.save(toBeCreatedUser);
        return modelMapper.map(savedUser, UserDto.class);
    }


    //@Override
    public User getUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id with "+id+" not found"));
    }


    public User saveUser(User newUser){
        return userRepository.save(newUser);
    }




}
