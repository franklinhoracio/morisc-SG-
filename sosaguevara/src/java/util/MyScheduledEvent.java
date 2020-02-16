/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Date;
import org.primefaces.model.DefaultScheduleEvent;

/**
 *
 * @author franklin
 */
public class MyScheduledEvent extends DefaultScheduleEvent {

    public MyScheduledEvent() {
        super();
    }

public MyScheduledEvent(String title, Date start, Date end, Object data, String styleClass) {
		super(title, start, end, data);
		super.setStyleClass(styleClass);
	}
}
