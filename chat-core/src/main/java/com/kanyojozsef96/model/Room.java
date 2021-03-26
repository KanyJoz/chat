package com.kanyojozsef96.model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class Room {
    public enum RoomType {
        FRIENDS("Friends"),
        WORK("Work"),
        SPORT("Sport"),
        LOVE("Love"),
        EDUCATION("Education"),
        HEALTH("Health"),
        POLITICS("Politics");

        private final StringProperty value = new SimpleStringProperty(this, "value");

        RoomType(String v) {
            this.setValue(v);
        }

        public String getValue() {
            return value.get();
        }

        public StringProperty valueProperty() {
            return value;
        }

        public void setValue(String value) {
            this.value.set(value);
        }

        @Override
        public String toString() {
            return this.getValue();
        }
    }

    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private StringProperty name = new SimpleStringProperty(this, "name");
    private ListProperty<String> rules = new SimpleListProperty<>(this, "rules");
    private ObjectProperty<RoomType> roomType = new SimpleObjectProperty<>(this, "roomType");

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableList<String> getRules() {
        return rules.get();
    }

    public ListProperty<String> rulesProperty() {
        return rules;
    }

    public void setRules(ObservableList<String> rules) {
        this.rules.set(rules);
    }

    public RoomType getRoomType() {
        return roomType.get();
    }

    public ObjectProperty<RoomType> roomTypeProperty() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType.set(roomType);
    }

    @Override
    public String toString() {
        return this.getName() + "--" + this.getRoomType().toString();
    }
}
