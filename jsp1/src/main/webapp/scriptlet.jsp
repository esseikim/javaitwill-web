<%@page import="com.itwill.jsp1.model.Contact"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>JSP</title>
    </head>
    <body>
        <h1>JSP scriptlet 활용</h1>
        <%-- scriptlet:  
          JSP 안에서 Java 코드들을 작성하기 위한 태그.
          지역 변수 선언, 객체 생성, 메서드 호출, 조건문, 반복문, ...

		 JSP 페이지 내에 작성된 자바 코드 부분(Java block )
         이 코드는 서버에서만 실행되며, 클라이언트에게는 전혀 노출되지 않음.
		 JSP 파일은 서버 측에서 실행되어 HTML 형식의 응답을 생성하는 데 사용되는 스크립트임.
		 서버에서 실행되는 이유
		 1. JSP 파일의 역할: JSP 파일은 서버에서 클라이언트의 요청에 응답을 생성하는 용도로 사용됨. 
		 				  JSP 파일은 웹 애플리케이션 서버에서 동적으로 처리되어 최종적으로 HTML 형식의 응답을 생성함.
		 2. 서버 측 코드: JSP 파일 내에 포함된 <% %> 태그는 서버 측 코드 블록을 나타냄. 
					   이러한 코드 블록은 서버에서 실행되어 Java 코드로 변환되고, 그 결과가 클라이언트에게 전달되는 것이 아니라 서버 내에서 처리됨.
					   클라이언트에게는 그 실행 결과만이 제공되는 것.
        --%>
        
        <% // 테이블을 작성할 더미 데이터 생성: > 서버에서만 실행.
        ArrayList<Contact> data = new ArrayList<>(); // 빈 리스트 생성
        for (int i = 0; i < 10; i++) {
            Contact c = new Contact(i, "이름_" + i, "전화번호_" + i, "email_" + i);
            data.add(c);
        }
        %>
        
        <table>
            <caption>연락처</caption>
            <thead>
                <tr>
                    <th>NO.</th>
                    <th>이름</th>
                    <th>전화번호</th>
                    <th>이메일</th>
                </tr>
            </thead>
            <tbody>
            <% 
            for (Contact c : data) {
                // JSP 내장객체 out을 사용한 HTML 코드 출력.
                out.print("<tr>");
                out.print("<td>" + c.getId() + "</td>");
                out.print("<td>" + c.getName() + "</td>");
                out.print("<td>" + c.getPhone() + "</td>");
                out.print("<td>" + c.getEmail() + "</td>");
                out.print("</tr>");
            }
            %>
            </tbody>
        </table>
        
        <hr />
        
<%-- (4) 스크립트릿(scriptlet): <% ... %>
	     JSP가 Java로 변환될 떄, jspService() 메서드 안에 포함되는 자바 코드.
	     지역 변수 선언, 조건문, 반복문, 출력문, 메서드 호출, ....
	 (5) 식, 표현식(expression): <%= ... %>
	     JSP가 Java로 변환될 때, out.print() 메서드의 argument로 전달되는 값. 공백이 중요하지 않음
	     HTML에 바로 삽입되는 '값'   --%>
        <h1>scriptlet, expression 활용</h1>
        
        <table>
            <caption>연락처</caption>
            <thead>
                <tr>
                    <th>NO.</th>
                    <th>이름</th>
                    <th>전화번호</th>
                    <th>이메일</th>
                </tr>
            </thead>
            <tbody>
            <% for (Contact c : data) { %>
                <tr> <!-- experssion: 값을 넣기 위해서 사용. 즉, 문장의 일부분만을 사용함. 메서드의 리턴값 출력시 ';'을 사용하지 않기. -->
                    <td><%= c.getId() %></td> <!-- c.getId()=> 실행문: 식은 문장(51번 줄)의 일부. 그래서 c.getId();을 사용하면 문장의 중간에 ;을 쓴게 되어서 에러가 발생함. -->
                    <td><%= c.getName() %></td>
                    <td><%= c.getPhone() %></td>
                    <td><%= c.getEmail() %></td>
                </tr>
            <% } %>
            </tbody>
        </table>
        
        <hr />
        
        <h2>Unordered List</h2>
        <%-- ul 만들기: li은 연락처 리스트의 이름. --%>
        <ul>
        <% for (Contact c : data) { %>
            <li><%= c.getName() %></li>
        <% } %>
        </ul>
        
        <h2>Description List</h2>
        <%-- dl 만들기: dt는 연락처에서 이름, dd는 연락처에서 전화번호와 이메일 --%>
        <dl>
        <% for (Contact c : data) { %>
            <dt><%= c.getName() %></dt>
            <dd><%= c.getPhone() %></dd>
            <dd><%= c.getEmail() %></dd>
        <% } %>
        </dl>
        
    </body>
</html>