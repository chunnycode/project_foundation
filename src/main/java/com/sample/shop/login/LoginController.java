package com.sample.shop.login;

import com.sample.shop.login.dto.LoginDto;
import com.sample.shop.login.service.LoginService;
import com.sample.shop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) throws Exception {
		return ResponseEntity
				.ok()
				.body(loginService.doLogin(loginDto));
    }


    @GetMapping("/test")
    public ResponseEntity check() throws Exception {
        return ResponseEntity
                .ok()
                .body("passed");
    }

    //토큰 재발급 요청
    @PostMapping("/reissue")
    public ResponseEntity reissueToken(HttpServletRequest request) throws Exception {
        return ResponseEntity
                .ok()
                .body(loginService.reissueToken(request));
    }



}
