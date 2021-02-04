package photograde;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Point_table")
public class Point {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String photoNm;
    private String status;
    private Integer point;
    private Long gradeId;

    @PostPersist
    public void onPostPersist(){
        PointAwarded pointAwarded = new PointAwarded();
        BeanUtils.copyProperties(this, pointAwarded);
        pointAwarded.publishAfterCommit();


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
