package parsers;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// Represents Task parser
public class TaskParser {

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {
        List<Task> listOfTasks = new ArrayList<>();
        JSONArray taskArray = new JSONArray(input);

        for (Object object : taskArray) {
            try {
                JSONObject taskJson = (JSONObject) object;
                List<Tag> tags = jsonToListOfTags((JSONArray) taskJson.get("tags"));
                Task task = new Task(taskJson.getString("description"));
                task.setStatus(Status.valueOf((String) taskJson.get("status")));
                task.setDueDate(determineDueDate(taskJson));
                task.setPriority(jsonToPriority((JSONObject) taskJson.get("priority")));
                for (Tag t : tags) {
                    task.addTag(t);
                }
                listOfTasks.add(task);
            } catch (Exception e) {
                //
            }
        }

        return listOfTasks;
    }


    private DueDate determineDueDate(JSONObject obj) {
        if (!obj.get("due-date").equals(null)) {
            return jsonToDueDate((JSONObject) obj.get("due-date"));
        } else {
            return null;
        }
    }

    private DueDate jsonToDueDate(JSONObject j) {
        Calendar c = Calendar.getInstance();
        int year = j.getInt("year");
        int month = j.getInt("month");
        int day = j.getInt("day");
        int hour = j.getInt("hour");
        int min = j.getInt("minute");

        c.set(year, month, day, hour, min);
        DueDate date = new DueDate();
        date.setDueDate(c.getTime());
        return date;
    }

    private Priority jsonToPriority(JSONObject j) {
        boolean important = j.getBoolean("important");
        boolean urgent = j.getBoolean("urgent");
        int priorityPicker = 1;
        if (important && urgent) {
            priorityPicker = 1;
        } else if (important && !urgent) {
            priorityPicker = 2;
        } else if (!important && urgent) {
            priorityPicker = 3;
        } else if (!important && !urgent) {
            priorityPicker = 4;
        }
        Priority priority = new Priority(priorityPicker);
        return priority;
    }

    private List<Tag> jsonToListOfTags(JSONArray j) {
        List<Tag> listOfTags = new ArrayList<>();
        for (Object object : j) {
            JSONObject tag = (JSONObject) object;
            listOfTags.add(new Tag((String) tag.get("name")));
        }
        return listOfTags;
    }


}
