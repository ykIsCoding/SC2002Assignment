package Views.Authentications;

import Utils.PageUtils;
import Views.Interfaces.IView;

public class AccessDeniedView implements IView {
    @Override
    public void handleInput(int selection) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void render() {
        // TODO Auto-generated method stub
         PageUtils.printTitle("Access Denied");
    }
}
