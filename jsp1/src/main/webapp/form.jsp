<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%> 
    <%-- trimDirectiveWhitespaces:
    자바로 컴파일된 파일에서 여러가지 공백들을 잘라버리겠다. 
    --%>
<!DOCTYPE html>
<html>
	<head>
	  	<meta charset="UTF-8" />
		<title>JSP</title>
	</head>
	<body>
        <!-- 요청 방식(method)
            1. GET: 기본값. 클라이언트에서 서버로 보내는 정보가 질의 문자열(query string)에 포함.
            2. POST: 클라이언트에서 서버로 보내는 정보가 HTTP 패킷에 포함되는 방식. -> 요청 파라메타를 주소줄에서 보지 못함. 확인 하는 곳 개발자 도구 > 네트워크 > 페이로드
            (참고) URL 형식
            프로토콜://서버주소:포트/(context root 포함)경로/[파일 이름]?질의문자열
            http://192.168.20.31:8081/jsp1/form-result.jsp?username=오쌤.
         -->
		<form action="form-result.jsp" method="post"> <!-- get방식으로 요청 주소. -->
            <input type="text" name="username" placeholder="이름 입력" required autofocus/> 
            <!-- name속성은 request parameter의 변수이름.
            name: input Text의 값 queryStream할 떄 필요.
            required: 브라우저가 정보창 보여줌
            autofocus: 이 웹페이지가 열렸을 떄 커서 작동.
             -->
             <input type="submit" value="전송" />
        </form>
	</body>
</html>