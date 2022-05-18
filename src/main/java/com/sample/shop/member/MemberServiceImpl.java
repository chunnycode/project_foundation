package com.sample.shop.member;

import com.sample.shop.dto.MemberRequest;
import com.sample.shop.dto.TokenResponse;
import com.sample.shop.entity.Member;
import com.sample.shop.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

	private final MemberRepository memberRepository ;
//	private final PasswordEncoder passwordEncoder;


	@Override
	public Optional<Member> findByMemberName(String memberName) {
		return memberRepository.findByMemberName(memberName);
	}


	@Override
	@Transactional
	public TokenResponse doLogin(MemberRequest memberRequest) throws Exception {
		Optional<Member> test = memberRepository.findByMemberName(memberRequest.getMemberName());
		Member member = memberRepository.findByMemberName(memberRequest.getMemberName())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
	/*	Auth auth = authRepository.findByMemberId(member.getMemberId())
				.orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));*/
//		if (!passwordEncoder.matches(memberRequest.getPassword(), member.getPassword())) {
		if (!memberRequest.getPassword().equals(member.getPassword())) {
			throw new Exception("비밀번호가 일치하지 않습니다.");
		}

		String accessToken = "";
		/*String refreshToken = auth.getRefreshToken();   //DB에서 가져온 Refresh 토큰
		refresh 토큰은 유효 할 경우 Access Token 새로 줌
		if () {

		} else {*/

		// 새로 발급
		long ACCESS_TOKEN_VALID_TIME = 1 * 60 * 1000L;   // 1분
		String SECRET_KEY = "Y2h1bm55Y29kZS1zcHJpbmctYm9vdC1qd3QtdHV0b3JpYWwtc2VjcmV0LWNodW5ueWNvZGUtdGVjaC1zcHJpbmctYm9vdC1qd3QtdHV0b3JpYWwtc2VjcmV0";
		Claims claims = Jwts.claims();//.setSubject(userPk); // JWT payload 에 저장되는 정보단위
		claims.put("memberId", member.getMemberId());
		Date now = new Date();
		accessToken = Jwts.builder()
				.setClaims(claims) // 정보 저장
				.setIssuedAt(now) // 토큰 발행 시간 정보
				.setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME)) // set Expire Time
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 사용할 암호화 알고리즘과
				.compact();

//			refreshToken
//			//DB Refresh 토큰 갱신
//		}

		return TokenResponse.builder()
				.ACCESS_TOKEN(accessToken)
//				.REFRESH_TOKEN(refreshToken)
				.build();
	}
}
