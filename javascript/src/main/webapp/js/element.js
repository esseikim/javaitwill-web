/**
 * element.js 
 */

//HTML 문서에 'DOMContentLoaded' 이벤트 리스너를 등록.
//브라우저가 HTML 문서의 모든 요소들을 생성(DOMContentLoaded)하고 난 후, 이벤트 리스너 함수를 실행.
document.addEventListener('DOMContentLoaded', function () {
    
 //id로 HTML 요소를 찾음:
 const btn1 = document.getElementById('btn1');
 //console.log(btn1);
 
 //찾은 HTML 요소에 'click' 이벤트 리스너를 등록:
 //addEventListener('eventName', callback(나중에 부르는 함수라는 의미로 callback라고도 불림));
 //callback: 이벤트가 발생했을 떄 브라우저가 호출하는 함수.
 btn1.addEventListener('click', function () {
     //1.id로 찾기
     //id="d1"인 요소를 찾아서 바탕색을 변경
     const d1 = document.getElementById('d1');
     d1.style.backgroundColor ='lavender';
 });
 
 //id='btn2' 버튼에 'click' 이벤트 리스너를 등록.
 
 const btn2 = document.getElementById('btn2');
 
 btn2.addEventListener('click', function () {
     //2.class로 찾기
     //class="c1"인 요소들의 바탕색을 lime으로 변경
     const divisions = document.getElementsByClassName('c1');
     //console.log(divisions);
     
     for (let element of divisions) {
         //console.log(element);
         element.style.backgroundColor = 'lime';
     }
 })
    //3.tag로 찾기
    //id="btn3"인 버튼에 'click' 이벤트 리스너를 등록:
    const btn3 = document.getElementById('btn3');
    btn3.addEventListener('click', function () {
     // tag 이름이 div인 모든 요소의 바탕색을 변경:
     const divisions = document.getElementsByTagName('div');
     
     for (let div of divisions) {
         div.style.backgroundColor = 'lightpink';
     }
 })
 
    //CSS selector(선택자) 문법을 argument로 사용하는 메서드:
    //tagName, .className, #id, tagName.className#id
    //parent descendent: 자손 요소 찾기
    //parent>child: 자식 요소 찾기
    //element:peudo_selector: (예) button:hover(마우스가 올라갔을 때), tr:nth-child(odd)
    
    //document.querySelector(selector): selector로 찾을 수 있는 "첫번째" 요소를 리턴.
    //document.querySelectorAll(selector): selector로 찾을 수 있는 "모든" 요소들의 collection을 리턴.
    
    //selector는 id가 될 수도, class가 될 수도, tag가 될 지 모르기 때문에 구분자(#)를 넣어줌
    const btn4 = document.querySelector('#btn4');
    btn4.addEventListener('click', function () {
        const division = document.querySelector('.c2');
        //console.log(division);
        division.style.backgroundColor='crimson';
    });
    
    const btn5 = document.querySelector('#btn5');
    btn5.addEventListener('click', function () {
        const divisions = document.querySelectorAll('.c2');
        //console.log(division);
        for (let d of divisions) {
            d.style.backgroundColor ='DodgerBlue';
        }
    })
})


//만약 'DOMContentLoaded' 이벤트 리스너를 등록하지 않고, 
//const btn1 = document.getElementById('btn1');
//console.log(btn1);
//위 문장을 body의 맨 위쪽에 올린다면 btn1이 위 문장보다 아래에 있으니 null 값이 출력(console.log(btn1);)되지만
//'DOMContentLoaded' 이벤트 리스너를 등록하고 두번째 아규먼트인 함수에
//const btn1 = document.getElementById('btn1');
//console.log(btn1);
//문장을 넣으면 원하는 값을 얻을 수 있게됨
//그럼에도 불구하고 script는 body의 맨 하단에 놓는 것이 성능면에서 가장 좋음