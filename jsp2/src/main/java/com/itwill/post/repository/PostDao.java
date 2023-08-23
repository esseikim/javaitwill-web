package com.itwill.post.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.post.datasource.HikariDataSourceUtil;
import com.itwill.post.model.Post;
import com.zaxxer.hikari.HikariDataSource;


// 여러개의 메서드(dao)를 이용해 서비스 만듦. dao: 싱글톤으로 작성. <- PostService.java 참고.
// Repository(Persistance) Layer: 저장소/영속성(영구적 저장) 계층. 
// DB CRUD(Cread, Read, Update, Delete) 작업을 수행하는 계층.  
public class PostDao {
    // Slf4j 로깅 기능 사용: 로거객체 만듦.
    // Logger 객체는 getLogger 메서드를 이용해 생성. build up 패턴
    private static final Logger log = LoggerFactory.getLogger(PostDao.class);
    private static PostDao instance = null;
    private HikariDataSource ds; // 데이터소스 이용하는 싱글톤 객체 -> sql 사용 가능.
    private PostDao() {
        ds = HikariDataSourceUtil.getInstance().getDataSource(); // dao는 ds(데이터 소스) 사용 가능.
    };
    

    public static PostDao getInstance() {
        if (instance == null) {
            instance = new PostDao();
        }
        return instance;
    }

    // POSTS 테이블에서 전체 레코드를 id 검색(내림차순으로 정렬).
    private static final String SQL_SELECT_ALL = "select * from POSTS order by ID desc";

    public List<Post> select() {
        List<Post> list = new ArrayList<>(); // 인터페이스 List: 객체 생성 불가 -> 구현 클래스: ArrayList와 LinkedList.

        log.info(SQL_SELECT_ALL);

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection(); // ConnectionPool에서 Connection 객체를 빌려옴(드라이버 직접 연결할 필요x). 
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();
            while (rs.next()) { // rs(행) 개수만큼 반복  
                // 테이블 컬럼 내용을 Post 타입 객체로 변환하고 리스트에 추가:
                Post post = recordToPost(rs); // 컬럼 내용(ResultSet)을 아규먼트로 넘겨줌. 
                list.add(post);
            }
            log.info("# of rows = {}", list.size()); // 행의 개수 출력

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close(); // 물리적인 접속 해제가 아닌 풀에 반환하는 것을 의미함. 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    // 모든 SELECT 문에서 사용하기 위해서. 전체 검색, PK 검색 모두 작업 동일.
    private Post recordToPost(ResultSet rs) throws SQLException { // 호출 해주는 쪽에서 Exception catch. -> throw만 하면 됨. 
        // POST 타입을 만들 수 있는 객체 6개 -> POST 객체 생성 가능.
        long id = rs.getLong("ID");
        String title = rs.getString("TITLE");
        String content = rs.getString("CONTENT");
        String author = rs.getString("AUTHOR");
        LocalDateTime created = rs.getTimestamp("CREATED_TIME").toLocalDateTime();
        LocalDateTime modified = rs.getTimestamp("MODIFIED_TIME").toLocalDateTime();

        Post post = new Post(id, title, content, author, created, modified);

        return post;
    }

    
    // 새 포스트 작성하기.
    private static final String SQL_INSERT = "insert into POSTS (TITLE, CONTENT, AUTHOR) values (?, ?, ?)"; 
    // SQL 문장 정의. `?`: 어떤 값이 올 지 알 수 없음. 아규먼트 post를 통해 get.                                                                                   
                                                                                                         
    public int insert(Post post) {
        log.info("insert({})", post);
        log.info(SQL_INSERT);

        int result = 0; // DB에서의 executeUpdate() - insert결과를 저장할 변수. 
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, post.getTitle()); // 쿼리 실행 전 `?` 채움.
            stmt.setString(2, post.getContent());
            stmt.setString(3, post.getAuthor());

            result = stmt.executeUpdate(); // cf) rs = stmt.executeQuery(); <- SELECT. 

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 포스트 아이디(PK)로 검색하기.
    private static final String SQL_SELECT_BY_iD = "select * from POSTS where ID = ?"; 
    // POSTS 테이블에서의 타입과 Post 클래스 타입 매칭. 
    // id: number 타입 > `?`에 setLong().  VARCHAR2 > String. TimeStamp > TimeStamp                                                                    
                                                                                   
    public Post select(long id) {
        log.info("select(id={})", id); // 메서드 호출 확인 로그. String.format() 메서드와 System.out.printf() 메서드와 비슷. 
        log.info(SQL_SELECT_BY_iD);

        Post posts = null;  // 리스트로 만들어줘야 하는 것이 아님. 혼동하지 않기, form에서 Post는 1개만 생성됨.  
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_iD);
            stmt.setLong(1, id); 

            rs = stmt.executeQuery(); // SELECT 쿼리 > executeQuery();
            if (rs.next()) { // 검색된 결과가 있으면
                posts = recordToPost(rs); // 레코드를 포스트로 변경.
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return posts;
    }

    // 포스트 아이디(PK)로 삭제하기:
    private static final String SQL_DELETE_BY_ID = "delete from POSTS where ID = ?";

    public int delete(long id) {
        log.info("delete(id={}", id);
        log.info(SQL_DELETE_BY_ID);

        int result = 0; // SQL 실행 결과를 저장할 변수.  ResultSet 불필요.
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ds.getConnection(); // 1. 커넥션 생성, Connection pool에서 Connection 하나를 빌려옴.
            stmt = conn.prepareStatement(SQL_DELETE_BY_ID); // 2. stmt를 conn을 이용해 만듦.
            stmt.setLong(1, id); // 3. `?` 채움. 
            result = stmt.executeUpdate(); // 4. 실행

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    // 해당 아이디의 포스트의 제목과 내용, 수정 시간을 업데이트:
    // set: 변경할 내용. 
    private static final String SQL_UPDATE = "update POSTS set TITLE =?, CONTENT = ?, MODIFIED_TIME =sysdate where ID =?"; 
	public int update(Post post) {
		log.info("delete({})", post);
		log.info(SQL_UPDATE);
		int result = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_UPDATE);
			stmt.setString(1, post.getTitle());
			stmt.setString(2, post.getContent());
			stmt.setLong(3, post.getId());
			result = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}