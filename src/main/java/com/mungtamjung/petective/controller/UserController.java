package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.dto.ResponseDTO;
import com.mungtamjung.petective.dto.UserDTO;
import com.mungtamjung.petective.model.UserEntity;
import com.mungtamjung.petective.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/signup")
    public ResponseEntity<?> registUser(@RequestBody UserDTO userDTO){
        try{
            if(userDTO == null || userDTO.getPassword()==null){
                throw new RuntimeException("Invalid Password value");
            }
            UserEntity user=UserEntity.builder()
                    .userid(userDTO.getUserid())
                    .password(userDTO.getPassword())
                    .build();

            UserEntity registerUser = userService.create(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .id(registerUser.getId())
                    .userid(registerUser.getUserid())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
        UserEntity user = userService.getByCredentials(
                userDTO.getUserid(),
                userDTO.getPassword()
        );

        if(user!=null){
            final UserDTO responseUserDTO = userDTO.builder()
                    .userid(user.getUserid())
                    .id(user.getId())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        }else{
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
