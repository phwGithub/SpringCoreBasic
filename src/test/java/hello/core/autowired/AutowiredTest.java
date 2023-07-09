package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void autowiredOption() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        @Autowired(required = false) // 자동 주입 대상이 없으면 수정자 메서드 자체가 호출이 안됨
        public void setNoBean1(Member noBean1) { // Spring이 관리 안하는 빈
            System.out.println("noBean1 = " + noBean1);
        }

        @Autowired // 호출은 되지만 자동 주입 대상이 없으면 빈에 null이 들어
        public void setNoBean2(@Nullable Member noBean2) { // Spring이 관리 안하는 빈
            System.out.println("noBean2 = " + noBean2);
        }

        @Autowired // 호출은 되지만 자동 주입 대상이 없으면 빈에 Optional.empty가 들어감
        public void setNoBean3(Optional<Member> noBean3) { // Spring이 관리 안하는 빈
            System.out.println("noBean3 = " + noBean3);
        }
    }

}
