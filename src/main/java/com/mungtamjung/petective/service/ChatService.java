package com.mungtamjung.petective.service;

import com.mungtamjung.petective.model.ChatEntity;
import com.mungtamjung.petective.repository.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public ChatEntity create(final ChatEntity chatEntity){
        return chatRepository.save(chatEntity);
    }
}
