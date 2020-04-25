package tests;

import model.ImportantReminder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImportantReminderTest {

    private ImportantReminder testImportantReminder;
    private String testReminderName;


    @BeforeEach
    public void setup() {
        testReminderName = "Do important work";
        testImportantReminder = new ImportantReminder(testReminderName, "high");
    }

    @Test
    public void testGetters() {
        assertEquals(testImportantReminder.getName(), testReminderName);
        assertEquals(testImportantReminder.getUrgency(), "high");
        assertEquals(testImportantReminder.getStatus(), "not done");
    }

    @Test
    public void testSetters() {
        testImportantReminder.setUrgency("medium");
        testImportantReminder.setName("Do more important work");


        assertEquals(testImportantReminder.getUrgency(), "medium");
        assertEquals(testImportantReminder.getName(), "Do more important work");

        testImportantReminder.doEvent();
        assertEquals(testImportantReminder.getUrgency(), "none");
        assertEquals(testImportantReminder.getStatus(), "done");
    }



}
