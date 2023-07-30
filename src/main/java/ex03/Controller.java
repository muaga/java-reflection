package ex03;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    // 2) @Controller를 만들어서 각 클래스도 자동 실행할 수 있게 하여 그 속에 존재하는
    // @RequesMapping이 붙은 메소드를 실행할 수 있게 하려고 한다.
}
