package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, String> {
    Optional<ChatRoomEntity> findBySenderAndReceiver(String sender, String receiver);

}
