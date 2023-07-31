<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>JSP</title>
                <% 
                // 요청 파라미터의 한글이 깨지지 않도록 하기 위해서: 
                request.setCharacterEncoding("UTF-8");
                // 요청 파라미터 값 찾기: 요청에 포함된 요청 파라미터 값 찾는 메서드. form에서 설정한 name 속성의 값으로 찾음.
                // 아닌 경우에만 else 실행
                String username = request.getParameter("username");
                String color =request.getParameter("color");
                String colorValue = "";
                if(color.equals("r")){ 
                    colorValue = "crimson";
                }else if (color.equals("g")){
                    colorValue = "darkgreen";
                }else if (color.equals("b")){
                    colorValue = "dodgerblue";
                }
                %>
                
                <c:set var="colorValue2" value="black"></c:set>
                <!-- 변수선언, 값. rqparm 중 name=color 사용. el에서는 ==로 비교, 이퀄스 사용 x, 큰 따옴표 가리지 않지만, 미관상 ''
                만약 파라미터 값이 r과 같으면 color 변수를 red로 설정하겠다
                자바 코드와 비슷하지 않음. 태그들로 이루어짐. if 뿐임. else 없음. 
                조건을 만족하는지 전부 검사를 함. 
                -->
                <c:if test="${param.color =='r' }"></c:if>
                        <c:set var="colorValue2" value= "red"></c:set>
                <c:if test="${param.color =='g' }"></c:if>
                        <c:set var="colorValue2" value= "green"></c:set>
                <c:if test="${param.color =='b' }"></c:if>      <!-- 태그 속성(test) 값  -->
                        <c:set var="colorValue2" value= "blue"></c:set>
                        
                        <%-- colorValue에 있는 변수의 값을 이 자리에 쓰겠다. jsp 태그 
                        id 속성 추가. span 태그들 중 span1 --%>
                <style >
                        span#span1{
                                color: <%=colorValue%>; 
                        }
                        
                        span#span2{
                            color: ${colorValue2 };
                           <%-- JSTL의 <c:set> 태그에서 선언한 변수는 EL에서 사용 가능.--%>
                        }
                </style>
	    </head>
        
		<body>
				<h1>요청 결과 페이지</h1>
                <h2>JSP</h2>
                <h3>아이디: 
                   <span id= "span1"><%= username %></span>
                </h3><%--요청 파라미터에 있는 값을 이 자리에 쓰겠다. el<-스크립트릿로 표시한 것. 색 지정을 위해 span 사용
                   username=오쌤&color=r  두개의 요청 파라미터가 전달됨. 
                   --%>
                   
                   <hr/>
                   <h2>JSTL, EL</h2>
                   <h3> 아이디:
                            <span id = "span2">${ param.username }</span> <!-- el에서 rqp 사용하는 방법 -->
                   </h3>
                   
                   <hr/>
                    <c:choose>  
                            <c:when test="${ param.username == 'admin' }"> <!-- { param.username == 'admin' } : true/false의 문자열이 됨 -->
                                    <h2>관리자 페이지</h2>
                            </c:when>
                            <c:otherwise>
                                    <h2>일반 사용자 페이지</h2>
                            </c:otherwise>
                    </c:choose>      <!--선택하겠다는 코어태그. 
                    if를 대체(else 없음)할 수 있는 choose: switch, when: case, otherwise: default 검사를 한번만 하기-->             
		</body>
</html>