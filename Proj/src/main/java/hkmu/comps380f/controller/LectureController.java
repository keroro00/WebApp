package hkmu.comps380f.controller;

import hkmu.comps380f.dao.CommentRepository;
import hkmu.comps380f.dao.PollRepository;
import hkmu.comps380f.dao.attachmentRowMapper;
import hkmu.comps380f.dao.lectureRowMapper;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Lecture;
import hkmu.comps380f.view.DownloadingView;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/Lecture")
public class LectureController {

    
    
    @Resource
    private PollRepository PollRepo;

    @Resource
    private CommentRepository ComRepo;

    private final JdbcOperations jdbcOp;

    @Autowired
    public LectureController(DataSource dataSource) {
        jdbcOp = new JdbcTemplate(dataSource);
    }

    // Controller methods, Form object, ...
    @GetMapping(value = {"", "/list"})
    public String list(ModelMap model) {
        String SQL_LIST_LECTURE
                = "select * from lectures";
        String SQL_LIST_MATERIALS
                = "select material_id, l.lecture_name, m.file_name, m.content\n"
                + "from lectures l\n"
                + "right join materials m ON l.LECTURE_ID = m.LECTURE_ID\n"
                + "order by l.LECTURE_ID";
        List<Lecture> lectures = jdbcOp.query(SQL_LIST_LECTURE, new lectureRowMapper());
        List<Attachment> attachmentList = jdbcOp.query(SQL_LIST_MATERIALS, new attachmentRowMapper());

        model.addAttribute("LectureDatabase", lectures);
        model.addAttribute("PollDatabases", PollRepo.findAllQA());
        return "list";
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("add", "LectureForm", new Form());
    }
        public static class SubmitForm {
            private String comment;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }}

    public static class Form {

        private String lectureName;
        private List<MultipartFile> attachments;

        // Getters and Setters of LectureName, body, attachments
        public String getLectureName() {
            return lectureName;
        }

        public void setLectureName(String lectureName) {
            this.lectureName = lectureName;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    @PostMapping("/create")
    public View create(Form form, Principal principal) throws IOException {
        String SQL_INSERT_LECTURE
                = "insert into lectures  values (?, ?)";
        String SQL_INSERT_MATERIAL
                = "insert into materials values (?,?,?,?,?)";
        String SQL_LIST_LECTURE
                = "select * from lectures order by lecture_id DESC FETCH FIRST 1 ROWS ONLY";

        List<Lecture> lectures = jdbcOp.query(SQL_LIST_LECTURE, new lectureRowMapper());
        Lecture lecture = new Lecture();
        if (lectures.size() == 1) {
            lecture.setId(lectures.get(0).getId() + 1);
        } else {
            lecture.setId(1);
        }
        lecture.setLectureName(form.getLectureName());
        jdbcOp.update(SQL_INSERT_LECTURE, lecture.getId(), form.getLectureName());

        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {

                lecture.addAttachment(attachment);
                String SQL_LIST_MATERIALS
                        = "select * "
                        + "from materials "
                        + "order by material_id DESC FETCH FIRST 1 ROWS ONLY";
                List<Attachment> attachmentList = jdbcOp.query(SQL_LIST_MATERIALS, new attachmentRowMapper());
                long number;
                if (attachmentList.size() == 1) {
                    number = attachmentList.get(0).getId() + 1;
                } else {
                    number = 1;
                }

                jdbcOp.update(SQL_INSERT_MATERIAL, number, lecture.getId(), filePart.getOriginalFilename(), filePart.getBytes(), filePart.getContentType());
            }
        }
        return new RedirectView("/Lecture/view/" + lecture.getId(), true);
    }

    @GetMapping("/view/{LectureId}")
    public ModelAndView view(@PathVariable("LectureId") long LectureId,
            ModelMap model) {

        String SQL_LIST_LECTURE
                = "select * from lectures where lecture_id = ?";
        String SQL_LIST_MATERIALS
                = "select material_id, l.lecture_name, m.file_name, m.content, m.MIME\n"
                + "from lectures l\n"
                + "right join materials m ON l.LECTURE_ID = m.LECTURE_ID\n"
                + "where m.lecture_id = ?\n"
                + "order by l.LECTURE_ID";
        List<Lecture> lectures = jdbcOp.query(SQL_LIST_LECTURE, new lectureRowMapper(), LectureId);
        List<Attachment> attachmentList = jdbcOp.query(SQL_LIST_MATERIALS, new attachmentRowMapper(), LectureId);
        Lecture lecture = lectures.get(0);

        for (int i = 0; i < attachmentList.size(); i = i + 1) {
            Attachment a = attachmentList.get(i);
            lecture.addAttachment(a);
        }

        if (lecture == null) {
            return new  ModelAndView("list");
        }
                String place="Lecture"+LectureId;
        List<Comment> updateCom = ComRepo.findByPlace(place);
        model.addAttribute("Poll", LectureId);
        model.addAttribute("Lecture", lecture);
         model.addAttribute("Comment", updateCom);

        return new ModelAndView("view", "SubmitForm", new SubmitForm());

    }
    
    @PostMapping("/view/{LectureId}")
    public View answer(@PathVariable("LectureId") long LectureId, SubmitForm form, Principal principal) throws IOException {

        if (!form.comment.isEmpty()) {
            String place;
            place = "Lecture" + LectureId;
            List<Comment> Oldcomments = ComRepo.findId(place, principal);
            Comment comment = new Comment();
            if (Oldcomments.size() == 1) {
                comment.setId(Oldcomments.get(0).getId() + 1);
            } else {
                comment.setId(1);
            }
            ComRepo.saveHistory(place, principal, comment.getId(), form.comment);
        }
    return new RedirectView("/Lecture/list", true);
    }

    @GetMapping("/edit/{LectureId}")
    public ModelAndView showEdit(@PathVariable("LectureId") long LectureId,
            Principal principal, HttpServletRequest request) {

        String SQL_LIST_LECTURE
                = "select * from lectures where lecture_id = ?";
        String SQL_LIST_MATERIALS
                = "select material_id, l.lecture_name, m.file_name, m.content, m.MIME\n"
                + "from lectures l\n"
                + "right join materials m ON l.LECTURE_ID = m.LECTURE_ID\n"
                + "where m.lecture_id = ?\n"
                + "order by l.LECTURE_ID";
        List<Lecture> lectures = jdbcOp.query(SQL_LIST_LECTURE, new lectureRowMapper(), LectureId);
        List<Attachment> attachmentList = jdbcOp.query(SQL_LIST_MATERIALS, new attachmentRowMapper(), LectureId);
        Lecture lecture = lectures.get(0);

        for (int i = 0; i < attachmentList.size(); i = i + 1) {
            Attachment a = attachmentList.get(i);
            lecture.addAttachment(a);
        }
        if (lecture == null) {
            return new ModelAndView(new RedirectView("/Lecture/list", true));
        }

        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("Lecture", lecture);
        Form lectureForm = new Form();
        lectureForm.setLectureName(lecture.getLectureName());

        modelAndView.addObject("LectureForm", lectureForm);
        return modelAndView;
    }

    @PostMapping("/edit/{LectureId}")
    public String edit(@PathVariable("LectureId") long LectureId, Form form,
            Principal principal, HttpServletRequest request) throws IOException {
        String SQL_EDIT_LECTURE
                = "update lectures set lecture_name = ? where lecture_id = ?";
        String SQL_INSERT_MATERIAL
                = "insert into materials  values (?,?,?,?,?)";
        String SQL_LIST_LECTURE
                = "select * from lectures where lecture_id = ?";
        List<Lecture> lectures = jdbcOp.query(SQL_LIST_LECTURE, new lectureRowMapper(), LectureId);

        Lecture lecture = lectures.get(0);
        if (lecture == null) {
            return "redirect:/Lecture/list";
        }

        lecture.setLectureName(form.getLectureName());
        jdbcOp.update(SQL_EDIT_LECTURE, lecture.getLectureName(), lecture.getId());

        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {

                lecture.addAttachment(attachment);
                String SQL_LIST_MATERIALS
                        = "select * "
                        + "from materials "
                        + "order by material_id DESC FETCH FIRST 1 ROWS ONLY";
                List<Attachment> attachmentList = jdbcOp.query(SQL_LIST_MATERIALS, new attachmentRowMapper());
                long number;
                if (attachmentList.size() == 1) {
                    number = attachmentList.get(0).getId() + 1;
                } else {
                    number = 1;
                }

                jdbcOp.update(SQL_INSERT_MATERIAL, number, lecture.getId(), filePart.getOriginalFilename(), filePart.getBytes(), filePart.getContentType());
            }
        }

        return "redirect:/Lecture/view/" + LectureId;
    }

    @GetMapping("/delete/{LectureId}")
    public View delete(@PathVariable("LectureId") long LectureId) {
        String SQL_DELETE_MATERIAL
                = "delete from materials where lecture_id = ?";
        String SQL_DELETE_LECTURE
                = "delete from lectures where lecture_id = ?";
        jdbcOp.update(SQL_DELETE_MATERIAL, LectureId);
        jdbcOp.update(SQL_DELETE_LECTURE, LectureId);
        ComRepo.DeleteAll("Lecture"+LectureId);
        return new RedirectView("/Lecture/list", true);
    }
    
            @GetMapping("/Comhistory/delete/{LectureId}/{username}/{id}")
    public String deleteLecture(@PathVariable("LectureId") long LectureId, @PathVariable("username") String username, @PathVariable("id") int id) {
        ComRepo.Delete("Lecture"+LectureId, username, id);
        return "redirect:/Lecture/list";
    }

    @GetMapping("/{LectureId}/delete/{attachment:.+}")
    public String deleteAttachment(@PathVariable("LectureId") long LectureId,
            @PathVariable("attachment") String name) {
        String SQL_DROP_MATERIAL
                = "delete from materials where lecture_id = ? and file_name = ? ";
        String[] parts = name.split(".");
        jdbcOp.update(SQL_DROP_MATERIAL, LectureId, name);
        return "redirect:/Lecture/edit/" + LectureId;
    }

    @GetMapping("/{LectureId}/attachment/{attachment:.+}")
    public View download(@PathVariable("LectureId") long LectureId,
            @PathVariable("attachment") String name) {

        String SQL_LIST_LECTURE
                = "select * from lectures where lecture_id = ?";
        String SQL_LIST_MATERIALS
                = "select material_id, l.lecture_name, m.file_name, m.content, m.MIME\n"
                + "from lectures l\n"
                + "right join materials m ON l.LECTURE_ID = m.LECTURE_ID\n"
                + "where m.lecture_id = ? and file_name = ?\n"
                + "order by l.LECTURE_ID";
        List<Lecture> lectures = jdbcOp.query(SQL_LIST_LECTURE, new lectureRowMapper(), LectureId);
        List<Attachment> attachmentList = jdbcOp.query(SQL_LIST_MATERIALS, new attachmentRowMapper(), LectureId, name);
        Lecture lecture = lectures.get(0);

            Attachment a = attachmentList.get(0);
            lecture.addAttachment(a);
        if (lecture != null) {
            Attachment attachment = lecture.getAttachment(name);
            if (attachment != null) {
                return new DownloadingView(attachment.getName(),
                        attachment.getMimeContentType(), attachment.getContents());
            }
        }

        return new RedirectView("/Lecture/list", true);
    }

}
