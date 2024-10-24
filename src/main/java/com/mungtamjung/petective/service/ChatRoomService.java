package com.mungtamjung.petective.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.mungtamjung.petective.model.ChatRoomEntity;
import com.mungtamjung.petective.model.PostEntity;
import com.mungtamjung.petective.model.UserEntity;
import com.mungtamjung.petective.repository.ChatRoomRepository;
import com.mungtamjung.petective.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private PostRepository postRepository;

    public List<ChatRoomEntity> retrieveChatRoomList(String userId) {
        System.out.println("Retrieving chat rooms for user ID: " + userId);
        return chatRoomRepository.findChatRoomsByUser(userId);
    }

    public ChatRoomEntity createChatRoom(String postId, String senderId) {
        // 게시글 조회
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 새로운 채팅방 객체 생성
        ChatRoomEntity chatRoom = new ChatRoomEntity();
        chatRoom.setSender(senderId);
        chatRoom.setReceiver(post.getWriter().getId()); // 게시글 작성자를 receiver로 설정

        // 이미 동일한 sender와 receiver로 생성된 채팅방이 있는지 확인
        List<ChatRoomEntity> existingRoom = chatRoomRepository.findBySenderAndReceiver(
                chatRoom.getSender(),
                chatRoom.getReceiver()
        );

        if (!existingRoom.isEmpty()) {
            // 채팅방이 이미 존재하면 그 채팅방을 반환
            return existingRoom.get(0);
        } else {
            // 새 채팅방 생성
            return chatRoomRepository.save(chatRoom);
        }
    }
}
