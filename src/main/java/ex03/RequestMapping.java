package ex03;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String uri();
    // 1) 사용자가 지정한 문구를 입력 시 @RequestMapping이 붙은 메소드가 실행되도록 했다.
}
