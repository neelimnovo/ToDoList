package model;

public class GeneralReminder extends Reminder {

    //MODIFIES: this
    //EFFECTS: creates a new GeneralReminder with given name and undone status
    public GeneralReminder(String name) {
        super(name);
    }

    //MODIFIES: this
    //EFFECTS: changes status of general reminder to done
    @Override
    public void doEvent() {
        this.status = "done";
    }


}
