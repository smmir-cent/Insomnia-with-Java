package view;

import java.io.Serializable;

/**
 * to hold information of app setting
 * @author Seyyed Mahdi Mirfendereski
 * @version 0.0
 */
public class Option implements Serializable {
    //light theme
    private boolean lightTheme;
    //system tray
    private boolean tray;
    public Option(boolean lightTheme,boolean tray){
        this.lightTheme=lightTheme;
        this.tray=tray;
    }
    public Option(){
    }

    public boolean isLightTheme() {
        return lightTheme;
    }

    public void setLightTheme(boolean lightTheme) {
        this.lightTheme = lightTheme;
    }

    public boolean isTray() {
        return tray;
    }

    public void setTray(boolean tray) {
        this.tray = tray;
    }
}
