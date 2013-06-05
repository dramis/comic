package com.charlesbergeron.garfieldget;

import com.charlesbergeron.garfieldget.factory.ComicFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.prefs.Preferences;


public class Main extends Application {

    /*Application preferences*/
    protected final Preferences prefs = Preferences.userNodeForPackage(Main.class);

    private static final String PREF_COMIC = "comic";
    private static final String PREF_COMIC_DEFAULT = "Garfield";

    /*SCENE DEFINITION*/
    private static final Double SCENE_WIDTH = 610.0;
    private static final Double SCENE_HEIGHT = 270.0;

    private static final String SCENE_STYLESHEET = "com/charlesbergeron/garfieldget/resource/style.css";
    private static final String SCENE_ICON = "com/charlesbergeron/garfieldget/resource/Garfield.png";
    private static final String SCENE_FXML = "resource/garfield_get.fxml";

    @Override
    public void init() throws Exception {
        super.init();

        //Init Comic factory
        ComicFactory.initFactory();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(SCENE_FXML));
        Parent root = (Parent) fxmlLoader.load();

        Controller controller = fxmlLoader.getController();


        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add(getClass().getClassLoader().getResource(SCENE_STYLESHEET).toExternalForm());

        primaryStage.setTitle("Go Comics");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream(SCENE_ICON)));

        //Minimal dimension
        primaryStage.setMinWidth(SCENE_WIDTH);
        primaryStage.setMinHeight(SCENE_HEIGHT);

        primaryStage.setOnCloseRequest(event -> prefs.put(PREF_COMIC, controller.comicSelect.getValue().toString()));

        //Init the controller
        controller.init(prefs.get(PREF_COMIC, PREF_COMIC_DEFAULT));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
