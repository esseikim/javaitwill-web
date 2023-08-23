<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>JSP</title>
        <%-- 
		     클라이언트에서 보낸 요청 파라미터의 값을 찾는 방법: 
             getParameter: 서버에서는 Get 방식, Post 방식 구별하지 않음.
        --%>

        <% 
        // 요청 파라미터의 한글이 깨지지 않도록 하기 위해서:
        request.setCharacterEncoding("UTF-8");
        
        // 요청 파라미터 값 찾기: 
        String username = request.getParameter("username"); // 요청 파라미터 값 찾는 메서드. form에서 설정한 name 속성의 값으로 찾음.
        String color = request.getParameter("color");
        String colorValue = "black";
        if (color.equals("r")) {
            colorValue = "crimson";
        } else if (color.equals("g")) {
            colorValue = "darkgreen";
        } else if (color.equals("b")) {
            colorValue = "dodgerblue";
        }
        %>
        
				<%-- JSTL(Core 라이브러리)을 사용하여 특정 조건을 검사하고 변수를 설정 --%>
				<%-- JSTL의 <c:set> 태그에서 선언한 변수는 EL에서 사용 가능. 
				     EL(Expression Language)에서는 == 연산자를 사용하여 비교를 수행하며, equals 메서드를 사용하지 않음. EL에서는 큰 따옴표(")나 작은 따옴표(')를 가리지 않음 
					 주로 큰 따옴표를 더 많이 사용하며, if 문의 역할만 하며 else는 없음. 조건을 만족하는지 전부 검사. --%>
				<c:set var="colorValue2" value="black"></c:set> <%-- <JSTL의 <c:set> 태그를 사용하여 변수를 설정 --%>
        <c:if test="${ param.color == 'r' }">  <%-- JSTL의 <c:if> 태그를 사용하여 조건을 검사, test 속성에는 조건식을 넣음. param 객체의 color 매개변수가 'r'과 같은지를 검사하는 조건식. 즉, 클라이언트가 요청한 URL의 쿼리 파라미터 중에 color 값이 'r'인 경우에만 조건이 참이 됨 --%>
            <c:set var="colorValue2" value="red"></c:set> <%-- color 파라미터의 값이 'r'인 경우에만 <c:if> 태그 내부의 코드가 실행, 조건에 따라 변수 colorValue2의 값이 설정 --%>
        </c:if>
        <c:if test="${ param.color == 'g' }">
            <c:set var="colorValue2" value="green"></c:set>
        </c:if>
        <c:if test="${ param.color == 'b' }">
            <c:set var="colorValue2" value="blue"></c:set>
        </c:if>
        
        <style>
			  /* EL
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
        */
        span#span1 {
            color: <%= colorValue %>;
        }
        
        span#span2 {
            color: ${ colorValue2 };
            <%-- JSTL의 <c:set> 태그에서 선언한 변수는 EL에서 사용 가능. --%>
        }
        </style>
    </head>
    <body>
        <h1>요청 결과 페이지</h1>
        <h2>JSP</h2>
        <h3>아이디: 
            <span id="span1"><%= username %></span>
        </h3>
        
        <hr />
        <h2>JSTL, EL</h2>
        <h3>아이디:
            <span id="span2">${ param.username }</span> <!-- JSP에서 EL(Expression Language)을 사용하여 요청 파라미터 값을 가져오려면 param 객체를 사용. `param.파라미터이름` 형식으로 사용됨. -->
        </h3>
        
        <hr />
				<%-- JSTL(Core 라이브러리)을 사용하여 조건에 따라 다른 내용을 출력. 
						c:choose, c:when, c:otherwise 태그를 활용하여 구현. 
						c:choose 태그의 내부에는 하나 이상의 c:when 블록과 하나의 c:otherwise 블록이 포함.
				 		 1. `c:when`: c:choose 태그 내부의 조건 중 하나를 나타냄. param 객체의 username 매개변수가 'admin'과 같은지를 검사. { param.username == 'admin' } : true/false의 문자열이 됨.
				  		 2. `c:otherwise`: 태그 내부의 조건 중 가장 마지막에 위치하며, 모든 c:when 블록의 조건이 거짓인 경우 실행. --%>
        <c:choose> 
            <c:when test="${ param.username == 'admin' }"> 
                <h2>관리자 페이지</h2>
            </c:when>
            <c:otherwise>
                <h2>일반 사용자 페이지</h2>
            </c:otherwise>
        </c:choose>
		<!-- 선택 하겠다는 코어태그. if를 대체(else 없음)할 수 있는 choose: switch, when: case, otherwise: default -> 검사를 한번만 하기 -->
    </body>
</html>