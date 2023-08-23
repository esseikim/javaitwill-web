<%@page import="com.itwill.jsp1.model.Contact"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>JSP</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <%-- 
           * <%@ include %> 지시문과 비슷하게 다른 JSP의 내용을 포함시킴.
           * jspf 확장자를 사용할 수 없고, jsp만 사용.
           * include하는 JSP 마다 각각의 Java 파일과 클래스들이 생성됨.

jsp:include 액션 태그: 포함되는 JSP의 내용을 처리하기 위한 각각의 독립적인 자바 파일과 클래스가 생성됨, JSP 페이지의 내용을 현재 JSP 페이지에 동적으로 추가. 
이때 포함시키는 JSP 페이지의 내용이 현재 페이지에 복사되어 포함되는 것이 아니라,런타임 중에 서버에서 포함시키는 페이지를 처리한 결과를 현재 페이지에 출력.
-> 포함시키는 JSP의 내용이 변경되거나 수정될 때마다 새로운 자바 파일과 클래스가 생성됨. 이는 런타임에서 동적으로 포함시키는 방식이기 때문임.

<%@ include %> 지시문은 해당 JSP 페이지의 내용을 포함시키는 것이 아니라, JSP 페이지를 컴파일할 때 포함시킬 대상 JSP의 내용을 그대로 복사하여 현재 페이지에 포함시키는 방식.
대상 JSP 페이지의 변경사항이 현재 페이지에 반영되는 것이 아니라, 컴파일 시점에서의 정적인 처리를 의미.
JSP의 내용이 수정되어도 다시 컴파일하지 않는 이상 변경된 내용이 반영되지 않음. 이는 정적인 처리로 인해 재사용성이나 모듈화 측면에서는 제약이 있을 수 있음.
        --%>
    
        <h1>JSP Action Tag</h1>
        <%-- 
          JSP 액션 태그: 스크립트릿에서 사용되는 자바 코드들을 HTML 또는 XML 등에 사용되는
          태그로 대체하기 위해서 정의된 태그.
          * <jsp:forward></jsp:forward>
          * <jsp:include></jsp:include>
          * <jsp:useBean></jsp:useBean>
          * <jsp:getProperty></jsp:getProperty>
          * <jsp:setProperty></jsp:setProperty>
        --%>
        
        <% // 스크립트릿에서 자바 객체 생성:
        Contact c1 = new Contact();
        %>
        <p>
            c1: <%= c1 %> <%-- c1.toString() 메서드가 자동 호출됨. --%>
            <br />
            c1.id: <%= c1.getId() %>
            <br />
            c1.name: <%= c1.getName() %> 
        </p>
        
        <%-- JSP action tag를 사용한 자바 객체 생성: --%>
        <jsp:useBean id="c2" class="com.itwill.jsp1.model.Contact"></jsp:useBean>
        <p>
            c2.id: <jsp:getProperty property="id" name="c2"/>
            <br />
            c2.name: <jsp:getProperty property="name" name="c2"/>
        </p>
		<%--
		id 속성: JSP 페이지 내에서 해당 객체를 식별하는 데 사용될 이름. 이 이름을 통해 자바 객체의 메서드를 호출하거나 속성에 접근 
		name 속성: JSP 페이지에서 사용할 자바 객체의 이름을 정의. 위에서 정의한 id 값을 사용하여 객체를 참조 --%>
        
        <% // 지역 변수 선언, 조건문, 반복문, 출력문, 메서드 호출, ....jspService() 메서드 안에 포함되는 자바 코드
        c1.setName("오쌤");
        %>
        <p>
            c1.name: <%= c1.getName() %>
        </p>
        
        <jsp:setProperty property="name" value="홍길동" name="c2"/> <%-- value: argument --%>
        <p>
            c2.name: <jsp:getProperty property="name" name="c2"/>
        </p>
        
        <%
        Contact c3 = new Contact(1, "오쌤", "010-0000-0000", "jake@itwill.com");
        %>
        <jsp:useBean id="c4" class="com.itwill.jsp1.model.Contact">
            <jsp:setProperty name="c4" property="id" value="4" />
            <jsp:setProperty name="c4" property="name" value="홍길동" />
            <jsp:setProperty name="c4" property="phone" value="010-1234-5678" />
            <jsp:setProperty name="c4" property="email" value="hgd@itwill.co.kr" />
        </jsp:useBean>
        <p>
            id: <jsp:getProperty property="id" name="c4"/>
            <br />
            name: <jsp:getProperty property="name" name="c4"/>
            <br />
            phone: <jsp:getProperty property="phone" name="c4"/>
        </p>
        
    </body>
</html>