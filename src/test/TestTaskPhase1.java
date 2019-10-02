import model.DueDate;
import model.Priority;
import model.Task;
import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static model.Status.DONE;
import static model.Status.TODO;
import static org.junit.jupiter.api.Assertions.*;

public class TestTaskPhase1 {

    private Task task;
    private Task task2;

    @BeforeEach
    public void runBefore() {
        try {
            task = new Task("This is the description");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not have thrown");
        }
    }

    @Test
    public void testConstructor() {
        assertEquals("This is the description", task.getDescription());
        assertEquals(TODO, task.getStatus());
        assertFalse(task.getPriority().isUrgent());
        assertFalse(task.getPriority().isImportant());
        assertTrue(task.getTags().isEmpty());
        assertEquals(null, task.getDueDate());
    }

    @Test
    public void testConstructorWithParsedDescription() {
        Task task2 = new Task("Description##todo;urgent;");
        assertEquals("Description", task2.getDescription());
        assertEquals(TODO, task2.getStatus());
        assertTrue(task2.getPriority().isUrgent());
        assertFalse(task2.getPriority().isImportant());
        assertNull(task2.getDueDate());


    }

    @Test
    public void testOverrideEqualsAndHashCode() {
        String notATask = "not a task";
        assertFalse(task.equals(notATask));
        Task task2 = new Task("Task2##urgent;DONE;");
        Task sameTask = new Task("This is the description");
        assertTrue(task.equals(sameTask));
        assertFalse(task.equals(task2));

    }

    @Test
    public void testConstructorNull() {
        try {
            String nullString = null;
            task2 = new Task(nullString);
            fail("EmptyStringException should have thrown");
        } catch (EmptyStringException e) {
            // expected
        }

    }

    @Test
    public void testConstructorEmpty() {
        try {
            task2 = new Task("");
            fail("EmptyStringException should have thrown");
        } catch (EmptyStringException e) {
            // expected
        }

    }

    @Test
    public void testAddTag() {
        try {
            task.addTag("tag1");
            task.addTag("tag2");
            task.addTag("tag3");
            assertTrue(task.containsTag("tag1"));
            assertTrue(task.containsTag("tag2"));
            assertTrue(task.containsTag("tag3"));
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testAddTagNull() {
        try {
            task.addTag("tag1");
            String nullString = null;
            task.addTag(nullString);
            fail("EmptyStringException should have thrown");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testAddTagEmpty() {
        try {
            task.addTag("tag1");
            task.addTag("");
            fail("EmptyStringException should have thrown");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testAddTagTagInSet() {
        try {
            task.addTag("tag1");
            task.addTag("tag2");
            task.addTag("tag1");
            task.addTag("tag2");
            assertTrue(task.containsTag("tag1"));
            assertTrue(task.containsTag("tag2"));
            assertEquals(2, task.getTags().size());
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not have thrown");
        }
    }

    @Test
    public void testRemoveTag() {
        task.addTag("tag1");
        task.addTag("tag2");
        assertEquals(2, task.getTags().size());
        try {
            task.removeTag("tag2");
            assertFalse(task.containsTag("tag2"));
            assertTrue(task.containsTag("tag1"));
            assertEquals(1, task.getTags().size());
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not have thrown");
        }
    }

    @Test
    public void testRemoveTagNull() {
        task.addTag("tag1");
        task.addTag("tag2");
        try {
            String nullString = null;
            task.removeTag(nullString);
            fail("EmptyStringException should have thrown");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testRemoveTagEmpty() {
        task.addTag("tag1");
        task.addTag("tag2");
        try {
            task.removeTag("");
            fail("EmptyStringException should have thrown");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testRemoveTagTagNotInSet() {
        task.addTag("tag1");
        task.addTag("tag2");
        assertEquals(2, task.getTags().size());
        task.removeTag("tag3");
        assertEquals(2, task.getTags().size());
    }

    @Test
    public void testSetPriority() {
        try {
            assertFalse(task.getPriority().isImportant());
            assertFalse(task.getPriority().isUrgent());
            Priority p = new Priority(1);
            task.setPriority(p);
            assertTrue(task.getPriority().isImportant());
            assertTrue(task.getPriority().isUrgent());
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not have thrown");
        }
    }

    @Test
    public void testSetPriorityNull() {
        try {
            Priority p = new Priority(1);
            task.setPriority(p);
            task.setPriority(null);
            fail("NullArgumentException should have thrown");
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void testSetStatus() {
        try {
            assertEquals(TODO, task.getStatus());
            task.setStatus(DONE);
            assertEquals(DONE, task.getStatus());
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not have thrown");
        }
    }

    @Test
    public void testSetStatusNull() {
        try {
            task.setStatus(DONE);
            task.setStatus(null);
            fail("NullArgumentException should have thrown");
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void TestSetDescription() {
        try {
            assertEquals("This is the description", task.getDescription());
            task.setDescription("New description now");
            assertEquals("New description now", task.getDescription());
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not have thrown");
        }
    }

    @Test
    public void TestSetDescriptionNull() {
        try {
            task.setDescription(null);
            fail("EmptyStringException should have thrown");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void TestSetDescriptionEmpty() {
        try {
            task.setDescription("");
            fail("EmptyStringException should have thrown");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testSetDueDate() {
        assertNull(task.getDueDate());
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 0, 10, 10, 10, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        DueDate newDate = new DueDate(calendar.getTime());
        task.setDueDate(newDate);
        Calendar calendarNumTwo = Calendar.getInstance();
        calendarNumTwo.setTime(task.getDueDate().getDate());
        calendarNumTwo.set(Calendar.MILLISECOND, 0);
        assertEquals(calendarNumTwo.getTime(), calendar.getTime());
    }

    @Test
    public void testToString() {
        Calendar calendar = Calendar.getInstance();
        DueDate newDate = new DueDate(calendar.getTime());
        task.setDueDate(newDate);

        String expected = "\n{\n\tDescription: This is the description\n\tDue date: "
                + task.getDueDate().toString() + "\n\tStatus: TODO\n\tPriority: DEFAULT\n\tTags:  \n}";
        assertEquals(expected, task.toString());

        task.addTag("tag1");
        expected = "\n{\n\tDescription: This is the description\n\tDue date: "
                + task.getDueDate().toString() + "\n\tStatus: TODO\n\tPriority: DEFAULT\n\tTags: #tag1\n}";
        assertEquals(expected, task.toString());

        task.addTag("tag2");
        String tagStr = task.getTags().toString();
        tagStr = tagStr.substring(1, tagStr.length() - 1);
        expected = "\n{\n\tDescription: This is the description\n\tDue date: "
                + task.getDueDate().toString() + "\n\tStatus: TODO\n\tPriority: DEFAULT\n\tTags: "
                + tagStr + "\n}";
        assertEquals(expected, task.toString());
    }
}