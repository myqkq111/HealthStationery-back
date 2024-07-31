package com.example.finalproject.service;

import com.example.finalproject.mapper.OAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OAuthService {

    @Autowired
    public OAuthMapper oAuthMapper;

}
