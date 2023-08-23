/**
 * reply.js
 * 댓글 등록, 목록 검색, 수정, 삭제
 * `/post/detail.jsp`에 포함.
 */

document.addEventListener('DOMContentLoaded', () => {
    // 댓글 개수 표시 영역(span)
    const replyCountSpan = document.querySelector('span#replyCount');
    // 댓글 목록 표시 영역(div)
    const replies = document.querySelector('div#replies');
    
    // 댓글 삭제 버튼의 이벤트 리스너 (콜백) 함수
    const deleteReply = (e) => {
        // console.log(e);
        console.log(e.target); // e.target: 이벤트가 발생한 타겟. 여기서는 삭제 버튼.
        
        if (!confirm('정말 삭제할까요?')) {
            return;
        }
        
        // 삭제할 댓글 아이디:
        const id = e.target.getAttribute('data-id');
        // 삭제 요청 URL
        const reqUrl = `/spring2/api/reply/${id}`;
        // 삭제 요청을 Ajax 방식으로 보냄.
        axios.delete(reqUrl)
            .then((response) => {
                console.log(response);
                alert('댓글 삭제 성공');
                getRepliesWithPostId(); // 댓글 목록 갱신.
            })
            .catch((error) => {
                console.log(error);
            });
        
    };
    
    // 댓글 수정 모달 객체 생성
    const modal = new bootstrap.Modal('div#replyUpdateModal', {backdrop: false}); //  `backdrop: true`:  modal 외부 클릭 시 사라짐.
    // 모달의 엘리먼트 찾기
    const modalInput = document.querySelector('input#modalReplyId');
    const modalTextarea = document.querySelector('textarea#modalReplyText');
    const modalBtnUpdate = document.querySelector('button#modalBtnUpdate');
    
    // 댓글 수정 버튼의 이벤트 리스너 (콜백) 함수 - 댓글 수정 모달을 보여주는 함수
    const showUpdateModal = (e) => {
        // console.log(e); // e: 이벤트 객체.
        // console.log(e.target); // e.target: 이벤트가 발생한 타겟. 여기서는 수정 버튼.
        const id = e.target.getAttribute('data-id');
        const reqUrl = `/spring2/api/reply/${id}`;
        axios.get(reqUrl) // 서버로 GET 방식의 Ajax 요청을 보냄. 요청을 보내기 위해서는 url과 데이터가 필요함.
            .then((response) => {
                // reponse에 포함된 data 객체에서 id, replyText 값을 찾음. -> response 안의 data: replyReadDto와 동일.
                const { id, replyText } = response.data;
                
                // id와 replyText를 모달의 input과 textarea에 씀.
                modalInput.value = id;
                modalTextarea.value = replyText;
                
                // 모달을 보여줌.
                modal.show();
            }) // 성공 응답이 왔을 때 실행할 콜백을 등록.
            .catch((error) => console.log(error)); // 실패 응답이 왔을 때 실행할 콜백 등록.
    };
    
    const updateReply = (e) => {
        // 수정할 댓글 아이디.
        const id = modalInput.value;
        // 수정할 댓글 내용.
        const replyText = modalTextarea.value;
        // PUT 방식의 Ajax 요청을 보냄.
        const reqUrl = `/spring2/api/reply/${id}`;
        const data = { replyText }; // js의 객체 선언(Object): { key: value }, { replyText: replyText }. 1개의 맴버 변수를 갖고 있는 객체.
        // Ajax 요청에 대한 성공/실패 콜백 등록.
        axios.put(reqUrl, data)
            .then((response) => {
                alert(`댓글 업데이트 성공(${response.data})`);
                getRepliesWithPostId(); // 댓글 목록 업데이트.
            })
            .catch((error) => console.log(error))
            .finally(() => modal.hide());
    };
    
    // 모달에서 [수정 내용 저장] 버튼 이벤트 리스너 등록.
    modalBtnUpdate.addEventListener('click', updateReply);
    


    // 댓글 목록 HTML을 작성하고 replies 영역에 추가하는 함수.
    // argument `data`: Ajax 요청의 응답으로 전달받은 데이터.
    const makeReplyElements = (data) => { // data: response.data, response 객체에 존재하는 data: 배열.
        // 댓글 개수 업데이트
        replyCountSpan.innerHTML = data.length; // 배열 길이(원소 개수).
        
        replies.innerHTML = ''; // <div>의 컨텐트를 지움.
        
        let htmlStr = ''; // 문자열을 덧붙이기 위한 let, 비어있는 변수로 초기값 설정.
        // for (let i = 0; i < data.length; i++) {}
        // for (let x in data) {} -> 인덱스 iteration
        for (let reply of data) {
            console.log(reply);
            
            // Timestamp 타입 값을 날짜/시간 타입 문자열로 변환:
            const modified = new Date(reply.modifiedTime).toLocaleString(); // js의 Date 클래스.
            
            // 댓글 1개를 표시할 HTML 코드:
            htmlStr += `
            <div class="card">
                <div>
                    <span class="d-none">${reply.id}</span>
                    <span class="fw-bold">${reply.writer}</span>
                    <span class="text-secondary">${modified}</span>
                </div>
                <div>
                    ${reply.replyText}
                </div>
                <div>
                    <button class="btnDelete btn btn-outline-danger" data-id="${reply.id}">
                        삭제
                    </button>
                    <button class="btnModify btn btn-outline-success" data-id="${reply.id}">
                        수정
                    </button>
                </div>
            </div>
            `;
            
        }
        
        // 작성된 HTML 코드를 replies <div> 영역 안에 포함. -> 이때 버튼 생성됨.
        replies.innerHTML = htmlStr; // 새로 바꿔 끼움. 덧붙이는 게 아님.
        
        // 모든 삭제 버튼들을 찾아서 클릭 이벤트 리스너를 등록:
        const deleteButtons = document.querySelectorAll('button.btnDelete');
        for (let btn of deleteButtons) {
            btn.addEventListener('click', deleteReply);
        }
        
        // 모든 수정 버튼들을 찾아서 클릭 이벤트 리스너를 등록:
        const modifyButtons = document.querySelectorAll('button.btnModify');
        for (let btn of modifyButtons) {
            btn.addEventListener('click', showUpdateModal);
        }
        
    };
    
    const getRepliesWithPostId = async () => { // Ajax 요청을 보내는 async 함수.
        // 댓글 목록을 요청하기 위한 포스트 번호(아이디)
        const postId = document.querySelector('input#id').value;
        // 댓글 목록을 요청할 URL
        const reqUrl = `/spring2/api/reply/all/${postId}`; // `/contextRoot/api/,...`
        
        // Ajax 요청을 보내고 응답을 기다림.
        try {
            const response = await axios.get(reqUrl);
            console.log(response);
            // 댓글 개수 업데이트 & 댓글 목록 보여주기
            makeReplyElements(response.data);
        } catch (error) {
            console.log(error);
        }
    };
    
    // 부트스트랩 Collapse 객체를 생성 - 초기 상태는 화면에서 안보이는 상태.
    const bsCollapse = new bootstrap.Collapse('div#replyToggleDiv', {toggle: false});
    
    // 버튼 아이콘 이미지
    const toggleBtnIcon = document.querySelector('img#toggleBtnIcon');
    
    // 댓글 등록/목록 보이기/숨기기 토글 버튼에 이벤트 리스너를 등록.
    const btnToggleReply = document.querySelector('button#btnToggleReply');
    btnToggleReply.addEventListener('click', () => {
        bsCollapse.toggle();
		// const toggle = btnToggleReply.getAttribute('data-toggle'); // btnToggleReply가 가지고 있는 data-toggle 속성의 값을 리턴.
        
        if (toggleBtnIcon.alt === 'toggle-off') {
             toggleBtnIcon.src = '../static/assets/icons/toggle2-on.svg';
             toggleBtnIcon.alt = 'toggle-on';
             
			 // btnToggleReply.innerHTML = '숨기기';  // 시작 태그와 엔드 태그 사이의 값을 변경.
             // btnToggleReply.setAttribute('data-toggle', 'toggle-on');

             // 댓글 전체 목록을 서버에 요청하고, 응답이 오면 화면 갱신.
             getRepliesWithPostId();
        } else {
            toggleBtnIcon.src = '../static/assets/icons/toggle2-off.svg';
            toggleBtnIcon.alt = 'toggle-off';
            replies.innerHTML = '';

			// btnToggleReply.innerHTML = '보이기';
            // btnToggleReply.setAttribute('data-toggle', 'toggle-off');
        }
    });
    
    // 댓글 등록 버튼
    const btnAddReply = document.querySelector('button#btnAddReply');
    
    const createReply = (e) => {
        const postId = document.querySelector('input#id').value;
        const replyText = document.querySelector('textarea#replyText').value;
        const writer = document.querySelector('input#writer').value;
        
        if (replyText === '') {
            alert('댓글 내용을 입력하세요.');
            return;
        }
        
        const data = { postId, replyText, writer, }; // 2015년 이후 js에서 Object 생성 문법. 3개의 변수를 갖는 js Object <- ReplyCreateDto 자바클래스와 동일하게 선언.
        
		// jsp: html + js...(화가), 브라우저(도화지)에 보냄.. 
        // 브라우저가 db에게 요청을 보내는 것. 브라우저가 js를 읽어들이고,   getRepliesWithPostId();    spring <- js
        axios.post('/spring2/api/reply', data) // POST 방식의 Ajax 요청 보냄.
            .then((response) => {
                alert(`댓글 등록 성공(${response.data})`); // 변수에 저장된 데이터 읽기. 서버에서 보내준 숫자, ReplyController - return ResponseEntity.ok(1);에서의 data:1을 나타냄 > alert 창에 1이 출력.
                
                // 댓글 입력 창의 내용을 지움.
                document.querySelector('textarea#replyText').value = '';
                
                // 댓글 목록을 새로 고침.
                getRepliesWithPostId();
                
            }) // 성공 응답이 왔을 때 실행할 콜백 함수 등록.
            .catch((error) => {
                console.log(error);
            }); // 에러 응답이 왔을 때 실행할 콜백 함수 등록.
    };
    btnAddReply.addEventListener('click', createReply);
    
// js의 for문
// for(let i = 0; i < length; i++){}.
// for(let x in data){}: index를 iteration.
// for(let x of data){}: 원소들을 iteration.


/*
   과거 객체 생성 문법 {property : property valu}.
   postId: postId,
   replyText: replyText,
   writer: writer,  // 마지막 ,는 없어도 있어도 됨.
*/

// 모든 html의 태그: getAttribute()를 가짐.
// innerHTML: 시작, end 태그 사이의 문자열, input: value, textarea도 동일하게 value 속성을 사용하여 값을 읽고 지움.
});