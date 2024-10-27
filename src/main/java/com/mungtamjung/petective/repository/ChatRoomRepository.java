package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.ChatRoomEntity;
import com.mungtamjung.petective.model.UserEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, String> {
    @Query("SELECT c FROM ChatRoomEntity c WHERE c.sender = :sender AND c.receiver = :receiver")
    Optional<ChatRoomEntity> findBySenderAndReceiver(@Param("sender") UserEntity sender, @Param("receiver") UserEntity receiver);

    @Query("SELECT c FROM ChatRoomEntity c WHERE c.sender.id = :userId OR c.receiver.id = :userId")
    List<ChatRoomEntity> findChatRoomsByUser(@Param("userId") String userId);

}
