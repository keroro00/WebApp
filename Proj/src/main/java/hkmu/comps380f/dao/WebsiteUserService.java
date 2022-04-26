package hkmu.comps380f.dao;

import hkmu.comps380f.model.WebsiteUser;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WebsiteUserService implements UserDetailsService {

    @Resource
    WebsiteUserRepository WebsiteUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        List<WebsiteUser> users = WebsiteUserRepo.findById(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        WebsiteUser user = users.get(0);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
