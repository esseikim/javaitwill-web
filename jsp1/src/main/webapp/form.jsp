<%@ page language="java" contentType="text/html; charset=UTF-8"
	  pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%> 
<%-- trimDirectiveWhitespaces: JSP 페이지가 자바로 변환될 때, 생성되는 서블릿 클래스의 자바 코드에서 페이지의 시작과 끝 부분에 있는 공백(화이트스페이스) 문자들을 제거하도록 지시하는 역할
     JSP 페이지를 자바 코드로 변환하면, JSP 페이지의 내용이 jspService() 메서드 내에 위치하게 됨. 이때, JSP 페이지의 시작과 끝 부분에 있는 공백 문자들은 불필요한 추가 공백으로 인식될 수 있어, 
	 이를 제거하여 생성되는 서블릿 클래스의 코드를 깔끔하게 유지하고자 할 때 trimDirectiveWhitespaces 디렉티브를 사용 --%>
<!DOCTYPE html>
<html>
	<head>
	  	<meta charset="UTF-8" />
		<title>JSP</title>
	</head>
	<body>
        <!-- 요청 방식(method)
            1. GET: 기본값. 클라이언트에서 서버로 보내는 정보가 질의 문자열(query string)에 포함.
            2. POST: 클라이언트에서 서버로 보내는 정보가 HTTP 패킷에 포함되는 방식. -> 요청 파라미터를 주소줄에서 보지 못함. 확인 하는 곳 개발자 도구 > 네트워크 > 페이로드
            (참고) URL 형식
            프로토콜://서버주소:포트/(context root 포함)경로/[파일 이름]?질의문자열
            http://192.168.20.31:8081/jsp1/form-result.jsp?username=오쌤.
         -->
		<form action="form-result.jsp" method="post"> <!-- post방식으로 요청 주소. -->
            <input type="text" name="username" placeholder="이름 입력" required autofocus/> 
            <!-- name 속성은 request parameter의 변수이름. input text의 값 queryString 시 필요. 
            name: : HTML 폼 요소에 부여되며, 서버로 해당 요소의 값을 전송할 때 사용되는 변수 이름을 지정. 이 변수 이름은 쿼리 문자열(query string)을 생성하는 데 활용됨(식별자 역할). 
            required: 해당 폼 요소가 필수 입력 사항임을 나타냄, 사용자가 필수 필드를 비우고 제출하면 브라우저가 정보창을 보여줌.
            autofocus: 웹 페이지가 열릴 때 해당 요소에 자동으로 커서가 위치

			질의문자열(Query String): 웹 요청에서 서버로 데이터를 전달하는 데 사용되는 매개 변수의 형태를 나타냄. 이 매개 변수들을 서버에서는 "리퀘스트 파라미터" 또는 "요청 파라미터" 라고 부름.
             -->
             <input type="submit" value="전송" />
        </form>
	</body>
</html>