package com.project.bongyang_club_backend.global.security;

import com.project.bongyang_club_backend.domain.member.domain.Member;
import com.project.bongyang_club_backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String si_number) throws UsernameNotFoundException {
        Member member = memberRepository.findById(si_number).orElseThrow(
                () -> new UsernameNotFoundException("User not found in the database")
        );
        log.info("User found in the database: {}", si_number);

        return new CustomUserDetails(member);
    }

}
