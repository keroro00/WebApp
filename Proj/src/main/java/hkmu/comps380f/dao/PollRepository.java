package hkmu.comps380f.dao;

import hkmu.comps380f.model.Poll;
import hkmu.comps380f.model.VoteHistory;
import java.security.Principal;
import java.util.List;

public interface PollRepository {

    public void createQA(Poll poll);
    public List<Poll> findQAById(long id);
    public List<Poll> findId();
    public List<Poll> findAllQA();
    public void saveHistory(Poll poll,Principal user,long historyId);
    public List<VoteHistory> findByPrimary(VoteHistory history);
    public List<VoteHistory> findAllByName(Principal user);
    public void delete(long id);
    public void edit(Poll poll);
    public void updateNo(Poll poll);
}
