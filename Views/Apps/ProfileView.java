package Views.Apps;

import Controllers.ViewControllerController;
import Models.Action;
import Models.CampCommiteeMember;
import Utils.DatabaseUtils;
import Utils.InputUtils;
import Utils.PageUtils;
import Views.Interfaces.IView;

import java.util.ArrayList;

/**
 *This is the ProfileView to show the profile of each user
 */
public class ProfileView implements IView {
    ViewControllerController vcc;
    ArrayList<Action> actions = new ArrayList();

    /**
     * ProfileView constructor takes in the ViewControllerController as its parameter.
     * This is for navigation purposes within the app.
     * @param vcc is the ViewControllerController
     */
    public ProfileView(ViewControllerController vcc){
        this.vcc = vcc;
        this.actions.add(new Action("Back To Home",1));
        this.actions.add(new Action("Change Password",2));
    }


    /**
     *The handle input function takes in an integer based on what the users enter and controls what the application does based on the choice
     * @param selection is the integer input by the user
     */
    @Override
    public void handleInput(int selection) {
        // TODO Auto-generated method stub
         switch(selection){
            case 1: this.vcc.navigate(3);break;
            case 2: 
                System.out.println("Enter a new password");
                String npw = InputUtils.tryGetPassword();
                if(npw!=null){
                    String s = DatabaseUtils.generateS().toString();
                   
                    String pw = DatabaseUtils.hashPassword(npw, s);
                    String[] userData = DatabaseUtils.getUserByID(this.vcc.getCurrentUser().getUserID());
                    userData[4]= pw;
                    userData[8]=s;
                    DatabaseUtils.setUserByID(userData[0],userData);
                    System.out.println("Password updated!");
                    //do save to database
                }
                render();
                break;
            default:
                System.out.println("Invalid Selection");
        }
    }

    /**
     * The render function outputs what is shown to the user and also sets up the business logic of getting an input from the user.
     */
    @Override
    public void render() {
        // TODO Auto-generated method stub
        String[] userData = DatabaseUtils.getUserByID(this.vcc.getCurrentUser().getUserID());
         PageUtils.printTitle("Profile");
         System.out.println("Name:");
         System.out.println(userData[1]);
         System.out.println("Email:");
         System.out.println(userData[2]);
         System.out.println("Faculty:");
         System.out.println(userData[5]);
         System.out.println("UserName:");
         System.out.println(userData[3]);
         if(this.vcc.getCurrentUser() instanceof CampCommiteeMember){
            System.out.println("Points:");
            System.out.println(userData[6]);
         }
         PageUtils.printActionBox(actions);
        int choice = InputUtils.tryGetIntSelection(actions);
        handleInput(choice);
    }
}
