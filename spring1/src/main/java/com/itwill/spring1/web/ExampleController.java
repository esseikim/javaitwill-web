package com.itwill.spring1.web;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itwill.spring1.dto.UserDto;

// xml 파일에 설정된 Dispatcher Servlet. 우리가 만든 controller: Handler controller
import lombok.extern.slf4j.Slf4j;
//  톰캣이 web.xml에서 읽고, DPS는 Request 객체 생성, handler Mapping을 통해 해당 controller의 메서드 호출
//  xml 파일에서 handlermapping, view resolver's  설정
//  DPS 생성될 때 생성자의 parameter로 contextConfigLocation  호출 

/*
 *  POJO(Plain Old Java Object): 평범한 자바 객체
 *  특정 클래스를 상속해야만 하거나, 상속 후에 메서드들을 override 해야하는 클래스가 아님. 즉, 일반 클래스
 *  SpringFramework: POJO로 작성된 클래스를 Controller로 사용 가능
 *  Request, Reponse 객체 없이(원래는 상속 필요) forward, redirect, getSession 가능
 *  DPS가 전부 실행 -> servlet을 상속받지 않아도, POJO가 controller가 될 수 있음
 *  
 *   컴포넌트 애너테이션
 *   @Component, @Controller, @Service, @Repository, ... 
 *   @Controller: DPS에게 알림 -> 우리가 만든 ExampleController의 메서드 호출 가능.
 *    <-  servlet.context.xml(DPS가 읽는 파일) 설정: @을 아래 명시한 패키지 밑에서만 찾음
 *   <context:component-scan base-package="com.itwill.spring1.web" />
 */

@Slf4j // Logger 객체(log) 생성
@Controller // Spring MVC 컴포넌트 애너테이션 중 하나 // 여기서는 작성만 함. 호출은 dispatcherServlet이.
// Controller 컴포넌트라는 것을 DPS에게 알려줌. DPS는 이 클래스를 보고 객체 생성, 메서드 호출하게 됨
public class ExampleController {
     
    @GetMapping("/") //  context root에 있는 Get 방식만을 처리하는 메서드. 요청주소: ("/" : context root)  리턴: jsp(String)
    //(지민)
    // forward나 redirect가 아닐 경우에 model에 저장을 해서 전달을 하면 됨.
    // controller에서 뷰로 데이터를 전달할 경우
    public String home(Model model) {
        log.info("home()");
        
        LocalDateTime now = LocalDateTime.now();  // 뷰에 전달할 데이터를 세팅.
        model.addAttribute("now", now); 
        
        return "index"; 
        // 애는 DispatcherServlet에게 다음을 넘겨줘. DispatcherServlet이 다음 뷰의 이름을 확인을 하고 jsp 호출 후 forward방식으로 응답을 해.

   
        // 뷰(jsp 파일)의 이름(/WEB-INF/views/index.jsp)  
        // servlet-context. xml: prefix, suffix setting -> 파일 이름만 리턴 -> dps는 index.jsp 파일로 forward
        
        // import org.springframework.ui.Model
        // model객체는 dps가 가지고 있음. dps는 model 타입 객체를 생성, 주입 ->  home() 메서드 호출 
        // model.addAttribute("now", now); <- 생성된 모델 객체의 주소값만 받아와서, 현재 시간 데이터 추가
        // 메서서드의 끝 = 메서드 호출. dps는 이 모델을 뷰에 전달
        // jsp로 포워딩하면서 모델을 줌(response): jsp가 우리의 브라우저에 html(index.jsp)을 동적으로 만들어 냄,  DispatcherServlet에게 리턴)
        

        //  Model: 뷰에 데이터 전달 시 파라미터로 선언
        //  모델 객체를 이용하는 것 = 서블릿을 직접 만들 때의 request.setAttribute와 같은 효과
        //  모델 & 뷰를 리턴  
         
        // "now" 변수명은 jsp에서 el로 사용 가능함. 
        //  링크(get 방식)의 주소: href=""
        
    }
  
    @GetMapping("/ex1") // contextroot 제외 주소. get 방식의 요청방식을 처리. 요청주소:  /ex1
    public void example1() {
        log.info("example1() 호출");
        
        //  리턴: dps는 jsp를 요청주소로 찾음(view resolver's 가 알려줌) 요청주소의 이름이 뷰의 이름이 됨.
    } 

 
    // 요청 처리할 Controller 메서드를 만들면 dispacherServlet가 request 분석해서, request 안의 값들을 넣어줌. 
    // 주의: 메서드의 파라미터 변수선언은 리퀘스트 파라미터 이름과 동일시
    // 클라이언트가 보내준 요청 주소 분석 
    @GetMapping("/ex2")
    public void getParamEx(String username, int age) {
        log.info("getParamEx(username={}, age={})", username, age);
    }
    
