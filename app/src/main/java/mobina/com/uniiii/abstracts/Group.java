package mobina.com.uniiii.abstracts;

import java.util.ArrayList;

public class Group {
    private int id;
    private String name;
    private ArrayList<User> members;

    public Group(int id, String name, ArrayList<User> members) {
        this.id = id;
        this.name = name;
        this.members = members;
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

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public ArrayList<User> getMembers() {
        return this.members;
    }
}
