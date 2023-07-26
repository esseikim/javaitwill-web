<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Spring 1</title>
	    </head>
		<body>
				<header>
                        <h1>메인 페이지</h1>
                        <h2>${ now }</h2>
                </header>
                
                <!-- 같은 웹서버 내에서 이동하는 페이지 만듦 -->
                <nav>
                        <ul>
                                <li>
                                        <c:url var = "ex1"  value="/ex1"/> <!-- contextroot 부터. http://localhost:8081/spring1/ -->
                                        <a href= "${ ex1 }" > Example 1</a> <!--  spring 주소 : http://localhost:8081/spring1/ex1  -->
                                <!--요청 처리할 controller 만들지 않음 = 현재의 representation을 찾지 못했다  -->
                                </li>
                                <li> <!-- 포워드, 리다이렉트 시 어떻게 될 지 보기 위한 sample -->
                                        <c:url var="sample" value="/sample"></c:url>                                
                                        <a href="${ sample }">Sample</a>      <!--이동하는 링크 생성  -->
                                </li>
                                 <li>
                                        <c:url var="forwardTest" value="/forward"></c:url>                                
                                        <a href="${ forwardTest }">포워드 테스트</a>      
                                 </li>
                                  <li>
                                        <c:url var="redirectTest" value="/redirect"></c:url>                                
                                        <a href="${ redirectTest }">리다이렉트 테스트</a> 
                                        <!-- 메인페이지만 보면 되니, contextroot로 요청 보내면 됨. -->     
                                 </li>
                        </ul>
                </nav>
		</body>
</html>