      // 결과: parameter name resolution. Compile the affected code with '-parameters' instead 
      //  getParamEx(username=홍길동, age=16)
      //  age1로 할 시 에러. age1을 리퀘스트파라미터 못 찾음. 스트링 타입. 기본 값 null -> null 값: integer.parseInt 불가. 
      //  동일 => 클라이언트에서 보내준 값이 제대로 파싱됨. 
    
    
    //(지민)
    // req.getParameter 리턴 값은 String임. -> 변환은 DispatcherServlet이 담당함. 호출하는 담당자가 DispatcherServlet인데 client한테 받은 request값을 분석해서 해당 parameter를 전달해줌. 
    // argument 조건: request parameter의 이름과 같게 작성해야 함.
    // 만약, 다르게 입력을 했을 경우
    // -> String의 경우  request parameter을 못 찾으면 null.
    // -> int의 경우 request parameter을 못 찾으면 req.getParameter 리턴 값은 String으로 null값이 리턴이 되는데 Integer.parserInt에서  execption됨.
    // -> 서버와 브라우저에서 다 에러 뜸
    // -> 해결 방안: 아래 참고.
    
    
    @PostMapping("/ex3")
    public String getPararmEx2(
            // @RequestParam 설정으로 불일치 가능: RequestParam의 name이 "username"인 것을 찾아 name(불일치)에 넣어라
            // but, 요청파라미터의 이름과 동일하게 하는 방법이 제일 간편
            // 없을 경우, log 출력값: getPararmEx2(name=null, age=123)
            @RequestParam(name = "username")String name,
            // 요청 파라메타 이름과 변수이름이 동일하지 않을 경우 -> requestParam이 name을 찾을 수가 없어 null.
            @RequestParam(defaultValue = "0") int age
            // ==> @RequestParam(defaultValue = "0"): RequestParam는 항상 String이어서 기본값 설정은 항상 문자열로.

            // RequestParam: 항상 문자열 -> 문자열을 int값 변환하는 것. getPararmEx2(name=홍길동, age=0)
            // 요청파라미터의 개수가 많을 경우, 요청 파라미터를 멤버변수로 갖는 클래스(DTO)를 만들고, 메서드를 만들 때 메서드에 그 객체를 선언해주면 됨. 
    
            // (지민)
            // ==> @RequestParam 사용하는 이유:
            // DispatcherServlet은 RequestParam이 똑같은 이름을 찾기에 RequestParam과 Parameter의 변수가 동일한 것을 알려줄 때
            // RequestParam이 비어있는 문자열이나 null인 값을 서버쪽에서 exception이 발생하지 않도록 기본값을 설정함.
            ) {
        log.info("getPararmEx2(name={}, age={})", name, age);
       
        return "ex2"; 
    }
    
    
      @GetMapping("/ex4")
      public String getPararmEx3(UserDto user) {
          log.info("getParamEx3(user={})", user);
          // getParamEx3(user=UserDto(username=홍길동, age=2))
          
          // @toString ->  UserDto에서 잘 만들어진 toString()을 볼 수 있음.  
          //  UserDto - 모델 클래스: 테이블의 데이터 들을 변수 선언 후 @Data
          
          // Dps 역할: request.getParam(name, age);    기본 생성자 호출 UserDto 객체 생성, setter 메서드 
          // 요청 파라미터 이름 앞에 set만 붙여서 setter메서드 호출. DTO의 변수선언: 요청 파라미터의 이름 동일하게  
          // 서비스 계층이 가지고 있는 메서드 호출하면서, 넘겨주기만 하면 됨 <- spring 프레임 워크 사용하는 이유 
          
          return "ex2"; 
          // DispatcherServlet은 컨트롤러의 메서드를 호출하기 위해서,
          // 1. 요청 파라미터들을 분석(request.getParameter()).
          // 2. UserDto의 기본 생성자를 호출해서 객체를 생성.
          // 3. 요청 파라미터들의 이름으로 UserDto의 setter 메서드를 찾아서 호출.
          // 4. UserDto 객체를 컨트롤러 메서드를 호출할 때 argument로 전달.
          // jsp로 가는 경우 보통 forward를 함. 
    }
      
      
      
