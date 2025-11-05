package com.sparta.member.domain.model;

import com.sparta.member.doamin.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @Nested
    @DisplayName("회원가입 요청 생성")
    class Member_signUp {

        @Nested
        @DisplayName("RED")
        class Red {
            @Test
            @DisplayName("RED - 전달 인자중 null 이 포함")
            void member_signUpRequest() {
            }
        }

        @Test
        @DisplayName("GREEN")
        void member_signUpRequest() {}
    }

}
