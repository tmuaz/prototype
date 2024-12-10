package org.sepp;

import java.util.Arrays;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.checkerframework.checker.units.qual.A;

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

        //root acts as the main background for list view and the output
        AnchorPane root = new AnchorPane();

        //Splits the listView and VBox
        HBox hBox = new HBox();

        //All items in the listView
        ListView<String> listView = new ListView<>();

        //Where the output will be (text and project name)
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);

        //Current project name
        Label projectName = new Label("Project name");

        //Where the text will be displayed
        AnchorPane miniPane = new AnchorPane();

        //output text
        Text output = new Text("1. Task name");
        AnchorPane.setTopAnchor(output, 0.0);
        AnchorPane.setLeftAnchor(output, 0.0);

        //Puts the text into the Anchor pane
        miniPane.getChildren().add(output);

        //Puts the project name and anchor pane (which holds the text) into VBOX
        vBox.getChildren().addAll(projectName, miniPane);

        //Separates the listView and VBOX
        hBox.getChildren().addAll(listView, vBox);

        //Finally puts everything into a single anchorpane
        root.getChildren().add(hBox);

        AnchorPane.setTopAnchor(hBox, 0.0);
        AnchorPane.setBottomAnchor(hBox, 0.0);
        AnchorPane.setRightAnchor(hBox, 0.0);
        AnchorPane.setLeftAnchor(hBox, 0.0);


        //Formats where the menu bar and split should go inside GUI
        layout.setCenter(root);
        layout.setTop(menuBar);

        //Builds scene
        Scene scene = new Scene (layout, 1024, 768);
        primaryStage.setTitle("Prototype");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
