import model.*;
import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {

    Project project;
    Project project2;

    @BeforeEach
    private void runBefore() {
        try {
            project = new Project("Description");
            project2 = new Project("Description");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not have thrown");
        }
    }

    @Test
    public void testConstructor() {
        assertEquals("Description", project.getDescription());
        assertEquals(0, project.getNumberOfTasks());
        assertEquals(project, project2);
        assertTrue(project.equals(project2));
        String notAProject = "String";
        assertFalse(project.equals(notAProject));
        assertTrue(project.equals(project));
        assertEquals(project.hashCode(), project.hashCode());
    }

    @Test
    public void testConstructorNull() {
        try {
            Project project2 = new Project(null);
            fail("EmptyStringException should have thrown");
        } catch (Exception e) {
            // expected
        }
    }

    @Test
    public void testConstructorEmptyString() {
        try {
            Project project2 = new Project("");
            fail("EmptyStringException should have thrown");
        } catch (Exception e) {
            // expected
        }
    }

    @Test
    public void testAddTask() {
        try {
            Task task1 = new Task("task1");
            Task task2 = new Task("task2");
            project.add(task1);
            assertTrue(project.contains(task1));
            project.add(task2);
            assertTrue(project.contains(task1));
            assertTrue(project.contains(task2));
            assertEquals(2, project.getNumberOfTasks());
            project.add(task1);
            assertTrue(project.contains(task1));
            assertTrue(project.contains(task2));
            assertEquals(2, project.getNumberOfTasks());
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not have thrown");
        }
    }

    @Test
    public void testAddTaskNull() {
        try {
            Task task1 = new Task("task1");
            Task task2 = new Task("task2");
            project.add(null);
            fail("NullArgumentExcepton should have thrown");
            project.add(task2);
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void testAddTaskNullSecondTask() {
        try {
            Task task1 = new Task("task1");
            Task task2 = new Task("task2");
            project.add(task1);
            project.add(null);
            fail("NullArgumentException should have thrown");
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void testRemoveTask() {
        try {
            Task task1 = new Task("task1");
            Task task2 = new Task("task2");
            project.add(task1);
            project.add(task2);
            assertEquals(2, project.getNumberOfTasks());
            project.remove(task2);
            assertTrue(project.contains(task1));
            assertFalse(project.contains(task2));
            assertEquals(1, project.getNumberOfTasks());
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not have thrown");
        }
    }

    @Test
    public void testRemoveTaskNull() {
        try {
            Task task1 = new Task("task1");
            Task task2 = new Task("task2");
            project.add(task1);
            project.add(task2);
            project.remove(null);

            fail("NullArgumentException should have thrown");
        } catch (NullArgumentException e) {
            // expected
        }
    }

//    @Test
//    public void testGetProgress() {
//        assertEquals(100, project.getProgress());
//        Task task1 = new Task("task1");
//        Task task2 = new Task("task2");
//        project.add(task1);
//        project.add(task2);
//        assertEquals(0, project.getProgress());
//        task1.setStatus(Status.DONE);
//        assertEquals(50, project.getProgress());
//        task2.setStatus(Status.DONE);
//        assertEquals(100, project.getProgress());
//    }

//    @Test
//    public void testIsCompleted() {
//        assertFalse(project.isCompleted());
//        Task task1 = new Task("task1");
//        Task task2 = new Task("task2");
//        project.add(task1);
//        project.add(task2);
//        assertFalse(project.isCompleted());
//        task1.setStatus(Status.DONE);
//        assertFalse(project.isCompleted());
//        task2.setStatus(Status.DONE);
//        assertTrue(project.isCompleted());
//    }

    @Test
    public void testContains() {
        try {
            Task task1 = new Task("task1");
            Task task2 = new Task("task2");
            project.add(task1);
            project.add(task2);
            assertTrue(project.contains(task1));
            assertTrue(project.contains(task2));
            assertFalse(project.contains(new Task("task3")));
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not have thrown");
        }
    }

    @Test
    public void testContainsNull() {
        try {
            Task task1 = new Task("task1");
            Task task2 = new Task("task2");
            project.add(task1);
            project.add(task2);
            project.contains(null);
            fail("NullArgumentException should have thrown");
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void testGetEstimatedTime() {
        Task task1 = new Task("Task1");
        Task task2 = new Task("Task2");
        Task task3 = new Task("Task3");
        project.add(task1);
        project.add(task2);
        project.add(task3);
        assertEquals(0, project.getEstimatedTimeToComplete());
        task1.setEstimatedTimeToComplete(8);
        assertEquals(8, project.getEstimatedTimeToComplete());
        task2.setEstimatedTimeToComplete(2);
        task3.setEstimatedTimeToComplete(10);
        assertEquals(20, project.getEstimatedTimeToComplete());
        Task task4 = new Task("Task4");
        project2.add(task4);
        task4.setEstimatedTimeToComplete(5);
        project.add(project2);
        assertEquals(5, project2.getEstimatedTimeToComplete());
        assertEquals(25, project.getEstimatedTimeToComplete());
    }

    @Test
    public void testGetProgress() {
        Task task1 = new Task("Task1");
        Task task2 = new Task("Task2");
        Task task3 = new Task("Task3");
        project.add(task1);
        project.add(task2);
        project.add(task3);
        assertEquals(0, project.getProgress());
        task1.setProgress(100);
        assertEquals(33, project.getProgress());
        task2.setProgress(50);
        task3.setProgress(25);
        assertEquals(58, project.getProgress());
        Task task4 = new Task("Task4");
        project2.add(task4);
        project2.add(project);
        assertEquals(29, project2.getProgress());
    }

    @Test
    public void testGetTasksDepreciated() {
        Task t1 = new Task("Task1");
        project.add(t1);
        try {
            project.getTasks();
            fail("Should have returned UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            //expected
        }
    }

    @Test
    public void testIsComplete() {
        Task t1 = new Task("Task1");
        t1.setProgress(100);
        project.add(t1);
        assertTrue(project.isCompleted());
        Task t2 = new Task("Task2");
        t2.setProgress(50);
        project2.add(t2);
        project.add(project2);
        assertFalse(project.isCompleted());
        t2.setProgress(100);
        assertTrue(project.isCompleted());
    }

    @Test
    public void testIterator() {
        Task task1 = new Task("Task 1");
        Task task2 = new Task("Task 2");
        Task task3 = new Task("Task 3");
        task1.setPriority(new Priority(1));
        task2.setPriority(new Priority(3));
        task3.setPriority(new Priority(1));
        project.add(task1);
        project.add(task2);
        project.add(task3);
        for (Todo t : project) {
            System.out.println(t.getDescription());
        }
    }

    @Test
    public void testIterator2() {
        Task task1 = new Task("Task 1");
        Task task2 = new Task("Task 2");
        Task task3 = new Task("Task 3");
        task1.setPriority(new Priority(2));
        task2.setPriority(new Priority(3));
        task3.setPriority(new Priority(1));
        project.add(task1);
        project.add(task2);
        project.add(task3);
        for (Todo t : project) {
            System.out.println(t.getDescription());
        }
    }
}