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
public class ChatRoomDTO {
    private String id;
    private String sender;
    private String receiver;
    private String senderUsername; // sender의 username
    private String receiverUsername; // receiver의 username
}
