package hkmu.comps380f.controller;

import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Lecture;
import hkmu.comps380f.view.DownloadingView;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
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

    private volatile long Lecture_ID_SEQUENCE = 1;
    private Map<Long, Lecture> LectureDatabase = new ConcurrentHashMap<>();

    // Controller methods, Form object, ...
    @GetMapping(value = {"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("LectureDatabase", LectureDatabase);
        return "list";
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("add", "LectureForm", new Form());
    }

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
        Lecture Lecture = new Lecture();
        Lecture.setId(this.getNextLectureId());
        Lecture.setLectureName(form.getLectureName());

        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                Lecture.addAttachment(attachment);
            }
        }
        this.LectureDatabase.put(Lecture.getId(), Lecture);
        return new RedirectView("/Lecture/view/" + Lecture.getId(), true);
    }

    private synchronized long getNextLectureId() {
        return this.Lecture_ID_SEQUENCE++;
    }

    @GetMapping("/view/{LectureId}")
    public String view(@PathVariable("LectureId") long LectureId,
            ModelMap model) {
        Lecture Lecture = this.LectureDatabase.get(LectureId);
        if (Lecture == null) {
            return "redirect:/Lecture/list";
        }
        model.addAttribute("LectureId", LectureId);
        model.addAttribute("Lecture", Lecture);
        return "view";
    }

    @GetMapping("/{LectureId}/attachment/{attachment:.+}")
    public View download(@PathVariable("LectureId") long LectureId,
            @PathVariable("attachment") String name) {
        Lecture Lecture = this.LectureDatabase.get(LectureId);
        if (Lecture != null) {
            Attachment attachment = Lecture.getAttachment(name);
            if (attachment != null) {
                return new DownloadingView(attachment.getName(),
                        attachment.getMimeContentType(), attachment.getContents());
            }
        }
        return new RedirectView("/Lecture/list", true);
    }

    @GetMapping("/{LectureId}/delete/{attachment:.+}")
    public String deleteAttachment(@PathVariable("LectureId") long LectureId,
            @PathVariable("attachment") String name) {
        Lecture Lecture = this.LectureDatabase.get(LectureId);
        if (Lecture != null) {
            if (Lecture.hasAttachment(name)) {
                Lecture.deleteAttachment(name);
            }
        }
        return "redirect:/Lecture/edit/" + LectureId;
    }

    @GetMapping("/edit/{LectureId}")
    public ModelAndView showEdit(@PathVariable("LectureId") long LectureId,
            Principal principal, HttpServletRequest request) {
        Lecture Lecture = this.LectureDatabase.get(LectureId);
        if (Lecture == null
                || (request.isUserInRole("ROLE_STUDENT"))) {
            return new ModelAndView(new RedirectView("/Lecture/list", true));
        }
        ModelAndView mav = new ModelAndView("edit");
        mav.addObject("LectureId", Long.toString(LectureId));
        mav.addObject("Lecture", Lecture);

        Form LectureForm = new Form();
        LectureForm.setLectureName(Lecture.getLectureName());
        mav.addObject("LectureForm", LectureForm);

        return mav;
    }

    @PostMapping("/edit/{LectureId}")
    public String edit(@PathVariable("LectureId") long LectureId, Form form,
            Principal principal, HttpServletRequest request)
            throws IOException {
        Lecture Lecture = this.LectureDatabase.get(LectureId);
        if (Lecture == null
                || (!request.isUserInRole("ROLE_ADMIN"))) {
            return "redirect:/Lecture/list";
        }
        Lecture.setLectureName(Lecture.getLectureName());

        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                Lecture.addAttachment(attachment);
            }
        }
        this.LectureDatabase.put(Lecture.getId(), Lecture);
        return "redirect:/Lecture/view/" + Lecture.getId();
    }

    @GetMapping("/delete/{LectureId}")
    public String deleteLecture(@PathVariable("LectureId") long LectureId) {
        if (this.LectureDatabase.containsKey(LectureId)) {
            this.LectureDatabase.remove(LectureId);
        }
        return "redirect:/Lecture/list";
    }

}
