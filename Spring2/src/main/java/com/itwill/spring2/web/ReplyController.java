package com.itwill.spring2.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.spring2.dto.ReplyCreateDto;
import com.itwill.spring2.dto.ReplyReadDto;
import com.itwill.spring2.dto.ReplyUpdateDto;
import com.itwill.spring2.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // 필드 값 초기화 > 생성자 의존성 주입.
@RestController
@RequestMapping("/api/reply") // Controller는 Mapping되는 주소 필요.
public class ReplyController {

	/*
	 * ResponseEntity: 클라이언트에게 전송될 HTTP 응답. 직접 클라이언틀로 response를 보냄. Reply를 저장하는 객체
	 * 리턴. -> 응답 본문, 상태 코드, 헤더 등을 포함하는 HTTP 응답을 캡슐화. 
	 * > 개발자는 세부적인 응답 설정을 조작하고, 다양한 상태 코드 및 헤더를 설정하여 클라이언트에게 전송.
	 */

	private final ReplyService replyService;

	// 댓글 등록 > 리턴 값: int.
	@PostMapping
	public ResponseEntity<Integer> createReply(@RequestBody ReplyCreateDto dto) { // 	private Timestamp modifiedTime;
		// Controller: DispatcherServlet이 호출함. @RequestBody 안에 ReplyCreateDto dto이 존재.
		// DPS가 RequestBody의 json 문자열을 보고 ReplyCreateDto의 setter 메서드를 호출해서 객체의 값을 넣어줌.
		// 문자열 분석 = 문자열 파싱. 우리가 추가한 라이브러리 > JSON 형식으로 만들어진 문자열을 분석.

		log.info("createReply(dot={})", dto);

		int result = replyService.create(dto);
		// ResponseEntity.status(500).build(); // httpStatusCode를 보내서 에러 처리.

		return ResponseEntity.ok(result); // result: 응답 보낼 때 전달할 Integer 값.
		// ok: 200번 성공 코드, 데이터 1을 리턴(클라이언트에게 보냄).
		// 데이터의 값을 보고 1인 경우에만 응답을 보내는 것으로 해야 함.
		// null: 실패가 아니라 데이터가 없다는 것임. 즉, 응답 자체는 성공한 것. 응답 객체의 응답코드: 200.
	}

	// 요청주소의 변수 표현: {}, Param과 동일하하게 선언 > DispatcherServlet이 해당 주소의 id를 Param에 넣어줌.
	@GetMapping("/all/{postId}")
	/**
	 * @param postId
	 * @return Reply들을 저장하고 있는 객체를 리턴.
	 */
	public ResponseEntity<List<ReplyReadDto>> read(@PathVariable long postId) {

		log.info("read(postId={})", postId);

		List<ReplyReadDto> list = replyService.read(postId);

		// 댓글 개수 확인
		log.info("# of replies = {}", list.size());

		return ResponseEntity.ok(list);
		// 잭슨 데이터 바인드 라이브러리 동작: Reply(자바 객체)를 가지고 옴 > 문자열로 만들어 클라이언트에 보내줌. 객체(추상적 개념)을 전달할 순 없음. 클라이언트 서버 간.
		// LocalDateTime을 제이슨 문자열 방법으로 바꾸는 방법을 모름. 자바 8버전부터 나옴. 라이브러리 적용이 안됨.
		// -> DTO 생성: JSP 태그라이브러리(JSTL)의 formatdate 태그: sql, Timestamp는 원하는 포맷대로 잘 바꿔주나,
		// LocalDateTime은 호환이 안 됨.
	}

	@DeleteMapping("/{id}") // 몇개의 행이 삭제 되었는지 int 보냄.
	public ResponseEntity<Integer> deleteReply(@PathVariable long id) {
		log.info("deleteReply(id={})", id);

		int result = replyService.delete(id);

		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReplyReadDto> readById(@PathVariable long id) {
		log.info("readById(id={})", id);

		ReplyReadDto dto = replyService.readById(id);
		log.info("dto={}", dto);

		return ResponseEntity.ok(dto); // 클라이언트에 보내줌.
	}

	@PutMapping("/{id}")
	// 필드 한개를 가진 객체{replyText} > 해당 필드 1개만을 가진 DTO 클래스가 있어야 함.
	// @RequestBody: JS에서 선언한 data의 {replyText : replyText} 처리, update를 할 내용.
	public ResponseEntity<Integer> updateReply(@PathVariable long id, @RequestBody ReplyUpdateDto dto) {
		log.info("updateReply(id={}, dto={})", id, dto);

		int result = replyService.update(id, dto); // DB에 업데이트하고 int(결과값)을 받고,

		return ResponseEntity.ok(result); // 메서드를 이용해 result 값을 넣어줌. 몇 개의 행 업데이트? > Integer.
	}

//    @AllArgsConstructor @Data
//    public class Test {
//        private int age;
//        private String name;
//    }
//    
//    @GetMapping
//    public Test hello() {
//        log.info("hello()");
//     return new Test(16, "오쌤");
//    }

	// Jackson 이란?
	// Java Object를 JSON으로 변환하거나 JSON을 Java Object로 변환하는데 사용하는 Java 라이브러리.
	// Data Binding: POJO 기반의 자바 객체들을 JSON으로 변환시킬 수 있음.
}