package com.example.finalproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.core.SpringVersion;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int memberId;
    private String name;
    private String content;
    private int sns;
    private LocalDateTime timestamp;
    private int transmitMemberId;
}
