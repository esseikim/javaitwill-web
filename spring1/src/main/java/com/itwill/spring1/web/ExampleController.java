package com.itwill.spring1.web;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itwill.spring1.dto.UserDto;

// xml 파일에 설정된 Dispatcher Servlet. 우리가 만든 Controller: Handler Controller.
import lombok.extern.slf4j.Slf4j;
//  톰캣이 web.xml에서 읽고, DPS는 Request 객체 생성, Handler Mappings을 통해 해당 Controller(Handler Controller)의 메서드 호출.
//  servlet-context.xml 파일에서 HandlerMappings, View Resolver's 설정.
//  DPS 생성: 생성자의 parameter로 contextConfigLocation 호출. 

/*
 *  POJO(Plain Old Java Object): 평범한 자바 객체.
 *  특정 클래스를 상속해야만 하거나, 상속 후에 메서드들을 override 해야하는 클래스가 아님. 즉, 일반 클래스.
 *  SpringFramework: POJO로 작성된 클래스를 Controller로 사용 가능.
 *  Request, Reponse 객체 없이(JSP(1), JSP(2) 상속 필요) forward, redirect, getSession 가능.
 *  DPS가 전부 실행 -> Servlet을 상속받지 않아도, POJO가 Controller가 될 수 있음.
 *  
 *   컴포넌트 애너테이션
 *   @Component, @Controller, @Service, @Repository, ... 
 *   @Controller: DPS에게 알림 -> 우리가 만든 ExampleController(HandlerController)의 메서드 호출 가능.
 *    <-  servlet.context.xml(DPS가 읽는 파일) 설정: @을 아래 명시한 패키지 밑에서만 찾음.
 *   <context:component-scan base-package="com.itwill.spring1.web" />
 */

@Slf4j // Logger 객체(log) 생성
@Controller // Spring MVC 컴포넌트 애너테이션 중 하나, Controller 컴포넌트라는 것을 DPS에게 알려줌 -> DPS는 이 클래스를 보고 객체 생성, 메서드 호출하게 됨.
public class ExampleController {
	// cf) JSP2의 경우 @WebServlet(name = "homeController", urlPatterns = {""}).
	@GetMapping("/") // ContextRoot에 있는 Get 방식만을 처리하는 메서드. 요청주소 "/": ContextRoot.
	// forward나 redirect가 아닐 경우, model에 저장을 해서 전달을 하면 됨.
	public String home(Model model) { // Model: Controller에서 뷰에 데이터 전달 시 파라미터로 선언. DPS는 Model 객체를 생성, 주입 > home() 메서드 호출.
		log.info("home()");

		LocalDateTime now = LocalDateTime.now(); // 뷰에 전달할 데이터.
		model.addAttribute("now", now); // 생성된 모델 객체의 주소값을 받고, 현재 시간 데이터 추가. "now" 변수명은 JSP에서 el로 사용 가능. 모델 객체의 이용: 서블릿을
										// 직접 만들 때(JSP1, JSP2)의 request.setAttribute와 같은 효과.

		return "index"; // 뷰(jsp 파일)의 이름(/WEB-INF/views/index.jsp). 모델 & 뷰 리턴.
		// DPS는 index.jsp 파일로 포워딩하면서 모델을 전달(response).
		// JSP는 브라우저에 HTML(index.jsp)을 동적으로 생성. DispatcherServlet에게 리턴(메서서드의 끝 = 메서드의 호출).
	}

	@GetMapping("/ex1") // ContextRoot 제외 주소. 요청 주소: `/ex1`.
	public void example1() {
		log.info("example1() 호출");

		// 컨트롤러 메서드가 void인 경우(뷰의 이름을 리턴하지 않는 경우)
		// 요청 주소의 이름이 뷰의 이름이 됨.
	}

	// 요청 처리할 Controller 메서드를 만들면, DispatcherServlet은 client의 request값을 분석, 해당
	// parameter를 전달.
	// 주의: 메서드의 파라미터 변수 선언은 리퀘스트 파라미터의 name과 동일하게 작성.
	@GetMapping("/ex2")
	public void getParamEx(String username, int age) {
		log.info("getParamEx(username={}, age={})", username, age);

		// 동일하게 작성해야 클라이언트에서 보내준 값이 제대로 파싱됨.
		// req.getParameter 리턴 값은 String.
		// 만약, 다르게 입력을 했을 경우: parameter name resolution. Compile the affected code with
		// '-parameters' instead.
		// -> String의 경우, req.getParameter: null.
		// -> int의 경우, req.getParameter: Integer.parserInt에서 execption됨.
		// -> 서버와 브라우저에서 전부 에러 발생.
	}

