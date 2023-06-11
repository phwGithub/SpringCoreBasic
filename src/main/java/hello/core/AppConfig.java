package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 구현체의 생성과 주입을 전문으로 하는 클래스
// 여기서 만들어진 구현체를 클라이언트(MemberServiceImpl, OrderServiceImpl)에 생성자를 통해서 주입한다
// 어떤 구현체를 주입할지는 여기서 결정한다 => 관심사의 분리

@Configuration // 스프링 전환을 위한 선언
public class AppConfig {

    // 실행 과정 예쌍
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    // call AppConfig.memberRepository

    // 실제 실행 과정
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    // 어째서? 스프링이 싱글톤을 보장하기 위해 무언가를 함


    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService"); // Configuration 싱글톤 테스트
        return new MemberServiceImpl(memberRepository());
    }

    // 역할을 명확하게 하기 위해 memberService() 에서 분리
    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    // 역할을 명확하게 하기 위해 orderService() 에서 분리
    @Bean
    public DiscountPolicy discountPolicy() {
        // 할인 정책이 변경됐을 시 이 부분만 변경하면 된다 => 배우를 교체하는 곳
        // 클라이언트 코드를 변경할 필요가 없어졌다
        // return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
