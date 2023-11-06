package Controllers;

import Controllers.Interfaces.IViewController;

public class ViewControllerController {
    AuthenticationController ac;
    AuthenticationViewController authvc;
	SuggestionViewController svc;
    AppViewController avc;
    EnquiryViewController evc;
    ProfileViewController pvc;
    CampViewController cvc;

    public ViewControllerController(AuthenticationController ac){
        this.ac = ac;
        authvc = new AuthenticationViewController();
        svc = new SuggestionViewController();
        avc = new AppViewController();
        evc =new EnquiryViewController();
        pvc = new ProfileViewController();
        cvc = new CampViewController();
    }

    public void setCurrentController(int num){
        switch(num) {
			case 0: 
                authvc.initialise(this);
                break;
			case 1: svc.initialise(this);break;
            case 2: avc.initialise(this);break;
			case 3: svc.initialise(this);break;
			case 4: pvc.initialise(this);break;
            case 5: cvc.initialise(this);break;
            case 6: evc.initialise(this);break;
            case 7: System.exit(0);
            default:
                System.out.println("error");
		}
        return;
    }
}
