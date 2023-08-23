<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>JSP</title>
    </head>
    <body>
        <h1>JSTL fmt</h1>
        
        <%-- JSTL <fmt:formatDate> 태그는 
             java.sql.Date, java.sql.Timestamp 타입의 객체들을 원하는 형식으로 포맷팅.
             java.time.LocalDate, java.time.LocalDateTime 객체들은 포맷팅을 못함.
        --%>
        <%-- JSTL을 사용한 변수 선언 --%>
        <c:set var="now" value="<%= Timestamp.valueOf(LocalDateTime.now()) %>"></c:set>
        
				<%-- EL(Expression Language): JSP에서 사용하는 간단한 표현식 언어.
					  	JSTL(JSP Standard Tag Library)과 함께 사용, JSP에서 변수의 값을 출력하거나 연산을 수행하는 데 사용
						EL을 사용하면 JSP 페이지에서 자바 코드를 작성하지 않고도 동적인 데이터를 처리(변수의 값을 편리하게 출력)
						EL은 변수의 값을 자동으로 문자열로 변환 --%>

				<h2>${ now }</h2> <%-- EL을 사용, now 객체가 가진 toString() 메서드의 결과를 화면에 출력 --%>
        <h2>date: 
            <fmt:formatDate value="${ now }" type="date" />
        </h2>
        <h2>time:
            <fmt:formatDate value="${ now }" type="time" />
        </h2>
        <h2>date &amp; time:
            <fmt:formatDate value="${ now }" type="both" />
        </h2>
        <h2>
            <fmt:formatDate value="${ now }" type="both" 
                dateStyle="full" timeStyle="full" />
        </h2>
        <h2>
            <fmt:formatDate value="${ now }" type="both" 
                dateStyle="short" timeStyle="short" />
        </h2>
        <h2>
            <fmt:formatDate value="${ now }" pattern="yyyy/MM/dd HH:mm:ss" /> <!-- 커스텀 -->
        </h2>
        
    </body>
</html>