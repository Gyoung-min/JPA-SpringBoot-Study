package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//@AllArgsConstructor
@RequiredArgsConstructor
public class MemberService {

    /*@Autowired
    private MemberRepository memberRepository;*/

    //setter Injection
    //테스트 코드 작성에 유리
    //단점 있어서 생성자 Injection을 권장
    /*private MemberRepository memberRepository;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    private final MemberRepository memberRepository;

    /*@Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    } => @AllArgsConstructor */
    //하지만 @RequiredArgsConstructor가 final에 더 좋다

    //회원 가입
    @Transactional
    public Long join(Member member) {
        //중복 검사
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
       List<Member> findMembers = memberRepository.findByName(member.getName());
    //동시에 아이디가 생성될 경우 문제가 생기니 유니크 제약 조건을 건다.
       if(!findMembers.isEmpty()) {
           throw new IllegalStateException("이미 존재하는 회원입니다");
       }

    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    //단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
