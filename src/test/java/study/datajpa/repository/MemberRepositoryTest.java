package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    @PersistenceContext
    EntityManager em;

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

    @Test
    public void paging(){
        memberRepository.save(new Member("member1"));
        memberRepository.save(new Member("member2"));
        memberRepository.save(new Member("member3"));
        memberRepository.save(new Member("member4"));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //반환 타입을 받으면 Page에서 카운트까지 보냄
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        List<Member> countent = page.getContent();
        long a = page.getTotalElements();

        assertThat(countent.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void bulkUpdate(){
        memberRepository.save(new Member("member1"));
        memberRepository.save(new Member("member2"));
        memberRepository.save(new Member("member3"));
        memberRepository.save(new Member("member4"));
        memberRepository.save(new Member("member5"));

        int resultCount = memberRepository.bulkAgePlus(20);

        /*em.clear();*/
        assertThat(resultCount).isEqualTo(3);
    }
}