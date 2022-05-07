/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.dao;

import hkmu.comps380f.model.Attachment;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author felix
 */
public class attachmentRowMapper implements RowMapper<Attachment> {
    private String attachmentName;
    private byte[] attachmentContent;

    @Override
    public Attachment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Attachment attachment = new Attachment();
        attachment.setId(rs.getLong("material_id"));
        attachment.setName(rs.getString("file_name"));
        attachment.setMimeContentType("MIME");
        Blob blob = rs.getBlob("content");
        byte[] bytes = blob.getBytes(1l, (int) blob.length());
        attachment.setContents(bytes);
        return attachment;
    }

}
