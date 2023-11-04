package Views.Authentications;
import java.util.ArrayList;

import Utils.PageUtils;
import Views.Interfaces.IView;

public class LoginView implements IView {
    public void render(){
       //dummy array to remove later
        ArrayList<String> actions = new ArrayList();
        actions.add("student");
        actions.add("staff");

        PageUtils.printTitle("Welcome To CAMS");
        System.out.println("Log in to CAMS");
        PageUtils.printActionBox(actions);
    }
}
