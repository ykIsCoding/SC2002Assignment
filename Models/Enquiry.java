package Models;

import java.security.Timestamp;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class Enquiry {
    private int createdBy;
    private String content;
    private String title;
    private String enquiryID;
    private EnquiryList el;
    private String timeStamp;
    private EnquiryResponseList erl;
    
    public Enquiry(EnquiryList el, String content, String title, EnquiryResponseList erl){
        this.el = el;
        this.erl = erl; 
        this.content = content;
        this.title = title;
        this.enquiryID = UUID.randomUUID().toString();
        this.timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    }

    //Get method for content,title and timestamp respectively
    public String getContent(){return this.content;}
    public String getTitle(){return this.title;}
    public String getTimestamp(){return this.timeStamp;}

    //Get method for the enquiry's enquiryID
    public String getEnquiryID(){
        return this.enquiryID;
    }

    //Editing enquiry
    public void edit(String newTitle, String newContent){
        this.title = newTitle;
        this.content = newContent;
    } 
}
