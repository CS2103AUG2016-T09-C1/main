package seedu.inbx0.model;

import java.util.Objects;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import seedu.inbx0.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;
    public String themeSetting;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }
    
    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }
    
    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }
    
    //@@author A0148044J
    public String getThemeSetting() {
    	return themeSetting;
    }
    
    public void updateLastUsedThemeSetting(String themeSetting) {
    	this.themeSetting = themeSetting;
    }
    
    public void setThemeSetting(String themeSetting) {
    	this.themeSetting = themeSetting;
    }

    public UserPrefs(){
        //Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        //this.setGuiSettings(visualBounds.getWidth(), visualBounds.getHeight(), 
        					//(int) visualBounds.getMinX(), (int) visualBounds.getMinY());
        this.setGuiSettings(1300, 800, 0, 0);
        this.themeSetting = "/view/LightGrey.css";
    }
    //@@author
    
    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof UserPrefs)){ //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs)other;

        return Objects.equals(guiSettings, o.guiSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings);
    }

    @Override
    public String toString(){
        return guiSettings.toString();
    }

}
