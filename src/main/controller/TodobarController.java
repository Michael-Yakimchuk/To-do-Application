package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.EditTask;
import ui.ListView;
import ui.PomoTodoApp;
import utility.JsonFileIO;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File todoOptionsPopUpFxmlFile = new File(todoOptionsPopUpFXML);
    private File todoActionsPopUpFxmlFile = new File(todoActionsPopUpFXML);

    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;

    private JFXPopup todoOptionsPopUp;
    private JFXPopup todoActionsPopUp;

    private Task task;

    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTodoActionsPopUp();
        loadTodoActionsPopUpActionListener();
        loadTodoOptionsPopUp();
        loadTodoOptionsPopUpActionListener();
    }

    // EFFECTS: load options pop up (Edit, Delete)
    private void loadTodoOptionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoOptionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodoOptionsPopUpController());
            todoOptionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // EFFECTS: load to do actions pop up (to do, up next, in progress, done, pomodoro!)
    private void loadTodoActionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoActionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodoActionsPopUpController());
            todoActionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: show view selector pop up when its icon is clicked
    private void loadTodoOptionsPopUpActionListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todoOptionsPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12,
                        15);
            }
        });
    }

    // EFFECTS: show options pop up when its icon is clicked
    private void loadTodoActionsPopUpActionListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todoActionsPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }

    // Inner class: view selector pop up controller
    class TodoOptionsPopUpController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("TodobarActionsPopUpController", "Edit is not implemented yet");
                    PomoTodoApp.setScene(new EditTask(task));
                    break;
                case 1:
                    Logger.log("TodobarActionsPopUpController", "Deleted task");
                    deleteTask();
                    break;
                default:
                    Logger.log("TodobarActionsPopUpController", "No action is implemented for the selected option");
            }
            todoOptionsPopUp.hide();
        }
    }

    private void deleteTask() {
        PomoTodoApp.getTasks().remove(task);
        JsonFileIO.write(PomoTodoApp.getTasks());
        PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));

    }

    // Inner class: option pop up controller
    class TodoActionsPopUpController {
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0: Logger.log("TodobarOptionsPopUpController", "todo is not implemented yet");
                    break;
                case 1: Logger.log("TodobarOptionsPopUpController", "up next is not implemented yet");
                    break;
                case 2: Logger.log("TodobarOptionsPopUpController", "in progress is not implemented yet");
                    break;
                case 3: Logger.log("TodobarOptionsPopUpController", "done is not implemented yet");
                    break;
                case 4: Logger.log("TodobarOptionsPopUpController", "pomodoro! is not implemented yet");
                    break;
                default: Logger.log("TodobarOptionsPopUpController", "No action is implemented");
            }
            todoActionsPopUp.hide();
        }
    }
}
