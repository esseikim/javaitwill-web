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

// Repository(Persistance) Layer(저장소/영속성(-영구적 저장) 계층) 
// DB CRUD(Cread, Read, Update, Delete) 작업을 수행하는 계층.  
public class PostDao {
    // Slf4j 로깅 기능 사용: 로거객체 만듦.
    private static final Logger log = LoggerFactory.getLogger(PostDao.class);
    // Logger 객체는 getLogger 메서드를 이용해 생성. build up 패턴

    private static PostDao instance = null;

    private HikariDataSource ds; // 데이터소스이용하는 싱글톤 객체. 이걸 이용해서 sql 이용함.

    private PostDao() {
        ds = HikariDataSourceUtil.getInstance().getDataSource(); // ds 이용가능.
    };

    public static PostDao getInstance() {
        if (instance == null) {
            instance = new PostDao();
        }
        return instance;
    }

    // POSTS 테이블에서 전체 레코드를 id 내림차순으로 정렬(최근 작성 포스트 먼저)해서 검색.
    private static final String SQL_SELECT_ALL = "select * from POSTS order by ID desc";

    public List<Post> select() {
        List<Post> list = new ArrayList<>(); // 인터페이스 list <- 객체 생성 불가. 구현 클래스: array/linkedlist

        log.info(SQL_SELECT_ALL);

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection(); // 풀에서 Connection 객체를 빌려옴. 이제는 드라이버 직접 연결 x
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();
            while (rs.next()) {
                // rs가 몇개 있는 지 모름. 테이블 컬럼 내용을 Post 타입 객체로 변환하고 리스트에 추가:
                // 전체 검색이든, primary key로 하든 똑같은 일을 할 것.
                Post post = recordToPost(rs); // 테이블 행을 post 타입으로 변환. 컬럼 내용(rs)을 알아야함. 아규먼트로 넘겨줌
                list.add(post);
            }
            log.info("# of rows = {}", list.size()); // 행의 개수 출력

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close(); // 물리적인 접속 해제가 아니라, 풀에 반환!
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    // 모든 select 문장에서 사용하기 위함.
    private Post recordToPost(ResultSet rs) throws SQLException { // 호출해주는 쪽에서 catch 할 것 -> 던지기만 하면 됨
        // POST 타입을 만들 수 있는 객체 6개 -> POST 만들 수 있음.
        long id = rs.getLong("ID");
        String title = rs.getString("TITLE");
        String content = rs.getString("CONTENT");
        String author = rs.getString("AUTHOR");
        LocalDateTime created = rs.getTimestamp("CREATED_TIME").toLocalDateTime();
        LocalDateTime modified = rs.getTimestamp("MODIFIED_TIME").toLocalDateTime();

        Post post = new Post(id, title, content, author, created, modified);

        return post;
        // 잘 동작하는 지 확인하기 위해서 단위테스트 -> 웹적용
        /*
         * package com.itwill.post.repository;
         * 
         * public class PostDaoTest {
         * 
         * }
         */
    }

    // 아래 메서드에서 사용할 sql 문장 정의
    // 새 포스트 작성
    private static final String SQL_INSERT = "insert into POSTS (TITLE, CONTENT, AUTHOR) values (?, ?, ?)"; // 어떤 값이 올지
                                                                                                            // 알 수 없음. 이
                                                                                                            // 값은 아래 메서드
                                                                                                            // 아규먼트
                                                                                                            // post에 있음

    public int insert(Post post) {
        log.info("insert({})", post);
        log.info(SQL_INSERT);

        int result = 0; // DB에서의 executeUpdate() - insert결과를 저장할 변수
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, post.getTitle()); // 문장 실행 전 ? 채움
            stmt.setString(2, post.getContent());
            stmt.setString(3, post.getAuthor());

            result = stmt.executeUpdate();
        } catch (Exception e) {
            // TODO Auto-generated catch block
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

    // 포스트 번호로 검색.
    private static final String SQL_SELECT_BY_iD = "select * from POSTS where ID = ?"; // 테이블에서 id: number 타입. ?에 숫자
                                                                                       // 넣어줘야 함. 바챠2 -> 문자열. 타임스탬프 ->
                                                                                       // 타임스탬프

    // 위를 사용할 메서드
    public Post select(long id) {
        log.info("select(id={})", id); // 메서드 호출 확인. stringformat, printf -> select(7)
        log.info(SQL_SELECT_BY_iD);

        Post posts = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ds.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_iD);
            stmt.setLong(1, id); // 물음표 먼저 채우기. 첫번째 물음표 1

            rs = stmt.executeQuery(); // select문장 execute.
            if (rs.next()) { // 검색된 결과가 있으면
                posts = recordToPost(rs); // 레코드를 포스트로 바꾸겠다.
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

        int result = 0; // SQL 실행 결과를 저장할 변수. 여기에 저장할 거라 result set 불필요
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ds.getConnection(); // 1. 커넥션 만든다. connection pool에서 connection 하나를 빌려오게 됨.
            stmt = conn.prepareStatement(SQL_DELETE_BY_ID); // 2. stmt는 conn을 이용해 만듦
            stmt.setLong(1, id); // 3. ? 채운다
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
    private static final String SQL_UPDATE = "update POSTS set TITLE =?, CONTENT = ?, MODIFIED_TIME =sysdate where ID =?"; // set:
                                                                                                                           // 변경할
                                                                                                                           // 내용

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

    private static final String SELECT_TITLE = "select * from POSTS where title like ?";
    private static final String SELECT_CONTENT = "select * from POSTS where content like ?";
    private static final String SELECT_TWO = "select * from POSTS where title like? or where content like ?";
    private static final String SELECT_AUTHOR = "select * from POSTS where author like ?";

 public Post serchValues(String category, String keyword) {
        log.info("serch({})");
        
        Post post = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ds.getConnection();
            
            if (category.equals("t")) {
                stmt = conn.prepareStatement(SELECT_TITLE);
                stmt.setString(1, "%" + keyword + "%");
                rs = stmt.executeQuery();
                
            } else if (category.equals("c")) {
                stmt = conn.prepareStatement(SELECT_CONTENT);
                stmt.setString(1, "%" + keyword + "%");
                rs = stmt.executeQuery();

            } else if (category.equals("tc")) {
                stmt = conn.prepareStatement(SELECT_TWO);
                stmt.setString(1, "%" + keyword + "%");
                stmt.setString(2, "%" + keyword + "%");
                rs = stmt.executeQuery();

            } else if (category.equals("a")) {
                stmt = conn.prepareStatement(SELECT_AUTHOR);
                stmt.setString(1, "%" + keyword + "%");
                rs = stmt.executeQuery();

            }
        
            while (rs.next()) {
                post = recordToPost(rs);
            }
            
    } catch (Exception e) {
        e.printStackTrace();
    }finally {
        try {
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    return post;



}
}