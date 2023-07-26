<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Post</title>
   </head>
   <body>
        <header>
        <h1>새 포스트 작성 페이지</h1>
       </header>
        
        <nav>
            <ul>
            <li>
                <c:url value= "/user/signout"  var="signOut"></c:url>
                <span>${signedInUser}</span>
                <a href="${signOut }" >로그아웃</a>
            </li>
                <li>
                    <c:url var="mainPage" value="/"></c:url>
                    <a href="${ mainPage }">메인 페이지</a>
                    <!-- <a href="">메인 페이지</a> -->
                </li>
                <li>
                    <c:url var="postList" value="/post"></c:url>
                    <a href="${ postList }">포스트 목록 페이지</a>
                    <!-- <a href="">포스트 목록 페이지</a> -->
                </li>
            </ul>
        </nav>
        
        <main>
            <!-- controller를 새로 만드는 게 아니라 같은 controller를 사용하겠다. -->
            <c:url value="/post/create" var="postCreate" />
            <form action="${ postCreate }" method="post"> <!--post 방식으로 action을 취하겠다~ value로 넘어가겠다.  -->
                <div>
                    <input type="text" name="title" placeholder="제목 입력" 
                        required autofocus/>
                </div>
                
                <div>
                    <!-- 줄 바꿈 -->
                    <textarea row="5" cols="80" name="content" placeholder="내용입력"
                        required></textarea>
                </div>
                
                <div>
                    <!-- 줄 바꿈 x -->
                    <%-- 로그인한 사용자 아이디를 value로 설정, 화면에서는 보이지 않게. 그래도 submit은 된다 --%>
                    <input type="hidden" name="author"  value= "${ signedInUser }"  readonly/>
                </div>
                
                <div>
                    <input type="submit" value="작성 완료" />
                </div>
            </form>
        </main>
    </body>
</html>