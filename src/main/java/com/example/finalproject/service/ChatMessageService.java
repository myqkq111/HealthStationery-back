package com.example.finalproject.service;

import com.example.finalproject.entity.ChatMessage;
import com.example.finalproject.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessage saveMessage(int memberId, String name, String content, int sns) {
        ChatMessage message = new ChatMessage();
        message.setMemberId(memberId);
        message.setName(name);
        message.setContent(content);
        message.setSns(sns);
        message.setTimestamp(LocalDateTime.now());
        message.setTransmitMemberId(11);
        return chatMessageRepository.save(message);
    }

    public ChatMessage saveAdminMessage(int memberId, String name, String content, int sns, int transmitMemberId) {
        ChatMessage message = new ChatMessage();
        message.setMemberId(memberId);
        message.setName(name);
        message.setContent(content);
        message.setSns(sns);
        message.setTimestamp(LocalDateTime.now());
        message.setTransmitMemberId(transmitMemberId);
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getChatHistory(int memberId) {
        List<ChatMessage> sentMessages = chatMessageRepository.findBySnsAndMemberId(0, memberId);
        List<ChatMessage> receivedMessages = chatMessageRepository.findBySnsOrTransmitMemberId(1, memberId);

        sentMessages.addAll(receivedMessages);

        // timestamp 기준으로 정렬
        Collections.sort(sentMessages, new Comparator<ChatMessage>() {
            @Override
            public int compare(ChatMessage msg1, ChatMessage msg2) {
                return msg1.getTimestamp().compareTo(msg2.getTimestamp());
            }
        });

        return sentMessages;
    }

    public List<ChatMessage> getAdminChatHistory(int memberId) {
        List<ChatMessage> sentMessages = chatMessageRepository.findBySnsOrTransmitMemberId(1, memberId);
        List<ChatMessage> receivedMessages = chatMessageRepository.findBySnsAndMemberId(0, memberId);

        sentMessages.addAll(receivedMessages);

        // timestamp 기준으로 정렬
        Collections.sort(sentMessages, new Comparator<ChatMessage>() {
            @Override
            public int compare(ChatMessage msg1, ChatMessage msg2) {
                return msg1.getTimestamp().compareTo(msg2.getTimestamp());
            }
        });

        return sentMessages;
    }

    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll(); // 모든 메시지를 가져오는 메소드
    }
}
