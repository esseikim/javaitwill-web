package com.itwill.spring3.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itwill.spring3.dto.member.MemberSignUpDto;
import com.itwill.spring3.repository.member.Member;
import com.itwill.spring3.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// UserDetailsService를 구현하는 클래스
// UserDetailsService: filter가 사용자 정보를 가져오기 위해 사용하는 클래스. 
// 이 클래스를 구현하게 되면, filter가 UserDetailsService를 이용할 수 있음. 
// 이 클래스는 UserDetails(사용자 정보)를 가지고 있어야 함. 
// filter: Controller로 가기 전 요청을 가로챔
@Slf4j
@RequiredArgsConstructor
@Service
// Security Filter Chain에서 UserDetailsService 객체를 사용할 수 있도록 하기 위해서.
public class MemberService implements UserDetailsService {
    
    private final MemberRepository memberRepository;
    
    
    // SecurityConfig에서 설정한 PasswordEncoder 빈(bean)을 주입해줌.
    // -> SecurityConfig에서 Bean으로 관리가 되어 있기에 스프링 컨테이너는 다음을 생성하고 있어서 필요한 곳에 넣어줌.
    // Spring Security 5 버전부터는 비밀번호는 반드시 암호화를 해야 함. 암호화 하지 않은 비밀번호는 로그인 불가
    // 비밀번호 암호화, 복호화를 해주는 비밀번호 인코더(Password encoder) 객체를 bean으로 생성해야 함. 
    // 비밀번호를 암호화하지 않으면 HTTP 403(access denied, 접근 거부) 또는
    // HTTP 500(internal server error, 내부 서버 오류)가 발생함. 
    private final PasswordEncoder passwordEncoder;
    
    // 회원 가입
    public Long registerMember(MemberSignUpDto dto) {
        log.info("registerMember(dto={})", dto);
        
        Member entity = Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .build();
        log.info("save 전: entity={}", entity);
        
        memberRepository.save(entity); // DB insert
        log.info("save 후: entity={}", entity);
        
        return entity.getId(); // DB에 저장된 ID(고유키)를 리턴.
    }

    
    // Security Filter Chain에서는 loadUserByUsername를 호출하여 DB에 존재하는지를 판단. 
    // -> 해당 메서드가 있어야만 Security Filter Chain가 제대로 동작이 될 수 있음.
    // -> 로그인 성공 여부 판단.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername(username={})", username);
        
        // DB에서 username으로 사용자 정보 검색(select).
        UserDetails user = memberRepository.findByUsername(username);
        
        if (user != null) {
            return user;
        }
        
        throw new UsernameNotFoundException(username + " - not found");
    }

}