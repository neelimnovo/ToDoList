package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NestedReminderMap {

    public static Map<String, ArrayList<GeneralReminder>> nestedReminderMap = new HashMap<>();


    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public void addReminderHeading(String heading) {
        nestedReminderMap.put(heading, new ArrayList<GeneralReminder>());
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public void addReminderSubs(String heading, String reminder) {
        ArrayList listSoFar = getReminderContents(heading);
        listSoFar.add(new GeneralReminder(reminder));

    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns the list of reminders for a given heading
    public ArrayList getReminderContents(String heading) {
        return nestedReminderMap.get(heading);
    }


    public void printNestedReminders(String key) {
        System.out.println(key + ":");
        for (GeneralReminder r : nestedReminderMap.get(key)) {
            System.out.println("            " + r.getName());
        }
    }

}

