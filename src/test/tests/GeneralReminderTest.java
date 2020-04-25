package tests;

import model.GeneralReminder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class GeneralReminderTest {

    private GeneralReminder testGeneralReminder;
    private String testReminderName;


    @BeforeEach
    public void setup() {
        testReminderName = "Do work";
        testGeneralReminder = new GeneralReminder(testReminderName);

    }

    @Test
    public void testReminderConstructor() {
        assertEquals(testGeneralReminder.getName(), "Do work");
        assertEquals(testGeneralReminder.getStatus(), "not done");

    }

    @Test
    public void testReminderModification() {
        // test name change
        testGeneralReminder.setName("Do more work");
        assertEquals(testGeneralReminder.getName(), "Do more work");

        //test status change
        testGeneralReminder.doEvent();
        assertEquals(testGeneralReminder.getStatus(), "done");
        testGeneralReminder.undoEvent();
        assertEquals(testGeneralReminder.getStatus(), "not done");
    }

    @Test
    public void testEquals() {
        testGeneralReminder.hashCode();
        assertTrue(testGeneralReminder.equals(new GeneralReminder("Do work")));
        assertFalse(testGeneralReminder.equals(new String("Do work")));
    }


}
