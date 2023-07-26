<%@page import="java.sql.Timestamp"%>
<%@page import="java.time.LocalDateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>   
<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>JSP</title>
	    </head>
		<body>
				<h1>JSTL fmt</h1>
                
                <%--JSTL <fmt: formatDate> 태그는 
                        java.sql.Date, java.sql.Timestamp 타입의 객체들을 원하는 형식으로 포맷팅.
                        java.time.LocalDate, javatime.LocalDateTime 객체들은 포맷팅을 못함. 
                        모든 시간 객체들을 포맷팅할 수 있는 게 아닌, 위의 두가지 타입에 대해서만 포맷팅 기능을 제공함. 
                 --%>
                <%--JSTL을 사용한 변수 선언 --%> 
             <%--   <c:set var= "now" value="<%=LocalDateTime.now()%>"></c:set>  
             timestamp의 객체가 됨!!! 포맷팅 못해서(위) now 변수에 대해서 수정한 게 아래! --%>
             <c:set var= "now" value="<%=Timestamp.valueOf(LocalDateTime.now())%>"></c:set>
             
                <h2>${now }</h2> <!-- now 객체가 가진 toString()를 화면에 출력한 것. 변수이름을 직접 사용하지 않고 el을 사용해서 사용하면 됨. type엔 지정된 문자열을 사용. -->
	           
               <h2> date:
                        <fmt:formatDate value="${now }"  type="date"  />
               </h2>
               
                <h2> time: 
                        <fmt:formatDate value= "${now }"  type="time"  />
                </h2>
                
                <h2> date &amp; time:
                        <fmt:formatDate value="${now }"  type= "both" />             
                </h2>
                
                   <h2> 
                        <fmt:formatDate value="${now }"  type= "both"  dateStyle= "full"  timeStyle="full"      />     
                  </h2>
                  
                    <h2> 
                        <fmt:formatDate value="${now }"  type= "both"  dateStyle= "short"  timeStyle="short"      />     
                  </h2>
                  <h2>
                        <fmt:formatDate value="${now }"  pattern="yyyy/MM/dd HH:mm:ss" /> 
                        <!-- 포맷을 원하는 패턴으로 지정 가능. 커스텀.  24시간, 분과 초 2자리 표현-->
                  </h2>
                  
    	</body>
</html>