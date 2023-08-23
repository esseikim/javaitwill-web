<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Spring 1</title>
    </head>
    <body>
        <header>
            <h1>Example 1</h1>
        </header>
        
        <main> <%-- `?` 부터 쿼리 스트링. name 속성 값을 이용해 서버는 getUsername, getAge으로 요청에 포함된 요청 파라미터 값을 추출. --%>
            <h2>GET submit</h2>
            <c:url var="ex2" value="/ex2" />
            <form action="${ ex2 }"> <%-- form method 기본값은 "get", 요청 주소: http://localhost:8081/spring1/ex2. --%>
                <input type="text" name="username" placeholder="이름 입력" /> 
								<input type="number" name="age" placeholder="나이 입력" />
                <input type="submit" value="제출" />
            </form>
            
            <hr />
            
            <h2>POST submit</h2>
            <c:url var="ex3" value="/ex3" />
            <form action="${ ex3 }" method="post">
                <input type="text" name="username" placeholder="이름 입력" />
                <input type="number" name="age" placeholder="나이 입력" />
                <input type="submit" value="제출" />
            </form>
            
            <hr />
            
            <h2>DTO submit</h2>
            <c:url var="ex4" value="/ex4" />
            <form action="${ ex4 }" method="get">
                <input type="text" name="username" placeholder="이름 입력" />
                <input type="number" name="age" placeholder="나이 입력" />
                <input type="submit" value="제출" />
            </form>
        </main>
    </body>
</html>