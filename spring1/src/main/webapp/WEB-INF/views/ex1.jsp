<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>  <!--c:라는 접두사로 시작하는 태그라이브러리 사용 가능  -->  
<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Spring 1</title>
	    </head>
		<body>
				<header>
                        <h1>Example 1</h1>
                </header>
                <main>
                        <%-- get방식으로 form 데이터 전달. --%>
                        <h2>GET submit</h2>
                        <c:url var="ex2"  value="/ex2"></c:url> <!-- / : context root까지 spring1 -->               
                        <form action="${ ex2  }"> <!-- form 메서드의 기본 값은 "get" -->
                                <input type="text"  name="username" placeholder="이름 입력"/>
                                <input type="number"  name="age" placeholder="나이 입력"/>
                                <input type="submit"  value="제출" /> <!-- form 양식을 제출하기 위한 버튼 -->
                        </form>
                 <!--         representation을 찾지 못했습니다 = 요청 처리 가능한 controller가 존재 하지 않음.
                         http://localhost:8081/spring1/ex2?username=&age=
                         http://localhost:8081/spring1/ex2: 요청처리주소
                         ? 부터 쿼리 스트링. form에서 지정한 name: 리퀘스트 파라미터 이름
                         서버에서 찾기 위해서 getUsername, getAge 하면 됨.  -->
                         
                     <hr/>
                    <h2>POST submit</h2>
                        <c:url var="ex3" value="/ex3" ></c:url> <!-- / : context root(spring1)까지. 그 아래의 주소 기입 -->               
                        <form action="${ ex3 }" method="post"> <!-- form 메서드의 기본 값은 "get" -->
                                <input type="text"  name="username" placeholder="이름 입력"/>
                                <input type="number"  name="age" placeholder="나이 입력"/>
                                <input type="submit"  value="제출" /> <!-- form 양식을 제출하기 위한 버튼 -->
                        </form>
                        
                        <hr/>
                        <h2>DTO submit</h2>
                        <c:url var="ex4" value="/ex4" ></c:url> <!-- / : context root까지 spring1 -->               
                        <form action="${ ex4 }" method="get"> <!-- form 메서드의 기본 값은 "get", 요청주소: ex4-->
                                <input type="text"  name="username" placeholder="이름 입력"/>
                                <input type="number"  name="age" placeholder="나이 입력"/>
                                <input type="submit"  value="제출" /> <!-- form 양식을 제출하기 위한 버튼 -->
                                 <!-- 현재의 representation을 찾지 못했다. 요청주소를 처리할 수 있는 controller를 만들지 않아서 -->
                                  <!--ex4 form을 처리하는 controller 메서드를 만들고 싶은 것 -->
                        </form>
                         
                </main>
		</body>
</html>