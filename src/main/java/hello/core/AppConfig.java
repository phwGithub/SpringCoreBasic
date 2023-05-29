package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
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
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
}
