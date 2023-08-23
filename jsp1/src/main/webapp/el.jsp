<%@page import="com.itwill.jsp1.model.Contact"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>JSP</title>
    </head>
    <body>
        <h1>EL(Expression Language)</h1>
        <%-- EL
          JSP expression 태그(<%= %>)를 대체하는 문법:
          ${ 식 }
          * 지시문 <%@ %> 안에서는 사용할 수 없음.
          * 선언문 <%! %> 안에서는 사용할 수 없음.
          * 스크립트릿 <% %> 안에서는 사용할 수 없음.
          * 식 <%= %> 안에서는 사용할 수 없음.
          * 그 이외의 JSP 안에서는 언제든지 사용 가능.
            - HTML 태그의 컨텐트
            - HTML 태그의 속성 값
            - CSS 프로퍼티 값
            - JavaScript 코드의 일부.
        --%>
        
        <p><%= 1 + 2 %></p>
        <p>${ 1 + 2 }</p>
        
        <%-- 상태 정보 유지:
          JSP에서 상태 정보들을 유지하기 위해서 사용하는 객체(변수 이름)들:
			(유지 정도 약 -> 강). 범위(원)
            * pageContext: JSP 페이지가 유지되는 동안
            * request: 요청 객체가 유지되는 동안
            * session: 세션이 유지되는 동안
            * application: 웹 애플리케이션이 동작하는 동안
            * 사용 범위: pageContext < request < session < application
          EL에서 상태 정보들을 유지하기 위해서 사용하는 객체(변수 이름)들:
            * pageScope
            * requestScope
            * sessionScope
            * applicationScope
            
          EL에서 상태 변수를 찾는 순서: ${ var } 거슬러 올라감.
            ${ pageScope.var } 
            ==> ${ requestScope.var }
            ==> ${ sessionScope.var }
            ==> ${ applicationScope.var }  => 여기서도 못찾으면 그 변수는 null이 됨.
        --%>
        
        <%-- 상태 저장 변수에 상태 저장 --%>
        <%
        pageContext.setAttribute("var1", 1); // pageContext에 정보를 저장. pageContext 객체에 1의 값을 저장.
        request.setAttribute("var2", "Hello"); // request에 정보를 저장. request 객체에 var2를 저장.
        
        Contact c = new Contact(1, "a", "02-", "@");
        session.setAttribute("var2", c); // session에 정보를 저장
        %>
        
        <h2>JSP Expression 태그를 사용한 상태 정보 읽기</h2>
        <p>
            var1: <%= pageContext.getAttribute("var1") %> <br />
            request.var2: <%= request.getAttribute("var2") %> <br />
            session.var2: <%= session.getAttribute("var2") %> <br />
		    <!-- 변수명이 동일해도 저장되는 객체가 다르기에 괜찮음. -->
        </p>
        
        <h2>EL을 사용한 상태 정보 읽기</h2>
        <p>
            var1: ${ var1 } <br /> <%-- ${pageScope.var1}과 동일 --%>
            request.var2: ${ var2 } <br /> <%-- ${requestScope.var2}과 동일 --%>
            session.var2: ${ sessionScope.var2 } <br />
         <!-- 변수명이 동일하면 어디에 있는 건지 명시해줘야 함. session
	            만약 var2만 명시하면 찾는 순서에 의해 해당 변수를 찾을 수 없음. -->
        </p>
        
    </body>
</html>