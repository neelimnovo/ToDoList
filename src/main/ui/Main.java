package ui;


import model.ImportantReminder;
import model.ListOfImportantReminders;
import model.ListOfReminders;
import model.NestedReminderMap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;


public class Main {

    private static Scanner input = new Scanner(System.in);
    private static ListOfReminders remindersList = new ListOfReminders();
    private static ListOfImportantReminders importantRemindersList = new ListOfImportantReminders();
    private static NestedReminderMap nestedReminders;


    private static final String SAVE_ERROR_MESSAGE = "Error in saving file, data may be lost on closing.";
    private static final String SAVEFILE_GENERAL = "GeneralReminders.json";
    private static final String SAVEFILE_IMPORTANT = "ImportantReminders.json";
    private static final String SAVEFILE_SHOPPINGLIST = "ShoppingList.json";
    private static final String SAVEFILE_NESTED = "NestedReminders.json";
    private static final String PROMPT_MESSAGE = "\nIs there anything else you would like to do?";



    public static void main(String[] args) {

        //Save file loading
        try {
            remindersList.load(SAVEFILE_GENERAL);
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found, old data may be lost");
        }
        try {
            importantRemindersList.load(SAVEFILE_IMPORTANT);
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found, old data may be lost");
        } finally {
            System.out.println("Welcome to the ListKeeper!");
            System.out.println("What would you like to do today?");
            showMenuMessages();
            initiateChoice();
        }
    }


    //main menu choices
    private static void initiateChoice() {
        String entry = input.nextLine();

        if (entry.equals("A")) {
            addReminderChoice();
            rePrompt(PROMPT_MESSAGE);

        } else if (entry.equals("B")) {
            tickOffChoice();
            rePrompt(PROMPT_MESSAGE);

        } else if (entry.equals("C")) {
            //shoppingListChoice();
            rePrompt(PROMPT_MESSAGE);

        } else if (entry.equals("D")) {
            printListChoice();
            rePrompt(PROMPT_MESSAGE);

        } else if (entry.equals("E")) {
            closeAppChoice();

        } else {
            rePrompt("Invalid command, try again");
        }
    }






    //helper methods for main menu

    //A
    private static void addReminderChoice() {
        System.out.println("1)General, 2)Important Reminder or 3) Nested Reminder?");
        String nextEntry = input.nextLine();

        if (nextEntry.equals("1")) {
            enterGeneralReminder();

        } else if (nextEntry.equals("2")) {
            enterImportantReminder();

        } else if (nextEntry.equals("3")) {
            enterNestedReminder();

        } else {
            System.out.println("Wrong input try again");
        }
    }



    //A1
    private static void enterGeneralReminder() {
        System.out.print("Enter reminder:");
        String nextName = input.nextLine();


        // adds reminder to list
        remindersList.addReminder(nextName);
        System.out.println("\"" + nextName + "\"" + " has been added to the list");
        try {
            remindersList.save(SAVEFILE_GENERAL);
        } catch (IOException e) {
            System.out.println(SAVE_ERROR_MESSAGE);
        }
    }

    //A2
    private static void enterImportantReminder() {
        System.out.print("Enter important reminder:");
        String nextName = input.nextLine();

        System.out.println("Enter urgency (one of 'high', 'medium', or 'low'):");
        String nextUrgency = input.nextLine();

        ImportantReminder nextReminder = new ImportantReminder(nextName, nextUrgency);

        // adds reminder to list
        importantRemindersList.addReminder(nextReminder);
        System.out.println("\"" + nextName + "\"" + " has been added to the list");
        try {
            importantRemindersList.save(SAVEFILE_IMPORTANT);
        } catch (IOException e) {
            System.out.println(SAVE_ERROR_MESSAGE);
        }
    }

    //A3
    private static void enterNestedReminder() {
        System.out.print("Enter reminder header:");
        String nextName = input.nextLine();
        nestedReminders = new NestedReminderMap();
        nestedReminders.addReminderHeading(nextName);

        System.out.print("Add inner reminder:");
        String nextInnerName1 = input.nextLine();
        nestedReminders.addReminderSubs(nextName, nextInnerName1);
        System.out.println("\"" + nextInnerName1 + "\"" + " has been added to the list");

        addFurtherReminder(nextName);

    }

    //A4
    private static void addFurtherReminder(String nextName) {
        String nextCommand = "";
        while (!nextCommand.equals("n")) {
            System.out.println("Add another reminder? (y/n)");
            nextCommand = input.nextLine();
            if (nextCommand.equals("n")) {
                break;
            }
            System.out.print("Add inner reminder:");
            String nextInnerName2 = input.nextLine();
            nestedReminders.addReminderSubs(nextName, nextInnerName2);
            System.out.println("\"" + nextInnerName2 + "\"" + " has been added to the list");
        }
    }


    //B
    private static void tickOffChoice() {
        System.out.println("1)General or 2)Important Reminder?");
        String nextEntry = input.nextLine();

        if (nextEntry.equals("1")) {
            tickOffGeneralReminder();

        } else if (nextEntry.equals("2")) {
            tickOffImportantReminder();

            //TODO add tick off for nested reminders
        } else {
            System.out.println("Wrong input, try again");
        }
    }

    //B1
    private static void tickOffGeneralReminder() {
        System.out.print("Enter reminder number to tick-off: ");

        String nextItem = input.nextLine();
        remindersList.getReminder(parseInt(nextItem)).doEvent();
        try {
            remindersList.save(SAVEFILE_GENERAL);
        } catch (IOException e) {
            System.out.println(SAVE_ERROR_MESSAGE);
        }
    }

    //B2
    private static void tickOffImportantReminder() {
        System.out.print("Enter reminder number to tick-off: ");

        String nextItem = input.nextLine();
        importantRemindersList.getReminder(parseInt(nextItem)).doEvent();
        try {
            remindersList.save(SAVEFILE_IMPORTANT);
        } catch (IOException e) {
            System.out.println(SAVE_ERROR_MESSAGE);
        }

    }







    //D
    private static void printListChoice() {
        System.out.println("Show 1)General, 2)Important, 3) Shopping List or 4) Nested List?");
        String moreInput = input.nextLine();

        //Print general list
        if (moreInput.equals("1")) {
            //remindersList.printAllReminders();


            //print important list
        } else if (moreInput.equals("2")) {
            //importantRemindersList.printAllReminders();

            //wrong input
        } else if (moreInput.equals("3")) {
            //printShoppingList();

        } else if (moreInput.equals("4")) {
            System.out.println("Which nested reminder to print?");
            String key = input.nextLine();
            //nestedReminders.printNestedReminders(key);
        } else {
            System.out.println("Wrong input, try again");
        }
    }



    //E
    private static void closeAppChoice() {
        System.out.println("Thank you for using ListKeeper");
        System.exit(0);
    }




    private static void showMenuMessages() {
        System.out.println("A) Add a reminder");
        System.out.println("B) Tick-off a reminder");
        System.out.println("C) Manage a shopping list");
        System.out.println("D) Show all items");
        System.out.println("E) Exit application");
    }

    private static void rePrompt(String prompt) {
        System.out.println(prompt);
        showMenuMessages();
        initiateChoice();
    }

}


