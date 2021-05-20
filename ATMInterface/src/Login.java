import com.opencsv.bean.CsvBindByName;

public class Login {

    @CsvBindByName
    private String uid;

    @CsvBindByName
    private String username;

    @CsvBindByName
    private String password;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("uid: " + this.uid + "\n");
        sb.append("username: " + this.username + "\n");
        sb.append("password " + this.password);
        return sb.toString();
    }
}
