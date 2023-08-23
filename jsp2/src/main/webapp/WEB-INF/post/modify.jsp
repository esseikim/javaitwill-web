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
                    <c:url value="/user/signout" var="signOut" />
                    <span>${ signedInUser }</span>
                    <a href="${ signOut }">로그아웃</a>
                </li>
                <li>
                    <c:url value="/" var="mainPage" />
                    <a href="${ mainPage }">메인 페이지</a>
                </li>
                <li>
                    <c:url value="/post" var="postList" />
                    <a href="${ postList }">포스트 목록</a>
                </li>
                <li> <!-- 뒤로 가기 -->
                    <c:url value="/post/detail" var="postDetail">
                        <c:param name="id" value="${ post.id }"></c:param>
                    </c:url>
                    <a href="${ postDetail }">포스트 상세보기</a>
                </li>
            </ul>
        </nav>
        
		<!--  method: 요청방식. 
			  action: 가려고 하는 주소(요청 보내는 주소). 지정하지 않으면, 현재 페이지 그대로를 요청 다시 보내는 것. -->
        <main>
            <form id="postModifyForm">
                <div> <!-- `id`: element를 찾기 위함. `name`: 서버로 보내기 위한 리퀘스트 파라미터. -->
                    <input id="id" name="id" 
                        type="text" value="${ post.id }" readonly /> <!-- PostModifyController에서 setAttribute한 이름 사용. -->
                </div>
                <div>
                    <input id="title" name="title" 
                        type="text" value="${ post.title }" autofocus />
                </div>
                <div>
                    <textarea id="content" name="content" 
                        rows="5" cols="80">${ post.content }</textarea>
                </div>
                <div>
                    <input type="text" value="${ post.author }" readonly />
                </div>
                <c:if test="${ signedInUser == post.author }"> <!-- js의 이벤트 핸들러 등록. form 안에 있든 바깥에 있든 하는 일이 다르기 때문에. -->
                    <div>
                        <button id="btnUpdate">수정완료</button>
                        <button id="btnDelete">삭제</button>
                    </div>
                </c:if>
            </form>
        </main>
        
        <script src="../js/post-modify.js"></script> <!-- 한단계 올라가서 js 폴더 찾고, 파일 이름 찾아 들어감. -->
    </body>
	<!-- js는 html body안에 작성하지 않고 별도의 파일로 만들기.
		 jsp는 컨트롤러에서 포워딩해서 올 것 -> WEB-INF에 작성 가능(서버 내부에서만 접속 가능한 폴더), 외부에서 접근 불가
		 js, css, image: webapp 폴더 아래에 폴더 생성 후 작성. -->
	
	<!-- 
		<button> 태그가 폼 안에 있는 경우: form을 서브밋함(from의 기본동작 적용).
		기본적으로 "submit" 타입으로 동작(버튼 클릭이 폼 서브밋으로 연결) -> 사용자가 해당 버튼을 클릭하면 폼의 데이터가 서버로 전송. 
		즉, 폼의 action 속성에 지정된 URL로 데이터가 전달되고 서버에서 처리됨.
		<button> 태그가 폼 밖에 있는 경우:
		폼 밖의 <button> 태그는 단순히 클릭 이벤트를 처리할 뿐, 폼의 서브밋과는 무관(해당 버튼은 폼을 서브밋하지 않음, 폼의 데이터는 전송되지 않음).
		다만 클릭 이벤트를 활용하여 JavaScript 코드를 실행하거나 다른 페이지로 이동하는 등의 동작을 수행 가능.
		
		즉, <button> 태그가 폼 안에 있을 때에는 기본적으로 폼 서브밋과 연결되어 데이터를 전송하게 되며, 폼 밖에 있을 경우에는 클릭 이벤트만을 처리하게 됨. 
		
		<button> (기본값은 type="submit"):
		type 속성을 명시하지 않은 경우, <button> 태그의 기본 type 값은 "submit". -->
</html>