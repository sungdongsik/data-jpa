package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);

       Member findMember = memberRepository.findById(saveMember.getId()).get();

        System.out.println("===>" + findMember);
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void namedQuery(){
        Member member = new Member("memberA");
        memberRepository.save(member);

        List<Member> result = memberRepository.findByUsername("memberA");
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void testQuery(){
        Member member = new Member("memberA");
        memberRepository.save(member);

        List<Member> result = memberRepository.findUser("memberA", 0);
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void findUsernameList(){
        Member member = new Member("memberA");
        memberRepository.save(member);

        List<String> result = memberRepository.findUsernameList();
        for (String s : result){
            System.out.println(s + "=========>");
        }
    }

    @Test
    public void findMemberDto(){
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("memberA");
        m1.setTeam(team);
        memberRepository.save(m1);




        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto s : memberDto){
            System.out.println(s + "=========>");
        }
    }

    @Test
    public void findByNames(){
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("memberA");
        m1.setTeam(team);
        memberRepository.save(m1);




        List<Member> memberDto = memberRepository.findByNames(Arrays.asList("memberA", "memberB"));
        for (Member s : memberDto){
            System.out.println(s + "=========>");
        }
    }

    @Test
    public void returnType(){
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("memberA");
        m1.setTeam(team);
        memberRepository.save(m1);




        List<Member> aaa = memberRepository.findListByUsername("memberA");
    }
}