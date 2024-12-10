package org.sepp;

import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class GUI extends Application {
    public class Context{
        Config config = null;
        File runDirectory = null;
        File selected = null;

        public Context(){

        }

        public List<String> getConfigFileNames() {
            File[] files = Config.listConfigs();
            return Arrays.stream(files)
                         .map(f -> {
                             String name = f.getName();
                             return name.substring(0, name.length() - 5);
                         })
                         .toList();
        }
    }

    Context context = new Context();

    public static void main (String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuBar menuBar = new MenuBar();

        //Created menus at the top of GUI
        Menu fileMenu = new Menu("File");
        Menu configMenu = new Menu("Config");
        Menu tasksMenu = new Menu("Tasks");
        Menu helpMenu = new Menu("Help");

        //Menu items for file Menu
        MenuItem run = new MenuItem("Run...");
        MenuItem directory = new MenuItem("Set directory");

        //Retrieves all file menu items into the fileMenu
        fileMenu.getItems().addAll(
                run,
                directory
        );

        //Menu items for configMenu
        MenuItem newConfig = new MenuItem("Create new config");
        Menu loadConfig = new Menu("Load config");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As...");
        MenuItem close = new MenuItem("Close");
        MenuItem pref = new MenuItem("Preferences...");
        MenuItem quit = new MenuItem("Quit");


        loadConfig.getItems().addAll(
                context.getConfigFileNames().stream().map(MenuItem::new).toList()
        );
        loadConfig.getItems().add(new MenuItem("Browse..."));

        //Retrieves all config menu items into the configMenu
        configMenu.getItems().addAll(
                newConfig,
                loadConfig,
                save,
                saveAs,
                new SeparatorMenuItem(),
                pref,
                quit

        );

        //Menu items for taskMenu
        MenuItem newTask = new MenuItem("New");
        MenuItem delete = new MenuItem("Delete");

        //Retrieves all task items into taskMenu
        tasksMenu.getItems().addAll(
                newTask,
                delete
        );

        // Gathers all the menus to the menuBar
        menuBar.getMenus().addAll(fileMenu,configMenu,tasksMenu,helpMenu);

        //Creates the original Layout of GUI
        BorderPane layout = new BorderPane();

        //Makes a split down the middle with left and right in GUI
        SplitPane splitPane = new SplitPane();
        AnchorPane left = new AnchorPane();
        AnchorPane right = new AnchorPane();
        splitPane.getItems().addAll(left,right);

        //Formats where the menu bar and split should go inside GUI
        layout.setCenter(splitPane);
        layout.setTop(menuBar);

        //Builds scene
        Scene scene = new Scene (layout, 1024, 768);
        primaryStage.setTitle("Prototype");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
