package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.dto.ChatRoomDTO;
import com.mungtamjung.petective.dto.ResponseDTO;
import com.mungtamjung.petective.model.ChatRoomEntity;
import com.mungtamjung.petective.model.ChatEntity;
import com.mungtamjung.petective.service.ChatRoomService;
import com.mungtamjung.petective.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("chat")
public class ChatController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatService chatService;

    // 채팅방 리스트 조회
    @GetMapping("")
    public ResponseEntity<?> chatRoomList(){
        try{
            List<ChatRoomEntity> chatRoomList = chatRoomService.retrieveChatRoomList();
            ResponseDTO responseDTO = new ResponseDTO(true, 200,null, chatRoomList);
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            ResponseDTO responseDTO = new ResponseDTO(false, 400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    // 채팅방 생성
    @PostMapping("")
    public ResponseEntity createChatRoom(@RequestBody ChatRoomDTO chatRoomDTO){
        try{
            ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                    .sender(chatRoomDTO.getSender())
                    .receiver(chatRoomDTO.getReceiver())
                    .build();
            ChatRoomEntity chatRoom = chatRoomService.createChatRoom(chatRoomEntity);

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
