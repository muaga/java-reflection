package ex02;

// 홍길동이 만든 어노테이션 클래스


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
// METHOD : 메소드가 실행된다.
@Retention(RetentionPolicy.RUNTIME)
// RUNTIME : 런타임 시 어노테이션이 실행된다
public @interface RequestMapping {
    // @interface : 사용자가 커스텀해서 사용할 수 있는 어노테이션
    // Target과 Retention을 작성해야 한다.

    String uri() default "";
    // 1) 지정해 놓은 uri을 키보드로 입력 시 해당 메소드가 실행되도록 했다.
}


