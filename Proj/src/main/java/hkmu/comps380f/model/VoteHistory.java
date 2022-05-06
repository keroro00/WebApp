package hkmu.comps380f.model;

import java.io.Serializable;

public class VoteHistory implements Serializable {
    private long id;
    private String username;
    private long historyid;
    private String answer;
    
    public VoteHistory(){
}
    
        public VoteHistory(long id, String username, long historyid, String answer){
            this.id = id;
            this.username = username;
            this.historyid = historyid;
            this.answer = answer;
}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getHistoryid() {
        return historyid;
    }

    public void setHistoryid(long historyid) {
        this.historyid = historyid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
        
}
