package model;

public class ImportantReminder extends Reminder {

    //is of 4 types: none, low, medium, high
    private String urgency;


    //EFFECTS: creates a new important reminder with given name and urgency
    public ImportantReminder(String name, String urgency) {
        super(name);
        this.urgency = urgency;

    }

    //EFFECTS: returns urgency of important reminder
    public String getUrgency() {
        return urgency;
    }

    //MODIFIES: this
    //EFFECTS: sets urgency of important reminder to given value
    public void setUrgency(String u) {
        this.urgency = u;
    }


    //MODIFIES: this
    //EFFECTS: sets status to done and urgency to none
    @Override
    public void doEvent() {
        this.status = "done";
        this.urgency = "none";
    }



}
