package hkmu.comps380f.controller;

import hkmu.comps380f.dao.CommentRepository;
import hkmu.comps380f.dao.PollRepository;
import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Poll;
import hkmu.comps380f.model.VoteHistory;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/Poll")
public class PollController {

    private volatile long Poll_ID_SEQUENCE = 1;
    private Map<Long, Poll> PollDatabase = new ConcurrentHashMap<>();

    @Resource
    private PollRepository PollRepo;

    @Resource
    private CommentRepository ComRepo;

    // Controller methods, Form object, ...
    @GetMapping(value = {"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("PollDatabases", PollRepo.findAllQA());
        return "list";
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("pollAdd", "PollForm", new Form());
    }

    public static class Form {

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
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

    public static class SubmitForm {

        private int total;
        private int number_of_a;
        private int number_of_b;
        private int number_of_c;
        private int number_of_d;
        private String answers;
        private int id;
        private String comment;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAnswers() {
            return answers;
        }

        public void setAnswers(String answers) {
            this.answers = answers;
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

    public static class CommentForm {

        private String comment;
    }

    @PostMapping("/create")
    public View create(Form form, Principal principal) throws IOException {
        Poll Poll = new Poll();
        List<Poll> countIds = PollRepo.findId();
        if (countIds.size() == 0) {
            Poll.setId(1);
        } else {
            Poll countId = countIds.get(0);
            Poll.setId(countId.getId() + 1);
        }
        Poll.setPoll_q(form.getPoll_q());
        Poll.setPoll_a_a(form.getPoll_a_a());
        Poll.setPoll_a_b(form.getPoll_a_b());
        Poll.setPoll_a_c(form.getPoll_a_c());
        Poll.setPoll_a_d(form.getPoll_a_d());
        Poll.setTotal(0);
        Poll.setNumber_of_a(0);
        Poll.setNumber_of_b(0);
        Poll.setNumber_of_c(0);
        Poll.setNumber_of_d(0);

        this.PollDatabase.put(Poll.getId(), Poll);
        PollRepo.createQA(Poll);
        return new RedirectView("/Poll/view/" + Poll.getId(), true);
    }

    private synchronized long getNextPollId() {
        return this.Poll_ID_SEQUENCE++;
    }

    @GetMapping("/view/{PollId}")
    public ModelAndView view(@PathVariable("PollId") long PollId,
            ModelMap model, Principal principal) {
        List<Poll> polls = PollRepo.findQAById(PollId);
        if (polls == null) {
            return new ModelAndView("list");
        }

        Poll poll = polls.get(0);
        VoteHistory history = new VoteHistory();
        history.setId(PollId);
        history.setUsername(principal.getName());
        List<VoteHistory> UpdateHists = PollRepo.findByPrimary(history);
        if (UpdateHists.size() != 0) {
            VoteHistory UpdateHist = UpdateHists.get(0);
            poll.setAnswers(UpdateHist.getAnswer());
        }
        String place="Poll"+PollId;
        List<Comment> updateCom = ComRepo.findByPlace(place);
        model.addAttribute("Poll", poll);
         model.addAttribute("Comment", updateCom);

        return new ModelAndView("pollView", "PollSubmitForm", new SubmitForm());
    }

    @PostMapping("/view/{PollId}")
    public View answer(@PathVariable("PollId") long PollId, SubmitForm form, Principal principal) throws IOException {
        List<Poll> Oldpolls = PollRepo.findQAById(PollId);
        Poll Poll = new Poll();
        Poll.setId(form.getId());

        if (!form.comment.isEmpty()) {
            String place;
            place = "Poll" + PollId;
            List<Comment> Oldcomments = ComRepo.findId(place, principal);
            Comment comment = new Comment();
            if (Oldcomments.size() == 1) {
                comment.setId(Oldcomments.get(0).getId() + 1);
            } else {
                comment.setId(1);
            }
            ComRepo.saveHistory(place, principal, comment.getId(), form.comment);
        }
            if (form.getAnswers().equals("N")) {
            return new RedirectView("/Poll/list", true);
        }
        Poll.setAnswers(form.getAnswers());
        Poll Oldpoll = Oldpolls.get(0);
        Poll.setNumber_of_a(Oldpoll.getNumber_of_a());

        Poll.setNumber_of_b(Oldpoll.getNumber_of_b());

        Poll.setNumber_of_c(Oldpoll.getNumber_of_c());

        Poll.setNumber_of_d(Oldpoll.getNumber_of_d());

        VoteHistory history = new VoteHistory();
        history.setId(PollId);
        history.setUsername(principal.getName());
        List<VoteHistory> UpdateHists = PollRepo.findByPrimary(history);
        String checkAns;
        checkAns = "N";
        if (UpdateHists.size() == 1) {
            VoteHistory UpdateHist = UpdateHists.get(0);
            history.setHistoryid(UpdateHist.getHistoryid() + 1);
            switch (form.getAnswers()) {
                case "A":
                    Poll.setNumber_of_a(Oldpoll.getNumber_of_a() + 1);
                    break;
                case "B":
                    Poll.setNumber_of_b(Oldpoll.getNumber_of_b() + 1);
                    break;
                case "C":
                    Poll.setNumber_of_c(Oldpoll.getNumber_of_c() + 1);
                    break;
                case "D":
                    Poll.setNumber_of_d(Oldpoll.getNumber_of_d() + 1);
                    break;
            }
            switch (UpdateHist.getAnswer()) {
                case "A":
                    Poll.setNumber_of_a(Poll.getNumber_of_a() - 1);
                    break;
                case "B":
                    Poll.setNumber_of_b(Poll.getNumber_of_b() - 1);
                    break;
                case "C":
                    Poll.setNumber_of_c(Poll.getNumber_of_c() - 1);
                    break;
                case "D":
                    Poll.setNumber_of_d(Poll.getNumber_of_d() - 1);
                    break;
            }

        } else {
            history.setHistoryid(1);
            switch (form.getAnswers()) {
                case "A":
                    Poll.setNumber_of_a(Oldpoll.getNumber_of_a() + 1);
                    break;
                case "B":
                    Poll.setNumber_of_b(Oldpoll.getNumber_of_b() + 1);
                    break;
                case "C":
                    Poll.setNumber_of_c(Oldpoll.getNumber_of_c() + 1);
                    break;
                case "D":
                    Poll.setNumber_of_d(Oldpoll.getNumber_of_d() + 1);
                    break;
            }
        }
        Poll.setTotal(Poll.getNumber_of_a() + Poll.getNumber_of_b() + Poll.getNumber_of_c() + Poll.getNumber_of_d());
        this.PollDatabase.put(Poll.getId(), Poll);
        PollRepo.updateNo(Poll);
                if (UpdateHists.size() == 1) {
            VoteHistory UpdateHist = UpdateHists.get(0);
            history.setHistoryid(UpdateHist.getHistoryid() + 1);
            checkAns = UpdateHist.getAnswer();
            if (checkAns.equals(form.getAnswers())) {
                return new RedirectView("/Proj/Poll/list");
            } else {
                PollRepo.saveHistory(Poll, principal, history.getHistoryid());
            }

        } else {
            PollRepo.saveHistory(Poll, principal, history.getHistoryid());
        }
        return new RedirectView("test/" + Poll.getId(), true);
    }

    @GetMapping("view/test/{PollId}")
    public ModelAndView test(@PathVariable("PollId") long PollId,
            ModelMap model) {
        Poll Poll = this.PollDatabase.get(PollId);
        if (Poll == null) {
            return new ModelAndView("list");
        }

        model.addAttribute("PollId", PollId);
        model.addAttribute("Poll", Poll);
        return new ModelAndView("test");
    }

    @GetMapping("/delete/{PollId}")
    public String deleteLecture(@PathVariable("PollId") long PollId) {
        PollRepo.delete(PollId);
        return "redirect:/Poll/list";
    }

    @GetMapping("/edit/{PollId}")
    public ModelAndView edit(@PathVariable("PollId") long PollId, ModelMap model) {
        model.addAttribute("PollDatabase", PollRepo.findQAById(PollId));
        return new ModelAndView("pollEdit", "Poll", new Form());
    }

    @PostMapping("/edit/{PollId}")
    public View edit(Form form) throws IOException {
        Poll poll = new Poll();
        poll.setId(form.getId());
        poll.setPoll_q(form.getPoll_q());
        poll.setPoll_a_a(form.getPoll_a_a());
        poll.setPoll_a_b(form.getPoll_a_b());
        poll.setPoll_a_c(form.getPoll_a_c());
        poll.setPoll_a_d(form.getPoll_a_d());

        PollRepo.edit(poll);
        return new RedirectView("/Poll/list", true);
    }

    @GetMapping({"/votehistory"})
    public String votehistory(ModelMap model, Principal principal) {
        model.addAttribute("VoteHistorys", PollRepo.findAllByName(principal));
        return "VoteHistory";
    }
    
        @GetMapping({"/Comhistory"})
    public String comhistory(ModelMap model, Principal principal) {
        model.addAttribute("ComHistorys", ComRepo.findByUser(principal));
        return "ComHistory";
    }
}
