/**   
*  This file is part of SimpleCalendar.
    
*  SimpleCalendar is free software: you can redistribute it and/or modify   
*  it under the terms of the GNU General Public License as published by   
*  the Free Software Foundation, either version 3 of the License, or   
*  (at your option) any later version.
     
*  SimpleCalendar is distributed in the hope that it will be useful,   
*  but WITHOUT ANY WARRANTY; without even the implied warranty of   
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the   
*  GNU General Public License for more details.

*  <http://www.gnu.org/licenses/>.
     
*/  
package com.charlesbergeron.garfieldget.fx.calendar;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.util.Date;

/**
 * A simple calendar to pick up a date.
 *
 */
public class SimpleCalendar extends VBox{

	private final Popup popup;
	final DatePicker datePicker;
	
	public SimpleCalendar() {
		popup = new Popup();
		popup.setAutoHide(true);
		popup.setAutoFix(true);
		popup.setHideOnEscape(true);

		datePicker = new DatePicker();
		datePicker.dateProperty().addListener((ov, oldDate, newDate) -> {
            if (popup.isShowing())
                popup.hide();

        });
		popup.getContent().add(datePicker);

		final Button calenderButton = new Button();
		calenderButton.setId("CalenderButton");
		calenderButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent ae) {
				Parent parent = SimpleCalendar.this.getParent();
				// Popup will be shown at upper left corner of calenderbutton
				Point2D point = calenderButton.localToScene(0, 0);

                //todo
                // Il faudrait trouver une solution pour calculer dynamiquement la taille du pop-up calendar
                // Actuellement le getWidth/getHeight marche pas la premiere fois:
                //  Width: 14.414212226867676
                //  Height: 14.914212226867676
                //  Width: 177.0
                //  Height: 207.0
				final double layoutX = parent.getScene().getWindow().getX() + parent.getScene().getX() + point.getX() - 177 + 20;
				final double layoutY = parent.getScene().getWindow().getY() + parent.getScene().getY() + point.getY() - 207 + 30;


				popup.show(SimpleCalendar.this, layoutX, layoutY);

			}
		});
		
		getChildren().add(calenderButton);
	}
	
	/**
	 * @return a string bean property to bind the date information to a desired node
	 */
	public ObjectProperty<Date> dateProperty() {
		return datePicker.dateProperty();
	}
	
}
