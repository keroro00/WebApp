package hkmu.comps380f.dao;

import hkmu.comps380f.model.WebsiteUser;
import java.util.List;

public interface WebsiteUserRepository {

    public void save(WebsiteUser user);

    public List<WebsiteUser> findAll();

    public List<WebsiteUser> findById(String username);
    
    public List<WebsiteUser> findToEdit(String username);

    public void delete(String username);
    
    public void edit(WebsiteUser user);
}
