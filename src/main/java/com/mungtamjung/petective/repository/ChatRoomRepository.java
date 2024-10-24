package com.mungtamjung.petective.repository;

import com.mungtamjung.petective.model.ChatRoomEntity;
import com.mungtamjung.petective.model.UserEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, String> {
    @Query("SELECT c FROM ChatRoomEntity c WHERE c.sender = :sender OR c.receiver = :receiver")
    List<ChatRoomEntity> findBySenderAndReceiver(@Param("sender") String sender, @Param("receiver") String receiver);

    @Query("SELECT c FROM ChatRoomEntity c WHERE c.sender = :userId OR c.receiver = :userId")
    List<ChatRoomEntity> findChatRoomsByUser(@Param("userId") String userId);

}
