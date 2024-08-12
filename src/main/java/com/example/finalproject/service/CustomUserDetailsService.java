package com.example.finalproject.service;

import com.example.finalproject.mapper.MemberMapper;
import com.example.finalproject.mapper.OAuthMapper;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    public OAuthMapper oAuthMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberVO member = memberMapper.findByEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        String password = member.getPassword();
        if (password == null) {
            return User.withUsername(member.getEmail())
                    .password("")
                    .build();
        } else {
            return User.withUsername(member.getEmail())
                    .password(member.getPassword())
                    .build();
        }
    }
}


