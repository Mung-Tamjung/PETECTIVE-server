package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.dto.ChatRoomDTO;
import com.mungtamjung.petective.dto.ResponseDTO;
import com.mungtamjung.petective.model.ChatRoomEntity;
import com.mungtamjung.petective.model.ChatEntity;
import com.mungtamjung.petective.model.UserEntity;
import com.mungtamjung.petective.service.ChatRoomService;
import com.mungtamjung.petective.service.ChatService;
import com.mungtamjung.petective.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.util.List;

@Controller
@RequestMapping("chat")
public class ChatController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    // 채팅방 리스트 조회
    @GetMapping
    public ResponseEntity<?> chatRoomList(){
        try{
            // 현재 로그인한 사용자 ID 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = authentication.getName();
            System.out.println("Current user ID: " + userId); // 현재 로그인된 사용자 ID 로그

            List<ChatRoomEntity> chatRoomList = chatRoomService.retrieveChatRoomList(userId);
            ResponseDTO responseDTO = new ResponseDTO(true, 200,null, chatRoomList);
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    // 채팅방 생성
    @PostMapping("/{postId}")
    public ResponseEntity createChatRoom(@PathVariable String postId, @RequestBody ChatRoomDTO chatRoomDTO){
        try{
            // 현재 로그인한 사용자 ID 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = authentication.getName(); // 로그인한 사용자의 ID

            // UserEntity를 가져오기
            UserEntity sender = userService.getUserDetail(userId)
                    .orElseThrow(() -> new RuntimeException("User not found")); // 사용자가 없을 경우 예외 처리

            // 채팅방 생성
            ChatRoomEntity chatRoom = chatRoomService.createChatRoom(postId, sender.getId());

            ResponseDTO responseDTO = new ResponseDTO(true, 200,null, chatRoom);
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    // 특정 채팅방의 채팅 기록을 가져옴
    @GetMapping("/history/{chatRoomId}")
    public ResponseEntity<?> getChatHistory(@PathVariable String chatRoomId) {
        try {
            List<ChatEntity> chatHistory = chatService.getChatHistory(chatRoomId);
            ResponseDTO responseDTO = new ResponseDTO(true, 200,null, chatHistory);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO(false, 400, "Error fetching chat history: " + e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
