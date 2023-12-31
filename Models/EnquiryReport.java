package Models;

import java.time.LocalDate;
import java.util.ArrayList;

import Utils.DatabaseUtils;
import Utils.PageUtils;

/**
 * This class is a type of Report, the Enquiry Report that inherits from the Report class
 */
public class EnquiryReport extends Report {
    private final String campID;
    private static final String header = "ENQUIRY REPORT\n";
    private static final String tableHeader = "ROW NAME EMAIL ENQUIRY\n";
    
    private static final String csvtableHeader = "ROW,NAME,EMAIL,ENQUIRY\n";

    /**
     * The constructor of this Enquiry Report Class
     * @param campID is the ID of the camp
     */
    public EnquiryReport(String campID){
        super();
        this.campID =campID;
    }

    /**
     * THis method attempts to print out the report in a customised format.
     * @return the printed customised format
     */
    private String printCampName(){
        ArrayList<String []> x = DatabaseUtils.readCamps();
        for(int p=0;p<x.size();p++){
            if(x.get(p)[0].equals(campID)){
                String cn = "\n=======================================================\nCAMP DETAILS\nCamp Name: "+x.get(p)[1].toUpperCase()+"\nCamp Date:"+x.get(p)[2]+"\nCamp Registration Closing Date:"+x.get(p)[3]+"\nUser Group:"+x.get(p)[4]+"\nLocation:"+x.get(p)[5]+"\nTotal Slots:"+x.get(p)[6]+"\nCamp Committee Slots:"+x.get(p)[7]+"\nDescription:"+x.get(p)[8]+"\nStaff in Charge:"+DatabaseUtils.getUserByID(x.get(p)[9])[1]+"\n=======================================================\n";
                return cn;
            }
        }
        return "";
    }

    /**
     * Access the Database for the list of Camps
     * @return a string of the camp details
     */
    private String printCampNameCSV(){
        ArrayList<String []> x = DatabaseUtils.readCamps();
        for(int p=0;p<x.size();p++){
            if(x.get(p)[0].equals(campID)){
                String cn = x.get(p)[1]+","+x.get(p)[2]+","+x.get(p)[3]+","+x.get(p)[4]+","+x.get(p)[5]+","+x.get(p)[6]+","+x.get(p)[7]+","+x.get(p)[8]+","+DatabaseUtils.getUserByID(x.get(p)[9])[1]+"\n";
                return cn;
            }
        }
        return "";
    }

    /**
     * Method that overrides the GenerateReport in the Report Class
     * @param type is the number input by the user for Txt and Csv accordingly.
     * @return true all the time
     */
    public boolean generateReport(int type){
        ArrayList<String[]> x = DatabaseUtils.getEnquiriesByCampID(campID);
        
        String cnt ="";
        for(int b=0;b<x.size();b++){
            cnt+= b +" "+DatabaseUtils.getUserByID(x.get(b)[2])[1]+" "+DatabaseUtils.getUserByID(x.get(b)[2])[2]+" "+x.get(b)[3]+"\n";
        }
        String endLine = "=======================================================\nGenerated on:"+ PageUtils.localDateToFullLocalDateString(LocalDate.now());
        String allString = header+printCampName()+tableHeader+cnt+endLine;
        if(type==1){
            DatabaseUtils.exportFile("Exports/EnquiryReport_"+campID+".txt", allString);
        }else{
            cnt="";
            for(int b=0;b<x.size();b++){
            cnt+= b +","+DatabaseUtils.getUserByID(x.get(b)[2])[1]+","+DatabaseUtils.getUserByID(x.get(b)[2])[2]+","+x.get(b)[3]+"\n";
            }
            allString = header+printCampNameCSV()+csvtableHeader+cnt;
            DatabaseUtils.exportFile("Exports/EnquiryReport_"+campID+".csv", allString);
        }
        return true;
    }
}
