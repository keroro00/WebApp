/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hkmu.comps380f.model;

/**
 *
 * @author TonyHo
 */
public class Comment {

    private String place;
    private String username;
    private int id;
    private String comment;

    public Comment() {

    }

    public Comment(String place, String username, int id, String comment) {
        this.place = place;
        this.username = username;
        this.id = id;
        this.comment = comment;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
