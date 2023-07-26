/**
 * array_functions.js
 */

//document 객체.addEventListener 메서드(첫번째 아규먼트 문자열, 함수)
document.addEventListener(`DOMContentLoaded`, function() {
    const numbers = [1, 2, 3, 4, 5, 6, 7];
    // 배열 concat() 메서드: 배열에 새로운 원소를 끝에 추가. 원소가 추가된 새로운 배열을 리턴.
    // 배열 push() 메서드: 기존 배열의 끝에 원소를 추가. 리턴값은 없음. 원본 배열자체가 변경


    const arr = []; // 빈 배열 선언.
    console.log(arr);

    arr.push(100);
    console.log(arr);

    arr.push(200);
    console.log(arr);

    //const odd2 = [];
    //odd2 = odd2.concat(1); //에러 const는 변할 수 없음
    let arr2 = [];
    console.log(arr2);

    arr2 = arr2.concat(1);
    console.log(arr2);

    arr2 = arr2.concat(2);
    console.log(arr2);

    //1. 배열 numbers의 원소들 중에서 홀수들만 원소로 갖는 배열을 만들고 출력.
    // > [1, 3, 4, 7]


    let odds = []; //홀수들을 저장할 배열 선언.
    for (let x of numbers) { //배열의 원소를 순서대로 반복.
        if (x % 2 != 0) { //2로 나눈 나머지가 있으면(홀수이면)
            odds = odds.concat(x); //홀수를 추가한 새로운 배열을 생성
        }
    }
    console.log(odds);


    //위 문장을 한 문장으로
    odds = numbers.filter((x) => x % 2);
    //filter함수는 자체적으로 반복문과 concat을 가지고 있음
    //true나 false를 리턴해주는 argument로 넘겨줌
    console.log(odds);

    //2. 배열 numbers의 원소를 제곱한 숫자들을 원소로 갖는 배열을 만들고 출력.
    // > [1, 4, 9, 16, 25, 36, 49]

    let squares = [];

    for (let x of numbers) {
        squares = squares.concat(x ** 2); // **: 거듭제곱 연산자.
    }
    console.log(squares);

    //위 문장을 한 문장으로
    squares = numbers.map((x) => x ** 2);
    //map은 배열의 원소를 아규먼트로 전달함
    //map은 리턴값 자체를 배열에 넘겨 줌
    console.log(squares);
    
    //3. 배열 numbers의 원소들 중에서 홀수의 제곱을 원소로 갖는 배열을 만들고 출력.
    // > [1, 9, 25, 49]

    let oddSquares = [];

    for (let x of numbers) {
        if (x % 2 != 0) {
            oddSquares = oddSquares.concat(x ** 2);
        }
    }
    console.log(oddSquares);
    
    oddSquares = numbers.filter((x) => x % 2).map((x) => x ** 2);
    console.log(oddSquares);
});