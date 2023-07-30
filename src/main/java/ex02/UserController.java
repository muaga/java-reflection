package ex02;

// 3) 미리 지정해 놓은 요청문을 입력을 하면 어노테이션으로 실행할 수 있도록 연결된 메소드가 실행되도록 한다.
public class UserController {
    // RequesetMapping의 어노테이션을 만들어, 런타임 시 자동으로 호출되도록 했다.
    // 지정된 String 타입의 문구(해당 메소드는 "/login")를 키보드에 입력 시 JVM은 @RequestMapping 어노테이션을
    // 확인 후, 각 지정해놓은 문구에 맞는 메소드를 자동 실행 시켜준다.
    // 이 동작을 [리플랙션]이라고 한다.
    // 리플랙션 : 동적으로 실행 시 모든 파일을 분석, 분석 후 실행한다.
    // 근데 만약, 어노테이션이 있으면 리플랙션은 어노테이션이 붙은 파일만 분석, 실행한다.
    // 어노테이션 : JVM에게 위치를 제공하는 것(=깃발)

    @RequestMapping(uri ="/login")
    public void login(){
        System.out.println("login() 호출됨");
    }

    @RequestMapping(uri = "/join")
    public void join(){
        System.out.println("join() 호출됨");
    }
    @RequestMapping(uri = "/check")
    public void check(){
        System.out.println("check() 호출됨");
    }

}
