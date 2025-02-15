package com.crosscert.firewall.controller;

import com.crosscert.firewall.dto.MemberDTO;
import com.crosscert.firewall.entity.Role;
import com.crosscert.firewall.repository.MemberRepository;
import com.crosscert.firewall.service.MemberService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("로그인_테스트")
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void beforeEach() {
        MemberDTO.Request.Create createDto = MemberDTO.Request.Create.builder()
                .email("test@crosscert.com")
                .name("test")
                .password("password")
                .devIp("172.77.0.1")
                .netIp("172.77.0.2")
                .role(Role.MEMBER)
                .build();
        memberService.signup(createDto);
    }

    @AfterEach
    public void afterEach(){
        memberService.deleteByEmail("test@crosscert.com");
    }

    @Test
    public void 로그인성공() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "test@crosscert.com")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void 로그인실패_비밀번호_오류() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "test@crosscert.com")
                        .param("password", "wrong_password"))   //비밀번호 틀림.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }


}