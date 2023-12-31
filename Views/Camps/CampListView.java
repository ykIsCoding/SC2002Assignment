package Views.Camps;

import Controllers.ViewControllerController;
import Models.*;
import Utils.DatabaseUtils;
import Utils.InputUtils;
import Utils.PageUtils;
import Views.Interfaces.IView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

/**
 * This is the CampListView to show a list of the camps to the user.
 */
public class CampListView extends CampList implements IView {
    ViewControllerController vcc;


    private ArrayList<Action> actions = new ArrayList<>();
    private ArrayList<Camp> currentCampList;
    
    private String filterCondition = "alphabetical";

    /**
     * This function takes in the integer as input by the user when filtering the camp list and sets the filter condition. This filter condition will determine what camps will be shown.
     * @param cond is the integer corresponding to the filter
     */
    private void setFilterCondition(int cond){
        switch(cond){
            case 1: 
                this.filterCondition="ascending date";
                break;
            case 2: 
                this.filterCondition="descending date";
                break;
            case 3: 
                this.filterCondition="location";
                break;
            case 4:
                this.filterCondition="camp committee slots";
                break;
            case 5:
                this.filterCondition="attendee slots";
                break;
            case 6:
                this.filterCondition="ascending registration closing date";
                break;
            case 7:
                this.filterCondition="descending registration closing date"; 
                break;
            case 8: 
                this.filterCondition="only my faculty"; 
                break;
            case 9:
                this.filterCondition="for everyone";  
                break;
            case 10:
                this.filterCondition="alphabetical";  
                break;
            case 11:
                this.filterCondition="user is attendee";
                break;
            case 12:
                this.filterCondition="user is ccm";
                break;
        }
    }

    /**
     * The CampListView constructor takes in the ViewControllerController as its parameter.
     * This is for navigation purposes within the app.
     * @param vcc is the ViewControllerController
     */
    public CampListView(ViewControllerController vcc){
        this.vcc=vcc;
        setup();
	}

    /**
     * setup is the function to set up the class. It is used for refreshing the class.
     */
    private void setup(){
        retrieveCampsFromDB();
        this.actions = new ArrayList<>();
        this.actions.add(new Action("Back To Home", 1)); 
        
        
        if(vcc.getCurrentUser() instanceof Staff){
            this.currentCampList = getCampList();
            this.actions.add(new Action("Create Camp", 3));
             this.actions.add(new Action("View Camps I Created", 4));
        }else if(vcc.getCurrentUser() instanceof CampCommiteeMember){
            this.currentCampList = getCampListByFacultyOrAll(this.vcc.getCurrentUser().getFaculty());
        }else if(vcc.getCurrentUser() instanceof Student){
            this.currentCampList = getCampListByFacultyOrAll(this.vcc.getCurrentUser().getFaculty());
            this.currentCampList.removeIf((Camp c)->!c.getVisibility());
        }

        if(this.currentCampList.size()>0){
            this.currentCampList.sort(Comparator.comparing((Camp x) -> x.getCampName().toLowerCase()));
            this.actions.add(new Action("View Camp", 2));
            this.actions.add(new Action("Filter Camp By Condition", 5));
        }
    }

