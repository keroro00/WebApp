/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author felix
 */
public class lectureRowMapper implements RowMapper<String>{
    private String lectureName;

    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        lectureName = rs.getString("lecture_name");
        return lectureName;
    }
    
}
