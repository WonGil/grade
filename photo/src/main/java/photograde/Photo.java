package photograde;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Photo_table")
public class Photo {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String photoNm;
    private String camera;
    private Long gradeId;
    private String user;

    @PrePersist
    public void onPrePersist(){
        PhotoRegistered photoRegistered = new PhotoRegistered();
        BeanUtils.copyProperties(this, photoRegistered);
        photoRegistered.publishAfterCommit();


    }

    @PreRemove
    public void onPreRemove(){
        PhotoDeleted photoDeleted = new PhotoDeleted();
        BeanUtils.copyProperties(this, photoDeleted);
        photoDeleted.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        photograde.external.Grade grade = new photograde.external.Grade();
        // mappings goes here
        grade.setGradeId(this.getId());
        grade.setStatus("Grade Cancelled");
        PhotoApplication.applicationContext.getBean(photograde.external.GradeService.class)
            .gradeCancel(grade);


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
    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }
    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }




}
