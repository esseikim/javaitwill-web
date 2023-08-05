/**
 *  댓글 영역 보이기/숨기기 토글
 *  댓글 검색, 등록, 수정, 삭제
 */

/*한번만 호출*/
document.addEventListener('DOMContentLoaded', () => {
	// 로그인한 사용자 이름 -> 댓글 등록, 수정, 삭제할 때 사용하기 위해서.
    const authName = document.querySelector('div#authName').innerText;
	
	
    //  부트스트랩 Collapse 객체를 생성. 초기 상태는 화면에 보이지 않는 상태
    // {toggle: false} : 자바스크립트 객체 {key:value}
    const bsCollapse = new bootstrap.Collapse('div#replyToggleDiv', { toggle: false }); 


    // 토글 버튼을 찾고, 이벤트 리스너를 등록
    const btnToggleReply = document.querySelector('#btnToggleReply');
    // 모든 이벤트리스너는 아규먼트로 이벤트를 전달받는다. 
    // 이벤트를 발생시킨 타겟(btnToggleReply)을 찾고, 속성을 찾을 수 있음.  
    btnToggleReply.addEventListener('click', (e) => {
        bsCollapse.toggle();
        // console.log(e.target.innerText);  // innerText: 시작, end 태그 사이의 문자열 

		// 버튼 이름 바꾸기.
        // e.target: 버튼이라는 요소 출력
        if (e.target.innerText === '보이기') {
            e.target.innerText = '숨기기'

            // 댓글 목록 불러오기
            // collapse(댓글 보여주기)가 열렸을 때 함수 호출하면 됨.  
            getRepliesWithPostId();
        } else {
            e.target.innerText = '보이기'
        }
    });



    // 댓글 삭제 버튼 클릭을 처리하는 이벤트 리스너 콜백: 
    const deleteReply = (e) => {
        // 이벤트가 발생한 target 찾기.
        //  console.log(e.target);

        const result = confirm('정말로 삭제할까요?');
        if (!result) {
            return;
        }

        // data-id에 있는 값 -> 삭제할 댓글 아이디
        // 삭제할 댓글 아이디
        const id = e.target.getAttribute('data-id');

        // Ajax DELETE 방식 요청 주소
        const reqUrl = `/api/reply/${id}`;

        // axios 라이브러리가 가진 함수를 연쇄호출
        axios
            .delete(reqUrl) // Ajax DELETE 요청을 보냄
            .then((response) => {
                console.log(response);

                // 댓글 목록 새로고침
                // const postId = document.querySelector('input#id').value;
                // 함수 선언시 argument를 선언하지 않았기에 param 작성시 해당 param은 무시됨.
                getRepliesWithPostId();

            }) // 성공 응답일 때 실행할 콜백 등록
            .catch((error) => console.log(error)); // 실패 응답일 때 실행할 콜백 등록
    };
    
    
    // 댓글 수정 버튼들의 클릭을 처리하는 이벤트 리스너 콜백:
    const updateReply = (e) => {
	    // console.log(e.target);  e.target: 버튼 

        // 수정할 댓글 아이디: data-id
        const replyId = e.target.getAttribute('data-id');
        // 댓글 입력 textarea 아이디
        const textAreaId = `textarea#replyText_${replyId}`;
        
        //  수정할 댓글 내용(textarea에 입력된 내용)
        const replyText = document.querySelector(textAreaId).value;
        if(replyText ===''){
            alert('수정할 댓글 내용을 입력하세요.')
            return;
        }
        
        // 요청 주소
        //-> 'http://localhost:8090/api/reply/10' 주소 줄일 뿐임.
        //-> queryString, pathVariable은 다름.
        //-> queryString에서 requestParameter의 이름은 중요함. -> 서버에서도 변수선언시 이름 동일하게 해야 함.
        //-> pathVariable에는 replyTd는 고정값이 아니라 변할 수 있는 변수. -> 변수의 이름 자체가 중요하지 않음
        // js: replyTd, controller: id는 함수와 비슷함.
        //  (예) ==> 함수를 호출하는 곳에서는 replyTd값을 넘긴거고 받는 곳에서는 id라고 하겠다.
        // Ajax PUT 요청 
        const reqUrl = `/api/reply/${replyId}`; // 요청주소
        
        
        // Ajax 요청에서 보낼 데이터.
        // @requestBody로 들어감.
        // {replyText: replyText}, 요청 데이터(수정할 댓글 내용.)
        // data가 response.data이고 생긴 모양이 객체이기에 하나의 데이터라도 dto 선언 필
        const data = {replyText};  // 요청 데이터(수정할 댓글 내용)
    
        // axios 라이브러리가 가진 함수를 연쇄호출 // PUT 방식의 Ajax 요청
        axios
            .put(reqUrl, data)
            .then((response) => {   // 성공 응답일 때 실행할 콜백 등록
                console.log(response);
                getRepliesWithPostId(); // 댓글 목록 새로고침.
                // getRepliesWithPostId(postId);
            })
            .catch((error) => console.log(error)); // 실패 응답일 때 실행할 콜백 등록
    };


    // const: 바뀔 수 없는 상수 변수, let: 값이 바뀌는 변수 선언
    // `` : 문자열 템플릿 안에 변수 사용 가능
    const makeReplyElements = (data) => {
        // 댓글 개수를 배열(data)의 원소 개수로 업데이트. 'span#replyCount': 댓글 개수
        document.querySelector('span#replyCount').innerText = data.length;

        // 댓글 목록을 삽입할 div 요소를 찾음
        const replies = document.querySelector('div#replies');

        // div 안에 작성된 기존 내용은 삭제
        replies.innerHTML = '';

        // div 안에 삽입할 HTML 코드 초기화
        let htmlStr = '';
        for (let reply of data) {    // for of: 배열에서 원소를 하나씩 꺼내 선언한 변수 reply에 채움
            htmlStr += ` 
               <div class = "card my -2">
                    <div>
                        <span class="d-none">${reply.id}</span>
                        <span class="fw-bold">${reply.writer}</span>
                      </div>
                      `;
                      
         // 로그인한 사용자와 댓글 작성자가 같을 때만 삭제, 수정 버튼을 보여줌.
         if (authName === reply.writer) {
			 htmlStr += `
             <textarea id="replyText_${reply.id}">${reply.replyText}</textarea>
             <div>
                <button class="btnDelete btn btn-outline-danger" data-id="${reply.id}">삭제</button>
                <button class="btnModify btn btn-outline-success" data-id="${reply.id}">수정</button>
             </div>
           `;
        } else {
            htmlStr += `
            <textarea id="replyText_${reply.id}" readonly>${reply.replyText}</textarea>
            `;
        }
        
        htmlStr += '</div>';

        }

        // 작성된 HTML 문자열을 div 요소의 innerHTML로 설정.
        replies.innerHTML = htmlStr;   // html에 들어갔을 때 버튼이 생성됨

        // 모든 댓글 삭제 버튼들에 이벤트 리스너 등록
        // -> 버튼'들'이기에 다수의 버튼에 id값이 필요함. -> data-id는 만든 것. 즉, 버튼 데이터의 속성값을 설정함.
        // 버튼마다 삭제할 댓글 id는 다름 -> reply.id를 저장할 수 있는 속성 생성  data-id="${reply.id}"
        // querySelector: 같은 element가 여러개 -> 가장 먼저 등장하는 하나를 찾음. All: 같은 element 전부를 찾음
        const btnDeletes = document.querySelectorAll('button.btnDelete');
        for (let btn of btnDeletes) {
            btn.addEventListener('click', deleteReply);  // 콜백함수 대신 함수 생성
        }
        // e.target : button 에서 속성 찾기

        // 모든 댓글 수정 버튼들에 이벤트 리스너를 등록 
        // 함수 호출시점: 버튼 생성(btn), 버튼 클릭된 후 실행(updateReply). 위치 중요하지 않음 
        const btnModifies = document.querySelectorAll('button.btnModify');
        for (let btn of btnModifies) {  // btnModifies: 버튼들의 배열
            btn.addEventListener('click', updateReply);

        }
    }


/* 
비동기적인(Asynchronous) 실행은 프로그램에서 특정 작업을 동기적으로 처리하지 않고, 
해당 작업이 완료될 때까지 기다리지 않고 다음 작업을 실행하는 방식을 의미합니다.
일반적인 동기적인 실행은 순차적으로 코드가 실행되며, 특정 작업이 완료되기 전까지 다음 작업은 실행되지 않습니다. 
이로 인해 특정 작업이 시간이 오래 걸릴 경우, 전체 프로그램의 성능이 저하될 수 있습니다.
*/


    // 비동기적 처리
    // await라는 키워드가 들어가면 async를 붙여줘야함(비동기 함수임을 뜻함)
    // 비동기적으로 요청이 올 때까지 기다렸다가 처리하는 코드가 없는 (즉각즉각 리턴이 오는 코드) 함수에서는 async 수식어 불필요
    // body 안의 비동기적 처리 코드 유무 확인 후 적용 
    // await가 없을 경우에는 async수식어 안 써도 됨.


    // 비동기 함수 async
    // 포스트 번호에 달려있는 댓글 목록(Ajax 요청으로)을 가져오는 함수:
    const getRepliesWithPostId = async () => {
        const postId = document.querySelector('input#id').value; // 포스트 아이디(번호)
        const reqUrl = `/api/reply/all/${postId}`;   
        // Ajax 요청 주소. 문자열 템플릿 (ajax - api로 전부 시작할 것)  
        // -> pathVariable 사용시 `` 사용.


        // Ajax 요청을 보내고 응답을 기다림. 
        // Ajax 요청방식: get, post, put, delete
        /*
	        Ajax 요청:
	        get -> 목록을 가져오는 것(select)
	        Post -> 댓글 등록(insert)
	        put -> 댓글 수정(update)
	        delete -> 댓글 삭제(delete)
        */
        // form에서 사용할 수 있는 방식: get, post 
        try {
            const response = await axios.get(reqUrl);   //  Ajax 요청을보내는 코드. axios(라이브러리)의 await: 요청이 올 때까지 기다림. 서버동작 후 응답이 올 때까지 기다림.
            //   console.log(response);  // 응답이 내려와 response에 저장. 그 결과를 가지고서 js가 success라는 문자열 출력 
            makeReplyElements(response.data); // response 안에 data라는 property를 넘겨줌. 
        } catch (error) {
            console.log(error);
        }
    };

    // 댓글 등록 버튼을 찾고, 이벤트 리스너 등록.  
    const btnReplyCreate = document.querySelector('button#btnReplyCreate');
    btnReplyCreate.addEventListener('click', () => {
        // 포스트 아이디 찾음(몇번 포스트의 댓글? )
        const postId = document.querySelector('input#id').value;
        // 댓글 내용. 댓글 내용이 비어있을 경우 요청 x
        const replyText = document.querySelector('textarea#replyText').value;
        // TODO: 댓글 작성자는 admin. 나중에 로그인한 사용자 아이디로 변경. 
        // const writer = 'admin';
		const writer = authName;
		
        //  alert(`${postId}, ${replyText}, ${writer}`);

        // 댓글 내용이 비어 있으면 경고창을 보여주고 종료.
        if (replyText === '') {
            alert('댓글 내용을 입력하세요.');
            return;
        }

        // js에서의 {}: 힘수의 body, for/if문, 변수 선언 + {} <- js object
        // // {postId: postId, replyText: replyText, writer: writer}; 
        // object의 key name === 지역변수명 일치 -> key name 생략 가능


        // Ajax 요청에서 보낼 데이터.
        /*
        js에서 {}:
        1. 함수 바디
        2. 선언 = {}
           => object
           => (예) {x: 1, y: 'abc'}
           => 2015년 이후 문법:
           if(지역 변수 이름 === object의 key name) {
               const data = {postId, replyText, writer}; 
           }
        */
        // Ajax 요청에서 보낼 데이터.
        const data = { postId, replyText, writer };
        // Ajax 요청을 보낼 URL
        const reqUrl = '/api/reply'; // 공통 주소. data 안에 id 존재. pathvariable을 만들 필요 없을 땐 `` 사용하지 않아도 됨.

        // 함수의 연쇄적 호출. async(try, catch) 함수와 동일함.            
        // Ajax POST 방식의 요청을 보냄. Ajax: 요청방식의 이름 = 함수 이름
        // post(): 요청을 보냄
        // then(): 응답이 오면 기다렸다가 성공 응답일 때 실행할 콜백
        axios
            .post(reqUrl, data)
            .then((response) => {
                console.log(response);

                // 댓글 목록 새로고침
                getRepliesWithPostId();
                // 댓글 입력한 내용을 지움. 
                document.querySelector('textarea#replyText').value = '';
            })
            .catch((error) => {
                console.log(error);
            }); // 실패(error)일 때 실행할 콜백 등록. 

    });



});