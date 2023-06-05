package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    public static void main(String[] args) {
        // AppConfig appConfig = new AppConfig();
        // MemberService memberService = appConfig.memberService();
        // OrderService orderService = appConfig.orderService();

        // 스프링으로 전환 => 기존 AppConfig 설정 정보를 기반으로 스프링 컨테이너 생성
        // AppConfig에 @Bean 태그가 있는 메소드들을 호출하여 객체 생성, 이름은 기본적으로 메소드의 이름을 따라간다
        // Bean의 이름은 무조건 고유해야 한다 => 아닐 시 에러, 현업에서는 명확하게 다른 이름을 써야 함
        // 그 후 만들어진 Bean 사이의 의존관계를 주입한다
        // 현재 만들어진 AppConfig는 호출하면서 다른 Bean도 생성되어 생성 -> 주입 이라는 단계가 명확하게 나누어지지 않는다
        // 단계를 나누는 것이 어떤 면에서 유리할까?
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // @Bean으로 등록된 객체(Bean)들을 스프링 컨테이너에서 가져오기
        // 이름은 디폴트로 객체 생성 메소드의 이름을 따라감
        // 이 방식이 기존 자바 코드보다 뭐가 더 좋을까?
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        OrderService orderService = ac.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
    }
}
