<%@ page import="com.itwill.jsp1.model.Contact"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>JSP</title>
    </head>
    <body>
        <h1>JSTL</h1>
        <h2>JSP Standard Tag Library</h2>
        <%-- JSTL 사용하기:
            1. POM.xml 파일에 의존성(dependency) 추가(jstl:jstl:1.2) 그룹아이디:아티펙트아이디:버전번호
            2. JSTL을 사용하는 JSP 파일에서 taglib 지시문을 설정.
        --%>
        
        <%
        // HTML 리스트 아이템으로 사용할 더미 데이터 생성:
        String[] sites = {"YouTube", "Instagram", "Facebook"};
        // -> scriptlet에서 선언한 지역 변수는 EL에 사용할 수 없음.
        // -> EL에서 사용할 수 있는 변수는 pageContext, requet, session, application에 저장된 속성들.
        
        pageContext.setAttribute("sites", sites);
        %>
        
        <h2>JSP scriptlet, expression</h2>
        <ul>
        <% for (String s : sites) { %>
            <li><%= s %></li>
        <% } %>
        </ul>
        
<%-- ${}: EL(Expression Language)
      prefix 접두사는 위와 아래가 동일해야 함. core -> c 접두사를 써서 정의. 보통은 선언문 비슷하게, 혹은 앞글자만 따서 정의.
      JSP core 라이브러리에 있는 태그(for each)들을 사용할 때 c라는 접두사를 쓰겠다 선언. 아닌 걸 사용하게 되면 동작하지 않음. --%>
        <h2>JSTL, EL</h2>
        <ul>
            <c:forEach items="${ sites }" var="s">
                <li>${ s }</li>
            </c:forEach>
        </ul>
        
        <%-- 테이블에서 사용할 더미 데이터 --%>
        <% 
        ArrayList<Contact> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Contact c = new Contact(i, "이름_" + i, "phone_" + i, "email_" + i);
            list.add(c);
        }
        
        // 리스트를 EL에서 사용할 수 있도록 하기 위해서:
        pageContext.setAttribute("contacts", list);
        %>
        
        <h2>JSP 이용한 테이블 작성</h2>
        <table>
            <thead>
                <tr>
                    <th>NO.</th>
                    <th>이름</th>
                    <th>전화번호</th>
                    <th>이메일</th>
                </tr>
            </thead>
            <tbody>
                <% for (Contact c : list) { %>
                    <tr>
                        <td><%= c.getId() %></td> <%-- 함수 직접 호출. 메서드 호출 --%>
                        <td><%= c.getName() %></td>
                        <td><%= c.getPhone() %></td>
                        <td><%= c.getEmail() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        
        <h2>JSTL, EL 이용한 테이블 작성</h2>
        <table>
            <thead>
                <tr>
                    <th>NO.</th>
                    <th>이름</th>
                    <th>전화번호</th>
                    <th>이메일</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ contacts }" var="c">
<%-- list에서 꺼낸 변수들을(Contact 타입) c에 집어 넣겠다. 멤버변수들만 써주면 getter 메서드를 찾아서 써줌. 
     getter 메서드 필수 (EL에서 getter 메서드를 찾기 위해서 관습을 잘 지켜야함.)
     안에서는 EL을 쓰기 가능. cf) EL 안에서는 JSP가 가진 지역변수 접근 불가. page,request, session, application에 저장된 변수 이름만 접근 가능. --%>
                    <tr>
                        <td>${ c.id }</td>
                        <td>${ c.name }</td>
                        <td>${ c.phone }</td>
                        <td>${ c.email }</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
    </body>
</html>