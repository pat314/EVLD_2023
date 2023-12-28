package com.example.final_dictionary;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EventBus {
    /**
     * All the method in subscribers will be call when calling publish method.
     */
    private static EventHandler<ActionEvent> subscribers = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {

        }
    };

    /**
     * Subscribe to the EventBus so that they can be handled when receiving requirement.
     *
     * @param subscriber given subscriber
     */
    public static void subscribe(EventHandler<ActionEvent> subscriber) {
        subscribers = subscriber;
    }

    public static void publish(ActionEvent event) {
        subscribers.handle(event);
    }
}