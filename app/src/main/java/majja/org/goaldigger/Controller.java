package majja.org.goaldigger;


import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class Controller {

    private String userName;
    private String passWord;

    public Controller(){

    }

    private String getUserAndPass(){
        return userName + "," + passWord;
    }

    public boolean login(String userName, String passWord){
        if(userName.equals("Kalle") && passWord.equals("blomma")){
            this.userName = userName;
            this.passWord = passWord;
            return true;
        }
        else {
            return false;
        }
    }

    public String[] getProjects(){
        String[] str = {"Result", "From", "Database"};
        return str;
    }

    public String[] getMilestones(String project){
        String[] str = {"Milestones", "From", "Database"};
        return str;
    }

    public String[] getItems(String project){
        String[] str = {"Items", "From", "Database"};
        return str;
    }

    public boolean addFriend(String friendName){
        //check if friend exists and return true or false
        return true;
    }

    public String[] getFriends(){
        String[] str = {"Pelle", "Stina", "Klas"};
        return str;
    }

    public String createUser(String email, String user, String pass){
        //check if user exists or email is wrong or other stuff
        return "Success";
    }

    public String resetPassword(String userName){
        //check username and send email to the person with recover link
        return "Recovery email has been sent";
    }


}
