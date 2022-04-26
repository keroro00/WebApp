package hkmu.comps380f.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Lecture implements Serializable {

    private long id;
    private String lectureName;
    private Map<String, Attachment> attachments = new ConcurrentHashMap<>();

    // Getters and Setters of id, LectureName, subject, body (not attachments)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public Attachment getAttachment(String name) {
        return this.attachments.get(name);
    }

    public Collection<Attachment> getAttachments() {
        return this.attachments.values();
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.put(attachment.getName(), attachment);
    }

    public int getNumberOfAttachments() {
        return this.attachments.size();
    }

    public boolean hasAttachment(String name) {
        return this.attachments.containsKey(name);
    }

    public Attachment deleteAttachment(String name) {
        return this.attachments.remove(name);
    }
}
