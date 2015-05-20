package majja.org.goaldigger;

/**
 * Created by Goaldigger on 28/04/15.
 */
public class PromptRunnable implements Runnable {
    private String value;
    private String oldPassword, newPassword, confirmPassword;

    public void setValue(String value) {
        this.value = value;
    }
    public void setPassValue(String oldPassword,String newPassword, String confirmPassword){
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }
    public String[] getChangePasswordValue(){
        String[] passwords = new String[3];
        passwords[0] = oldPassword;
        passwords[1] = newPassword;
        passwords[2] = confirmPassword;
        return passwords;
    }
    public String getValue(){
        return this.value;
    }
    public void run() {}
}