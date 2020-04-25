package model;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class ListOfReminders implements Saveable, Loadable {
    public ArrayList<Reminder> generalReminders;


    // EFFECTS: constructs an empty list of clowns
    public ListOfReminders() {
        generalReminders = new ArrayList<>();

    }


    //MODIFIES: this
    //EFFECTS: creates a new reminder and adds it to the list
    public void addReminder(String item) {
        GeneralReminder r = new GeneralReminder(item);
        generalReminders.add(r);
    }

    //MODIFIES: this
    //EFFECTS: adds given reminder to the list
    public void addReminder(Reminder r) {
        generalReminders.add(r);
    }

    //REQUIRES: a valid position within the list
    //EFFECTS: returns the general reminder at given position
    // refactor position to use 1 based indexing and not zero
    public Reminder getReminder(int position) {
        return generalReminders.get(position);
    }



    //EFFECTS: saves reminder list in json format to given filename
    // may throw IOException according to FileWriter behaviours
    @Override
    public void save(String fileName) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(generalReminders);
        FileWriter writer = new FileWriter(fileName);
        writer.write(json);
        writer.close();
    }


    @Override
    //MODIFIES: this
    //EFFECTS: reads .json file and stores it in the generalReminders array
    //Referenced from TA Repo jmlu99
    public void load(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader fr = new FileReader(fileName);
        JsonReader reader = new JsonReader(fr);
        GeneralReminder[] localGeneralReminders = gson.fromJson(reader, GeneralReminder[].class);
        for (GeneralReminder r : localGeneralReminders) {
            addReminder(r);
        }
    }

}

