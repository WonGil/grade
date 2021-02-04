package photograde;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="PointCancel_table")
public class PointCancel {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String photoNm;
    private String status;
    private Integer point;
    private Long gradeId;

    @PrePersist
    public void onPrePersist(){
        PointCanceled pointCanceled = new PointCanceled();
        BeanUtils.copyProperties(this, pointCanceled);
        pointCanceled.publishAfterCommit();


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
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }




}
