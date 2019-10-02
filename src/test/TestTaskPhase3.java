import model.Tag;
import model.Task;
import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestTaskPhase3 {
    private Task task;
    private Tag tag1;
    private Tag tag2;
    private Tag tag3;

    @BeforeEach
    public void runBefore() {
        task = new Task("Task 1");
        tag1 = new Tag("Tag 1");
        tag2 = new Tag("Tag 2");
        tag3 = new Tag("Tag 3");
    }

    @Test
    public void testTaskAddOneTag() {
        try {
            task.addTag(tag1);
            assertTrue(task.containsTag(tag1));
            assertTrue(task.containsTag("Tag 1"));
            assertTrue(task.containsTag(new Tag("Tag 1")));
            assertTrue(tag1.containsTask(task));
        } catch (NullArgumentException e) {
            fail("Should not have thrown NullArgumentException");
        }
    }

    @Test
    public void testTaskAddOneTagByString() {
        task.addTag("Tag 1");
        assertTrue(task.containsTag(tag1));
        assertTrue(task.containsTag("Tag 1"));
        assertTrue(task.containsTag(new Tag("Tag 1")));
    }

    @Test
    public void testTaskAddOneTagAlternate() {
        task.addTag(new Tag("Tag 3"));
        assertTrue(task.containsTag(tag3));
        assertTrue(task.containsTag("Tag 3"));
    }

    @Test
    public void testTaskAddMultipleTags() {
        task.addTag(tag1);
        task.addTag(tag2);
        assertTrue(task.containsTag(tag1));
        assertTrue(task.containsTag(tag2));
        assertTrue(task.containsTag("Tag 1"));
        assertTrue(task.containsTag("Tag 2"));
        assertTrue(task.containsTag(new Tag("Tag 1")));
        assertTrue(task.containsTag(new Tag("Tag 2")));
        assertTrue(tag1.containsTask(task));
        assertTrue(tag2.containsTask(task));
    }

    @Test
    public void testTaskAddMultipleTagsByString() {
        task.addTag("Tag 1");
        task.addTag("Tag 2");
        assertTrue(task.containsTag(tag1));
        assertTrue(task.containsTag(tag2));
        assertTrue(task.containsTag("Tag 1"));
        assertTrue(task.containsTag("Tag 2"));
        assertTrue(task.containsTag(new Tag("Tag 1")));
        assertTrue(task.containsTag(new Tag("Tag 2")));
    }

    @Test
    public void testTaskAddMultipleTagsAlternateWay() {
        task.addTag(new Tag("Tag 1"));
        task.addTag(new Tag("Tag 2"));
        assertTrue(task.containsTag(tag1));
        assertTrue(task.containsTag(tag2));
        assertTrue(task.containsTag("Tag 1"));
        assertTrue(task.containsTag("Tag 2"));
        assertTrue(task.containsTag(new Tag("Tag 1")));
        assertTrue(task.containsTag(new Tag("Tag 2")));
    }

    @Test
    public void testTaskAddNullValue() {
        Tag tagNull = null;
        try {
            task.addTag(tagNull);
            fail("Should have thrown a NullArgumentException");
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void testTaskContainsNull() {
        try {
            String nullString = null;
            task.containsTag(nullString);
            fail("Should have thrown EmptyStringException");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testRemoveOneTask() {
        task.addTag(tag1);
        task.addTag(tag2);
        assertTrue(task.containsTag(tag1));
        assertTrue(task.containsTag(tag2));
        try {
            task.removeTag(tag1);
            assertFalse(task.containsTag(tag1));
        } catch (Exception e) {
            fail("Should not have thrown NullArgumentException");
        }
    }

    @Test
    public void testRemoveOneTaskByString() {
        task.addTag(tag1);
        task.addTag(tag2);
        assertTrue(task.containsTag(tag1));
        assertTrue(task.containsTag(tag2));
        task.removeTag("Tag 1");
        assertFalse(task.containsTag(tag1));
    }

    @Test
    public void testRemoveOneTaskAlternateWay() {
        task.addTag(tag1);
        task.addTag(tag2);
        assertTrue(task.containsTag(tag1));
        assertTrue(task.containsTag(tag2));
        task.removeTag(new Tag("Tag 1"));
        assertFalse(task.containsTag(tag1));
        assertFalse(task.getTags().isEmpty());
    }

    @Test
    public void testRemoveMultipleTask() {
        task.addTag(tag1);
        task.addTag(tag2);
        assertTrue(task.containsTag(tag1));
        assertTrue(task.containsTag(tag2));
        task.removeTag(tag1);
        task.removeTag(tag2);
        assertFalse(task.containsTag(tag1));
        assertFalse(task.containsTag(tag2));
        assertTrue(task.getTags().isEmpty());
    }

    @Test
    public void testRemoveMultipleTaskByString() {
        task.addTag(tag1);
        task.addTag(tag2);
        assertTrue(task.containsTag(tag1));
        assertTrue(task.containsTag(tag2));
        task.removeTag("Tag 1");
        task.removeTag("Tag 2");
        assertFalse(task.containsTag(tag1));
        assertFalse(task.containsTag(tag2));
        assertTrue(task.getTags().isEmpty());
    }

    @Test
    public void testRemoveMultipleTaskAlternateWay() {
        task.addTag(tag1);
        task.addTag(tag2);
        assertTrue(task.containsTag(tag1));
        assertTrue(task.containsTag(tag2));
        task.removeTag(new Tag("Tag 1"));
        task.removeTag(new Tag("Tag 2"));
        assertFalse(task.containsTag(tag1));
        assertFalse(task.containsTag(tag2));
        assertTrue(task.getTags().isEmpty());
    }

    @Test
    public void testRemoveNullTask() {
        task.addTag(tag1);
        Tag tagNull = null;
        try {
            task.removeTag(tagNull);
            fail("Should have thrown NullArgumentException");
        } catch (NullArgumentException e) {
            // expected
        }

    }

    @Test
    public void testSetProgress() {
        task.setProgress(10);
        assertEquals(10, task.getProgress());
        task.setProgress(50);
        assertEquals(50, task.getProgress());
    }

    @Test
    public void testSetProgressException() {
        try {
            task.setProgress(101);
            fail("Should have thrown InvalidProgressException");
        } catch(InvalidProgressException e) {
            // expected
        }
    }

    @Test
    public void testSetEstimatedTimeToComplete() {
        task.setEstimatedTimeToComplete(5);
        assertEquals(5, task.getEstimatedTimeToComplete());
        task.setEstimatedTimeToComplete(2);
        assertEquals(2, task.getEstimatedTimeToComplete());
    }

    @Test
    public void testSetEstimatedTimeNegativeValueException() {
        try {
            task.setEstimatedTimeToComplete(-1);
            fail("Should have thrown NegativeInputException");
        } catch(NegativeInputException e) {
            // expected
        }
    }



}