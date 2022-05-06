package hkmu.comps380f.controller;

import hkmu.comps380f.dao.WebsiteUserRepository;
import hkmu.comps380f.model.WebsiteUser;
import java.io.IOException;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/user")
public class WebsiteUserController {

    @Resource
    private WebsiteUserRepository WebsiteUserRepo;

    @GetMapping({"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("WebsiteUsers", WebsiteUserRepo.findAll());
        return "listUser";
    }

    public static class Form {

        private String username;
        private String password;
        private String fullname;
        private String phone;

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
        private String address;

        // ... getters and setters for each of the properties
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        private String[] roles;

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
    }

    @GetMapping("/signup")
    public ModelAndView signup() {
        return new ModelAndView("signup", "WebsiteUser", new Form());
    }

    @PostMapping("/signup")
    public View signup(Form form) throws IOException {
        WebsiteUser user = new WebsiteUser(form.getUsername(),
                form.getPassword(), form.getFullname(), form.getPhone(), form.getAddress(), form.getRoles()
        );
        WebsiteUserRepo.save(user);
        return new RedirectView("/cslogin", true);
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("addUser", "WebsiteUser", new Form());
    }

    @PostMapping("/create")
    public View create(Form form) throws IOException {
        WebsiteUser user = new WebsiteUser(form.getUsername(),
                form.getPassword(), form.getFullname(), form.getPhone(), form.getAddress(), form.getRoles()
        );
        WebsiteUserRepo.save(user);
        return new RedirectView("/user/list", true);
    }

    @GetMapping("/delete/{username}")
    public View deleteUser(@PathVariable("username") String username) {
        WebsiteUserRepo.delete(username);
        return new RedirectView("/user/list", true);
    }

    @GetMapping("/edit/{username}")
    public ModelAndView edit(@PathVariable("username") String username, ModelMap model) {
        model.addAttribute("WebsiteUsers", WebsiteUserRepo.findToEdit(username));
        return new ModelAndView("editUser", "WebsiteUser", new Form());
    }

    @PostMapping("/edit/{username}")
    public View edit(Form form) throws IOException {
        WebsiteUser user = new WebsiteUser(form.getUsername(),
                form.getPassword(), form.getFullname(), form.getPhone(), form.getAddress(), form.getRoles()
        );
        WebsiteUserRepo.edit(user);
        return new RedirectView("/user/list", true);
    }
}
