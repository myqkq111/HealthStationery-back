package com.example.finalproject;

import com.example.finalproject.mapper.MemberMapper;
import com.example.finalproject.vo.MemberVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FinalprojectApplicationTests {

	@Autowired
	MemberMapper memberMapper;

	@Test
	void register(){
		MemberVO member = new MemberVO();
		member.setCate("home");
		member.setEmail("test@test.com");
		member.setPassword("123456");
		member.setName("Test Name");
		member.setFm("man");
		member.setTell("01012341234");
		//
		String birthString = "20240809";

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.parse(birthString, formatter);

		Date birthDate = java.sql.Date.valueOf(localDate);
		member.setBirth(birthDate);
		member.setMailaddr("38803");
		member.setRoadaddr("경북 영천시 신녕면 구디티길 6");
		member.setDetailaddr("뒷길");
		memberMapper.insert(member);
		MemberVO foundMember = memberMapper.findByEmail(member.getEmail());
		assertThat(foundMember).isNotNull();
		assertThat(foundMember.getCate()).isEqualTo("home");
		assertThat(foundMember.getName()).isEqualTo("Test Name");
	}
}