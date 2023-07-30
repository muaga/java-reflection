package ex02;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Scanner;

// 어노테이션을 사용하는 App을 홍길동이 다시 만들었다.
// 그러기 위해 어노테이션 클래스를 먼저 생성해야 한다.
public class App {

    // 2) @RequestMapping이 붙은 메소드를 실행하기 위한 코드를 작성한다.
    public static void findUri(UserController uc, String uri) throws Exception{
        //매개변수는 사용할 메소드가 있는 UserController 클래스와 입력받을 키워드에 맞는 메소드를 실행하기 위한 uri를 넣는다.
        // throws Exception을 해주는 이유 : 입력받은 uri 자체가 해당 메소드들에 없을 때, 발생할 예외를 처리하기 위해 해준다.

        boolean isFind = false;
        // false로 초기화를 하는 이유는 처음 uri와 일치하는 메소드를 찾지 못한 상태로 하여
        // 이후 uri가 입력되었을 때, 입력받은 uri와 메소드의 uri가 일치한 지의 여부 확인과
        // 여부에 따라 적절한 실행이 되도록 한다.

        Method[] methods =  uc.getClass().getDeclaredMethods();
        // getClass() : uc 클래스의 정보들을 호출한다.
        // getDeclaredMethods() : uc 클래스의 모든 메소드를 호출한다.
        // 주로 Method[]와 함께 사용한다.
        // Method [] methods : uc 클래스의 메소드를 배열에 넣는다.

        for(Method mt : methods) {
            Annotation anno = mt.getDeclaredAnnotation(RequestMapping.class);
            // getDeclaredAnnotation() : for문을 돌면서 담긴 메소드를 mt에 담은 다음, 괄호 안에 있는 어노테이션이 붙은 class에서 메소드를 찾는다.
            // Annotation : 해당 메소드에 적용된 어노테이션이 반환하는 객체가 어떤 타입인 지 모르기 때문에, 모든 어노테이션의 부모인 Annotation에 받는다.
            // 해당 방법이 Exception과 같이 가장 안전한 방법이다.

            RequestMapping rm = (RequestMapping) anno;
            System.out.println("1");

            // Annotation 타입으로 받은 anno(최상위)를 RequestMappig 어노테이션 타입으로 사용하기 위해 다운캐스팅을 한다.
            // Annotation 타입으로 받은 객체는 공통된 어노테이션을 동작하므로, 특정 어노테이션을 사용하고 싶을 때는 다운캐스팅을 해야한다.

            if(rm.uri().equals(uri)){
                System.out.println("2");

                // RequestMapping 어노테이션이 붙은 메소드의 uri가 입력 받은 uri와 동일하다면

                isFind = true;
                // 일치한 메소드를 찾았다고 하면, isFind는 true가 되어 아래의 코드가 실행된다.

                mt.invoke(uc);
                // invoke() - 리플랙션 중 하나 : 실행 중인 객체나 클래스의 메소드를 실행할 수 있다.
                // 어떤 상황에서 어떤 메소드를 실행시킬 지 런타임시 결정해야 하는 경우, invoke()를 주로 사용한다.
                // findUri()의 Exception과 상관관계가 있는 이유는, invoke에서 발생할 수 있는 예외도 함께 위임을 했기 때문에

            }
            if(isFind == false){
                System.out.println("404 Not Found");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String uri = sc.nextLine();

        findUri(new UserController(), uri);

        // ex01과 달리
        // 임꺽정이 원하는 기능(메소드)를 만들 때 마다, 지정해놓은 @RequestMapping만 메소드 명 위에 붙이고,
        // 메소드 실행할 조건문을 적어놓으면 기능이 실행 할 수 있도록 만들었다.
        // 이를 통해 임꺽정은 원하는 기능을 마음대로 만들어도, 홍길동에게 유지보수 신청을 할 필요가 없었다.
        // 하지만 임꺽정이 다른 새로운 클래스를 만든 후, 메소드 위에 @RequestMapping을 붙였더니 그 클래스는
        // 실행이 되지 않았다. @RequestMapping은 RequestMapping가 붙은 메소드만 실행될 수 있는데, findUri의
        // 매개변수가 UserController 였기 때문이다.
        // 결국, 홍길동이 다른 클래스의 메소드를 사용한다면, 매개변수를 수정하는 등 또 유지보수가 필요했다.
    }

}
