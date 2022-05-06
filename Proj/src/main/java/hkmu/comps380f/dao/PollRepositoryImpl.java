package hkmu.comps380f.dao;

import hkmu.comps380f.model.Poll;
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
public class PollRepositoryImpl implements PollRepository {

    private final JdbcOperations jdbcOp;

    private static final class PollExtractor implements ResultSetExtractor<List<Poll>> {

        @Override
        public List<Poll> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Map<String, Poll> map = new HashMap<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                Poll poll = map.get(String.valueOf(id));
                if (poll == null) {
                    poll = new Poll();
                    poll.setId(id);
                    poll.setPoll_q(rs.getString("poll_q"));
                    poll.setPoll_a_a(rs.getString("poll_a_a"));
                    poll.setPoll_a_b(rs.getString("poll_a_b"));
                    poll.setPoll_a_c(rs.getString("poll_a_c"));
                    poll.setPoll_a_d(rs.getString("poll_a_d"));
                    poll.setTotal(rs.getInt("total"));
                    poll.setNumber_of_a(rs.getInt("number_of_a"));
                    poll.setNumber_of_b(rs.getInt("number_of_b"));
                    poll.setNumber_of_c(rs.getInt("number_of_c"));
                    poll.setNumber_of_d(rs.getInt("number_of_d"));
                    map.put(String.valueOf(id), poll);
                }

            }
            return new ArrayList<>(map.values());
        }
    }

    private static final class IdExtractor implements ResultSetExtractor<List<Poll>> {

        @Override
        public List<Poll> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Map<String, Poll> map = new HashMap<>();
            while (rs.next()) {
                int count = rs.getInt("id");
                Poll poll = map.get(String.valueOf(count));
                if (poll == null) {
                    poll = new Poll();
                    poll.setId(count);

                    map.put(String.valueOf(count), poll);
                }

            }
            return new ArrayList<>(map.values());
        }
    }

    private static final class HistExtractor implements ResultSetExtractor<List<VoteHistory>> {

        @Override
        public List<VoteHistory> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Map<String, VoteHistory> map = new HashMap<>();
            while (rs.next()) {
                long id = rs.getLong("id");
                String username = rs.getString("username");
                long history_id = rs.getLong("history_id");
                String key = String.valueOf(id)+username+String.valueOf(history_id);
                VoteHistory votehistory = map.get(key);
                if (votehistory  == null) {
                    votehistory = new VoteHistory();
                    votehistory.setId(id);
                    votehistory.setHistoryid(history_id);
                    votehistory.setUsername(username);
                    votehistory.setAnswer(rs.getString("answer"));
                    key = String.valueOf(id)+username+String.valueOf(history_id);
                    

                    map.put(key, votehistory);
                }

            }
            return new ArrayList<>(map.values());
        }
    }

    @Autowired
    public PollRepositoryImpl(DataSource dataSource) {
        jdbcOp = new JdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public void createQA(Poll poll) {
        final String SQL_INSERT_POLLQA
                = "INSERT INTO poll_qa VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcOp.update(SQL_INSERT_POLLQA, poll.getId(), poll.getPoll_q(), poll.getPoll_a_a(), poll.getPoll_a_b(), poll.getPoll_a_c(), poll.getPoll_a_d(), poll.getTotal(), poll.getNumber_of_a(), poll.getNumber_of_b(), poll.getNumber_of_c(), poll.getNumber_of_d());

    }

    @Override
    @Transactional(readOnly = true)
    public List<Poll> findQAById(long id) {
        final String SQL_SELECT_USER
                = "select * from poll_qa"
                + " where poll_qa.id = ?";
        return jdbcOp.query(SQL_SELECT_USER, new PollExtractor(), id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Poll> findId() {
        final String SQL_SELECT_USER
                = "select id from poll_qa order by id DESC FETCH FIRST 1 ROWS ONLY";
        return jdbcOp.query(SQL_SELECT_USER, new IdExtractor());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Poll> findAllQA() {
        final String SQL_SELECT_USER
                = "select * from poll_qa ";
        return jdbcOp.query(SQL_SELECT_USER, new PollExtractor());
    }

    @Override
    @Transactional
    public void saveHistory(Poll poll, Principal user, long historyId) {
        final String SQL_INSERT_POLLQA
                = "INSERT INTO user_poll_history VALUES ( ?, ?, ?, ?)";
        jdbcOp.update(SQL_INSERT_POLLQA, poll.getId(), user.getName(), historyId, poll.getAnswers());

    }

    @Override
    @Transactional(readOnly = true)
    public List<VoteHistory> findByPrimary(VoteHistory history) {
        final String SQL_SELECT_HIST
                = "select * from user_poll_history where id = ? and username = ? order by history_id DESC FETCH FIRST 1 ROWS ONLY ";
        return jdbcOp.query(SQL_SELECT_HIST, new HistExtractor(), history.getId(), history.getUsername());

    }
    
        @Override
    @Transactional(readOnly = true)
    public List<VoteHistory> findAllByName(Principal user) {
        final String SQL_SELECT_HIST
                = "select * from user_poll_history where username = ?";
        return jdbcOp.query(SQL_SELECT_HIST, new HistExtractor(),user.getName());

    }

    @Override
    @Transactional
    public void delete(long id) {
        final String SQL_DELETE_QA = "delete from poll_qa where id=?";
        final String SQL_DELETE_History = "delete from user_poll_history where id=?";

        jdbcOp.update(SQL_DELETE_History, id);
        jdbcOp.update(SQL_DELETE_QA, id);
    }

    @Override
    @Transactional
    public void edit(Poll poll) {
        final String SQL_EDIT_QA
                = "update poll_qa set poll_q = ?, poll_a_a = ?, poll_a_b = ?, poll_a_c = ?, poll_a_d = ? where id = ?";

        jdbcOp.update(SQL_EDIT_QA, poll.getPoll_q(), poll.getPoll_a_a(), poll.getPoll_a_b(), poll.getPoll_a_c(), poll.getPoll_a_d(), poll.getId());
    }

    @Override
    @Transactional
    public void updateNo(Poll poll) {
        final String SQL_EDIT_QA
                = "update poll_qa set total = ?, number_of_a = ?, number_of_b = ?, number_of_c = ?, number_of_d = ? where id = ?";

        jdbcOp.update(SQL_EDIT_QA, poll.getTotal(), poll.getNumber_of_a(), poll.getNumber_of_b(), poll.getNumber_of_c(), poll.getNumber_of_d(), poll.getId());
    }
}
