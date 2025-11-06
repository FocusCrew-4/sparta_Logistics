package com.sparta.member.interfaces.web;

import com.sparta.member.domain.model.Member;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1/member")
public class MemberController {

    @PostMapping("/login")
    public ResponseEntity<?> login(

    ) {

        return ResponseEntity.ok("hello");
    }

}
