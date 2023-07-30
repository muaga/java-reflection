package ex03;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class App {

    // 3) 패키지 디렉토리 내의 .class 파일을 찾아 인스턴스를 생성하고, 생성된 인스턴스들을
    // instances 리스트에 추가한다.
    public static Set<Object> componentScan(String pkg) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 현재 사용 중인 스레드의 콘텍스트클래스로더에 찾은 후, 클래스로더에 담는다.
        // 자바에서 클래스를 동적으로 로딩하기 위해 classLoader를 사용한다.
        // ClassLoader : 클래스의 파일을 찾고, 로드하고 정의하는 담당
        // ContextClassLoader : 각각의 스레드마다 할당되는 ClassLoader, 각 독립적으로 사용된다.

        Set<Object> instances = new HashSet<>();
        // HashSet 타입의 instances 리스트를 만든다.

        URL packageUrl = classLoader.getResource(pkg);
        // classLoader를 사용하여 지정된 패키지(pkg)의 리소스를 찾아서 URL타입으로 가지고 온다.
        // 해당 URL을 통해 리소스에 접근하거나 가져올 수 있다.
        // URL : 인터넷 상에서의 패키지의 위치(프로토콜이 적용된 URL)
        // getResource() : 클래스로더를 통해 지정된 패키지 내의 리소스를 찾기위해 사용된다.
        // 클래스 로더가 지정된 패키지를 검색하는 순서대로 클래스 경로(ClassPath)에서 리소스를 찾는다.
        // pkg : 패키지 내 리소스의 경로, 이 경로는 패키지 내에 있는 특정 리소스 파일을 지정한다.
        // * 지정된 패키지 : 호출하는 쪽에서 componentScan(ex03)와 같은 패키지 이름을 전달한다.

        File packageDirectory = new File(packageUrl.toURI());
        // packageUrl을 URI 타입으로 변환한 후, file 타입으로 가져온다.
        // URL -> URI 변환 이유 : URL보다 범위가 훨씬 커서 다양한 종류의 식별자를 포함한다.
        // 이를 통해 해당 패키지를 Java 파일 객체로 변환한다.
        // ex03을 찾는다. 실제로 컴파일된 경로를 찾아서, 파일 객체를 만든다.
        // File : 파일이나 디렉토리를 나타내는 클래스, 패키지의 실제 파일 시스템 경로
        // packageDirectory : File 객체로서 리소스가 위치한 디렉토리를 가리킨다. 리소스 디렉토리에 대한 파일 조작 작업을 수행할 수 있다.
        // 예를 들어, 디렉토리 내의 파일 목록을 가져오거나, 새 파일을 생성하거나, 디렉토리를 삭제하는 등의 작업을 할 수 있다.
        // packageUrl.toURI() : packageUrl의 URL을 URI로 변환
        // URI : 통합 자원 식별자, 위의 방법은 리소스의 위치를 나타내는 방법

        // 해당 패키지 내부의 모든 파일을 찾아서 Set<class>에 담기
        for(File file : packageDirectory.listFiles()) {
            // packageDirectory 디렉토리 내에 있는 파일들을 순회한다.

            if (file.getName().endsWith(".class")) {
                // 만일, file의 이름의 끝이 ".class"로 끝난다면 true / 아니면 false
                // endsWith() : 문자열이 지정된 접미사로 끝나는 지에 따라 true/false를 나타낸다.

                String className = pkg + "." + file.getName().replace(".class", "");
                // .class 파일인 경우, 해당 패키지 속 클래스의 전체 이름을 생성하는 과정이다. = className
                // 결과 값은, App.class ---> ex03.App 이렇게 변경될 것이다.
                // replace() : target의 문자열을 replacement 문자열로 변경된다.


                    Class cls = Class.forName(className);
                if (cls.isAnnotationPresent(Controller.class)) {
                    // class.forName() : 컴파일 시 직접적인 참조 없이, 클래스를 로드한다.
                    // 로드한 Class 객체를 cls에 담는다.

                    instances.add(cls.newInstance());

                }
            }
        }

        return instances;
    }


    // 2) @RequestMapping이 붙은 메소드를 실행하기 위한 코드를 작성한다.
    public static void findUri(Set<Object> instances, String uri) throws Exception {
        boolean isFind = false;
        for (Object obj : instances) {
            //

            Method[] methods = obj.getClass().getDeclaredMethods();
            // getClass() : uc 클래스의 정보들을 호출한다.
            // getDeclaredMethods() : uc 클래스의 모든 메소드를 호출한다.
            // 주로 Method[]와 함께 사용한다.
            // Method [] methods : uc 클래스의 메소드를 배열에 넣는다.

            for (Method mt : methods) {
                Annotation anno = mt.getDeclaredAnnotation(RequestMapping.class);

                RequestMapping rm = (RequestMapping) anno;
                // 최상위 부모인 Annotation 타입으로 메소드를 받은 anno를 다운캐스팅 하여 rm에 담는다.

                if (rm.uri().equals(uri)) {
                    isFind = true;
                    mt.invoke(obj);
                }
            }
        }
        if (isFind == false) {
            System.out.println("404 Not Found");
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String uri = sc.nextLine();

        Set<Object> instances = componentScan("ex03");
        // componentScan 메소드에 직접적인 @Controller 언급은 없지만,
        // 지정도니 패키지 내에 있는 클래스를 스캔하여 인스턴스를 생성하는 과정에서
        // @Controller가 있다면, 해당 클래스의 인스턴스도 생성될 것이다.

        findUri(instances, uri);

    }
}
/**
 * class Loader
 * JVM의 클래스 로더(class loader)는 실행 시에 필요한 클래스를 동적으로 메모리에 로드하는 역할을 한다.
 * 먼저 기존에 생성된 클래스 객체가 메모리에 존재하는지 확인하고 있으면 객체의 참조를 반환하고,
 * 없으면 classpath에 지정된 경로를 따라서 클래스 파일을 찾아 해당 클래스 파일을 읽어서 Class 객체로 변환한다.
 * 만일 못 찾으면 우리가 익히아는 ClassNotFoundException 예외를 띄우게 된다.
 * 클래스로더는 계층 구조로 이루어져 있으며, 각각의 클래스로더는 부모 클래스로더를 가지고 있다.
 */


