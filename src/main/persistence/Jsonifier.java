package persistence;


import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Year;
import java.util.Calendar;
import java.util.List;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagJson = new JSONObject();
        tagJson.put("name", tag.getName());
        return tagJson;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priorityJson = new JSONObject();

        priorityJson.put("important", priority.isImportant());
        priorityJson.put("urgent", priority.isUrgent());
        return priorityJson;
    }

    // EFFECTS: returns JSON representation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject dueDateJson = null;
        dueDateJson = new JSONObject();
        Calendar c = Calendar.getInstance();
        c.setTime(dueDate.getDate());
        dueDateJson.put("year", c.get(Calendar.YEAR));
        dueDateJson.put("month", c.get(Calendar.MONTH));
        dueDateJson.put("day", c.get(Calendar.DAY_OF_MONTH));
        dueDateJson.put("hour", c.get(Calendar.HOUR_OF_DAY));
        dueDateJson.put("minute", c.get(Calendar.MINUTE));
        return dueDateJson;

    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        JSONObject taskJson = new JSONObject();
        JSONArray tagsJson = new JSONArray();

        for (Tag t : task.getTags()) {
            tagsJson.put(tagToJson(t));
        }

        taskJson.put("description", task.getDescription());
        taskJson.put("tags", tagsJson);

        if (task.getDueDate() != null) {
            taskJson.put("due-date", dueDateToJson(task.getDueDate()));
        } else {
            taskJson.put("due-date", JSONObject.NULL);
        }
        taskJson.put("priority", priorityToJson(task.getPriority()));
        taskJson.put("status", task.getStatus());
        return taskJson;
    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray listOfTaskJson = new JSONArray();

        for (Task t : tasks) {
            listOfTaskJson.put(taskToJson(t));
        }

        return listOfTaskJson;
    }
}
