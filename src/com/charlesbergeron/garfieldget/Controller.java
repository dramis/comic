package com.charlesbergeron.garfieldget;

import com.charlesbergeron.garfieldget.comics.Comic;
import com.charlesbergeron.garfieldget.factory.ComicFactory;
import com.charlesbergeron.garfieldget.fx.calendar.SimpleCalendar;
import com.charlesbergeron.garfieldget.util.DateUtil;
import com.charlesbergeron.garfieldget.util.FileUtil;
import com.charlesbergeron.garfieldget.util.TransitionUtil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Controller {

    /* FXML variable*/
    @FXML
    protected BorderPane root;
    @FXML
    protected SimpleCalendar calendar;
    @FXML
    protected ComboBox comicSelect;
    @FXML
    protected Text currentDateText;
    @FXML
    protected ImageView image;

    /* The current displayed comic */
    protected Comic theComic;

    /* The current displayed date */
    private GregorianCalendar theCurrentDate = new GregorianCalendar();

    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MMMM dd,yyyy");

    /* Handle function */
    @FXML
    protected void handlePreviousButtonAction(ActionEvent event) {
        theCurrentDate.add(GregorianCalendar.DAY_OF_MONTH, -1);
        if (theCurrentDate.before(theComic.getFirstStripDate())) {
            theCurrentDate.add(GregorianCalendar.DAY_OF_MONTH, 1);

        }
        displayImage();
    }

    @FXML
    protected void handleNextButtonAction(ActionEvent event) {
        theCurrentDate.add(GregorianCalendar.DAY_OF_MONTH, 1);
        if (theCurrentDate.after(new GregorianCalendar())) {
            theCurrentDate = new GregorianCalendar();
        }
        displayImage();
    }

    @FXML
    protected void handleTodayButtonAction(ActionEvent event) {
        theCurrentDate = new GregorianCalendar();
        displayImage();
    }

    public void init(String comicName) {
        /* memory map between process */
        final List<File> files = new ArrayList<>();

        //Init the list of comics
        ComicFactory.getComicsNameList().forEach(comicLabel -> {comicSelect.getItems().add(comicLabel);});

        comicSelect.setValue(comicName);
        setComic(comicName);

        /*
        *
        * ActionEvent
        *
        * */
        image.setOnDragDetected((mouseEvent) -> {
            image.getStyleClass().add("imageZoneOnDrag");

            Dragboard db = image.startDragAndDrop(TransferMode.COPY);
            ClipboardContent filesToCopyClipboard = new ClipboardContent();
            URL urlComic = null;

            try {
                urlComic = new URL(theComic.getUrl(theCurrentDate));
                File file = FileUtil.writeCreateFile(urlComic, DateUtil.format("yyyyMMdd", theCurrentDate) + ".gif");
                files.add(file);
                filesToCopyClipboard.putFiles(files);
                db.setContent(filesToCopyClipboard);

            } catch (MalformedURLException e) {/* not a lot of thing to do here. */}

            mouseEvent.consume();
        });

        image.setOnDragDone((dragEvent) -> {
            files.forEach(File::delete);
            files.clear();
            image.getStyleClass().remove("imageZoneOnDrag");
            dragEvent.consume();
        });

        /*
        *
        *  Listener
        *
        */
        root.widthProperty().addListener(
                (observable, oldValue, newValue) -> {
                    Double width = (Double)newValue;
                    image.setFitWidth(width);
                });

        root.heightProperty().addListener(
                (observable, oldValue, newValue) -> {
                    Double height = (Double)newValue - 95;
                    image.setFitHeight(height <= 0 ? 1:height);
                });

        calendar.dateProperty().addListener( (observable, oldValue, newValue) -> {
            theCurrentDate.setTime(newValue);

            if (theCurrentDate.after(new GregorianCalendar())) {
                theCurrentDate.setTime(oldValue);
            }
            if (theCurrentDate.before(theComic.getFirstStripDate())) {
                theCurrentDate.setTime(oldValue);
            }

            displayImage();
        });

        comicSelect.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    setComic((String)newValue);
                    displayImage();
        });

        //Display today strip:
        displayImageWithoutTransition();
    }

    private void displayImage() {
        final ArrayList<Image> imagesGrfl = new ArrayList<>();

        final String dateText = SIMPLE_DATE_FORMAT.format(theCurrentDate.getTime());

        Thread workerThread = new Thread( () -> {

            Image imageGrfl = new Image(theComic.getUrl(theCurrentDate));
            imagesGrfl.add(imageGrfl);
        });

        workerThread.run();
        Thread workerThreadImgTransition = new Thread( () -> TransitionUtil.createFadeOutTransition(image, actionEvent -> {
            try {
                workerThread.join();
                image.setImage(imagesGrfl.get(0));
                TransitionUtil.createFadeInTransition(image, actionEvent2 -> {
                }).play();
            } catch (InterruptedException ignored) {}
        }).play());

        Thread workerThreadTextTransition = new Thread( () -> TransitionUtil.createFadeOutTransitionText(currentDateText, actionEvent -> {
            try {
                workerThread.join();
                currentDateText.setText(dateText);
                TransitionUtil.createFadeInTransitionText(currentDateText, actionEvent2 -> {
                }).play();
            } catch (InterruptedException ignored) {}
        }).play());

        workerThreadImgTransition.run();
        workerThreadTextTransition.run();

    }

    private void displayImageWithoutTransition() {

        final String dateText = SIMPLE_DATE_FORMAT.format(theCurrentDate.getTime());

        Image imageGrfl = new Image(theComic.getUrl(theCurrentDate));
        image.setImage(imageGrfl);
        currentDateText.setText(dateText);
    }

    protected void setComic(String comicName) {
        theComic = ComicFactory.makeComic(comicName);
    }
}
