package com.mungtamjung.petective.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class ChatRoomEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private String sender; //채팅방 생성자 userID
    private String receiver; //채팅방 수신자 userID

    //게시글과 연관 추가
}
