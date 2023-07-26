/**
 * object.js
 */

 document.addEventListener('DOMContentLoaded', function () {
     //JSON(JavaScript Object Notation): 자바스크립트 객체 표기법
     // { property: value, ...}
     
     //const person = {}; // 얘는 이미 객체임, 다만 아무런 속성을 가지고 있지 않은 객체.
     
     const person = {
         name: '오쌤',
         age: 16,
         phone: ['010-0000-0000', '02-0000-0000'],
     };
     console.log(person);
     
     //객체의 프로퍼티 접근(사용)
     console.log(person.name); //자바와 마찬가지로 필드에 접근할 때 '객체이름.필드이름' 형식으로 접근함
     console.log(person['age']); //person 객체의 age 프로퍼티 값을 읽음.
     console.log(person.phone);
     console.log(person.phone[0]);
     console.log(person['phone'][1]);
     
     //자바스크립트 객체는 프로퍼티를 추가할 수 있음.
     person.company = '아이티윌';
     console.log(person);
     //person.name = '홍길동'; //반면에 이건 person 객체의 name 프로퍼티 값을 변경한다는 뜻
     
     //객체(object)에서 for-in 구문: 객체의 프로퍼티 이름들을 iteration. (for-of는 불가)
     for (let key in person) {
         console.log(key, ':', person[key]); // person.key 는 불가능
     }
     
     //메서드를 갖는 객체:
     const score = {
         korean: 100,
         english: 90,
         math: 70,
         total: 70,
         sum: function () {
             return this.korean + this.english + this.math;
         },
         mean: function () {
             return this.sum() / 3;
         }
     }
    
     console.log(score);
     console.log(score.sum());
     console.log(score.mean());
     
     // 생성자 함수:
     function Score(korean, english, math) {
         //필드
         this.korean = korean;
         this.english = english;
         this.math = math;
         
         //메서드
         this.sum = function () {
             return this.korean + this.english + this.math;
         };
         this.mean = function () {
             return this.sum() / 3;
         };
     }
     
     // 생성자 함수 호출:
     const score1 = new Score(10, 20, 30);
     console.log(score1);
     console.log(score1.sum());
     console.log(score1.mean());
     
     const score2 = new Score(90, 95, 89);
     console.log(score2);g
     console.log(score2.sum());
     console.log(score2.mean());
     
     //JSON만 이용할 땐 필드의 일부 값이 바뀌면 객체를 새로 생성해줘야됐는데(이름이 없기 때문)
     //생성자 함수는 이름이 있기 때문에 재활용이 가능한 메서드가 된다.
     
     //※자바와 다르게 JS는 프로퍼티로 키워드를 사용해도 상관없음
     //따라서 person.for와 같은 문장을 구별하기 위해 person['for']와 같은 문법을 제공하는 것
 });