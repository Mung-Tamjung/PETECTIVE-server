package com.mungtamjung.petective.dto;

import com.mungtamjung.petective.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private String sender; //user id
    private String receiver; //user id
    private String message;

    private String chatRoom; //chatroom id
    private LocalDateTime createdAt;
}
