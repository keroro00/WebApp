package hkmu.comps380f.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Poll implements Serializable {

    private long id;
    private String poll_q;
    private String poll_a_a;
    private String poll_a_b;
    private String poll_a_c;
    private String poll_a_d;
    private int total;
    private int number_of_a;
    private int number_of_b;
    private int number_of_c;
    private int number_of_d;
    private String answers;
    
        public Poll() {
    }

    public Poll(long id, String poll_q, String poll_a_a,String poll_a_b, String poll_a_c,String poll_a_d, int total, int number_of_a, int number_of_b, int number_of_c, int number_of_d, String answers) {
        this.id = id;
        this.poll_q = poll_q;
        this.poll_a_a = poll_a_a;
        this.poll_a_b = poll_a_b;
        this.poll_a_c = poll_a_c;
        this.poll_a_d = poll_a_d;
        this.total = total;
        this.number_of_a = number_of_a;
        this.number_of_b = number_of_b;
        this.number_of_c = number_of_c;
        this.number_of_d = number_of_d;
        this.answers = answers;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPoll_q() {
        return poll_q;
    }

    public void setPoll_q(String poll_q) {
        this.poll_q = poll_q;
    }

    public String getPoll_a_a() {
        return poll_a_a;
    }

    public void setPoll_a_a(String poll_a_a) {
        this.poll_a_a = poll_a_a;
    }

    public String getPoll_a_b() {
        return poll_a_b;
    }

    public void setPoll_a_b(String poll_a_b) {
        this.poll_a_b = poll_a_b;
    }

    public String getPoll_a_c() {
        return poll_a_c;
    }

    public void setPoll_a_c(String poll_a_c) {
        this.poll_a_c = poll_a_c;
    }

    public String getPoll_a_d() {
        return poll_a_d;
    }

    public void setPoll_a_d(String poll_a_d) {
        this.poll_a_d = poll_a_d;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNumber_of_a() {
        return number_of_a;
    }

    public void setNumber_of_a(int number_of_a) {
        this.number_of_a = number_of_a;
    }

    public int getNumber_of_b() {
        return number_of_b;
    }

    public void setNumber_of_b(int number_of_b) {
        this.number_of_b = number_of_b;
    }

    public int getNumber_of_c() {
        return number_of_c;
    }

    public void setNumber_of_c(int number_of_c) {
        this.number_of_c = number_of_c;
    }

    public int getNumber_of_d() {
        return number_of_d;
    }

    public void setNumber_of_d(int number_of_d) {
        this.number_of_d = number_of_d;
    }


}
