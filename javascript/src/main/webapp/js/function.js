/**
 * funcion.js
 * 07_function.html에 포함
 */

/**
 * JavaScript에서 함수를 선언(정의)하는 방법:
 * Function 함수이름([파라미터, ...]){
 *      실행 코드;
 *      [return [리턴값]];
 * }
 * 
 * - 함수의 리턴 타입을 선언하지 않음.
 * - 파라미터를 선언할 때 var, const, let 키워드를 사용하지 않음.
 * 
 * */

// 함수 선언:
 function add(x, y) {
     return x + y;
 }
 
 // 함수 호출:
 let result = add(1, 2);
 console.log('result =', result);
 
 // argument: 함수를 호출할 때 함수에 전달하는 값.
 // parameter: 전달받은 argument를 저장하기 위한 지역 변수. 함수 선언부에서 선언.
 
 //자바스크립트 함수는 파라미터의 타입을 검사하지 않음.
 result = add('안녕하세요', 'Hello');
 console.log(result);
 
 //자바스크립트 함수는 파라미터 개수를 검사하지 않음.
 result = add(10, 20, 30); // 함수 선언의 parameter보다 더 많은 argument를 전달.
 console.log('result =', result); //result = 30;
 
 result = add(1); // 함수 선언의 parameter 개수보다 적은 argument를 전달.
 console.log('result =', result); //result = NaN;
 // x 는 1, y 는 undefined(전달하지 않은 파라미터의 값)임 따라서 1 + undefined라 NaN(Not a Number)가 출력되는 것
 // undefined: 변수는 선언되었으나 변수에 값이 할당되지 않음.  
 
 // 자바스크립트의 모든 함수는 arguments 이름의 프로퍼티를 가지고 있음.
 // 함수 호출에서 전달한 모든 값들을 저장하는 (배열과 비슷한) 객체.
 function test () {
     console.log(arguments);
     for (let x of arguments) {
         console.log(x);
     }
 }
 
 test(1);
 test(1, 'hello');
 
 /*
 JavaScript 함수의 특징: 함수는 객체(object)!
 1. 함수는 프로퍼티(property - 자바의 필드)를 가질 수 있음. (예) arguments
 2. 함수는 변수에 저장할 수 있음.
 3. 함수의 argument로 다른 함수를 전달할 수 있음.
 4. 함수 내부에서 다른 함수를 선언(정의)할 수 있음.
 5. 함수는 다른 함수를 리턴할 수 있음.
 */

 const plus = add; //2.함수는 변수에 저장할 수 있음
 result = plus(100, 200);
 console.log('result=', result);
 
 //보통 위의 const plus = add;처럼 사용하진 않고
 //익명 함수(이름이 없는 함수) 형태로 사용함(근데 이것마저도 자주 쓰이는 건 아님, 그냥 함수 정의하는 거랑 별 차이 없기 때문에)
 const minus = function (x, y) {
     return x - y;
 }
 
 console.log('minus =', minus(1, 2));
 
 //익명 함수를 선언과 동시에 호출:
 result = (function (x, y) {
     return x / y;
 })(1, 2);
 
 console.log(result); // 출력 0.5;
 
 //함수를 argument로 갖는 함수
 function calculate(x, y, operator) {
     return operator(x, y);
 }
 
result = calculate(1, 2, add);
console.log('result=', result);

result = calculate(1, 2, function (x, y) {
    return x - y;
})

console.log('result=' , result);



function increase(n) {
    
    //4.함수 안에서 함수를 정의 : 내부 함수
    function addN(x) {
        return x + n;
    }
    
    //5.함수를 리턴
    return addN;
    
    //위 4. 5.를 한 줄로
    //return function (x) { return x + n; };
}

//increaseTen 은 x + 10을 return하는 함수가 됨
const increaseTen = increase(10);
console.log(increaseTen); //출력 : ƒ addN(x) {return x + n;}
console.log(increaseTen(1)); // 출력 : 11

const increaseOne = increase(1);
console.log(increaseOne(1)); // 출력 : 2

// 화살표 함수(arrow function) : 자바의 람다표현식과 비슷
// (파라미터, ...) => { 실행코드; ... } 
// (파라미터, ...) => 리턴값

const fnAdd = (x, y) => { return x + y; };
console.log(fnAdd(3, 4)); // 출력 : 7

const fnSubtract = (x, y) => x - y;
console.log(fnSubtract(3, 4)); // 출력 : -1

// 화살표 함수: 순서 중요. function: 순서 중요하지 않음
// js에서의 함수: 객체 -> this를 가짐. 
// click과 eventlistener에서 달라짐. 
// function(){}: 익명함수 

test1();
function test1(){
    console.log('test1');
}
test();

const test2 = () => console.log('test');  // 익명함수를 변수에 저장. ; : 실행문
test2();
// test2 변수가 만들어져야 함수 호출 가능. 


// js에서 함수를 선언하면 다른 실행문보다 위로 올라가게 됨. 
