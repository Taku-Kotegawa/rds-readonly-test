package com.example.rdsreadonlytest.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestServiceTest {

    @Autowired
    TestService target;

    @Test
    void readOnly() {
        String result = target.readOnly();
        System.out.println(result);

    }

    @Test
    void writable() {
        String result = target.writable();
        System.out.println(result);
    }
}