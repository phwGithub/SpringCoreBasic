package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    // 정률할인 적용을 위해 할인정책 구현체를 변경해야 한다
    // 여기서 문제가 발생했다
    // 다형성을 활용하고 인터페이스와 구현 객체를 분리했으나
    // 객체지향설계 원칙 중 OCP와 DIP를 지키지 못했다
    // why? 클라이언트(OrderServiceImpl)가 인터페이스와 구현체 모두 의존하고 있기 때문 -> DIP 위반
    // => 기능을 변경하려면 클라이언트 코드를 변경해야 한다 -> OCP 위반
    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
