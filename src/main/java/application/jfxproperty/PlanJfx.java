package application.jfxproperty;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PlanJfx {
    private SimpleStringProperty description;
    private SimpleStringProperty date;
    private SimpleStringProperty lien;
    private SimpleStringProperty username;
    private SimpleLongProperty id;
    private SimpleStringProperty theme;

    public PlanJfx(String description, LocalDateTime date, Optional<String> lien, String username, long id, String theme) {
        this.description = new SimpleStringProperty(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
        this.date = new SimpleStringProperty(date.format(formatter));
        this.lien = new SimpleStringProperty(lien.map(x->x.toString()).orElse(""));
        this.username = new SimpleStringProperty(username);
        this.id = new SimpleLongProperty(id);
        this.theme = new SimpleStringProperty(theme);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getLien() {
        return lien.get();
    }

    public SimpleStringProperty lienProperty() {
        return lien;
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public String getTheme() {
        return theme.get();
    }

    public SimpleStringProperty themeProperty() {
        return theme;
    }
}
