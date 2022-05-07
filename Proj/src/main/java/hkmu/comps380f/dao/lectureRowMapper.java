/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import hkmu.comps380f.model.Lecture;

/**
 *
 * @author felix
 */
public class lectureRowMapper implements RowMapper<Lecture>{
    private String lectureName;
    private long lecture_id;

    @Override
    public Lecture mapRow(ResultSet rs, int rowNum) throws SQLException {
        lectureName = rs.getString("lecture_name");
        lecture_id = Long.valueOf(rs.getInt("lecture_id"));
        Lecture lecture = new Lecture();
        lecture.setId(lecture_id);
        lecture.setLectureName(lectureName);
        
        return lecture;
    }
    
}
