package mobina.com.uniiii.abstracts;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable{
    private int id;
    private String name;
    private ArrayList<User> members;
    boolean creator;

    public Group(int id, String name, ArrayList<User> members, boolean creator) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.creator = creator;
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

    public void setCreator(boolean creator) {
        this.creator = creator;
    }

    public boolean getCreator() {
        return this.creator;
    }
}
