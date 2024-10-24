package com.mungtamjung.petective.dto;

import com.mungtamjung.petective.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private String sender; //user id
    private String receiver; //user id
    private String message;

    private String chatRoom; //chatroom id
    //private String sentTime;
}
