package hkmu.comps380f.dao;

import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.VoteHistory;
import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    private final JdbcOperations jdbcOp;
        
        private static final class IdExtractor implements ResultSetExtractor<List<Comment>> {

        @Override
        public List<Comment> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Map<String, Comment> map = new HashMap<>();
            while (rs.next()) {
                int count = rs.getInt("id");
                Comment comment = map.get(String.valueOf(count));
                if (comment == null) {
                    comment = new Comment();
                    comment.setId(count);

                    map.put(String.valueOf(count), comment);
                }

            }
            return new ArrayList<>(map.values());
        }
    }    
     private static final class CommentExtractor implements ResultSetExtractor<List<Comment>> {

        @Override
        public List<Comment> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Map<String, Comment> map = new HashMap<>();
            while (rs.next()) {
                String username = rs.getString("username");
                int id = rs.getInt("id");
                String key = username + String.valueOf(id);
                
                Comment comment = map.get(key);
                if (comment == null) {
                    comment = new Comment();
                    comment.setId(id);
                    comment.setUsername(rs.getString("username"));
                    comment.setPlace(rs.getString("place"));
                    comment.setComment(rs.getString("comment"));
                    

                    map.put(key, comment);
                }

            }
            return new ArrayList<>(map.values());
        }
    }   
     
     
     @Autowired
    public CommentRepositoryImpl(DataSource dataSource) {
        jdbcOp = new JdbcTemplate(dataSource);
    }

        @Override
    @Transactional
    public void saveHistory(String place, Principal user, int id,String comment) {
        final String SQL_INSERT_COMMENT
                = "INSERT INTO comment VALUES ( ?, ?, ?, ?)";
        jdbcOp.update(SQL_INSERT_COMMENT, place, user.getName(), id, comment);

    }
    
        @Override
    @Transactional(readOnly = true)
    public List<Comment> findId(String place, Principal user) {
        final String SQL_SELECT_COMMENT
                = "select id from comment where place = ? and username = ? order by id DESC FETCH FIRST 1 ROWS ONLY";
        return jdbcOp.query(SQL_SELECT_COMMENT, new IdExtractor(),place, user.getName());
    }

        @Override
    @Transactional(readOnly = true)
    public List<Comment> findByPlace(String place) {
        final String SQL_SELECT_COMMENT
                = "select * from comment"
                + " where place = ?";
        return jdbcOp.query(SQL_SELECT_COMMENT, new CommentExtractor(), place);
    }
    
            @Override
    @Transactional(readOnly = true)
    public List<Comment> findByUser(Principal user) {
        final String SQL_SELECT_COMMENT
                = "select * from comment"
                + " where username = ?";
        return jdbcOp.query(SQL_SELECT_COMMENT, new CommentExtractor(), user.getName());
    }
}
