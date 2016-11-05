package seedu.inbx0.model;

import java.util.Objects;

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
    
    public String getThemeSetting() {
    	return themeSetting;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }
    
    public void updateLastUsedThemeSetting(String themeSetting) {
    	this.themeSetting = themeSetting;
    }

    public UserPrefs(){
        this.setGuiSettings(1300, 800, 0, 0);
        this.themeSetting = "default";
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }
    
    public void setThemeSetting(String themeSetting) {
    	this.themeSetting = themeSetting;
    }

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
