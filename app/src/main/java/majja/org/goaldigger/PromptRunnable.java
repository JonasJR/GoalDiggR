package majja.org.goaldigger;

/**
 * Created by Jonas on 28/04/15.
 */
public class PromptRunnable implements Runnable{
    private String v;
    private String oldPass,newPass;

    public void setValue(String inV){
        this.v = inV;
    }
    public void setPassValue(String oldPass,String newPass){
        this.oldPass = oldPass;
        this.newPass = newPass;
    }
    public String getChangePasswordValue(){
        return (oldPass + ":" + newPass);
    }
    public String getValue(){
        return this.v;
    }
    public void run(){
        this.run();
    }
}
