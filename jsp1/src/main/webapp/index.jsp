<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <!-- JSP 페이지가 어떤 언어로 작성되었는지, 어떤 형식의 컨텐츠를 생성하는지, 문자 인코딩을 어떻게 처리해야 하는지 등을 서버에 알려주는 역할 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- core : 변수 "c" 사용 가능-->
<!-- JSP 페이지 내에서 자바 코드를 작성하려면 스크립트릿 블록을 사용해야 함. 이 스크립트릿 블록 안에 자바 코드를 작성하여 페이지의 동적인 처리를 수행 가능 -->
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>JSP</title>
    </head>
    <body>
        <h1>Index</h1>
        <!-- "First Servlet" 링크는 "ex1"라는 URL 경로에 대한 링크를 생성. 
			 "ex1" 경로는 웹 애플리케이션의 루트(http://localhost:8081/jsp1/)를 기준으로 상대 경로로 작성. 
			  클라이언트가 해당 링크를 클릭하면 ex1이라는 URL 경로로 요청을 보내게 됨. -->
        <ul>
            <li>
                <a href="ex1">첫번째 Servlet</a>
            </li>
            <li>
                <a href="ex2">두번째 Servlet</a>
            </li>
            <li>
                <a href="ex3">포워드</a>
            </li>
            <li>
                <a href="ex4">리다이렉트</a>
            </li>
            <li>
                <!-- URL 상대경로: 
                http://localhost:8081/contextRoot/ 까지를 현재 작업 디렉토리로 하고, 
                그 이후 주소만 표기 -->
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
                <a href="form2.jsp">form 제출</a>
            </li>
            <li>
                <a href="form2-result.jsp?username=adm&in&color=b">클릭 1</a> <!-- 클릭 1의 경우 (직접 하드코딩) -->
            </li>
            <li> <!-- JSP 페이지에서 JSTL과 EL을 사용하여 요청 파라미터 값을 처리하고 출력. 클릭 2의 경우 (c:url과 c:param을 사용) 두 클릭 모두 form2-result.jsp로 이동하며, 파라미터를 전달. 둘의 차이점: URL을 하드코딩하여 파라미터를 직접 전달, c:url과 c:param을 사용하여 파라미터를 설정한 후 EL을 통해 동적으로 URL을 생성하는 것 -->
                <c:url var="reqURL" value="form2-result.jsp">
                    <c:param name="username" value="adm&in"></c:param> 
                    <c:param name="color" value="g"></c:param> <%-- Request Parameter --%>
                </c:url>
                <a href="${ reqURL }">클릭 2</a> <!-- var: 변수선언. Request Parameter 이름 지정. jstl에서의 var: el을 사용하여 변수 값을 읽음 -->
            </li>
            <li>
                <a href="format.jsp">포맷팅</a>
            </li>
            <li>
                <a href="mvc">MVC pattern</a> <!-- JSP가 아닌 서블릿 객체 필요 -->
            </li>
        </ul>
				<!-- 
						- 쿼리 파라미터를 생성할 때는 파라미터 이름과 값을 key=value 형식으로 전달하며, 여러 파라미터는 "&"로 구분 
						- URL: http://example.com/page?username=admin&color=blue
						- 파라미터가 포함되지 않은 상태에서 파라미터 값을 가져올 경우 NullPointerException 오류가 발생(equals.)
						- 특수 문자를 사용할 때는 URL 인코딩 값을 사용( "&" : %26을 사용, "=" : %3D를 사용) -> 특수 문자가 파라미터의 구분이나 값의 구분으로 해석x
					    - JSTL을 사용하면 URL 생성 및 파라미터 추가 등을 간편하게 처리 가능				
					-->
    </body>
</html>