package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, String> {
     ChatRoomEntity findBySenderAndReceiver(String sender, String receiver); //userid로 조회
}
