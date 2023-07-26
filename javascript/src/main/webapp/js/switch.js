/**
 * switch.js
 * 03_switch.html에 포함
 * switch-case 구문 
 */

/*함수가 함수를 갖는다. 콜백함수. 
이벤트의 종류, 이벤트를 처리하는 인터페이스객체(실행할 함수) = 익명객체  
DOM 문서객체: Document Object Model 
문서객체: h1, head, body, select, button p,..
가 로딩이 끝났을 때 
-DOMContentLoaded: HTML 문서의 모든(element)들이 만들어졌을 때 발생하는 이벤트  
 브라우저가 DOM을 만들어주고(이벤트발생), 이 이벤트를 처리할 수 있는 함수를 만들어줌. 
- 이벤트 처리 함수를 등록,
- 안쪽의 코드: 이벤트 발생했을 때 실행됨. 
- 별도의 html 파일을 만들 때 이 형식 만들기. 안전하게 코드 작성 가능 
*/

// 도큐먼트에 이벤트리스너를 등록하고 끝, 브라우저가 DOM들을 다 만들어내고 나면
// 브라우저가 이벤트를 발생시킴.  DOMContentLoaded 
// 그때서야 getElementById 해서 찾을 수 있고, 
// null이 아닌 값을 출력. 즉 위치 관계 없이 안전 실행
// 화면에 그려지는 속도가 느려짐. 한참 뒤에 뜸. 웹페이지 접속했는데, 
// 그래서 js는 제일 밑에 넣어라. 접속했는데 답답... 
document.addEventListener('DOMContentLoaded', function(){
 
 /*value를 읽기 위해선 셀렉트를 찾아야함.
  select#weekday element를 찾음.*/
 const weekday = document.getElementById('weekday');
 console.log(weekday);



// div#result element를 찾음. 값을 변경할 게 아니라면 웬만해선 전부 const 쓰기
const result = document.getElementById('result');


// button#btn element를 찾음.
const btn = document.getElementById('btn');
// btn에 click 이벤트 리스너를 등록. 
btn.addEventListener('click', printResult);

/*onclick: 클릭할 때 */

function printResult(){
    /*버튼이 눌렸을 때! select의 값을 먼저 읽어야 함. sun? wed?..값을 읽겠다(하)*/
    const value = weekday.value; 
    // select에서 선택된 값을 읽음, Value 자바에선 undefiened -> 비교 불가, default로 감. 
    switch(value){
        case 'mon':
        case 'tue':
        case 'wed':
        case 'thu':
        case 'fri':
               result.innerHTML = '학원 출석';/*안쪽의 html코드*/          
            break;
        case 'sat':
        case 'sun':
               result.innerHTML = '캠핑';
            break;
        default:    
               result.innerHTML = '몰라요~';  
               // case를 만나 break를 만날 때까지 실행한다. 덮어쓰고 디폴트까지 감. 무조건 몰라요
               //select문이라 다른 게 클릭될 일이 없지만 자바와 동일함 
          /*
          === 연산자로 비교한다. switch-case문. 
          타입을 자동변환하지 않고, 타입과 값이 일치하는 case의 문장을 실행. swich(타입) - case(타입) 
          select, div 찾고, 버튼 눌렸을 대 값 찾고 switch! 
          이벤트가 발생했을 때만 함수 호출. 
          보이지 않았음. 
          */
    }
}

});


