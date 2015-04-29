package majja.org.goaldigger;

import java.io.Serializable;

/**
 * Created by Jonas on 29/04/15.
 */
public class Friend implements Serializable{
    private String name, email;

    public Friend(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return "Name: " + name + " Email: " + email;
    }

    public void addFriend(User user){

    }
}
