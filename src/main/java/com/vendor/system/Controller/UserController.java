package com.vendor.system.Controller;

import com.vendor.system.DTO.ApiResponse;
import com.vendor.system.DTO.RegisterUserDTO;
import com.vendor.system.DTO.SignInDTO;
import com.vendor.system.Entity.User;
import com.vendor.system.Repository.UserRepository;
import com.vendor.system.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @Operation(summary = "register user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserDTO userDto , BindingResult result) {
        try {
            if (result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                for (FieldError fieldError : result.getFieldErrors()) {
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
                return new ResponseEntity<>(new ApiResponse<>(false,errors,"Bad Request" ), HttpStatus.BAD_REQUEST);
            }
            else if (userService.existsByUsername(userDto.getUsername())) {
                return new ResponseEntity<>(new ApiResponse<>(false,null,"Username is already taken"),HttpStatus.NOT_FOUND);
            }
            else if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
                return new ResponseEntity<>(new ApiResponse<>(false,null,"Password doesn't match"),HttpStatus.NOT_FOUND);
            }
            userService.register(userDto);
            return new ResponseEntity<>(new ApiResponse<>(true,null,"User registered successfully"),HttpStatus.OK);
        } catch (Exception e) {
        return new ResponseEntity<>(new ApiResponse<>(false,null,"Internal server error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }

    @PostMapping("/signin")
    @Operation(summary = "log in user")
    public ResponseEntity<?> signInUser(@Valid @RequestBody SignInDTO signInRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                for (FieldError fieldError : result.getFieldErrors()) {
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
                return new ResponseEntity<>(new ApiResponse<>(false,errors,"Bad Request" ), HttpStatus.BAD_REQUEST);
            }  
            User user = userRepository.findByUsername(signInRequest.getUsername())
            .orElse(null);
            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(false,null,"No username found"),HttpStatus.NOT_FOUND);
            }
            // Check if password matches
            else if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
                return new ResponseEntity<>(new ApiResponse<>(false,null,"Invalid password" ), HttpStatus.BAD_REQUEST);
            }
            String token = userService.login(signInRequest.getUsername(), signInRequest.getPassword());
            return new ResponseEntity<>(new ApiResponse<>(true,token,"Logged"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Internal server error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "get user datails of logged in user")
    @GetMapping("")
    public ResponseEntity<?> getUser(HttpServletRequest request){
        try {
            Principal principal = request.getUserPrincipal();
            String username = principal.getName();
            User user = userRepository.findByUsername(username).orElse(null);
            if(user == null)
            {
                return new ResponseEntity<>(new ApiResponse<>(false,null,"No users found to delete"),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new ApiResponse<>(true,user,"Successfully retreived the user"),HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Internal server error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "delete the logged in user")
    @DeleteMapping("")
    public ResponseEntity<?> deleteUser(HttpServletRequest request){
        try {
            Principal principal = request.getUserPrincipal();
            String username = principal.getName();
            User user = userRepository.findByUsername(username).orElse(null);
            if(user == null)
            {
                return new ResponseEntity<>(new ApiResponse<>(false,null,"No users found to delete"),HttpStatus.NOT_FOUND);
            }
            Long userId = user.getId();
            userRepository.deleteById(userId);
            return new ResponseEntity<>(new ApiResponse<>(true,null,"Successfully deleted the user"),HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false,null,"Internal server error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
