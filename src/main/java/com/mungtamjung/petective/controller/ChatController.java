package com.mungtamjung.petective.controller;

import com.mungtamjung.petective.dto.ChatRoomDTO;
import com.mungtamjung.petective.dto.ResponseDTO;
import com.mungtamjung.petective.model.ChatRoomEntity;
import com.mungtamjung.petective.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatRoomService chatRoomService;

    // 채팅방 리스트 조회
    @GetMapping("/chat")
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
    @PostMapping("/chat")
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
}
