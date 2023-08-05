package com.itwill.spring3.web;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.spring3.dto.PostCreateDto;
import com.itwill.spring3.dto.PostSearchDto;
import com.itwill.spring3.dto.PostUpdateDto;
import com.itwill.spring3.repository.post.Post;
import com.itwill.spring3.service.PostService;
import com.itwill.spring3.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller   // property 적용 불가 
@RequestMapping("/post")
public class PostController {
    
    private final PostService postService;
    private final ReplyService replyService;
    
    
    @GetMapping
    public String read (Model model) {  
    	// 요청주소로 요청이 들어왔을 때 dps이 model 객체를 넘겨줌.
        log.info("read()");
        
        //  포스트 목록 검색 
        List<Post> list = postService.read();
        
        // Model 검색 결과를 세팅.
        model.addAttribute("posts", list);
        
        return "/post/read";
    }
    
    // Pre: Controller로 가기 전(페이지 접근 이전)에 인증(권한, 로그인) 여부를 확인, 
    // Post: 메서드 끝난 이후 인증하는 경우
    // 문자열로 메서드 호출. Spring EL: 메서드 호출 시 ' ' 사용. (이미 " " 사용)
    // .hasRole("USER") [SecurityConfig.java] == "hasRole('USER')"
    // 요청이 들어왔을 때, 메서드 실행 전에 로그인 필터(hasRole('USER'))를 통해 거름
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/create")
    public void create() {
        log.info("create() Get"); 
        // 페이지만 보여주면 됨. 리턴 값 없을 시 요청주소와 동일하게 post 폴더 밑의 create.html을 찾아감
        
    } // 리턴 값이 없는 경우 view의 이름은 요청주소와 같음 
    
    
    @PreAuthorize("hasRole('USER')") 
    @PostMapping("/create")
    public String create(PostCreateDto dto) { 
    	// dps는 기본생성자를 호출함. 
    	// 스프링에서 서블릿은 잭슨데이터바인드라이브러리를 이용하여 클라이언트의 json 형태의 문자열(데이터)을 java 객체로 바꿔줌. 
    	// 기본생성자 호출하고 필드의 값을 넣도록 함 -> 기본생성자 게터 필수.  
        log.info("create(dto={}) POST", dto);  // 기본생성자로 객체 생성 후 getter 메서드로 필드 값 채움. 
        
        // form에서 submit(제출)된 내용을 DB 테이블에 insert
        postService.create(dto);
        
        // DB 테이블 insert 후 포스트 목록 페이지로 redirect 이동. (Post Redirect Get)
        return "redirect:/post";
    }
    
    
    // path variable: 매핑 주소에서 "details/{}"
    // 쿼리 스트링은 argument로, 쿼리 스트링 == 파라미터 이름
    // 문자열들의 배열의 요청 주소를 매핑 가능함.
    // -> 뷰는 매핑주소가 details로 들어올 경우 다음 매핑을 찾음. 그 반대도 마찬가지.
    // -> 리턴 값이 만약 String이면 각각 return을 지정해야 함.
    
    // "/post/details", "/post/modify" 요청 주소들을 처리하는 컨트롤러 메서드.
    @PreAuthorize("hasRole('USER')")
    @GetMapping({"/details", "/modify"})  // 쿼리스트링의 경우 아규먼트로 전달 받아야 함
    public void read(Long id, Model model) {
        log.info("read(id={})", id);
        
        // POSTS 테이블에서 id에 해당하는 포스트를 검색. 
        Post post = postService.read(id);
        
        // 결과를 model에 저장 -> 뷰로 전달됨. 
        model.addAttribute("post", post);
        
        // REPLIES 테이블에서 해당 포스트에 달린 댓글 개수를 검색.
        // List<Reply> replyList = replyService.read(post);
        // model.addAttribute("replyCount", replyList.size());

        long count = replyService.countByPost(post);
        model.addAttribute("replyCount", count);
        
        // 컨트롤러 메서드의 리턴값이 없는 경우(void인 경우),
        // 뷰의 이름은 요청 주소와 같다!
        // details -> details.html, modify -> modify.html
    }
    
    @PreAuthorize(" hasRole('USER') ") 
    @PostMapping("/delete")
    public String delete(Long id) {
        log.info("delete(id={})", id);
        
        // postService를 이용해서 DB 테이블에서 포스트를 삭제하는 서비스 호출: 
        postService.delete(id);
        
        return "redirect:/post";
    }
    
    
    // form의 name 속성과 DTO 필드 값이 같을 경우 찾을 수 있음.
    @PreAuthorize("hasRole('USER')") 
    @PostMapping("/update")
     public String upadate(PostUpdateDto dto) {  // id, title, content 모두를 전달받아야 함. 
        log.info("update(dto={})", dto);
        
        // 포스트 업데이트 서비스 호출: 
        /*
         * Post entity = postRepository.findById(dto.getId()).orElseThrow();
         * entity.update(dto);
         */
        postService.update(dto);
        
        return "redirect:/post/details?id=" + dto.getId();
    }
    
    @GetMapping("/search")
    public String search(PostSearchDto dto, Model model) {
        log.info("search(dto={})", dto);
        
        // postService의 검색 기능 호출: 
        List<Post> list = postService.search(dto);
        
        // Controller에서 검색한 결과(list)를 뷰로 전달
        model.addAttribute("posts", list); 
        // 같은 html을 사용하려면 thymeleaf에서 사용하는 변수명으로 동일하게 맞춰야 함. read()와 동일시
         
        return "/post/read";    // 검색용 html 생성 x
    }
    
    
}
