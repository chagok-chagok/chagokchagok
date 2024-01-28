import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAspect {

    @Before("")
    public void checkLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 여기에서 "anonymousUser"는 Spring Security에서 로그인되지 않은 사용자를 나타냅니다.
        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            throw new SecurityException("로그인이 필요합니다.");
        }
    }
}
