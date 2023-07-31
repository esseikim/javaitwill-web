/**
 *  post-modify.js
 * WEB-INF/post/modify.jsp에서 사용되는 파일
 */

 /* 1. 두번째 인자: 이벤트 핸들러 리스너 함수. 
    2. element를 찾아 동작 등록. button 같은 element가 먼저 만들어져야 함.
    DOMContentLoaded: documentobject들이 전부 만들어진 다음에 실행할 코드다. 
 */
 document.addEventListener('DOMContentLoaded', function(){
     // form 요소를 찾음.
     const form = document.querySelector('#postModifyForm');
     /* css 쿼리 셀렉터 사용법과 동일. form의 id 찾는 방법.
     [form]#postModifyForm
      class = . */
     
     // input#id 요소를 찾음. 변수이름 const
     const inputId = document.querySelector('input#id');
     
     // inpu#title 요소를 찾음.
     const inputTitle = document.querySelector('input#title');
     
     // textarea#content 요소를 찾음
     const textareaContent = document.querySelector('textarea#content');
     
     // 수정완료 버튼을 찾음. 버튼 태그 중 id가 btnUpdate인 걸 찾겠다.
     const btnUpadate = document.querySelector('button#btnUpdate');  
     const btnDelete = document.querySelector('button#btnDelete');
     
     // 삭제 버튼에 클릭 이벤트 핸들러(리스너)를 등록. 익명함수의 아규먼트 이벤트타입 e
    btnDelete.addEventListener('click', (e) => {
        e.preventDefault();
        // -> 원래의 form 안에 있는 버튼 또는 input[type=submit]의 기본(클릭) 동작은 
        // 폼의 내용을 서버로 제출(서버로 새로운 요청을 보냄).
        // 버튼의 기본동작이 기능하지 않도록 방지.(제출 안됨! -> 주소창 바뀌지 않음)
        // 동작 확인: console 창에 log 출력 / alert(브라우저가 띄워주는 창)
/*        alert('삭제 버튼 클릭됨.');*/

        // jsp- ${}: el   vs   js- '${}' : js 변수 찾아감. 
        
       const id = inputId.value;  // 요소를 찾은 변수를 이용하여(태그의 속성이름 - id)의 value를 씀
       const result = confirm(`NO. ${id} 정말 삭제할까요?`); // ``문자열 용도: 문자열 템플릿 사용.
       // confirm()의 리턴 값: (1) 확인 -> true  (2) 취소 -> false
       
       console.log(`삭제 확인 결과 = ${result}`);  
       // 브라우저 개발자 도구 콘솔 창 로그. 이클립스 콘솔 창x(자바스크립트: 브라우저에서 실행됨. -> 로그도 브라우저 콘솔창에서 보이게 됨)
       
       // jsp에서 foreach/if/curl/el 같은 태그를 쓰면, 웹서버에서 자바코드로 변환돼서 그 자바코드가 실행됨. 서버에서 실행된 jsp 코드는 html이 됨. 브라우저가 보여줌(client로 html이 돼서 오는 것.)
       // js도 서버가 보내주는 것. 브라우저가 요청(새로고침)을 보낸 것. modify?id=1 과 post-modify.js 
       // 브라우저가 html에 있는 것들을 그리다가, 제일 마지막에 js(서버에 있음) 쓰겠다는 코드 있어서 브라우저가 서버에게 요청을 한번 더 함. 서버는 js파일코드를 브라우저에게 보내줌. 브라우저는 그 코드를 임시 폴더에 저장하고 있다가, 이벤트 발생 시 브라우저 이벤트를 실행함. 
       // 즉 js는 서버쪽에서 다운로드 됐다가 브라우저가 실행하는 것!!  jsp: 서버쪽에서 실행함.
      
       // 사용자가 confirm 창에서 '확인'을 클릭했을 때 form을 submit 하면 됨.
      if (result){ // true
          form.action = 'delete'; // 폼 제출(요청) 주소   /post/delete를 처리할 수 있는 controller(servlet)을 만들면 됨. 
          form.method = 'post'; // 요청 방식
          form.submit(); // 폼 제출(요청 보내기)
      }           
      
    });
    
    btnUpdate.addEventListener('click', (e) => {
         e.preventDefault(); // 기본 동작인 폼 제출 기능을 막음.
         
         const id = inputId.value; // 포스트 번호. 
         const title = inputTitle.value; // 포스트 제목. 비어있는 지 확인
         const content = textareaContent.value; // 포스트 내용. 비어있는 지 확인

         if (title === '' || content === ''){ // 타입까지 검사해줌. title이 문자열이 맞느냐? 맞다면 비교. 
             // 제목 또는 내용이 비어 있으면 
             alert('제목과 내용은 반드시 입력해야 합니다.');
             return; // 함수 종료. 아래쪽 코드를 실행하면 안 됨. 
         }
         
         const result = confirm(`NO ${id} 포스트를 업데이트할까요?`);   
         
         if(result) {
             form.action = 'update'; // 업데이트 요청 주소 // 업데이트를 처리하는 controller 만들어줘야 함. 
             form.method = 'post'; // 요청 방식
             form.submit(); // 폼 제출(요청 보내기)
         }
      });    
 });