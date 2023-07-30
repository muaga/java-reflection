package ex03;

public class UserController {

    @RequestMapping(uri = "/login")
    public void login(){
        System.out.println("login 실행");
    }

    @RequestMapping(uri = "/join")
    public void join(){
        System.out.println("join 실행");
    }
}
