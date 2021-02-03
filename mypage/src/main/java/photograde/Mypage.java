package photograde;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Mypage_table")
public class Mypage {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private String photoNm;
        private String user;
        private String camera;
        private String status;
        private Integer point;
        private Integer grade;
        private Long photoId;
        private Long gradeId;
        private Long pointId;

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
        public Integer getPoint() {
            return point;
        }

        public void setPoint(Integer point) {
            this.point = point;
        }
        public Integer getGrade() {
            return grade;
        }

        public void setGrade(Integer grade) {
            this.grade = grade;
        }
        public Long getPhotoId() {
            return photoId;
        }

        public void setPhotoId(Long photoId) {
            this.photoId = photoId;
        }
        public Long getGradeId() {
            return gradeId;
        }

        public void setGradeId(Long gradeId) {
            this.gradeId = gradeId;
        }
        public Long getPointId() {
            return pointId;
        }

        public void setPointId(Long pointId) {
            this.pointId = pointId;
        }
}
