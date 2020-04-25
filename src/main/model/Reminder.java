package model;

import java.util.Objects;

public abstract class Reminder {
    protected String name;
    protected String status;


    public Reminder(String name) {
        this.name = name;
        this.status = "not done";
    }



    //EFFECTS: gets name of reminder
    public String getName() {
        return this.name;
    }

    //EFFECTS: gets status
    public String getStatus() {
        return this.status;
    }

    //MODIFIES: this
    //EFFECTS: sets name of reminder
    public void setName(String name) {
        this.name = name;
    }

    //MODIFIES: this
    //EFFECTS: sets status to not done
    public void undoEvent() {
        this.status = "not done";
    }



    public abstract void doEvent();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reminder)) {
            return false;
        }
        Reminder reminder = (Reminder) o;
        return Objects.equals(name, reminder.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
