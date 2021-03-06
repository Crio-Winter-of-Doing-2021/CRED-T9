package com.crio.cred.service;

import com.crio.cred.dto.LoginDTO;
import com.crio.cred.dto.LoginResponseDTO;
import com.crio.cred.dto.SignUpDTO;
import com.crio.cred.dto.UserDTO;
import com.crio.cred.entity.User;
import com.crio.cred.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * The type User service.
 *
 * @author harikesh.pallantla
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Returns the user based on the email id.
     *
     * @param emailId the email id of the user.
     * @return user details
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        logger.trace("Entered loadUserByUsername");
        User user = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new
                        UsernameNotFoundException(String.format("User with email %s not found.", emailId)));
        logger.trace("Exited loadUserByUsername");
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(emailId)
                .password(user.getPassword())
                .authorities(Collections.emptySet())
                .disabled(user.isActive())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();
    }

    /**
     * Login user optional.
     *
     * @param loginDTO the login dto
     * @return the optional
     */
    public Optional<LoginResponseDTO> loginUser(LoginDTO loginDTO) {
        logger.trace("Entered loginUser");
        loginDTO.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
        Optional<User> optionalUser =
                userRepository.findByEmailIdAndPassword(loginDTO.getEmailId(), loginDTO.getPassword());
        modelMapper.getConfiguration().setFieldMatchingEnabled(false)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setSkipNullEnabled(true);
        logger.trace("Exited loginUser");
        return optionalUser.map(user -> modelMapper.map(user, LoginResponseDTO.class));
    }

    /**
     * Sign up user optional.
     *
     * @param signUpDTO the sign up dto
     * @return the optional
     */
    public Optional<UserDTO> signUpUser(SignUpDTO signUpDTO) {
        logger.trace("Entered signUpUser");
        User user = modelMapper.map(signUpDTO, User.class);
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        User registeredUser = userRepository.save(user);
        UserDTO userDTO = modelMapper.map(registeredUser, UserDTO.class);
        logger.trace("Exited signUpUser");
        return Optional.of(userDTO);
    }

    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     */
    public Optional<UserDTO> getUserById(Long userId) {
        logger.trace("Entered getUserById");
        Optional<User> optionalUser = userRepository.findById(userId);
        logger.trace("Exited getUserById");
        return optionalUser.map(user -> modelMapper.map(user, UserDTO.class));
    }

    /**
     * Checks if user exists or not.
     *
     * @param emailId the email id
     * @return the boolean
     */
    public boolean isUserExists(String emailId) {
        return userRepository.findByEmailId(emailId).isPresent();
    }

}
