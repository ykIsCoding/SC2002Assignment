package Models;

import Controllers.Abstract.AUser;

import java.util.ArrayList;
import java.util.List;

public class Student extends AUser {
    private boolean campCommiteeMembership;
    private List<Camp> registeredCamps;
    
    public Student(String UserID, String Faculty) {
        super(UserID, "Student", Faculty);
        //Assume default Camp Commitee Membership is False.
        setCampCommiteeMembership(false);

        // Initialize the list of registered camps
        this.registeredCamps = new ArrayList<>();
    }

    public List<Camp> getRegisteredCamps() {
        return registeredCamps;
    }
    public void setCampCommiteeMembership(boolean campCommiteeMembership) {
        this.campCommiteeMembership = campCommiteeMembership;
    }
    public boolean isCampComiteeMembership() {
        return campCommiteeMembership;
    }


    public boolean registerForCamp(Camp camp) {
       // Check if the student is already registered for the camp
       if (registeredCamps.contains(camp)) {
        System.err.println("Error: Student is already registered for the camp.");
        return false;
        }

        // Check if there are date clashes with already registered camps
        for (Camp registeredCamp : registeredCamps) {
            if (registeredCamp.getDate() == camp.getDate()) {
                System.err.println("Error: Date clash with another registered camp.");
                return false;
            }
        }

        // Check if the camp is already full
        if (camp.getAttendees().size() >= camp.getTotalSlots()) {
            System.err.println("Error: Camp is already full. Cannot register attendee.");
            return false;
        }
        // Check if the registration deadline has passed
        if (camp.getDate() > camp.getRegistrationClosingDate()) {
            System.err.println("Error: Registration deadline has passed. Cannot register attendee.");
            return false;
        }

        // Attempt to add the attendee to the list
        registeredCamps.add(camp);
        camp.registerAttendee(this); 
        //System.out.println("Student registered for the camp: " + camp.getCampName());
        return true;
    }



    @Override
    public void changePassword() {

    }

}
