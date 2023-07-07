package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    // 정률할인 적용을 위해 할인정책 구현체를 변경해야 한다
    // 여기서 문제가 발생했다
    // 다형성을 활용하고 인터페이스와 구현 객체를 분리했으나
    // 객체지향설계 원칙 중 OCP와 DIP를 지키지 못했다
    // why? 클라이언트(OrderServiceImpl)가 인터페이스와 구현체 모두 의존하고 있기 때문 -> DIP 위반
    // => 기능을 변경하려면 클라이언트 코드를 변경해야 한다 -> OCP 위반

    // 해결책
    // DIP 원칙 준수를 위해
    // 구현 클래스 의존을 모두 지우고 인터페이스에만 의존한다
    // 준수는 하였지만 구현체없이 코드를 실행해야하는 문제가 발생했다
    // => 누군가가 대신 구현체를 생성하고 주입해야 주어야 한다
    // 누가 이것을 해줄까?
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // AppConfig라는 구현체를 생성하고 주입하는 것을 전문으로 하는 클래스가 만들어졌다
    // 클라이언트에서는 인터페이스만 의존하도록 하고 생성자를 통해 실제 구현체를 받을 수 있도록 한다
    // 어떤 구현체가 주입이 될지는 여기서 알 수 없다 => 실행에만 집중
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 생성자를 만들어 AppConfig가 위의 두 가지에 인터페이스에 구현체를 주입할 수 있도록 한다
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
