package ex01;

// 1차 개발자 : 홍길동

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        // 키보드로 입력받은 값을 path라는 객체명을 지정했다.

        UserController uc = new UserController();
        // UserController의 객체를 생성한 후, uc라는 참조변수를 만든다.

        if(path.equals("/login")){
            uc.login();
        } else if (path.equals("/join")) {
            uc.join();
        }
        // if 문으로 키보드로 입력받은 path 값이 login, join일 때
        // uc의 각 메소드가 실행된다.

        // -----
        // 임꺽정이 메소드를 계속 추가할 때 마다, 홍길동이 실행 코드를 작성해줘야한다.
        // 1차-2차 개발자의 끊임없는 유지보수로 인해 해당 코드는 사용하기 불편하다.
    }
}
