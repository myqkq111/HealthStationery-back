package com.example.finalproject.repository;

import com.example.finalproject.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByMemberIdAndSns(int sns, int memberId);
    List<ChatMessage> findBySnsAndMemberId(int transmitMemberId, int sns);
    List<ChatMessage> findBySnsOrTransmitMemberId(int sns, int transmitMemberId);
}
