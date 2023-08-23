<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>JSP</title>
    </head>
    <body>
        <h1>form 제출 페이지</h1>
        <form action="form2-result.jsp" method="get">
            <div>
                <input type="text" name="username" placeholder="아이디 입력" autofocus />
<%-- http://localhost:8081/jsp1/form2-result.jsp?username=김세이&color=r 
     name 속성이 있는 경우에만 웹서버로 전송. 폼을 화면에 만들었다고 무조건적으로 전송되는 게 아님. --%>
            </div>
            <div>
                <select name="color">
                    <option value="r">빨강</option>
                    <option value="g">초록</option>
                    <option value="b">파랑</option>
                </select>
            </div>
            <div>
                <input type="submit" value="보내기" />
            </div>
        </form>
    </body>
</html>