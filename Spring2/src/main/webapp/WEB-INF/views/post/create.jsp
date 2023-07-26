<%@ page language="java" contentType="text/html; charset=UTF-8"
                      pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
                 <meta name="viewport" content="width=device-width, initial-scale=1">
				<title>Spring 2</title>
                <link 
                    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" 
                    rel="stylesheet" 
                    integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" 
                    crossorigin="anonymous">
	    </head>
        
		<body>
		<div class="container-fluid">
				<header class="my-2 p-5 text-center text-bg-dark">
                        <h1>포스트 작성</h1>
                </header>
                
                <nav class="navbar navbar-expand-lg bg-body-tertiary"> <!-- 네비게이션 메뉴 작성. 어디로 이동하느냐 -->
                        <ul class="navbar-nav bg-light">
                                <li class="nav-item">
                                        <c:url var="mainPage"  value="/" />
                                        <a  class="nav-link"  href = "${ mainPage }">메인 페이지</a>
                                </li>
                                <li class="nav-item">
                                        <c:url var="postListPage"  value="/post/list" />
                                        <a  class="nav-link"  href = "${ postListPage }">포스트 목록</a>
                                </li>
                        </ul>
                </nav>
                
                <main class="my-2"> <!-- form 양식 -->
                        <div class="card" > <!-- card 형태로 만들기 위해 div로 감쌈 -->
                                <form method="post">  <%-- action이 없을 경우 현재 페이지로 요청 보냄 --%>
                                         <div class="card-body" >
                                                    <div class="my-2">
                                                            <label class="form-label"  for="title">제목</label> <!-- for="title": title이란 id를 찾아가기 위한 label. for와 id 속성 이용 -->
                                                            <input  class="form-control"  type="text"  id="title"  name="title"  required autofocus/>
                                                    </div>
                                                    <div class="my-2">
                                                             <label class="form-label"  for="content">내용</label> 
                                                             <textarea  class="form-control" id="content"  name="content" required></textarea>
                                                    </div>
                                                     <div class="my-2" >
                                                            <label class="form-label" for="author">작성자 아이디</label> 
                                                            <input  class="form-control"  type="text"  id="author"  name="author"  required /> <!-- name 속성: requestparameter를 주기 위함  -->
                                                    </div>
                                        </div>
                                        <div class="card-footer my-2">
                                                <input class="form-control  btn btn-outline-primary" 
                                                type="submit"  value="작성완료"  />
                                        </div>
                                </form>
                        </div>
                </main>
				
				   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" 
				   integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" 
				   crossorigin="anonymous"></script>               
		</div>		
		</body>
</html>