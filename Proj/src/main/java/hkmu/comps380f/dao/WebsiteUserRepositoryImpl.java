package hkmu.comps380f.dao;

import hkmu.comps380f.model.WebsiteUser;
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
public class WebsiteUserRepositoryImpl implements WebsiteUserRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public WebsiteUserRepositoryImpl(DataSource dataSource) {
        jdbcOp = new JdbcTemplate(dataSource);
    }

    private static final class UserExtractor implements ResultSetExtractor<List<WebsiteUser>> {

        @Override
        public List<WebsiteUser> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Map<String, WebsiteUser> map = new HashMap<>();
            while (rs.next()) {
                String username = rs.getString("username");
                WebsiteUser user = map.get(username);
                if (user == null) {
                    user = new WebsiteUser();
                    user.setUsername(username);
                    user.setPassword(rs.getString("password"));
                    map.put(username, user);
                }
                user.getRoles().add(rs.getString("role"));
                
            }
            return new ArrayList<>(map.values());
        }
    }
    
        private static final class UserExtractorToEdit implements ResultSetExtractor<List<WebsiteUser>> {

        @Override
        public List<WebsiteUser> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Map<String, WebsiteUser> map = new HashMap<>();
            while (rs.next()) {
                String username = rs.getString("username");
                WebsiteUser user = map.get(username);
                if (user == null) {
                    user = new WebsiteUser();
                    user.setUsername(username);
                    user.setPassword(rs.getString("password"));
                    map.put(username, user);
                }
                user.getRoles().add(rs.getString("role"));
                user.setFullname(rs.getString("fullname"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                
            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    @Transactional
    public void save(WebsiteUser user) {
        final String SQL_INSERT_USER
                = "insert into users (username, password) values (?, ?)";
        final String SQL_INSERT_ROLE
                = "insert into user_roles (username, role) values (?, ?)";
        final String SQL_INSERT_INFORMATION
                = "insert into user_information (username, fullname, phone, address) values (?, ?, ?, ?)";
        jdbcOp.update(SQL_INSERT_USER, user.getUsername(), user.getPassword());
        jdbcOp.update(SQL_INSERT_INFORMATION, user.getUsername(), user.getFullname(), user.getPhone(), user.getAddress());
        System.out.println("User " + user.getUsername() + " inserted");

        for (String role : user.getRoles()) {
            jdbcOp.update(SQL_INSERT_ROLE, user.getUsername(), role);
            System.out.println("User_role " + role + " of user "
                    + user.getUsername() + " inserted");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebsiteUser> findAll() {
        final String SQL_SELECT_USERS
                = "select users.*, user_roles.role from users, user_roles"
                + " where users.username = user_roles.username";
        return jdbcOp.query(SQL_SELECT_USERS, new UserExtractor());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebsiteUser> findById(String username) {
        final String SQL_SELECT_USER
                = "select users.*, user_roles.role from users, user_roles"
                + " where users.username = user_roles.username"
                + " and users.username = ?";
        return jdbcOp.query(SQL_SELECT_USER, new UserExtractor(), username);
    }
    
        @Override
    @Transactional(readOnly = true)
    public List<WebsiteUser> findToEdit(String username) {
        final String SQL_SELECT_USER
                = "select users.*, user_roles.role, fullname, phone, address from users, user_roles, user_information where users.username = user_roles.username AND users.username = user_information.username and users.username = ?";
        return jdbcOp.query(SQL_SELECT_USER, new UserExtractorToEdit(), username);
    }

    @Override
    @Transactional
    public void delete(String username) {
        final String SQL_DELETE_USER = "delete from users where username=?";
        final String SQL_DELETE_ROLES = "delete from user_roles where username=?";
        final String SQL_DELETE_INFORMATION = "delete from user_information where username=?";
        jdbcOp.update(SQL_DELETE_INFORMATION, username);
        jdbcOp.update(SQL_DELETE_ROLES, username);
        jdbcOp.update(SQL_DELETE_USER, username);
    }

    @Override
    @Transactional
    public void edit(WebsiteUser user) {
        final String SQL_INSERT_USER
                = "update users set password = ? where username = ?";
        final String SQL_INSERT_ROLE
                = "update user_roles set role = ? where username = ?";
        final String SQL_INSERT_INFORMATION
                = "update user_information set fullname = ?, phone = ?, address = ? where username = ?";
        jdbcOp.update(SQL_INSERT_USER, user.getPassword(), user.getUsername());
        jdbcOp.update(SQL_INSERT_INFORMATION, user.getFullname(), user.getPhone(), user.getAddress(), user.getUsername());
        System.out.println("User " + user.getUsername() + " inserted");

        for (String role : user.getRoles()) {
            jdbcOp.update(SQL_INSERT_ROLE, role, user.getUsername());
            System.out.println("User_role " + role + " of user "
                    + user.getUsername() + " edited");
        }
    }

}
