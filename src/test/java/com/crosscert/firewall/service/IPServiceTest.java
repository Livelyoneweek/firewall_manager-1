package com.crosscert.firewall.service;

import com.crosscert.firewall.entity.IP;
import com.crosscert.firewall.entity.IpAddress;
import com.crosscert.firewall.repository.IPRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@SpringBootTest
@Transactional
class IPServiceTest {

    @Autowired
    IPRepository ipRepository;

    @Autowired
    IPService ipService;

    @Test
    @DisplayName("ipAddress 존재하지 않을 때")
    void findByAddressNO() {
        //given
        IpAddress ipAddress = new IpAddress("172.12.40.52");

        IP ip = IP.builder()
                .domain("domain")
                .description("description")
                .address(ipAddress)
                .build();
        ipRepository.save(ip);


        String testIpAddress = "172.12.40.53";

        //when
        Optional<IP> returnIp = ipService.findByAddress(testIpAddress);

        //then
        Assertions.assertTrue(returnIp.isEmpty());
    }

    @Test
    @DisplayName("ipAddress 존재할 때")
    void findByAddressOK() {
        //given
        IpAddress ipAddress = new IpAddress("172.12.40.52");

        IP ip = IP.builder()
                .domain("domain")
                .description("description")
                .address(ipAddress)
                .build();
        ipRepository.save(ip);


        String testIpAddress = "172.12.40.52";

        //when
        Optional<IP> returnIp = ipService.findByAddress(testIpAddress);

        //then
        Assertions.assertTrue(returnIp.isPresent());
    }
}