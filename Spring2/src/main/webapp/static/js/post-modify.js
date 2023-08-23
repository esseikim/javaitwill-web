/**
 * post-modify.js
 * /post/modify.jsp 에서 사용.
 */

/*  1. 이벤트 핸들러 리스너 함수. 
    2. element를 찾아 동작 등록. button 같은 element가 먼저 만들어져야 함.
    DOMContentLoaded: documentobject들이 전부 만들어진 다음에 실행할 코드.
    function(): 콜백함수(익명함수) 
 */
document.addEventListener('DOMContentLoaded', function() {

     // form 요소를 찾음.
    const modifyForm = document.querySelector("#modifyForm");
    
     // 삭제 버튼을 찾아서 클릭 이벤트 핸들러(리스너)를 등록. 
    const btnDelete = document.querySelector('#btnDelete');
    btnDelete.addEventListener('click', () => {
        const check = confirm('정말 삭제할까요?');
        
        // 사용자가 confirm 창에서 '확인'을 클릭했을 때.
        // JSP에서 버튼이 form 외부에 존재를 해도 다음 if문을 설정할 수 있음. 
        // -> form 내부의 값들을 처리, 버튼이 외부에 있어도 eventListener가 버튼의 작동을 정의하면 됨.
        if (check) {
            modifyForm.action = './delete'; // 'delete'와 동일 // 폼 요청 주소.
            // 현재 요청주소: `/modify` -> 삭제 버튼 클릭시 `/post`까지 유지 > 주소 delete 변경.
            modifyForm.method = 'post'; // 폼 요청 방식.
            modifyForm.submit(); // 폼 제출 -> 요청을 서버로 보냄.
        }
    });


    // 업데이트 버튼을 찾아서 이벤트 리스너를 등록.
    const btnUpadate = document.querySelector('#btnUpdate');
    btnUpdate.addEventListener('click', () => {
        // 제목과 내용이 입력되어 있는 지 확인. 
        const title = document.querySelector('input#title').value; // input에 입력된 값.
        const content = document.querySelector('textarea#content').value; // textarea에 입력된 값

        if (title === '' || content === '') { // 타입까지 검사. title이 문자열이 맞느냐? 맞다면 비교. 
            // 제목 또는 내용이 비어 있는 지 체크. 
            alert('제목과 내용은 반드시 입력해야 합니다.');
            return; // 함수 종료. 아래쪽 코드 실행 x 
        }

        const check = confirm( '변경 내용을 저장할까요?');
        if(check){
        modifyForm.action = './update' // 폼 요청 주소
        modifyForm.method = 'post'; // 폼 요청 방식
        modifyForm.submit();
        }

    });
});