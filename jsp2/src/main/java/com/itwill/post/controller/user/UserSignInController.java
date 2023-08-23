package com.itwill.post.controller.user;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.post.model.User;
import com.itwill.post.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class UserSignInController
 */
@WebServlet(name = "userSignInController", urlPatterns = { "/user/signin" })
public class UserSignInController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  private static final Logger log = LoggerFactory.getLogger(UserSignInController.class);
  private final UserService userService = UserService.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("doGet()");
		
		request.getRequestDispatcher("/WEB-INF/user/signin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("doPost()");
		
		// 요청 파라미터에 포함된 username과 password를 찾음.
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// Service 계층의 메서드를 호출해서 로그인 성공/실패 여부를 판단. 
		User user = userService.signIn(username, password);
		log.info("로그인 결과: {}", user);
		
		if(user == null) { // username 또는 password가 일치하지 않은 경우 > 로그인 실패.
		    // 로그인 페이지로 이동(redirect)
		    response.sendRedirect("/post/user/signin"); // 위로 다시 올라감(PRG pattern).    
		    // request./ response. : /context-root/path?query-string (Context-root 포함해 작성).
		    
		    return; // doPost 메서드 종료.
		}
		
		// 로그인 성공
		// (1) 셰션(session)에 로그인 정보를 저장.
		// -> 웹 브라우저(request): 항상 Session ID를 쿠키를 통해 저장 및 전달 -> 해당 클라이언트의 세션을 찾아 찾아 데이터를 유지하고 관리할 수 있음.
		HttpSession session = request.getSession(); // 현재 요청에 대한 세션 객체를 반환(요청에 세션 ID가 포함되어 있다면 해당 세션을 찾아서 반환하고, 세션 ID가 없는 경우에는 새로운 세션을 생성하여 반환). 
		session.setAttribute("signedInUser", user.getUsername()); // 세션 객체에 로그인 username만 저장. 로그인 된 사용자 이름만 변수"signedInUser"에 저장.
		
		// (2) 적절한 페이지로 이동. -> 메인 페이지로 이동.
		response.sendRedirect("/post");  // contextroot(main.jsp) 
		
		// HttpSession session = request.getSession(); 코드는 현재 클라이언트의 세션을 찾거나 생성하는 역할을 합니다. 이를 통해 클라이언트에게 고유한 세션을 부여하고, 이를 통해 세션을 통해 데이터를 저장하고 공유.
		// 클라이언트와 서버 간의 통신에서 세션 관리는 주로 "세션 식별자(Session ID)"를 사용하여 이루어짐.
		// 세션 식별자는 보통 쿠키를 통해 클라이언트에게 전달되며, 쿠키에 저장되어 클라이언트가 서버에 요청을 보낼 때마다 함께 전송됨.

		// 일반적으로 웹 브라우저는 쿠키를 사용하여 세션 식별자를 관리하고 저장. 
		// 서버는 클라이언트에게 세션 식별자를 담은 쿠키를 전송하고, 이후 클라이언트의 모든 요청에는 해당 쿠키가 함께 전송됨. 
		// 이렇게 함으로써 서버는 클라이언트를 식별하고 그에 해당하는 세션을 찾을 수 있음.
	}
}