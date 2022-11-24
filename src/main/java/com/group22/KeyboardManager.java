package com.group22;

import java.util.HashSet;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * 
 * The class {@code KeyboardManager} is used by {@code Engine} for key polling.
 * 
 * KeyboardManager takes the event system used in javafx and emulates a key polling system using hash sets.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class KeyboardManager {
    private HashSet<KeyCode> keysDown;
    private HashSet<KeyCode> keysPress;
    private HashSet<KeyCode> keysUp;

    /**
     * Creates a keyboard manager.
     * 
     * @param scene to use its evenets to contruct to polling mechanism.
     */
    public KeyboardManager(Scene scene) {
        this.keysDown = new HashSet<>();
        this.keysPress = new HashSet<>();
        this.keysUp = new HashSet<>();

        scene.setOnKeyPressed((e -> {
            if(!keysPress.contains(e.getCode()))
                keysDown.add(e.getCode());

            keysPress.add(e.getCode());
            keysUp.remove(e.getCode());
        }));

        scene.setOnKeyReleased((e -> {
            keysDown.remove(e.getCode());
            keysPress.remove(e.getCode());
            keysUp.add(e.getCode());
        }));
    }
     
    /**
     * Should be called when a frame or given time period is updated.
     */
    public void nextFrame() {
        keysDown.clear();
        keysUp.clear();
    }

    
    /** 
     * Checks if the key is being pressed down on the frame which the method is called.
     * 
     * @param keyCode
     *      The key code of the key to be checked.
     * 
     * @return boolean
     *      True if key is being pressed on the current frame, False otherwise.
     */
    public boolean getKeyDown(KeyCode keyCode) {
        return keysDown.remove(keyCode);
    }


    /** 
     * If the key is currently being pressed down this method return true otherwise it returns false.
     * 
     * @param keyCode
     *      The key code of the key to be checked.
     * 
     * @return boolean
     *      True if key is down, False if key is up.
     */
    public boolean getKeyState(KeyCode keyCode) {
        return keysPress.contains(keyCode);
    }

    
    /** 
     * Checks if the key is being released on the frame which the method is called.
     * 
     * @param keyCode
     *      The key code of the key to be checked.
     * 
     * @return boolean
     *      True if key is being released on the current frame, False otherwise.
     */
    public boolean getKeyUp(KeyCode keyCode) {
        return keysUp.remove(keyCode);
    }

    
    /** 
     * Gets an array of keys that are currently down.
     * 
     * @return KeyCode[]
     */
    public KeyCode[] getKeyStates() {
        KeyCode[] keyCodes = new KeyCode[this.keysPress.size()];
        this.keysPress.toArray(keyCodes);
        return keyCodes;
    }
}