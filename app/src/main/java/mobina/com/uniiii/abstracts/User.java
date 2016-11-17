package mobina.com.uniiii.abstracts;

public class User {

    private int id;
    private String name;
    private String email;
    private String mobile;
    private String latitude;
    private String longitude;
    private String update_time;

    public User(int id, String name, String email, String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public User(int id, String name, String email, String mobile, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public User(int id, String name, String email, String mobile, String latitude, String longitude, String update_time) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.latitude = latitude;
        this.longitude = longitude;
        this.update_time = update_time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setUpdateTime(String update_time) {
        this.update_time = update_time;
    }

    public String getUpdateTime() {
        return this.update_time;
    }


}
