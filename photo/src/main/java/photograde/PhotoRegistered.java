package photograde;

public class PhotoRegistered extends AbstractEvent {

    private Long id;
    private String photoNm;
    private String user;
    private String camera;
    private String gradeId;

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
    public String getCookingMethod() {
        return user;
    }

    public void setCookingMethod(String user) {
        this.user = user;
    }
    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }
    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }
}