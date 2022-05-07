package hkmu.comps380f.dao;
import hkmu.comps380f.model.Comment;
import java.security.Principal;
import java.util.List;

public interface CommentRepository {
    public void saveHistory(String place, Principal user, int id,String comment);
    public List<Comment> findId(String place, Principal user);
    public List<Comment> findByPlace(String place);
    public List<Comment> findByUser(Principal user);
}

