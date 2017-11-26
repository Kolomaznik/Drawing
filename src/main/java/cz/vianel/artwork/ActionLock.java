package cz.vianel.artwork;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.input.MouseEvent;

/**
 * ActionLock provides simple lock for GUI operations such as crop, brightness change...
 * This class preceded multiple actions running on same time
 */
public class ActionLock {
    private static boolean lock = false;

    /**
     * Provides way to ask if any GUI operation is already running
     * @return boolean Return true if actions are locked
     */
    public static boolean isLocked() {
        return lock;
    }

    public static void lock() {
        if (lock) {
            throw new RuntimeException("ActionLock is already locked!");
        }

        lock = true;
    }

    public static void unlock() {
        lock = false;
    }
}
