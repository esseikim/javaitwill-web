<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Spring 2</title>
        <link 
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" 
            rel="stylesheet" 
            integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" 
            crossorigin="anonymous">
<!-- rel: 링크된 리소스와 현재 문서와의 관계를 지정. <link> 요소에서 주로 사용되며, 주로 CSS 파일과 관련 있음.
     현재 문서와 링크된 리소스(여기서는 Bootstrap CSS 파일)가 스타일 시트 파일이라는 것을 의미.
     스타일 시트 파일은 웹 페이지의 스타일, 레이아웃, 디자인 등을 정의하는 데 사용됨. -->
    </head>
    <body>
    <div class="container-fluid">
        <header class="my-2 p-5 text-center text-bg-dark">
            <h1>포스트 상세 보기</h1>
        </header>
        
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
            <ul class="navbar-nav bg-light">
                <li class="nav-item">
                    <c:url var="mainPage" value="/" /> <!-- 파라미터 없을 땐 바로 닫아도 됨. -->
                    <a class="nav-link" href="${ mainPage }">메인 페이지</a>
                </li>
                <li class="nav-item">
                    <c:url var="postListPage" value="/post/list" />
                    <a class="nav-link" href="${ postListPage }">포스트 목록</a>
                </li>
            </ul>
        </nav>
        
        <main class="my-2">
            <section class="card">
                <form class="card-body">
                    <div class="my-2">
                        <label class="form-label" for="id">번호</label>
                        <input class="form-control" id="id" value="${ post.id }" readonly /> <!-- ${ post }: post.toString. -->
                    </div>
                    <div class="my-2">
                        <label class="form-label" for="title">제목</label>
                        <input class="form-control" id="title" value="${ post.title }" readonly />
                    </div>
                    <div class="my-2">
                        <label class="form-label" for="content">내용</label>
                        <textarea class="form-control" id="content" readonly>${ post.content }</textarea>
                    </div>
                    <div class="my-2">
                        <label class="form-label" for="author">작성자 아이디</label>
                        <input class="form-control" id="author" value="${ post.author }" readonly />
                    </div>
                    <div class="my-2">
                        <label class="form-label" for="createdTime">작성 시간</label>
                        <fmt:formatDate value="${ post.createdTime }"
                            pattern="yyyy-MM-dd HH:mm:ss"  var="created" />
                        <input class="form-control" id="createdTime" value="${ created }" readonly />
                    </div>
                    <div class="my-2">
                        <label class="form-label" for="modifiedTime">수정 시간</label>
                        <fmt:formatDate value="${ post.modifiedTime }"
                            pattern="yyyy-MM-dd HH:mm:ss"  var="modified" />
                        <input class="form-control" id="modifiedTime" value="${ modified }" readonly />
                    </div>
                </form>
                <div class="card-footer">
                    <c:url var="postModifyPage" value="/post/modify">
                        <c:param name="id" value="${ post.id }"></c:param> 
                    </c:url>
                    <a class="btn btn-outline-primary form-control"
                        href="${ postModifyPage }">수정하기</a> <!-- 버튼 버튼-모양-색상. -->
                </div>
            </section> <!-- 포스트 상세 보기 카드. -->
            
            <section class="my-2 card">
                <div class="card-header fw-bold">
                    <span>댓글</span>
                    <span id="replyCount">${ post.replyCount }</span>개
                    <button class="btn" id="btnToggleReply">
                        <img id="toggleBtnIcon" 
                            src="../static/assets/icons/toggle2-off.svg" 
                            alt="toggle-off" width="32"/>
                    </button>
                </div>
                <div class="card-body collapse" id="replyToggleDiv">
                    <!-- 내 댓글 등록. -->
                    <div class="my-2 row">
                        <label class="form-label" for="replyText">나의 댓글</label>
                        <div class="col-10"> <!-- div: block > bootrstrap의 12개의 col 중 10개를 사용. -->
                            <textarea class="form-control" id="replyText"></textarea>
                            <input class="d-none" id="writer" value="admin" /> <!-- TODO: 로그인 사용자 아이디. d-none: 화면에 표시x. -->
                        </div>
                        <div class="col-2">
                            <button class="form-control btn btn-outline-success" id="btnAddReply">
                                등록
                            </button><!-- id: js에서 해당 element 찾아 element 내용 변경을 위함. -->
                        </div>
                    </div>
                    
                    <!-- 댓글 목록 보여줄 영역. -->
                    <div class="my-2 row" id="replies"></div>
                </div>
            </section> <!-- 댓글 등록, 댓글 리스트 카드. -->
            
            <!-- 댓글 수정 모달. -->
            <div id="replyUpdateModal" class="modal" tabindex="-1"> <!-- 모달객체를 찾기 위한 id. -->
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">댓글 수정</h5>
                            <button type="button" class="btn-close"
                                data-bs-dismiss="modal"
                                aria-label="Close"></button> <!-- x 버튼. -->
                        </div>
                        <div class="modal-body">
                            <!-- 수정할 댓글 아이디 - 화면에 보이지 않도록 `d-none` 설정. -->
                            <input id="modalReplyId" class="d-none" />
                            <!-- 수정할 댓글 내용. -->
                            <textarea id="modalReplyText" class="form-control"></textarea>
                        </div>
                        <div class="modal-footer">
                            <button type="button"
                                class="btn btn-secondary"
                                data-bs-dismiss="modal">취소</button> <!-- bootstrap에서 data-bs-dismiss="modal"로 eventHandler 등록. -->
                            <button type="button" id="modalBtnUpdate"
                                class="btn btn-primary">변경 내용 저장</button>
                        </div>
                    </div>
                </div>
            </div> <!-- end modal -->

        </main>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" 
            crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
        <script src="../static/js/reply.js"></script>
    </div>
		<!-- <c:url var="myURL" value="/myPage">
		         <c:param name="param1" value="value1" />
		        <c:param name="param2" value="value2" />
		     </c:url>
		     <a href="${myURL}">링크 텍스트</a> 
		
		<c:url> 태그를 사용하여 URL을 생성하고, 그 URL에 param1과 param2 파라미터를 추가,
		${myURL}: 생성된 URL을 출력하는 표현식.
		
		생성된 URL: /myPage?param1=value1&param2=value2
		위의 예시 코드에서 링크 텍스트를 클릭하면 `/myPage`로 이동하며, URL에 param1=value1과 param2=value2가 추가되어 전달될 것. -->
    </body>
</html>