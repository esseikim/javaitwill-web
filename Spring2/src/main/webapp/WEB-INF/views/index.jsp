<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- 태그 라이브러리 -->    
<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
                 <meta name="viewport" content="width=device-width, initial-scale=1">
                 <!-- https://getbootstrap.com/docs/5.3/getting-started/introduction/ 참고 - Create a new index.html -->
                
				<title>Spring 2</title>
                 <link 
                    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" 
                    rel="stylesheet" 
                    integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <!-- https://getbootstrap.com/docs/5.3/getting-started/introduction/ 참고 - Include Bootstrap’s CSS and JS. 
           href: css 파일의 위치
           integrity: 원본 파일이 맞는지 확인하기 위한 key 값.
           bootstrap: 미리 지정되어 있는 css파일이 존재함.
        -->
	    </head>
        
		<body>
            <div class="container-fluid"> <!-- bootstrap이 가지고 있는 클래스 -> link를 통해 가져옴 -->
				<header class="my-2 p-4 text-center text-bg-dark"> 
                <!-- m:margin, y: y 영역(top,bottom), p:padding(안쪽 여백), text-center: 중앙 정렬, text-bg: text background -->
                        <h1>메인 페이지</h1>
                </header>
                
                <nav class="navbar navbar-expand-lg bg-body-tertiary">
                        <ul class="navbar-nav bg-light">
                                <li class="nav-item"> 
                                       <c:url var="postListPage"  value="/post/list" />
                                       <a class="nav-link"  href="${ postListPage }">포스트 목록</a>
                                </li>
                        </ul>
                </nav>
               <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" 
                             integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" 
                             crossorigin="anonymous"></script>
               <!-- src: js 파일의 위치--> 
	        </div>
    	</body>
</html>