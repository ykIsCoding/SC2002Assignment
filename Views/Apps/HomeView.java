package Views.Apps;
import Utils.InputUtils;
import Utils.PageUtils;
import Views.Interfaces.IView;

import java.util.ArrayList;

import Controllers.AppViewController;
import Controllers.AuthenticationController;
import Controllers.CampViewController;
import Controllers.ViewControllerController;
import Models.Action;

public class HomeView implements IView {
    AppViewController avc;
    
    Action actions[] = {
        new Action("View All Camps", 1), 
        new Action("View My Camps", 2),
        new Action("View My Profile",3),
        new Action("View My Enquiries",4),
        new Action("View My Suggestions",5),
         new Action("Log Out",6),
    };

    public HomeView(AppViewController avc){
        this.avc = avc;
        
    }

    @Override
    public void handleInput(int selection) {
        // TODO Auto-generated method stub
        switch (selection) {
            case 1:
                avc.inputToViewControllerController(5);
                break;
            case 2:
                avc.inputToViewControllerController(5);
                break;
            case 3:
                avc.inputToWithinViewController(2);
                break;
            case 4:
                avc.inputToViewControllerController(6);
                break;
            case 5:
                System.out.println("selected");
                avc.inputToViewControllerController(3);
                break;
            case 6:
                System.out.println("logging out");
                avc.inputToViewControllerController(0);
                break;
            default:
                break;
        }
        
    }

    

    public void render(){
        PageUtils.printTitle("Welcome");
        PageUtils.printActionBox(actions);
        int choice = InputUtils.tryGetIntSelection(1, 6);
        handleInput(choice);
    }
}
