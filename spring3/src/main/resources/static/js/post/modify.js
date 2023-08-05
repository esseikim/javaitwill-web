/**
 *  modify.html에서 사용
 *  포스트 업데이트 & 삭제 
 */

/*콜백함수: listener, document가 모두 생성 된 후(html 문서가 만들어진 후)에 콜백함수를 등록하겠다.
update, delete 버튼 모두 form의 기능을 이용해야함 -> form을 먼저 찾음  */
document.addEventListener('DOMContentLoaded', () => {

    // <form>의 요소를 찾음 
    const postModifyForm = document.querySelector('#postModifyForm');

    const btnUpdate = document.querySelector('#btnUpdate');
    btnUpdate.addEventListener('click', (e) => {
        const title = document.querySelector('#title').value;
        const content = document.querySelector('#content').value;

        if (title === '' || content === '') {
            alert('제목과 내용은 반드시 입력해야 합니다');
            return;
        }

        const result = confirm('변경 내용을 업데이트할까요?');
        if (!result) {
            return;
            }
            
            postModifyForm.action = '/post/update';
            postModifyForm.method = 'post';
            postModifyForm.submit();
    });
    
    
    const btnDelete = document.querySelector('#btnDelete');
    btnDelete.addEventListener('click', (e) => {
        const result = confirm('정말로 삭제할까요?');
        if ( !result ){
            return;
        }
        
        postModifyForm.action = '/post/delete' // submit 요청 주소
        postModifyForm.method = 'post' // submit 요청 방식
        postModifyForm.submit(); // 폼 제출(submit), 요청 보내기 
    });

});