package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
    private String description;
    private List<Todo> tasks;

    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        this.description = description;
        tasks = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (!contains(task)) {
            tasks.add(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (contains(task)) {
            tasks.remove(task);
        }
    }

    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }

    @Override
    public int getEstimatedTimeToComplete() {
        int toReturn;
        toReturn = 0;
        for (Todo t : tasks) {
            toReturn += t.getEstimatedTimeToComplete();
        }
        return toReturn;
    }

    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completed tasks (rounded down to the closest integer).
    //     returns 100 if this project has no tasks!
    public int getProgress() {
        int numerator = 0;
        int denominator = 0;
        for (Todo t : tasks) {
            denominator++;
            numerator += t.getProgress();
        }
        return numerator / denominator;
    }

    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
    //     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }

    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public Iterator<Todo> iterator() {
        return new PriorityIterator();
    }

    private class PriorityIterator implements Iterator<Todo> {
        private int total;
        private boolean doneImpAndUrg;
        private boolean doneImp;
        private boolean doneUrg;
        private boolean doneNotImpOrUrg;

        public PriorityIterator() {
            total = 0;
            doneImpAndUrg = false;
            doneImp = false;
            doneUrg = false;
            doneNotImpOrUrg = false;
        }

        @Override
        public boolean hasNext() {
            return total < tasks.size();
        }

        @Override
        public Todo next() throws NoSuchElementException {
            if (hasNext()) {
                if (!doneImpAndUrg) {
                    Todo t = getNextItem(1);
                    if (t != null) {
                        return t;
                    }
                }
                if (!doneImp) {
                    Todo t = getNextItem(2);
                    if (t != null) {
                        return t;
                    }
                }
                return otherHalf();
            }
            throw new NoSuchElementException();
        }

        private Todo otherHalf() {
            if (hasNext()) {
                if (!doneUrg) {
                    Todo t = getNextItem(3);
                    if (t != null) {
                        return t;
                    }
                }
                if (!doneNotImpOrUrg) {
                    Todo t = getNextItem(4);
                    if (t != null) {
                        return t;
                    }
                }
            }
            throw new NoSuchElementException();
        }

        private Todo getNextItem(int n) {
            for (int i = 0; i < tasks.size(); i++) {
                if (priorityToInt(tasks.get(i)) == n) {
                    total += 1;
                    return tasks.get(i);
                }
            }
            flipBooleans(n);
            return null;
        }

        private int priorityToInt(Todo t) {
            boolean isUrgent = t.priority.isUrgent();
            boolean isImportant = t.priority.isImportant();
            if (isImportant && isUrgent) {
                return 1;
            } else if (isImportant) {
                return 2;
            } else if (isUrgent) {
                return 3;
            } else {
                return 4;
            }
        }

        private void flipBooleans(int n) {
            if (n == 1) {
                doneImpAndUrg = true;
            } else if (n == 2) {
                doneImp = true;
            } else if (n == 3) {
                doneUrg = true;
            } else {
                doneNotImpOrUrg = true;
            }
        }

    }

}