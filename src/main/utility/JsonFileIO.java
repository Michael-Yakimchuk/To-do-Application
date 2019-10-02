package utility;

import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");


    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {
        String content = null;
        TaskParser parser = new TaskParser();
        List<Task> output;
        try {
            content = new Scanner(jsonDataFile).useDelimiter("\\Z").next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        output = parser.parse(content);
        return output;
    }

    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {
        JSONArray jsonTasks = Jsonifier.taskListToJson(tasks);
        String jsonString = jsonTasks.toString();
        try {
            FileWriter file = new FileWriter(jsonDataFile);
            file.write(jsonString);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
