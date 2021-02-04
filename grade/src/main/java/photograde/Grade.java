package photograde;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Grade_table")
public class Grade {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String photoNm;
    private String user;
    private String camera;
    private String status;
    private Integer grade;
    private Long gradeId;

    @PostPersist
    public void onPostPersist(){
        Graded graded = new Graded();
        BeanUtils.copyProperties(this, graded);
        graded.publishAfterCommit();


    }

    @PrePersist
    public void onPrePersist(){
        try {
            Thread.currentThread().sleep((long) (800 + Math.random() * 220));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PreRemove
    public void onPreRemove(){
        GradeCanceled gradeCanceled = new GradeCanceled();
        gradeCanceled.setGradeId(this.getId());
        gradeCanceled.setStatus("Grade Cancelled");


        BeanUtils.copyProperties(this, gradeCanceled);
        gradeCanceled.publishAfterCommit();

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPhotoNm() {
        return photoNm;
    }

    public void setPhotoNm(String photoNm) {
        this.photoNm = photoNm;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }




}
