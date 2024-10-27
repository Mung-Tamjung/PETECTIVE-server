package com.mungtamjung.petective.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.mungtamjung.petective.dto.ChatRoomDTO;
import com.mungtamjung.petective.model.ChatRoomEntity;
import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.model.UserEntity;
import com.mungtamjung.petective.repository.ChatRoomRepository;
import com.mungtamjung.petective.repository.PostRepository;
import com.mungtamjung.petective.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ChatRoomDTO> retrieveChatRoomList(String userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ChatRoomEntity> chatRooms = chatRoomRepository.findChatRoomsByUser(user.getId());

        // ChatRoomEntity를 ChatRoomDTO로 변환
        return chatRooms.stream()
                .map(chatRoom -> ChatRoomDTO.builder()
                        .id(chatRoom.getId())
                        .sender(chatRoom.getSender().getId())
                        .receiver(chatRoom.getReceiver().getId())
                        .senderUsername(chatRoom.getSender().getUsername())
                        .receiverUsername(chatRoom.getReceiver().getUsername())
                        .build())
                .collect(Collectors.toList());
    }

    public ChatRoomEntity createChatRoom(String postId, UserEntity sender) {
        // 게시글 조회
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 새로운 채팅방 객체 생성
        ChatRoomEntity chatRoom = new ChatRoomEntity();
        chatRoom.setSender(sender);
        chatRoom.setReceiver(post.getWriter()); // 게시글 작성자를 receiver로 설정

        // 이미 동일한 sender와 receiver로 생성된 채팅방이 있는지 확인
        Optional<ChatRoomEntity> existingRoom = chatRoomRepository.findBySenderAndReceiver(
                sender,
                post.getWriter());


        if (existingRoom.isPresent()) {
            // 채팅방이 이미 존재하면 그 채팅방을 반환
            return existingRoom.get();
        } else {
            // 새 채팅방 생성
            return chatRoomRepository.save(chatRoom);
        }
    }
    }

