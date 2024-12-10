package org.sepp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUI extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    MenuBar menuBar = new MenuBar();

    // Created menus at the top of GUI
    Menu fileMenu = new Menu("File");
    Menu configMenu = new Menu("Config");
    Menu tasksMenu = new Menu("Tasks");
    Menu helpMenu = new Menu("Help");

    MenuItem about = new MenuItem("About");
    helpMenu.getItems().add(about);

    // Menu items for file Menu
    MenuItem run = new MenuItem("Run...");
    MenuItem directory = new MenuItem("Set directory");

    // Retrieves all file menu items into the fileMenu
    fileMenu.getItems().addAll(run, directory);

    // Menu items for configMenu
    MenuItem newConfig = new MenuItem("Create new config");
    Menu loadConfig = new Menu("Load config");
    MenuItem save = new MenuItem("Save");
    MenuItem saveAs = new MenuItem("Save As...");
    MenuItem close = new MenuItem("Close");
    MenuItem pref = new MenuItem("Preferences...");
    MenuItem quit = new MenuItem("Quit");

    // Retrieves all config menu items into the configMenu
    configMenu
        .getItems()
        .addAll(
            newConfig,
            loadConfig,
            new SeparatorMenuItem(),
            save,
            saveAs,
            close,
            new SeparatorMenuItem(),
            pref,
            quit);

    // Menu items for taskMenu
    MenuItem newTask = new MenuItem("New");
    MenuItem delete = new MenuItem("Delete");

    // Retrieves all task items into taskMenu
    tasksMenu.getItems().addAll(newTask, delete);

    // Gathers all the menus to the menuBar
    menuBar.getMenus().addAll(fileMenu, configMenu, tasksMenu, helpMenu);

    // Creates the original Layout of GUI
    BorderPane layout = new BorderPane();

    // Makes a split down the middle with left and right in GUI
    SplitPane splitPane = new SplitPane();
    AnchorPane left = new AnchorPane();
    AnchorPane right = new AnchorPane();
    splitPane.getItems().addAll(left, right);

    // Formats where the menu bar and split should go inside GUI
    layout.setCenter(splitPane);
    layout.setTop(menuBar);

    // trying to get help menu to display the same text as the cli

    about.setOnAction(event -> showhelp());

    // Builds scene
    Scene scene = new Scene(layout, 1024, 768);
    primaryStage.setTitle("Prototype");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void showhelp() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Help");
    alert.setHeaderText("Help content");
    alert.setContentText("This is a help content"); // ill change the msg later
    alert.showAndWait();
  }
}
