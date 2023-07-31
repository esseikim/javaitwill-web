<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    <!-- 순서: 1.list.jsp  // -> 2. postDatailContorller         -> detail.jsp // -> 
    3. Postservice  -> 4. PostDao(reoository) > 5. postDatailContorller // -> 6. detail.jsp -->
    
    <!--url을 이용하려면 taglib이 있어야함 
    요청을 처리하는 서블릿 controller
    화면에 보여주는 jsp view -->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>POST</title>
    </head>
	<body>
        <header>
		     <h1>포스트 목록 페이지</h1>
        </header>
                
                <%--다시 돌아가기 --%>
             <nav>
                <ul>
                    <c:if test="${ not empty signedInUser }">
                        <li>
                        <span>${ signedInUser }</span>
                        <c:url var="signOut" value="/user/signout"></c:url>
                        <a href="${ signOut }">로그아웃</a>
                    </li>
                	</c:if>
                
	                <!-- 로그인한 username이 없는 경우 -->
	                <c:if test="${ empty signedInUser }">
	                    <li>
	                        <c:url var="signInPage" value="/user/signin"></c:url>
	                        <a href="${ signInPage }">로그인</a>
	                    </li>
	                    <li>
	                        <c:url var= "signUpPage" value= "/user/signup"></c:url>
	                        <a href= "${ signUpPage }">회원가입</a>
	                    </li>
	                </c:if>
                        <li>
                         <%-- contextroot: 절대경로 중요하지 않음 
                                           상대경로에서 현재 폴더(./)의 의미: contextroot까지의 주소 
                                            http://localhost:8081/post/ 
                                            <a href="./">메인 페이지</a>
                                            controller는 다 만들었기 때문에 링크에서 페이지 주소만 설정해주면 됨. 
                                            아래 설명: / - contextroot 
                                        --%>
                       		<c:url var="mainPage" value="/"></c:url> 
                          	<a href="${mainPage}">메인페이지</a>
                               <!--@WebServlet(name = "homeController", urlPatterns = {""}) 와는조금 다름. -->
                                    
                       </li>
                       <li>
                            <c:url var="postCreate" value= "/post/create"></c:url> <!-- value: postCreate에 저장된 주소  -->
                            <a href="${ postCreate }">새 포스트 작성</a>
                          <!-- @WebServlet(name = "postListController", urlPatterns = {"/post"}) listcontroller와 동일하게-->
                       </li>
                   </ul>
              </nav>
              
              <main>
                  <table>
                       <thead>
                           <tr>                            
                              <th>번호</th>
                              <th>제목</th>
                              <th>작성자</th>
                              <th>수정 시간</th>
                           </tr>       
                       </thead>
                       <tbody>
                           <c:forEach items="${ posts }" var= "post"><!--반복문. post를 통해 접근 가능  for(posts post<-변수선언 : posts<- 넘겨받은 애. )-->
                              <tr>                            
                                 <td>${ post.id }</td>  <!-- foreach 반복문 통해서 하나씩 꺼내서 post.id를 얻고 -->
                                 <td>
                                    <c:url value= "/post/detail"  var="postDetail">  <!-- http://localhost:8081/post/  post/detail?id=21 -->
	                                     <c:param name="id"  value="${ post.id }"></c:param>
	                                     <!-- 주소의 쿼리문에서 확인할 수 있도록 파라미터를 넘겨주는 태그. name 속성으로 확인-> 필수. id가 무슨 값인지 알아야함.  -->
                                    </c:url>
                                    <a href= "${ postDetail }">${ post.title }</a> <!-- 하나의 링크를 넣는다. postDetail 테이블에 값을 추가할 때 if문처럼 title에 링크(수정페이지) 추가 -->
                   <!-- 변수가 들어가있음. 값은 /post/detail" 경로와 requestparameter를 만들어서 보여주게 됨.   -->
                                 </td>
                                 <td>${ post.author}</td>
                                 <td>${post.modifiedTime}</td>
                              </tr> 
                            </c:forEach>  
                       </tbody>
                  </table>
                  <!-- 요청파라미터: keyword, category -->
                  <c:url value= "/post/search" var="searchPage"></c:url>  <!--post를 search 하는 기능  -->
                  <form action="${ searchPage }">
                       <select name="category">
                            <option value="t">제목</option>
                            <option value="c">내용</option>
                            <option value="tc">제목 + 내용</option>
                            <option value= "a">작성자</option>
                       </select>  
                       <input type="text"  name="keyword"  placeholder="검색어" required autofocus />
                       <input type="submit" value="검색" />
                  </form>
            </main>
		</body>
</html>