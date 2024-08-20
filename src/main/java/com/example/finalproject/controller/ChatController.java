package com.example.finalproject.controller;


import com.example.finalproject.entity.ChatMessage;
import com.example.finalproject.service.ChatMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

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
        chatMessageService.saveMessage(chatMessage.getMemberId(), chatMessage.getName(), chatMessage.getContent(), chatMessage.getSns());
        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessage.getMemberId()), "/queue/messages", chatMessage);
    }

    @GetMapping("/history")
    public List<ChatMessage> getChatHistory(@RequestParam int memberId) {
        System.out.println(chatMessageService.getChatHistory(memberId));
        return chatMessageService.getChatHistory(memberId);
    }
}
