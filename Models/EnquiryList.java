package Models;

import java.util.ArrayList;

public class EnquiryList {
    ArrayList<Enquiry> enquiryList = new ArrayList();

    public EnquiryList(){
        this.suggestionList.add(new Enquiry(this,"testing","enquiry 1"));
    }

    public ArrayList<Enquiry> getEnquiryList(){
        return this.enquiryList;
    }

    public void editEnquiry(String enquiryID, String content, ){

    }

    public Enquiry getEnquiry(String enquiryID){
        return this.enquiryList.get(enquiryID);
    }

    public void removeEnquiry(String enquiryID){
        for(int b=0;b<this.enquiryList.size();b++){
            if(this.enquiryList.get(b).getEnquiryID()==enquiryID){
                enquiryList.remove(this.enquiryList.get(b));
            }
        }
    }

    public void addEnquiry(Enquiry e){
        this.enquiryList.add(e);
    }
}