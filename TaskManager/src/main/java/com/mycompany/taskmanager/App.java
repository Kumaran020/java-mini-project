package com.mycompany.taskmanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Comparator;

public class App extends Application {

    // 🔹 Task class
    static class Task {
        String name;
        String priority;

        public Task(String name, String priority) {
            this.name = name;
            this.priority = priority;
        }

        public int getPriorityValue() {
            switch (priority) {
                case "High": return 1;
                case "Medium": return 2;
                default: return 3;
            }
        }

        @Override
        public String toString() {
            return name + " [" + priority + "]";
        }
    }

    ObservableList<Task> taskList = FXCollections.observableArrayList();
    ListView<Task> listView = new ListView<>(taskList);

    @Override
    public void start(Stage stage) {

        // 🔹 Title
        Label title = new Label("Smart To-Do Task Manager");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // 🔹 Input
        TextField taskField = new TextField();
        taskField.setPromptText("Enter Task");

        ComboBox<String> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("High", "Medium", "Low");
        priorityBox.setPromptText("Select Priority");

        Button addBtn = new Button("Add Task");

        // 🔹 Add Task Logic
        addBtn.setOnAction(e -> {
            String taskName = taskField.getText();
            String priority = priorityBox.getValue();

            if (!taskName.isEmpty() && priority != null) {
                taskList.add(new Task(taskName, priority));

                // 🔥 Sort by priority automatically
                FXCollections.sort(taskList, Comparator.comparingInt(Task::getPriorityValue));

                taskField.clear();
                priorityBox.setValue(null);
            }
        });

        // 🔹 Delete Task
        Button deleteBtn = new Button("Delete Selected Task");
        deleteBtn.setOnAction(e -> {
            Task selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                taskList.remove(selected);
            }
        });

        // 🔹 Layout
        VBox inputBox = new VBox(10,
                new Label("Add Task"),
                taskField,
                priorityBox,
                addBtn,
                deleteBtn
        );

        inputBox.setPadding(new Insets(10));
        inputBox.setStyle("-fx-border-color: black;");

        VBox listBox = new VBox(10,
                new Label("Task List (Auto Sorted by Priority)"),
                listView
        );

        listBox.setPadding(new Insets(10));
        listBox.setStyle("-fx-border-color: black;");

        HBox mainLayout = new HBox(20, inputBox, listBox);
        mainLayout.setPadding(new Insets(10));

        VBox root = new VBox(10, title, mainLayout);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Task Manager");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}