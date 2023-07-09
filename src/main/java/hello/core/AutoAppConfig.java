package hello.core;

import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // basePackages = "hello.core.member", // 범위 지정이 없으면 라이브러리 자바 코드까지 다 뒤진다.. 너무 느림
        // basePackageClasses = AutoAppConfig.class, // AutoAppConfig의 패키지 위치 부터 탐색
        // 권장 방법 : 설정 정보 파일(AppConfig)를 프로젝트 최상단에 두고 패키지 위치 지정 생략
        // 컴포넌트 스캔 기본 대상 : @Component, @Controller, @Repository, @Configuration, @Service
        //                      -> @Component 외 어노테이션은 이외의 다른 부가 기능도 수행
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    // 수동 빈 등록 시 자동 빈과 이름이 같다면 오버라이딩 함, 오류 없음
    // 스프링 부트에서는 자동 빈과 수동 빈 충돌 시 오류로 되도록 기본 설정 변경
//    @Bean(name = "memoryMemberRepository")
//    MemoryMemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}
