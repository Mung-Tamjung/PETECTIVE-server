package com.mungtamjung.petective.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;



@Data
@EqualsAndHashCode(callSuper = false)
//@Builder
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatEntity{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private String sender;
    private String receiver;
    private String message;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String created_at;

//    @ManyToOne
//    @JoinColumn()
//    private ChatRoomEntity chatRoom;
    private String roomId; //채팅룸ID(FK)

}
