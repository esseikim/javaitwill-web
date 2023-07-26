package com.itwill.spring3.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// entity 클래스 이름, id 컬럼의 타입
public interface PostRepository extends JpaRepository<Post, Long> {
    
    // id 내림차순 정렬:
    // select * from POSTS order by ID desc
    // 메서드 이름에 규칙을 두면 JPA 라이브러리가 자동 생성해줌. 
    List<Post> findByOrderByIdDesc();
    
    
    // 제목으로 검색: JPA - 별명으로 컬럼을 찾음. 
    // ? : prepared statement. 아규먼트 String title는  findByTitle과 일치함
    // Contains = like 포함하고 있음. 키워드와 정확히 일치하는 것만 찾는 것이 아니라. 
    // JPA에서 사용하는 키워드를 이용해 메서드를 만들면 그 메서드를 자동으로 만들어줌
    
    // select * from posts p 
    // where lower(p.title) like lower('%' || ? || '%') <- 대소문자 구분없이 검색
    // order by p.id desc
    List<Post> findByTitleContainsIgnoreCaseOrderByIdDesc(String title);  
    
    
    // 내용으로 검색: 
    // select * from posts p 
    // where lower(p.content) like lower('%' || ? || '%') 
    // order by p.id desc
    List<Post> findByContentContainsIgnoreCaseOrderByIdDesc(String content);
    
    // 작성자로 검색: 
    // select * from posts p 
    // where lower(p.author) like lower('%' || ? || '%') 
    // order by p.id desc
    List<Post> findByAuthorContainsIgnoreCaseOrderByIdDesc(String author);
    
    // 제목 또는 내용으로 검색:
    // select * from posts p
    // where lower(p.title) like lower('%' || ? || '%')
    //         or lower(p.content) like lower('% ' || ? || '%')
    // order by p.id desc
    List<Post> findByTitleContainsIgnoreCaseOrContentContainsIgnoreCaseOrderByIdDesc(String title, String content);
    
    
    // JPQL(JPA Query Language) 문법으로 쿼리를 작성하고, 그 쿼리를 실행하는 메서드 이름을 설정
    // JPQL은 Entity 클래스(Post)의 이름과 필드 이름들을 사용해서 작성.
    // (주의) DB 테이블 이름과 컬럼 이름을 사용하지 않음. 
    // p: POST 테이블에 있는 모든 컬럼을 검색하겠다( * x. 엔터티 클래스의 별명 적용).  p.id / p.title
    // p. : Post의 p
    // argumnet 1개로 keyword 변수(parameter)가 채워짐 
    @Query(
                "select p from Post p" + 
                " where lower(p.title) like lower('%' || :keyword || '%') " +
                " or lower(p.content) like lower('%' || :keyword || '%') "  +
                " order by p.id desc"
                        
            )
    List<Post> searchByKeyword(@Param("keyword") String keyword);  // String keyword <- 마음대로
    
    
}