    /**
     * The handle input function takes in an integer based on what the users enter and controls what the application does based on the choice
     * @param selection is the integer input by the user.
     */
    public void handleInput(int selection) {
        switch(selection){
            case 1:this.vcc.navigate(3);break;
            case 2: 
                System.out.println("Enter the camp to view:");
                int choiceOfCamp = InputUtils.tryGetIntSelection(0, getCampList().size()-1);
                CampView cv = new CampView(getCampByIndex(choiceOfCamp),this.vcc);
                cv.render();
                break;
            case 3:
                String campName;
                String date;
                String closingDate;
                String usergroup;
                String location;
                int slots;
                int ccslots;
                String description;

                System.out.println("Enter camp name:");
                campName = InputUtils.tryGetString();
                System.out.println("Enter camp date (DD/MM/YYYY):");
                date = InputUtils.tryGetDate();
                System.out.println("Enter camp registration closing date (DD/MM/YYYY):");
                closingDate = InputUtils.tryGetDate();
                System.out.println("Enter usergroup:");
                usergroup = InputUtils.tryGetString().toUpperCase();
                System.out.println("Enter location:");
                location = InputUtils.tryGetString();
                System.out.println("Enter Attendee slots:");
                slots = InputUtils.tryGetIntSelection(1,10000);
                System.out.println("Enter camp committee slots:");
                ccslots = InputUtils.tryGetIntSelection(1,10);
                System.out.println("Enter Description:");
                description = InputUtils.tryGetString();

                Camp tempCamp = new Camp(
                    UUID.randomUUID().toString(),
                    campName,
                    PageUtils.stringToLocalDate(date),
                    PageUtils.stringToLocalDate(closingDate),
                    usergroup,
                    location,
                    slots,
                    ccslots,
                    description,
                        vcc.getCurrentUser().getUserID()
                    );
                tempCamp.setVisibility(false);
                addCamp(tempCamp);
                System.out.println("Camp Added!");
                render();
                break;
            case 4:
                CampListStaffMenuView clsmv = new CampListStaffMenuView(this.vcc);
                clsmv.render();
            case 5:
            if(this.vcc.getCurrentUser() instanceof Student){
                System.out.println("Enter a number to filter by condition (enter -1 to cancel):\n1: date (ascending)\n2: date (descending)\n3: location\n4: camp committee slots\n5: attendee slots\n6: registration closing date (ascending)\n7: registration closing date (descending)\n8: Only for my faculty\n9: for everyone\n10: alphabetical\n11: Camps that I am attendee in\n12: Camps that I am Camp Committee Member in");
            }else{
                System.out.println("Enter a number to filter by condition (enter -1 to cancel):\n1: date (ascending)\n2: date (descending)\n3: location\n4: camp committee slots\n5: attendee slots\n6: registration closing date (ascending)\n7: registration closing date (descending)\n8: Only for my faculty\n9: for everyone\n10: alphabetical");
            }
                int condition = InputUtils.tryGetIntSelection(1,(this.vcc.getCurrentUser() instanceof Student)?12:10);
                setup();
                setFilterCondition(condition);
                switch(condition){
                    case -1: break;
                    case 1: this.currentCampList.sort(Comparator.comparing(Camp::getDate));break;
                    case 2: this.currentCampList.sort(Comparator.comparing(Camp::getDate).reversed());break;
                    case 3: this.currentCampList.sort(Comparator.comparing(Camp::getLocation));break;
                    case 4: this.currentCampList.sort(Comparator.comparing(Camp::getCampCommitteeSlots));break;
                    case 5: this.currentCampList.sort(Comparator.comparing(Camp::getTotalSlots));break;
                    case 6: this.currentCampList.sort(Comparator.comparing(Camp::getRegistrationClosingDate));break;
                    case 7: this.currentCampList.sort(Comparator.comparing(Camp::getRegistrationClosingDate).reversed());break;
                    case 8: this.currentCampList.removeIf((Camp c)->!c.getUserGroup().equalsIgnoreCase(this.vcc.getCurrentUser().getFaculty()));break;
                    case 9: this.currentCampList.removeIf((Camp c)->!c.getUserGroup().equalsIgnoreCase("all"));break;
                    case 10: this.currentCampList.sort(Comparator.comparing((Camp x) -> x.getCampName().toLowerCase()));break;
                    case 11:this.currentCampList.removeIf((Camp c)->!c.getAttendees().isAttendee((Student) this.vcc.getCurrentUser()));break;
                    case 12:this.currentCampList.removeIf((Camp c)->!DatabaseUtils.checkIfStudentIsCampCommitteeMember(this.vcc.getCurrentUser().getUserID(), c.getCampID()));break;
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
        PageUtils.printTitle("All Camps (ordered by "+this.filterCondition+")");
        if(getCampList().size()==0){
            PageUtils.printTitle("There are currently no camps");
        }else{
            for(int h=0;h<getCampList().size();h++){
                if(this.filterCondition.endsWith("closing date")){
                    PageUtils.printRow(h,getCampList().get(h).getCampName(),getCampList().get(h).getRegistrationClosingDate().toString());
                }else if(this.filterCondition.endsWith("date")){
                    PageUtils.printRow(h,getCampList().get(h).getCampName(),getCampList().get(h).getDate().toString());
                }else if(this.filterCondition.contains("location")){
                    PageUtils.printRow(h,getCampList().get(h).getCampName(),getCampList().get(h).getLocation());
                }else if(this.filterCondition.contains("camp committee slots")){
                    PageUtils.printRow(h,getCampList().get(h).getCampName(),String.valueOf(getCampList().get(h).getCampCommitteeSlots()));
                }else if(this.filterCondition.contains("attendee slots")){
                    PageUtils.printRow(h,getCampList().get(h).getCampName(),String.valueOf(getCampList().get(h).getTotalSlots()));
                }else if(this.filterCondition.contains("for everyone")){
                    PageUtils.printRow(h,getCampList().get(h).getCampName(),getCampList().get(h).getUserGroup());
                }else if(this.filterCondition.contains("my faculty")){
                    PageUtils.printRow(h,getCampList().get(h).getCampName(),getCampList().get(h).getUserGroup());
                }else if(this.filterCondition.contains("user is attendee")){
                    PageUtils.printRow(h,getCampList().get(h).getCampName(),getCampList().get(h).getUserGroup());
                }else if(this.filterCondition.contains("user is ccm")){
                    PageUtils.printRow(h,getCampList().get(h).getCampName(),getCampList().get(h).getUserGroup());
                }else{
                PageUtils.printRow(h,getCampList().get(h).getCampName(),getCampList().get(h).getUserGroup());
                }
            }
        }
        PageUtils.printActionBox(actions);
        int choice = InputUtils.tryGetIntSelection(this.actions);
        handleInput(choice);
    }
}
