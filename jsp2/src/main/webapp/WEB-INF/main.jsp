<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>POST</title>
    </head>
    <body>
        <header>
            <h1>메인 페이지</h1>
        </header>
        
        <nav>
            <ul>
                <!-- 로그인한 username이 있는 경우 -->
				<!-- 
				Session에 저장한 이유: redirect에서는 request 객체가 달라짐. 즉, 값이 동일하지가 않음. 
				단, Session에 저장을 하면 request 객체가 바뀌어도 동일한 Session 객체에서 확인 가능.
				EL: 변수가 존재 시 해당 변수를 찾는 루트 -> 1) page context 2) reqest 3) session 4) app -->
                <c:if test="${ not empty signedInUser }">
                    <li>
                        <span>${ signedInUser }</span>
                        <c:url var="signOut" value="/user/signout"></c:url>
                        <a href="${ signOut }">로그아웃</a>
                    </li>
                </c:if>
                
                <!-- 로그인한 username이 없는 경우 -->
                <c:if test="${ empty signedInUser }">
                    <li>
                        <c:url var="signInPage" value="/user/signin"></c:url>
                        <a href="${ signInPage }">로그인</a>
                    </li>
                    <li>
                        <c:url var="signUpPage" value="/user/signup"></c:url>
                        <a href="${ signUpPage }">회원가입</a>
                    </li>
                </c:if>
                
                <li>
                    <c:url var="postList" value="/post"></c:url>
                    <a href="${ postList }">포스트 목록 페이지</a>
                </li>
            </ul>
        </nav>
        
        <main>
            <!-- TODO -->
        </main>
    </body>
<!-- "nav" 태그:
	  웹 페이지 내에서 탐색을 위한 링크들을 그룹화할 때 사용하는 태그. 주로 페이지의 주요 내용과 분리하여 네비게이션 역할을 하는 요소들을 포함함.
	  	"ul" (unordered list, 순서 없는 목록)를 사용하여 메뉴 항목들을 리스트 형태로 그룹화하고, 
	    "li" (list item)를 사용하여 각각의 메뉴 항목을 구성.
	    "a" 태그를 사용하여 각 항목에 링크를 설정하여 다른 페이지로 이동. -->

<!-- 
상대경로: 현재 파일이나 위치를 기준으로 다른 파일의 경로를 나타내는 방법.
웹 어플리케이션의 루트 경로까지는 생략하고 그 이후의 주소 부분만 표기하고 있음.
파일의 이름을 알면 브라우저에서 직접 접근 가능.
JSP 파일은 서버 사이드에서 동작하는 웹 어플리케이션의 일부로서, 서버에서 해석되고 실행됨. 
이때, JSP 파일은 웹 클라이언트(브라우저)에게 직접 전송되는 HTML로 변환되어서 보여지게 됨.
JSP 코드를 열람하거나 공격에 이용하려는 시도를 차단하기 위해 JSP 파일은 WEB-INF 폴더에 위치시켜서 클라이언트로부터의 직접 접근을 차단하고, 서버에서만 실행되도록 보장함.

localhost:8081/jsp1/el.jsp: 생성되는 HTML 코드를 브라우저에서 바로 확인 가능.
WEB-INF 폴더: 웹 어플리케이션에서만 접근할 수 있는 폴더로, 브라우저에서 직접 접근할 수 없음(웹 클라이언트 직접 접근 불가능). 서블릿이나 JSP에서 이 폴더 내의 리소스를 포워드하는 경우에만 접근이 가능함.
web.xml은 웹 어플리케이션의 환경설정 파일로, WEB-INF 폴더에 위치함. 웹 어플리케이션의 중요한 설정 정보를 외부에서 직접 접근하지 못하게 하기 위함. -->

<!-- 
Session: 웹 애플리케이션에서 데이터를 유지하고 공유하는 방법 중 하나.
웹 애플리케이션에서 클라이언트 간에 상태를 유지하기 위한 메커니즘 중 하나로, 클라이언트가 웹 서버에 접속한 후 해당 클라이언트의 상태를 일정 기간동안 유지.
웹 애플리케이션에서 리다이렉트를 수행하면 클라이언트의 요청이 새로운 요청으로 변경되기 때문에, 기존 요청과는 별도의 request 객체가 생성됨. 
이로 인해 리다이렉트 이후에도 이전 요청에서 전달한 데이터가 유지되지 않는 문제가 발생할 수 있음.
"Session"을 활용하면 데이터를 세션 객체에 저장하여 서로 다른 request들 간에 데이터를 공유할 수 있음.
세션은 서버 측에서 클라이언트 별로 유지되는 저장소로, 클라이언트가 세션에 데이터를 저장하거나 읽어올 수 있음.
따라서 리다이렉트 이후에도 동일한 세션 객체를 통해 데이터에 접근할 수 있음.
세션은 일정 기간동안 유지되며, 클라이언트가 세션을 종료하거나 만료될 때까지 사용할 수 있음. 
이를 통해 로그인 상태, 장바구니 정보, 사용자 환경 설정 등을 유지하고 공유할 수 있음. -->
</html>