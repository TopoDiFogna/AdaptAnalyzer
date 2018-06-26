package it.polimi.adaptanalyzertool.gui.utility;

/**
 * <p>This interface defines all the methods that the center screens have to implement.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public interface ChildScreenController {
    /**
     * <p>Used to set the parent of the screen.</p>
     *
     * @param screen the parent of the screen.
     */
    void setParentScreen(ScreenController screen);
}
