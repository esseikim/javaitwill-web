<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <!-- html문법이 아님. 자바 코드 사용 가능. -->
<!-- /index.jsp 파일임, /ex1과는 다른 파일.-->
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>JSP</title>
    </head>wq`      
    <body>
        <h1>Index</h1>
        
        <ul>
            <li>
                <a href="ex1">First Servlet</a> 
                <!-- 상대경로 링크 지정: servlet 주소 -> 
                http://localhost:8081/jsp1/ 만 사용해도 뜸 web.xml에서
                 welcome-file-list에서 파일 이름이 지정이 안 되어 있을 댸 해당 파일 이름이 열림.-->
            </li>
            <li>
                <a href="ex2">second Servlet</a> <!-- ex2를 처리할 수 있는 jsp나 Servlet이 존재하지 않기에 -->
            </li>
            <li>
                <a href="ex3">포워드</a>
            </li>
            <li>
                <a href="ex4">리다이렉트</a>
            </li>
            <li>
                <!-- URL: 상대경로: http://localhost:8081/contextRoot/ 까지를 현재 작업 디렉토리로 하고, 그 이후 주소만 표기 -->
                <a href="intro.jsp">JSP 소개</a>
            </li>
            <li>
                <a href="form.jsp">form 제출</a>
            </li>
            <li>
                <a href="main.jsp">include 지시문(directive)</a>
            </li>
            <li>
                <a href="scriptlet.jsp">스크립트릿(scriptlet)</a>
            </li>
            <li>
                <a href="actiontag.jsp">액션 태그(action tag)</a>
            </li>
            <li>
                <a href="el.jsp">EL(Expression Language)</a>
            </li>
            <li>
                <a href="jstl.jsp">JSTL</a>
            </li>
            <li>
               <a href= "form2.jsp">form 제출</a>
            </li>
            <li>
                <a href= "form2-result.jsp?username=admin&color=b">클릭 1</a>
                   <!-- rqparameter 만들어줘야함. 쿼리문으로 넘겨줘야 함(전송해줘야함.).
                    그렇지 않으면 nullpointerException  발생. equals. 하고 들어갈 때!
                   rqp를 구분하기 위해 &를 씀 
                   &: 구분이 아닌, 앤드 자체를 username에 포함되는 문자열에 넣어주고 싶음. 실제로 전달하는 데이터 중 하나고 싶으면
                   = x: 변수 이름만 있고 값이 없는 상태. 
                    &의 utf 코드값을 넣어주면 됨.   <-  %26
                    직접  url을 만드는 것보다 jstl 태그 라이브러리를 통해 url을 만들어주는 게 좋음  
                   -->
            </li>
             <li>
            <%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>를 추가하여 core변수를 사용할 수 있게 만듦 --%>
                <c:url var="reqURL" value="form2-result.jsp">
                      <c:param name="username" value="adm&in"></c:param> <%--request 파라미터 --%>
                        <c:param name="color" value="g"></c:param>
                </c:url>
                    <a href="${ reqURL }">클릭 2</a>
             </li>
              <li>
                <a href="format.jsp">포맷팅</a>
              </li>
 
            <!-- rqp의 이름 지정. var: 변수선언. jstl에서의 var: el을 사용하면 변수의 값을 읽을 수 있음 -->
            
                <li>
                        <a href= "mvc">MVC pattern</a> <!-- 서블릿 객체 필요함. jsp가 아닌 -->
                </li>
        </ul>
    </body>
</html>