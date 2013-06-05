package com.charlesbergeron.garfieldget.util;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.SequentialTransitionBuilder;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-05-01
 * Time: 10:48
 * To change this template use File | Settings | File Templates.
 */
public class TransitionUtil {
    protected static final Double DURATION = 0.75;
    protected static final Double FADE_MAX = 1.0;
    protected static final Double FADE_MIN = 0.1;

    public static Transition createFadeOutTransition(final ImageView iv, EventHandler<ActionEvent> onFinishedEvent){

        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(DURATION), iv);
        fadeOutTransition.setFromValue(FADE_MAX);
        fadeOutTransition.setToValue(FADE_MIN);
        fadeOutTransition.setOnFinished(onFinishedEvent);

        return fadeOutTransition;
    }

    public static Transition createFadeInTransition(final ImageView iv, EventHandler<ActionEvent> onFinishedEvent){

        FadeTransition fadeInTransition
                = new FadeTransition(Duration.seconds(DURATION), iv);
        fadeInTransition.setFromValue(FADE_MIN);
        fadeInTransition.setToValue(FADE_MAX);
        fadeInTransition.setOnFinished(onFinishedEvent);

        return fadeInTransition;
    }

    public static Transition createFadeOutTransitionText(final Text textCtrl, EventHandler<ActionEvent> onFinishedEvent){

        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(DURATION), textCtrl);
        fadeOutTransition.setFromValue(FADE_MAX);
        fadeOutTransition.setToValue(FADE_MIN);
        fadeOutTransition.setOnFinished(onFinishedEvent);

        return fadeOutTransition;
    }

    public static Transition createFadeInTransitionText(final Text textCtrl, EventHandler<ActionEvent> onFinishedEvent){

        FadeTransition fadeInTransition
                = new FadeTransition(Duration.seconds(DURATION), textCtrl);
        fadeInTransition.setFromValue(FADE_MIN);
        fadeInTransition.setToValue(FADE_MAX);
        fadeInTransition.setOnFinished(onFinishedEvent);

        return fadeInTransition;
    }

    public static SequentialTransition createTransition(final ImageView iv, final Image img){

        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(DURATION), iv);
        fadeOutTransition.setFromValue(FADE_MAX);
        fadeOutTransition.setToValue(FADE_MIN);
        fadeOutTransition.setOnFinished(actionEvent -> iv.setImage(img));

        FadeTransition fadeInTransition
                = new FadeTransition(Duration.seconds(DURATION), iv);
        fadeInTransition.setFromValue(FADE_MIN);
        fadeInTransition.setToValue(FADE_MAX);

        return SequentialTransitionBuilder.create()
                                          .children(fadeOutTransition, fadeInTransition)
                                          .build();
    }

    public static SequentialTransition createTransitionText(final Text textCtrl, final String text){

        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(DURATION), textCtrl);
        fadeOutTransition.setFromValue(FADE_MAX);
        fadeOutTransition.setToValue(FADE_MIN);
        fadeOutTransition.setOnFinished(actionEvent -> textCtrl.setText(text));

        FadeTransition fadeInTransition
                = new FadeTransition(Duration.seconds(DURATION), textCtrl);
        fadeInTransition.setFromValue(FADE_MIN);
        fadeInTransition.setToValue(FADE_MAX);

        return SequentialTransitionBuilder.create()
                .children(fadeOutTransition, fadeInTransition)
                .build();
    }
}
