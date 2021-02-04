package photograde;

public class PhotoRegistered extends AbstractEvent {

    private Long id;
    private String photoNm;
    private String camera;
    private Long gradeID;
    private String user;

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
        return gradeID;
    }

    public void setGradeId(Long gradeID) {
        this.gradeID = gradeID;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}