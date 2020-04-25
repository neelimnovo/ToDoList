package model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ListOfImportantReminders extends ListOfReminders {


    public ArrayList<ImportantReminder> importantReminders;

    public ListOfImportantReminders() {
        importantReminders = new ArrayList<>();
    }



    //MODIFIES: this
    //EFFECTS: adds given reminder to the list
    public void addReminder(ImportantReminder r) {
        if (importantReminders.contains(r)) {
            importantReminders.get(importantReminders.indexOf(r)).doEvent();
        } else {
            importantReminders.add(r);
        }
    }


    @Override
    //REQUIRES: a valid position within the list
    //EFFECTS: returns the important reminder at given position

    public Reminder getReminder(int position) {
        return importantReminders.get(position);
    }


    //EFFECTS: saves reminder list in json format to given filename
    @Override
    public void save(String fileName) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(importantReminders);
        FileWriter writer = new FileWriter(fileName);
        writer.write(json);
        writer.close();
    }


    @Override
    //MODIFIES: this
    //EFFECTS: reads .json file and stores it in the importantReminders array
    //Referenced from TA Repo jmlu99
    public void load(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader fr = new FileReader(fileName);
        JsonReader reader = new JsonReader(fr);
        ImportantReminder[] localImportantReminders = gson.fromJson(reader, ImportantReminder[].class);
        for (ImportantReminder r : localImportantReminders) {
            addReminder(r);
        }
    }




    //MODIFIES: this
    //EFFECTS: Sorts the list from high to low priority
    public void sortPriorityReminders() {
        ArrayList<ImportantReminder> sortedImportantReminders = new ArrayList<>();
        sortHelper(sortedImportantReminders, "high");
        sortHelper(sortedImportantReminders, "medium");

        sortHelper(sortedImportantReminders, "low");
        sortHelper(sortedImportantReminders, "none");
        importantReminders = sortedImportantReminders;
    }


    public void sortHelper(ArrayList<ImportantReminder> listParam, String urgency) {
        for (ImportantReminder i : importantReminders) {
            if (i.getUrgency().equals(urgency)) {
                listParam.add(i);
            }
        }
    }


}
