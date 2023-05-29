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

// 구현체의 생성과 주입을 전문으로 하는 클래스
// 여기서 만들어진 구현체를 클라이언트(MemberServiceImpl, OrderServiceImpl)에 생성자를 통해서 주입한다
// 어떤 구현체를 주입할지는 여기서 결정한다 => 관심사의 분리
public class AppConfig {
    
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    // 역할을 명확하게 하기 위해 memberService() 에서 분리
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    // 역할을 명확하게 하기 위해 orderService() 에서 분리
    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }
}
