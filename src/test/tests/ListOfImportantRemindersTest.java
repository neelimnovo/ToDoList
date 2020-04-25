package tests;

import model.ImportantReminder;
import model.ListOfImportantReminders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ListOfImportantRemindersTest {

    private ListOfImportantReminders testClass;
    private String testFile3 = "data/RemindersTest3.json";

    private ImportantReminder testI1;
    private ImportantReminder testI2;

    private ImportantReminder testI3;
    private ImportantReminder testI4;

    private ImportantReminder testI5;
    private ImportantReminder testI6;

    private ImportantReminder testI7;
    private ImportantReminder testI8;


    @BeforeEach
    public void setup() {
        testClass = new ListOfImportantReminders();

        testI1 = new ImportantReminder("TestI1", "none");
        testI2 = new ImportantReminder("TestI2", "none");

        testI3 = new ImportantReminder("TestI3", "low");
        testI4 = new ImportantReminder("TestI4", "low");

        testI5 = new ImportantReminder("TestI5", "medium");
        testI6 = new ImportantReminder("TestI6", "medium");

        testI7 = new ImportantReminder("TestI7", "high");
        testI8 = new ImportantReminder("TestI8", "high");
    }

    @Test
    public void testAddAndGetReminderPosition() {
        testClass.addReminder(testI1);
        testClass.addReminder(testI2);
        assertEquals(testClass.getReminder(1), testI2);
        testClass.addReminder(testI3);
        testClass.addReminder(testI4);
        assertEquals(testClass.getReminder(3), testI4);
        testClass.addReminder(testI1);
        assertEquals(testClass.importantReminders.get(0).getStatus(), "done");
        assertEquals(testClass.importantReminders.get(0).getUrgency(), "none");


    }

    @Test
    public void testSave() throws IOException {
        testClass.addReminder(testI1);
        testClass.addReminder(testI2);
        testClass.save(testFile3);

        File file = new File("C:\\Users\\User\\IdeaProjects\\project_neelim5\\data\\RemindersTest3.json");
        file.exists();

    }

    @Test
    public void testLoad() throws FileNotFoundException {
            String remindersSoFar = "";
            testClass.load("data/RemindersTest3.json");
            for (ImportantReminder r : testClass.importantReminders) {
                remindersSoFar = remindersSoFar + r.getName();
        }
        assertEquals(remindersSoFar, "TestI1TestI2");
    }

    @Test
    public void testSaveFileNotFound() {
        try {
            testClass.load("Non-existent file 2");
            fail("Test failed");
        } catch (FileNotFoundException e) {
            //test passes
        }
    }

    @Test
    public void testSortReminders() {
        String remindersSoFar = "";

        testClass.addReminder(testI1);
        testClass.addReminder(testI2);
        testClass.addReminder(testI5);
        testClass.addReminder(testI3);
        testClass.addReminder(testI4);
        testClass.addReminder(testI7);
        testClass.addReminder(testI8);

        testClass.sortPriorityReminders();

        for (ImportantReminder r : testClass.importantReminders) {
            remindersSoFar = remindersSoFar + " " + r.getName();
        }
        assertEquals(remindersSoFar, " TestI7 TestI8 TestI5 TestI3 TestI4 TestI1 TestI2");
    }





}
