package com.itwill.spring3.dto;

import lombok.Data;

@Data
public class PostSearchDto {

    // request parameter의 name과 동일하게 작성
    private String type;  // t, c, a,..
    private String keyword; 
}
