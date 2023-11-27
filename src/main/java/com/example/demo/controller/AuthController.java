package com.example.demo.controller;

import com.example.demo.dto.ErrorResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserDetailsImpl;
import com.example.demo.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AuthController {

  private final UserService userService;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public AuthController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userService = userService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @PostMapping("/login")
  @Operation(summary = "User Login")
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "200", description = "Login Successful"),
      @ApiResponse(responseCode = "401", description = "Invalid credentials"),
      @ApiResponse(
        responseCode = "400",
        description = "Bad Request",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
      ),
    }
  )
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    try {
      var encodedPassword = bCryptPasswordEncoder.encode(loginRequest.getPassword());

      User user = userService.validateUserCredentials(loginRequest.getUsername(), encodedPassword);

      String accessToken = JwtUtil.createToken(new UserDetailsImpl(user.getUsername(), user.getPassword(), null));

      LoginResponse response = new LoginResponse();
      response.setUsername(user.getUsername());
      response.setAccessToken(accessToken);

      return ResponseEntity.ok(response);
    } catch (BadRequestException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
