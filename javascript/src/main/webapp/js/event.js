/**
 * event.js
 */

 //documnet.addEventListener('DOMContentLoaded', () => {})도 가능 (똑같은 문장은 아님, 중요한 차이가 있긴 있음)
 document.addEventListener('DOMContentLoaded', function () {
     //const itemInput = document.getElementById('itemInput');
     //const btnInput = document.getElementById('btnInput');
     //const itemList = document.getElementById('itemList');
     
     const itemInput = document.querySelector('input#itemInput');
     const btnInput = document.querySelector('button#btnInput');
     const itemList = document.querySelector('ul#itemList');
     const itemInput2 = document.querySelector('input#itemInput2');
     const itemList2 = document.querySelector('ul#itemList2');
     const userName = document.querySelector('input#userName');
     const age = document.querySelector('input#age');
     const result = document.querySelector('div#result')
     
     btnInput.addEventListener('click', function () {
         //1. 인풋에 입력된 내용을 읽는다.
         const value = itemInput.value;
         //console.log(value);
         
         //input에 입력된 값으로 li요소를 만듦.
         const item = `<li>${value}</li>`;
         
         //li 요소를 ui에 추가
         itemList.innerHTML += item;
         
         //input의 값을 지움.
         itemInput.value = '';
         
         //input에 포커스를 줌.
         itemInput.focus();
     })
     
     
     itemInput2.addEventListener('keydown', function (e) {
         //지금까지 아규먼트를 선언하지 않았던 이유는 아규먼트를 저장해서 쓸 일이 없었기 때문임
         //아규먼트를 선언하지 않았지만 아규먼트는 항상 들어오고 있었음
         //아규먼트를 선언하지 않았음에도 불구하고 아규먼트가 들어온 이유는
         //자바와 다르게 JS는 아규먼트의 개수를 지키지 않아도 되기 때문
         //즉, 아규먼트를 선언하지 않아도 아규먼트가 들어올 수 있고, 1개만 선언해도 2개가 들어올 수 있다는 거임
         
         //console.log(e); // -> e: KeyboardEvent 객체
         //모든 이벤트 핸들러 함수(콜백)은 이벤트 객체를 argument로 전달받음.
         if (e.key === 'Enter') { //엔터 키가 눌렸을 때
             const item =`<li>${itemInput2.value}</li>`;
             itemList2.innerHTML += item;
             itemInput2.value = '';
             itemInput2.focus();
         }
     });
     
     userName.addEventListener('change', function (e) { 
         updateNameAndAge();
     });
     
     age.addEventListener('change', function (e) {
        updateNameAndAge();
     });
     
     function updateNameAndAge() {
         const name = userName.value;
         const age2 = age.value;
         const text = `<b>이름:</b> ${name}, <b>나이:</b> ${age2}`; //<b></b> 볼드체
         result.innerHTML = text;
        } 
        
     //※change 이벤트는 포커스를 바뀌어도, 엔터를 입력해도 발생함
 });