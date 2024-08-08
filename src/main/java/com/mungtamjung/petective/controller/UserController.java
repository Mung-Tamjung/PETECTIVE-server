package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.dto.ResponseDTO;
import com.mungtamjung.petective.dto.UserDTO;
import com.mungtamjung.petective.model.UserEntity;
import com.mungtamjung.petective.security.TokenProvider;
import com.mungtamjung.petective.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try{
            if(userDTO == null || userDTO.getPassword()==null){
                throw new RuntimeException("Invalid Password value");
            } else if(userDTO.getPassword().length() < 12){
                throw new RuntimeException("Insecure Password");
            }
            UserEntity user=UserEntity.builder()
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .build();

            UserEntity registerUser = userService.create(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .id(registerUser.getId())
                    .email(registerUser.getEmail())
                    .username(userDTO.getUsername())
                    .build();

            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, responseUserDTO);

            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            String eMessage = e.getMessage();

            if(eMessage == "DUPLICATED_USER_NAME" || eMessage == "DUPLICATED_USER_EMAIL"){
                ResponseDTO responseDTO = new ResponseDTO(false, 211, eMessage, null);
                return ResponseEntity.ok().body(responseDTO);
            }
            else{
                ResponseDTO responseDTO = new ResponseDTO(false, 400, eMessage, null);
                return ResponseEntity.badRequest().body(responseDTO);
            }
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
        UserEntity user = userService.getByCredentials(
                userDTO.getEmail(),
                userDTO.getPassword(), passwordEncoder
        );

        if(user!=null){
            final String token=tokenProvider.createToken(user);

            final UserDTO responseUserDTO = userDTO.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .username(userDTO.getUsername())
                    .token(token)
                    .build();

            ResponseDTO responseDTO = new ResponseDTO(true, 200, null, responseUserDTO);

            return ResponseEntity.ok().body(responseDTO);
        }else{
            ResponseDTO responseDTO = new ResponseDTO(false, 400, "Login failed", null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
