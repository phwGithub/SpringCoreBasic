package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    // 이대로 스프링을 실행하면 오류가 남 왜 why? myLogger의 스코프가 request라 요청이 없으면 빈이 잡히지 않는다
    // Provider의 DL 기능을 통해 해결 가능하다
    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
//        MyLogger myLogger = myLoggerObjectProvider.getObject();
        String requestUrl = request.getRequestURL().toString();

        // myLogger = class hello.core.common.MyLogger$$EnhancerBySpringCGLIB$$b19774e
        // 바이트 코드를 조작하여 myLogger를 상속하는 임시 myLogger를 만들어 둠 => 가짜 프록시 객체는 싱글톤 빈처럼 동작함
        // 실제 요청이 생길 때 진짜 request 빈을 찾아서 동작
        // ** 핵심은 진짜 객체 조회를 꼭 필요한 시점까지 지연처리 한다는 것이다
        System.out.println("myLogger = " + myLogger.getClass());
        myLogger.setRequestUrl(requestUrl);

        myLogger.log("controller test");
        logDemoService.logic("testId");

        return "OK";
    }
}
