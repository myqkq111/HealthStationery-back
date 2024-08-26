package com.example.finalproject.controller;

import com.example.finalproject.entity.ChatMessage;
import com.example.finalproject.service.ChatMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatMessageService chatMessageService, SimpMessagingTemplate messagingTemplate) {
        this.chatMessageService = chatMessageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        // 사용자 ID와 경로가 정확한지 확인
        messagingTemplate.convertAndSend("/topic/admin/"+"11", chatMessage);
        chatMessageService.saveMessage(chatMessage.getMemberId(), chatMessage.getName(), chatMessage.getContent(), chatMessage.getSns());
        System.out.println("Message sent to user ID: " + chatMessage.getTransmitMemberId());
    }

    @MessageMapping("/sendAdminMessage")
    public void sendAdminMessage(@Payload ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        messagingTemplate.convertAndSend("/topic/admin/"+chatMessage.getTransmitMemberId(), chatMessage);
        chatMessageService.saveAdminMessage(chatMessage.getMemberId(), chatMessage.getName(), chatMessage.getContent(), chatMessage.getSns(), chatMessage.getTransmitMemberId());
        System.out.println(chatMessage);
    }

    @GetMapping("/history")
    public List<ChatMessage> getChatHistory(@RequestParam int memberId) {
        return chatMessageService.getChatHistory(memberId);
    }

    @GetMapping("/historyAdmin")
    public List<ChatMessage> getChatHistoryAdmin(@RequestParam int memberId) {
        return chatMessageService.getAdminChatHistory(memberId);
    }

    @GetMapping("/rooms")
    public List<ChatMessage> getChatRooms() {
        return chatMessageService.getAllMessages();
    }
}
