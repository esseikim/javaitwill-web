<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%-- / : 무조건 contextroot를 의미
var: 변수이름, 변수이름은 el로 사용한다.
c url 태그 이용해서 . .. 이런 거 필요없이 이동.
<%: jsp의 스크립틀릿 사용, jstl: c(코어)같은 접두어 사용하는 jsp의 라이브러리 중 하나.  $: el --%>
<!-- <%@ include file="../../common/header.jsp"%>-->
<!DOCTYPE html>
<!--get 메서드: 톰캣이 url 맵핑되어있는 서블릿 찾아서 호출  -->
<html>
	<head>
		<meta charset="UTF-8">
		<title>POST</title>
    </head>
	<body>
		<header>
		    <h1>메인 페이지</h1>
	    </header>
                
        <!-- div대신 사용. -->
        <nav>
           <ul>
             <!-- 로그인한 username이 있는 경우 -->
             <!-- Session에 저장한 이유: redirect에서는 request 객체가 달라짐. 즉, 값이 동일하지가 않음 
             단, Session에 저장을 하면 request 객체가 바뀌어도(== redirect를 해도) 동일한 Session 객체에서 확인할 수가 있음.
             EL: 변수가 존재 시 해당 변수를 찾는 루트 -> 1) page context 2) reqest 3) session 4) app -->
             <c:if test="${ not empty signedInUser }">
                 <li>
                     <span>${ signedInUser }</span>
                     <c:url var="signOut" value="/user/signout"></c:url>
                     <a href="${ signOut }">로그 아웃</a>
                 </li>
             </c:if>
             
             <!-- 로그인한 username이 없는 경우 -->
             <c:if test="${ empty signedInUser }">
                 <li>
                     <c:url var="signInPage" value="/user/signin"></c:url>
                     <a href="${ signInPage }">로그인</a>
                 </li>
                 <li>
                     <c:url var= "signUpPage" value= "/user/signup"></c:url>
                     <a href= "${ signUpPage }">회원가입</a>
                 </li>
             </c:if>
             	 <li>
                     <c:url var="postList" value= "/post"> </c:url>
                     <a href="${ postList }">포스트 목록 페이지</a>  
                     <!--el: 변수 var를 찾아가고, 그 값은 contextroot/
                     url 태그 밑에 /: 무조건contextroot  -->
                 </li>
             </ul>
        </nav> 
   </body>  
</html>         
          <!-- 같은 페이지 안에서 이동하는 메뉴를 만들 때 div 대신에 사용하는 태그 
   http://localhost:8081/post/post에서 context root : post(여기까지는 고정!! contextroot), 주소: post 
   404가 나오는 이유: post 요청을 처리하는 서블릿이 없다라는 뜻. 요청 들어온 건 전부 서블릿이 받겠다 했음.     
    <- 클라이언트에서 요청(rq)을 보낸게 http://localhost:8081/post, controller(servlet): doget메서드 호출해서 view(main)으로 보냄
  	 	nav 안에 리스트(ul) 하나가 있는데, 링크(포스트목록 페이지) 하나가 있다. -->
       
                      
   <!-- <a href= "post">포스트 목록 페이지</a> -->
      <!--헷갈!!!!! 그래서 위 웹에서의 상대경로: contextroot까지 생략, 그 이후 주소 부분
     링크 처리하는 servlet(contrller)클래스를 .post(post.controller.post) 패키지에 만들고, jsp 클래스는 web-inf 밑에 만들기  
     jsp1에서의 jsp 파일: webapp 밑에 webinf까지 들어가지 않았음. -> jsp2: webinf 
     (1) 파일의 이름을 일반사용자가 알면, 그 파일을 직접 접근 가능 
     요청주소에서 localhost:8081/jsp1/ 까지는 유저가 알 수 있음. 
     이 파일에서 만들어주는 html을 클라이언트는 바로 확인할 수 있음. localhost:8081/jsp1/el.jsp 
     문제: jsp는 자바코드 - 서버변형 가능. 해킹의 수단이 될 수 있음. 
     (2)  webinf: webapplication만 접근할 수 있는 폴더
     브라우저에서 직접 접근할 수 있는 폴더가 아님. 서버의 프로세스들만 직접 접근 가능 (서블릿이 포워드하는 경우처럼)
     브라우저: 서버 밖. 톰캣과는 관련 없음. web.xml: 환경설정 파일이 이 아래에 있는 이유도 동일함 --> 
              
		