	@PostMapping("/ex3")
	public String getPararmEx2(

			// DispatcherServlet은 RequestParam을 동일한 name으로 찾음 > @ 명시x, name 불일치 > name을 찾을
			// 수가 없어 null. log: getPararmEx2(name=null, age=123).
			@RequestParam(name = "username") String name, @RequestParam(defaultValue = "0") int age) { // RequestParam는 항상 String이어서 기본값 항상 문자열로 설정.
		// 컨트롤러 메서드를 선언할 때, 파라미터 선언 앞에 @RequestParam 애너테이션을 사용하는 경우:
		// (1) 메서드 파라미터와 요청 파라미터 이름이 다를 때, name 속성으로 요청 파라미터 이름을 설정.
		// (2) 요청 파라미터 값이 없거나 비어있을 때, defaultValue 속성 설정.(> 서버쪽에서 exception이 발생하지 않도록 기본값 설정).

		log.info("getPararmEx2(name={}, age={})", name, age);

		return "ex2";
	}

	// 요청파라미터의 개수가 많을 경우, 요청 파라미터를 멤버변수로 갖는 클래스(DTO)를 만들고, 메서드의 파라미터로 객체 선언.
	// UserDto 모델 클래스: DB 필드 선언, @Data
	// 서비스 계층의 메서드 호출하면서, 아규먼트 그대로 넘겨주면 됨. <- Spring 프레임 워크 사용하는 이유
	@GetMapping("/ex4")
	public String getPararmEx3(UserDto user) { // DPS: request.getParam(name, age);
		log.info("getParamEx3(user={})", user); // getParamEx3(user=UserDto(username=홍길동, age=2))

		// DispatcherServlet은 컨트롤러의 메서드를 호출하기 위해서,
		// 1. 요청 파라미터들을 분석(request.getParameter()).
		// 2. UserDto의 기본 생성자를 호출해서 객체를 생성.
		// 3. 요청 파라미터들의 이름으로 UserDto의 setter 메서드를 찾아서 호출.(DTO의 필드명, 요청 파라미터의 이름 동일하게
		// 설정).
		// 4. UserDto 객체를 컨트롤러 메서드를 호출할 때 argument로 전달.
		// jsp로 가는 경우 보통 forward를 함.

		return "ex2";
	}

	// 순서: index.jsp -> ExampleController -> sample.jsp
	@GetMapping("/sample")
	public void sample() {
		log.info("sample()");
		// INFO: 이름이 [/spring1]인 컨텍스트를 다시 로드하는 것을 완료했습니다. <- 변경된 메서드가 적용했음을 의미.

	}

	@GetMapping("/forward")
	public String forwardTest() {
		log.info("forwardTest()");

		// 컨트롤러 메서드에서 다른 페이지(요청 주소)로 forward하는 방법:
		// "forward:"으로 시작하는 문자열을 리턴하면,
		// DispatcherServlet은 리턴값이 뷰의 이름이 아니라 포워드 이동할 페이지 주소로 인식. 요청 처리 할 수 있는 메서드 재호출.
		// 화면엔 sample.jsp가 보이지만, 요청주소 변경x.
		// forward 방식: 처음의 요청주소가 그대로 남아있음. > http://localhost:8081/spring1/forward
		// >> 콘솔 로그: forwardTest(), sample()

		return "forward:/sample"; // DispatcherServlet: reqest.getParmetar("/sample");
	}

//      post 방식의 요청-> 요청 처리 후 redirect ->클라이언트가 다시 get 방식의 요청을 보냄.
//      새글 작성의 경우, 작성된 글 post 방식으로 요청. insert 된 후 create 페이지가 아닌 목록 페이지로 이동.
//      서버 쪽에서 목록 페이지로 redirect 한 것. 클라이언트가 다시 get 방식의 요청을 보냄 > 화면에 보여지는 것: 새글 작성 페이지가 아닌 목록페이지.

	@GetMapping("/redirect")
	public String redirectTest(RedirectAttributes attrs) { // redirect 할 때 사용하는 속성.
		log.info("redirectTest()");

		attrs.addFlashAttribute("redAttr", "테스트");
		// addFlashAttribute(): 컨트롤러 메서드에서 리다이렉트 되는 페이지(sample.jsp)까지 유지되는 정보를 저장할 때:
		// flash: 임시, 한번 리다이렉트까지만 유지되는 정보-> sample.jsp까지 사용 가능.
		// redirect의 경우 forward와 달리 요청정보가 유효하지 않고 끊어지기 때문.

		return "redirect:/sample";
		// 주소 유지 x : http://localhost:8081/spring1/sample.
		// 새로운 요청이 만들어지는 것이 redirect.
	}

	// PRG 패턴: Post -> Redirect -> Get
	// 다음 패턴을 자주 사용하기에 redirect를 많이 사용함.
	// model의 경우 서로 다른 요청이 존재하기에 2개의 요청 모두에 존재하지 않음.
	// -> 첫번째 요청과 전혀 다른 2번째 요청이 있을 떄까지 존재하는 객체가 필요.
	// Spring에서는 RedirectAttributes 해당(Servlet에서는 Session).

	// Controller 메서드에서 다른 페이지(JSP가 아닌 다른 요청주소)로 redirect하는 방법:
	// "redirect:"으로 시작하는 문자열을 리턴하면,
	// DispatcherServlet은 리턴값이 뷰의 이름이 아니라 redirect로 이동할 페이지 주소로 인식.

}