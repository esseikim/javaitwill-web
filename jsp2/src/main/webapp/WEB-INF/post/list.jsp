<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 순서: 1.list.jsp  // -> 2. postDatailContorller  -> 
    3. Postservice  -> 4. PostDao(reoository) > 5. postDatailContorller // -> 6. detail.jsp -->
    
    <!-- url을 이용하려면 taglib이 있어야함 
				 요청을 처리하는 서블릿 controller, 화면에 보여주는 jsp view -->
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Post</title>
    </head>
    <body>
        <header>
            <h1>포스트 목록 페이지</h1>
        </header>
        
        <nav> <%-- 돌아가기 --%>
            <ul>
                <!-- 로그인한 username이 있는 경우 -->
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
                        <c:url var="signUpPage" value="/user/signup"></c:url>
                        <a href="${ signUpPage }">회원가입</a>
                    </li>
                </c:if>
                
								<!-- href="./"는 현재 위치(폴더)를 나타내는 상대경로, href="/"는 웹 애플리케이션의 context root(웹 애플리케이션의 최상위 경로). 즉,context root를 나타내는 절대경로 -->
                <li>
                    <c:url var="mainPage" value="/"></c:url> <!-- @WebServlet(name = "homeController", urlPatterns = {""}) 와는 조금 다름. -->
                    <a href="${ mainPage }">메인 페이지</a>
                </li>
                <li>
                    <c:url var="postCreate" value="/post/create"></c:url> <!-- value: 변수 postCreate에 저장된 주소  -->
                    <a href="${ postCreate }">새 포스트 작성</a>
                </li> <!-- cf) @WebServlet(name = "postListController", urlPatterns = {"/post"}) listcontroller 처럼 동일하게 -->
            </ul>
        </nav>
        
        <main>
            <table>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>수정시간</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${ posts }" var="post">
                        <tr>
                            <td>${ post.id }</td>
                            <td>
                                <c:url value="/post/detail" var="postDetail"> <!-- http://localhost:8081/post/  -> post/detail?id=21 -->
                                    <c:param name="id" value="${ post.id }"></c:param> <!-- 주소의 쿼리문에서 확인할 수 있도록 파라미터를 넘겨주는 태그. name 속성으로 확(필수). id 값 필요. -->
                                </c:url>
                                <a href="${ postDetail }">${ post.title }</a> <!-- 링크 추가 -->
                            </td>
                            <td>${ post.author }</td>
                            <td>${ post.modifiedTime }</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <c:url value="/post/search" var="searchPage"></c:url>
            <form action="${ searchPage }">
                <select name="category">
                    <option value="t">제목</option>
                    <option value="c">내용</option>
                    <option value="tc">제목 + 내용</option>
                    <option value="a">작성자</option>
                </select>
                <input type="text" name="keyword" placeholder="검색어" required autofocus />
                <input type="submit" value="검색" />
            </form>
        </main>
    </body>
</html>