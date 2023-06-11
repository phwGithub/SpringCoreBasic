package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class); // xxxxCGLIB는 AppConfig의 자식 클래스라 조회됨

        System.out.println("bean = " + bean.getClass());
        // 빈 이름 뒤에 $$xxxxCGLIB 이 붙는다 뭘까?
        // 이는 내가 등록한 AppConfig가 아닌 이 AppConfig를 상속한 임의의 클래스를 자동으로 만들어준 것이다
        // 이 클래스에서 싱글톤을 보장해주는 기능을 수행한다
        // ex) memoryMemberRepository가 이미 컨테이너에 존재하면 -> 컨테이너에서 찾아서 반환
        // ex) memoryMemberRepository가 컨테이너에 존재하지 않으면 -> 객체를 생성해서 컨테이너에 등록 후 반환
    }
}
