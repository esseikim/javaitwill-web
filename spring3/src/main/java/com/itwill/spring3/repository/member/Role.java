package com.itwill.spring3.repository.member;

public enum Role {
    USER("ROLE_USER", "USER"),
    ADMIN("ROLE_ADMIN", "ADMIN");
	  //-> 해당 객체가 생성자를 통해 생성이 되며, 각 이름(user, admin)은 변수가 됨.
    //-> 또한, 상수를 정의하기에 순서에 따라 0~ 번호가 메겨짐.
    //-> user: 0, admin: 1
	
    private final String key; // "ROLE_USER", "ROLE_ADMIN" <- 값 
    private final String name; // "USER", "ADMIN" <- 변수, 상수
    
    Role(String key, String name) {
        this.key = key;
        this.name = name;
    }
    
    public String getKey() {
        return this.key; // "ROLE_USER", "ROLE_ADMIN"
    }

}
/*
Enum은 자바에서 상수 값을 정의하는 특별한 클래스입니다. 
Enum은 열거형(Enumeration)의 줄임말로, 
여러 개의 상수를 선언하고 이를 변수처럼 사용할 수 있게 해줍니다.

위의 코드는 Role이라는 Enum 클래스를 정의하고 있습니다. 
이 Enum은 두 개의 상수 `USER`와 `ADMIN`을 가지고 있습니다. 
각각의 상수는 `ROLE_USER`와 `ROLE_ADMIN`이라는 문자열(값)을 `key`로 가지고 있으며, 
`USER`와 `ADMIN`이라는 문자열을 `name`(상수)으로 가지고 있습니다.
`ROLE_USER`와 `ROLE_ADMIN`은 실제로 권한을 나타내는 값으로 사용될 수 있고, 
`USER`와 `ADMIN`은 권한의 이름을 의미합니다.

Enum은 상수 값들을 정의할 때 유용하며, 특히 switch 문과 함께 사용하면 코드의 가독성을 높일 수 있습니다. 
또한, Enum은 상수값을 변경할 수 없기 때문에 안정성이 높고 오류를 방지할 수 있습니다. 
따라서 프로그램에서 일정한 상수 값을 사용해야 할 때 Enum을 활용하는 것이 좋습니다.
 */