      // 순서: index.jsp -> ExampleController -> sample.jsp
      @GetMapping("/sample")  
      public void sample() {
          log.info("sample()");
          //INFO: 이름이 [/spring1]인 컨텍스트를 다시 로드하는 것을 완료했습니다. <- 변경된 메서드를 적용했음을 의미
          
      }
      
      
      
      @GetMapping("/forward")
      public String forwardTest() {
          log.info("forwardTest()");
          
          return "forward:/sample"; 
          // (지민)
          // "forward:/sample"를 DispatcherServlet에게 전달을 하여 DispatcherServlet은 접두사를 빼고 해당 요청 주소를 reqest.getParmetar("/sample")을 찾아서
          // 해당 요청주소를 다시 controller에게 넘김. controller에서 해당 요청 주소를 찾아서 DispatcherServlet이 리턴 값을 받고 jsp를 호출 및 응답함.
          
          
          // 컨트롤러 메서드에서 다른 페이지(다른 요청 주소)로 forward(jsp로 직접 forward  x)하는 방법:
          // DPS는 리턴값이 뷰의 이름이 아닌 포워드 이동할 페이지 주소로 인식
          // 뷰(jsp)의 이름이 아닌 forward 주소임을 dispatcherServlet이 알게 되고, 요청 처리 할 수 있는 메서드 재호출. 
          // 화면엔 sample.jsp가 보이지만, 요청주소는 그대로 남아있음
          // forward 방식: 처음의 요청주소가 그대로 남아 있음 ->  http://localhost:8081/spring1/forward
          // 콘솔 로그: forwardTest(), sample() 
          
          // (지민)
          // Controller 메서드에서 다른 페이지(다른 요청 주소, jsp가 아님)로 forward하는 방법:
          // "forward:"으로 시작하는 문자열을 리턴하면,
          // DispatcherServlet은 리턴값이 뷰의 이름이 아니라 포워드 이동할 페이지 주소로 인식.
          // "/sample": 요청 주소. -> dispatcherServlet이 다시 호출함.
          // -> 같은 웹 서버 안에 있는 다른 페이지로 forward로 함. 이런 메서드를 호출할 수 있는 메서드를 다시 호출.
          // 처음 요청주소가 그대로 남아있는 방식: forward.
          // => 어차피 같은 페이지로 갈 것인데 이렇게 forward를 하진 않음. 잘 사용 안함.
      
      
      }
 
      
      
//      post 방식의 요청-> 요청 처리 후 redirect ->클라이언트가 다시 get 방식의 요청을 보냄
//      새글 작성의 경우, 작성된 글 post 방식으로 요청. insert 된 후 create 페이지가 아닌 목록 페이지로 이동
//      서버 쪽에서 목록페이지로 redirect 한 것. 클라이언트가 다시 get 방식의 요청을 보냄(화면에 보여지는 것: 테이블)
         
      @GetMapping("/redirect")
      public String redirectTest(RedirectAttributes attrs) {  // redirect 할 때 사용하는 속성
          log.info("redirectTest()");
       
        
          attrs.addFlashAttribute("redAttr", "테스트"); 
          // addFlashAttribute(): 컨트롤러 메서드에서 리다이렉트 되는 페이지(sample.jsp)까지 유지되는 정보를 저장할 때:
          // flash: 임시, 한번 리다이렉트까지만 유지되는 정보-> sample.jsp까지 사용 가능.
          // redirect의 경우 forward와 달리 요청정보가 유효하지 않고 끊어지기 때문. 
          
          
          return "redirect:/sample";
          // 주소 유지 x : http://localhost:8081/spring1/sample 
          // 새로운 요청이 만들어지는 것이 redirect
      }
      // (지민)
      // PRG 패턴: Post -> Redirect -> Get
      // 다음 패턴을 자주 사용하기에 redirect를 많이 사용함.
      // model의 경우 서로 다른 요청이 존재하기에 2개의 요청모두에 존재하지 않음. 
      // -> 첫번째 요청과 전혀 다른 2번째 요청이 있을 떄까지 존재하는 객체가 필요
      // Spring에서는 RedirectAttributes 해당.(servlet에서는 Session)
      
      // Controller 메서드에서 다른 페이지(다른 요청 주소, jsp가 아님)로 redirect하는 방법:
      // "redirect:"으로 시작하는 문자열을 리턴하면,
      // DispatcherServlet은 리턴값이 뷰의 이름이 아니라 redirect로 이동할 페이지 주소로 인식.
}
