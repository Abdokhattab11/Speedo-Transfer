package com.example.speedotansfer.contoller;

import com.example.speedotansfer.dto.authDTOs.LoginRequestDTO;
import com.example.speedotansfer.dto.authDTOs.LoginResponseDTO;
import com.example.speedotansfer.dto.authDTOs.RegisterDTO;
import com.example.speedotansfer.dto.authDTOs.RegisterReponseDTO;
import com.example.speedotansfer.dto.userDTOs.UserDTO;
import com.example.speedotansfer.exception.custom.InvalidJwtTokenException;
import com.example.speedotansfer.exception.custom.PasswordNotMatchException;
import com.example.speedotansfer.exception.custom.UserAlreadyExistsException;
import com.example.speedotansfer.exception.custom.UserNotFoundException;
import com.example.speedotansfer.service.impl.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register new user")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = RegisterDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = UserAlreadyExistsException.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = PasswordNotMatchException.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ConstraintViolationException.class), mediaType = "application/json")})
    @PostMapping("/register")
    public RegisterReponseDTO register(@RequestBody @Valid RegisterDTO registerDTO)
            throws UserAlreadyExistsException, PasswordNotMatchException, ConstraintViolationException {
        return authService.register(registerDTO);
    }

    @Operation(summary = "Login user and generate JWT")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = LoginResponseDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = AuthenticationException.class), mediaType = "application/json")})
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO)
            throws AuthenticationException {
        return authService.login(loginRequestDTO);
    }

}
