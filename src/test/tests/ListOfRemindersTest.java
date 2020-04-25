package tests;

import model.GeneralReminder;
import model.ListOfReminders;
import model.Reminder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ListOfRemindersTest {

    private ListOfReminders testClass;
    private ListOfReminders testClass2;


    @BeforeEach
    public void setup() {
        testClass = new ListOfReminders();

        testClass.addReminder("TestR1");
        testClass.addReminder("TestR2");
        testClass.addReminder("TestR3");

    }

    //add test for add and get reminder

    @Test
    public void testAddReminders() {
        assertEquals(testClass.getReminder(0).getName(),"TestR1");
        assertEquals(testClass.getReminder(1).getName(),"TestR2");
        assertEquals(testClass.getReminder(2).getName(),"TestR3");

        testClass2 = new ListOfReminders();
        testClass2.addReminder("TestR4");
        assertEquals(testClass2.getReminder(0).getName(), "TestR4");

        testClass2.addReminder(new GeneralReminder("testString"));
        assertEquals(testClass2.getReminder(1).getName(), "testString");
    }



    @Test
    public void testLoad() throws IOException {
        String remindersSoFar = "";
        testClass.load("data/RemindersTest2.json");
        for (Reminder r : testClass.generalReminders) {
            remindersSoFar = remindersSoFar + r.getName();
        }
        assertEquals(remindersSoFar, "TestR1TestR2TestR3TestR4TestR5");
    }


    @Test
    public void testSave() throws IOException {
        testClass.save("data/RemindersTest1.json");
        File file = new File("C:\\Users\\User\\IdeaProjects\\project_neelim5\\data\\RemindersTest1.json");
        file.exists();
    }




}

