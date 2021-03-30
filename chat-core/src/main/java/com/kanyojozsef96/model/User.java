package com.kanyojozsef96.model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class User {
    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private StringProperty username = new SimpleStringProperty(this, "username");
    private StringProperty password = new SimpleStringProperty(this, "password");
    private StringProperty email = new SimpleStringProperty(this, "email");
    private IntegerProperty age = new SimpleIntegerProperty(this, "age");
    private StringProperty sex = new SimpleStringProperty(this, "sex");
    private ListProperty<String> hobbies = new SimpleListProperty<>(this, "hobbies");

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public void setId(int id) { this.id.set(id); }

    public String getUsername() { return username.get(); }
    public StringProperty usernameProperty() { return username; }
    public void setUsername(String username) { this.username.set(username); }

    public String getPassword() { return password.get(); }
    public StringProperty passwordProperty() { return password; }
    public void setPassword(String password) { this.password.set(password); }

    public String getEmail() { return email.get(); }
    public StringProperty emailProperty() { return email; }
    public void setEmail(String email) { this.email.set(email); }

    public int getAge() { return age.get(); }
    public IntegerProperty ageProperty() { return age; }
    public void setAge(int age) { this.age.set(age); }

    public String getSex() { return sex.get(); }
    public StringProperty sexProperty() { return sex; }
    public void setSex(String sex) { this.sex.set(sex); }

    public ObservableList<String> getHobbies() { return hobbies.get(); }
    public ListProperty<String> hobbiesProperty() { return hobbies; }
    public void setHobbies(ObservableList<String> hobbies) { this.hobbies.set(hobbies); }

    @Override
    public String toString() {
        return "Username --> " + this.getUsername();
    }
}
