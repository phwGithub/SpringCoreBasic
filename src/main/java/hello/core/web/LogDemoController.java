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
    private final ObjectProvider<MyLogger> myLoggerObjectProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        MyLogger myLogger = myLoggerObjectProvider.getObject();
        String requestUrl = request.getRequestURL().toString();
        myLogger.setRequestUrl(requestUrl);

        myLogger.log("controller test");
        logDemoService.logic("testId");

        return "OK";
    }
}
