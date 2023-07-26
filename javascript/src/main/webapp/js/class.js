/**
 * 
 */

 //document에 EventListener를 등록하는 거임
 //화면에 보이는 모든 element들을 모두다 만들어냈을 때
 //화면에 보이는 모든 element들을 모두다 만들어내는 걸 DOMcontentLoaded임
 //아규먼트로 전달받은 함수를 실행하겠다.
 document.addEventListener('DOMContentLoaded', function () {
     
     // class 선언(정의)
     class Score {
         
         //생성자(자바와 다르게 클래스이름과 생성자 이름이 일치하지 않음)
         constructor (korean, english, math) {
             //클래스 프로퍼티들을 선언할 때는 var, const, let 키워드를 사용하지 않음.             
             //필드(JS에선 보통 프로퍼티라고 불림)의 선언과 초기화:
             this.korean = korean;
             this.english = english;
             this.math = math;
         }
         
         //클래스 생성자, 메서드 선언에서는 function 키워드를 사용하지 않음.
         //메서드 :
         sum() {
             return this.korean + this.english + this.math;
         }
         
         mean() {
             return this.sum() / 3;
         }
     }
     
     //클래스의 객체 생성:
     const score1 = new Score(100, 90, 95); //constructo호출 및 sum() 과 mean() 메서드 생성
     console.log(score1);
     console.log(score1.sum());
     console.log(score1.mean()); 
     //그전까지만해도 변수와 메서드들을 구분하지 않았는데 클래스라는 키워드가 등장하면서부터 구분하기 시작함(이는 자바와 비슷)
     
     const score2 = new Score(1, 2, 5);
     console.log(score2);
     console.log(score2.sum());
     console.log(score2.mean()); 
     
     //자바스크립트 클래스 작성 연습
     //property: width, height
     //method: area, perimeter
     class Rectangle {
         
         //생성자
         constructor(width, height) {
             //필드(property)
             this.width = width;
             this.height = height;
         }
         
         //메서드(method)
         //※Java와 다르게 생성된 객체의 property에 접근할 땐 반드시 this로 접근해야 함
         area() {
             return this.width * this.height;
         }
         
         perimeter() {
             return (this.width + this.height) * 2;
         }
     }
     
       const rec = new Rectangle(2, 3);
       console.log(rec);
       console.log(rec.area());
       console.log(rec.perimeter());
 }) 
 
