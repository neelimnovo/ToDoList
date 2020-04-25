package tests;

import model.NestedReminderMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NestedReminderMapTest {

    NestedReminderMap testMap;

    @BeforeEach
    public void setup() {
        testMap = new NestedReminderMap();
        testMap.addReminderHeading("Observer Pattern Classes:");
    }

    @Test
    public void test1() {
        testMap.addReminderSubs("Observer Pattern Classes:", "Observer");
        testMap.addReminderSubs("Observer Pattern Classes:", "Concrete Observer");
        testMap.addReminderSubs("Observer Pattern Classes:", "Subject");
        testMap.addReminderSubs("Observer Pattern Classes:", "Concrete Subject");
        testMap.getReminderContents("Observer Pattern Classes:");
    }

    @Test
    public void test2() {

    }

    @Test
    public void test3() {

    }

}