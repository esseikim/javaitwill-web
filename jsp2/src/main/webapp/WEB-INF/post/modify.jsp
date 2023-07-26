<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Post</title>
    </head>
    <body>
        <header>
            <h1>포스트 수정 페이지</h1>
        </header>
        
        <nav>
            <ul>
                 <li>
                        <c:url value= "/user/signout"  var="signOut"></c:url>
                         <span>${singedInUser}</span>
                         <a href="${signOut }" >로그아웃</a>
                </li>
                <li>
                    <c:url value="/" var="mainPage" />
                    <a href="${ mainPage }">메인 페이지</a>
                </li>
                <li>
                    <c:url value="/post" var="postList" />
                    <a href="${ postList }">포스트 목록</a>
                </li>
                <li>
                    <c:url value="/post/detail" var="postDetail">
                        <c:param name="id" value="${ post.id }"></c:param>
                    </c:url>
                    <a href="${ postDetail }">포스트 상세보기</a> <!-- 목록에서 어떤 글을 클릭하든, 상세보기페이지로 넘어가도록 하는 파일-->
                </li>
            </ul>
        </nav>
        
        <main>
            <form id="postModifyForm"> <!--action:가려고하는 주소.(요청보내는 주소) method: 요청방식  
            따로 지정하지 않으면, 현재 페이지를 보고 있는 그대로를 요청을 다시 보내는 것. -->
                <div> <!-- 요청파라미터에 보낼 name-->  
                    <input id="id" name="id" 
                        type="text" value="${ post.id }" readonly /><!-- controller에서 setAttribute한 이름 이용-->         
                </div>
                <div>
                    <input id="title" name="title" 
                        type="text" value="${ post.title }" autofocus /> <!-- 포커스 -->
                </div>
                <div> <!-- id: element를 찾기 위함. name: 서버로 보내기 위한 리퀘스트 파라미터.   -->
                    <textarea id="content" name="content"  
                        rows="5" cols="80">${ post.content }</textarea>
                </div>
                <div>
                    <input type="text" value="${ post.author }" readonly />
                </div>
                <c:if test="${ signedInUser == post.author }">
                <%-- 버튼이 사라져서 클릭 불가능. post 방식에서 update delete 불가능. 권한을 가진 사용자들만 들어올 수 있도록. --%>
                        <div>
                            <button id="btnUpdate">수정완료</button>  
                            <!-- input type submit이랑 동일함. form안의 버튼: form을 서브밋함(기본동작). form 안에 있으면 submit이 안됨. click만 될 뿐
                            submit은 기본임! form 안에 있는 button은 ! -->
                           <!-- 이벤트 핸들러를 js를 이용해 form안에 있든 바깥에 있든 등록해야함. 하는 일이 다르기 때문에. -->
                            <button id="btnDelete">삭제</button>
                        </div>
                </c:if>
            </form>
        </main>
        
        <script src='../js/post-modify.js'></script> <!--한단계 올라가서 js 폴더 찾고, 파일이름 찾아들어감  -->
    </body>
<!-- 가능하면 js는 html body안에 작성하지 않고 별도의 파일로 만드는 걸 추천함(긴 경우) 
jsp는 컨트롤러에서 포워딩해서 올것: 웹inf 에 작성가능(서버 내부에서만 접속 가능한 폴더), 외부 불가
js, 이미지 같은 것들은 webapp에!
-->
</html>
