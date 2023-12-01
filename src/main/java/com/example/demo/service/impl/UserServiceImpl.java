package com.example.demo.service.impl;

import com.example.demo.constants.AppConstants;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public User findUserAccountByUsername(String username) {
    log.info(AppConstants.FIND_LOG, username);
    return userRepository
      .findUserAccountByUsername(username)
      .orElseThrow(() -> new NotFoundException(String.format(AppConstants.NOT_FOUND_BY_USERNAME, username)));
  }

  @Override
  public User create(UserDto user) {
    log.info(AppConstants.CREATE_LOG, user.getName());
    checkUsername(user.getUsername());
    checkEmail(user.getEmail());

    User newUser = new User();
    newUser.setUsername(user.getUsername());
    newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    newUser.setEmail(user.getEmail());
    newUser.setName(user.getName());
    newUser.setRoles("ADMIN");

    return userRepository.save(newUser);
  }

  @Override
  public boolean validateUserCredentials(LoginRequest loginRequest) {
    String encodedPassword = bCryptPasswordEncoder.encode(loginRequest.getPassword());

    checkPassword(loginRequest.getUsername(), encodedPassword);
    return true;
  }

  private void checkPassword(String username, String password) {
    Optional<LoginResponse> user = userRepository.findUserByUsernameAndPassword(username, password);
  }

  private void checkUsername(String username) {
    Optional<User> user = userRepository.findUserAccountByUsername(username);
    if (user.isPresent()) {
      throw new ConflictException("The username already exists.");
    }
  }

  private void checkEmail(String email) {
    Optional<User> user = userRepository.findUserAccountByEmail(email);
    if (user.isPresent()) {
      throw new ConflictException("The email already exists.");
    }
  }
}
