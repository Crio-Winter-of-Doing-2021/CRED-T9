package com.crio.cred.controller;

import com.crio.cred.configuration.SpringFoxConfig;
import com.crio.cred.dto.LoginDTO;
import com.crio.cred.dto.LoginResponseDTO;
import com.crio.cred.dto.SignUpDTO;
import com.crio.cred.dto.UserDTO;
import com.crio.cred.security.JwtProvider;
import com.crio.cred.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;


/**
 * The type User controller.
 *
 * @author harikesh.pallantla
 */
@RestController
@Validated
@Slf4j
@CrossOrigin
@Api(tags = SpringFoxConfig.USER_TAG)
@SwaggerDefinition(
        info = @Info(description = "User operations.", title = "User Controller", version = "1.0")
)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    /**
     * Logs in the user.
     *
     * @param loginDTO the login dto
     * @return the response entity
     */
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Successfully authenticated", response = LoginResponseDTO.class),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Incorrect credentials")
    })
    @ApiOperation(value = "Logs in the user with the given credentials.", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        logger.trace("Entered login");
        Optional<LoginResponseDTO> loginResponseDTOOptional = userService.loginUser(loginDTO);
        if (loginResponseDTOOptional.isPresent()) {
            final LoginResponseDTO loginResponseDTO = loginResponseDTOOptional.get();
            String jwtToken = jwtProvider.generateJwtToken(loginDTO.getEmailId());
            loginResponseDTO.setToken(jwtToken);
            loginResponseDTO.setTokenType("Bearer");
            logger.trace("Exited login");
            return ResponseEntity.ok(loginResponseDTO);
        }
        logger.trace("Exited login");
        return ResponseEntity.notFound().build();
    }

    /**
     * Signs up the user.
     *
     * @param signUpDTO the sign up dto
     * @return the response entity
     */
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "User created successfully.", response = UserDTO.class),
            @ApiResponse(code = HttpServletResponse.SC_CONFLICT, message = "Email id already exists")
    })
    @ApiOperation(value = "Registers the user with the given details.", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> signUp(@Valid @RequestBody SignUpDTO signUpDTO) {
        logger.trace("Entered signUp");
        if (userService.isUserExists(signUpDTO.getEmailId()))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        Optional<UserDTO> signUpResponseDTO = userService.signUpUser(signUpDTO);
        if (signUpResponseDTO.isPresent()) {
            UserDTO user = signUpResponseDTO.get();
            String jwtToken = jwtProvider.generateJwtToken(user.getEmailId());
            user.setToken(jwtToken);
            user.setTokenType("Bearer");
            logger.trace("Exited signUp");
            return ResponseEntity.created(URI.create("/user/" + user.getUserId())).body(user);
        }
        logger.trace("Exited signUp");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user details if found
     */
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Found the user details", response = UserDTO.class),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "User with given user id not found.")
    })
    @ApiOperation(value = "Gets the user details by user id.",
            produces = MediaType.APPLICATION_JSON_VALUE, response = UserDTO.class,
            authorizations = {@Authorization("JWT")})
    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "userId") Long userId) {
        logger.trace("Entered getUserById");
        Optional<UserDTO> userById = userService.getUserById(userId);
        logger.trace("Exited getUserById");
        return ResponseEntity.of(userById);
    }

}