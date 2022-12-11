package com.group22;

import java.util.HashMap;
import java.util.HashSet;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * 
 * The class {@code KeyboardManager} is used by {@code Engine} for key polling.
 * 
 * KeyboardManager takes the event system used in javafx 
 * and emulates a key polling system using hash sets.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class KeyboardManager {
    private HashSet<KeyCode> keysDown;
    private HashMap<KeyCode, Long> keysPress;
    private HashSet<KeyCode> keysUp;

    private Scene scene;
    private KeyCode keyReleased;

    /**
     * Creates a keyboard manager.
     * 
     * @param scene 
     *      To use its evenets to contruct to polling mechanism.
     */
    public KeyboardManager() {}

    
    /** 
     * @param scene
     */
    public void setScene(Scene scene) {
        if (this.scene != null) {
            this.scene.onKeyReleasedProperty().set(null);
            this.scene.onKeyPressedProperty().set(null);
        }

        this.scene = scene;
        
        this.keysDown = new HashSet<>();
        this.keysPress = new HashMap<>();
        this.keysUp = new HashSet<>();

        scene.setOnKeyPressed((e -> {
            if (keysPress.get(e.getCode()) == null) {
                keysDown.add(e.getCode());
                keysPress.put(e.getCode(), Long.valueOf(System.nanoTime()));
            }

            keysUp.remove(e.getCode());
        }));

        scene.setOnKeyReleased((e -> {
            this.keyReleased = e.getCode();
            keysUp.add(e.getCode());
        }));
    }

    /**
     * Should be called when a frame or given time period is updated.
     */
    public void nextFrame() {
        keysDown.clear();

        KeyCode[] keys = new KeyCode[this.keysUp.size()];
        this.keysUp.toArray(keys);

        for (KeyCode key : keys) {
            keysDown.remove(key);
            keysPress.put(key, null);
        }

        keysUp.clear();
    }

    
    /** 
     * Checks if the key is being pressed 
     * down on the frame which the method is called.
     * 
     * @param keyCode
     *      The key code of the key to be checked.
     * 
     * @return 
     *      True if key is being pressed on the current frame, False otherwise.
     */
    public boolean getKeyDown(KeyCode keyCode) {
        return keysDown.remove(keyCode);
    }


    /** 
     * If the key is currently being pressed down,
     * this method return true otherwise it returns false.
     * 
     * @param keyCode
     *      The key code of the key to be checked.
     * 
     * @return 
     *      True if key is down, False if key is up.
     */
    public boolean getKeyState(KeyCode keyCode) {
        return keysPress.get(keyCode) != null;
    }

    
    /** 
     * @param ...keyCodes
     * @return KeyCode
     */
    public KeyCode getLastKeyDown(KeyCode ...keyCodes) {
        long lastTime = -1;
        KeyCode fastest = null;

        for (KeyCode keyCode : keyCodes) {
            Long time = this.keysPress.get(keyCode);

            if (time != null && time > lastTime) {
                lastTime = time;
                fastest = keyCode;
            }
        }

        return fastest;
    }
    
    /** 
     * Checks if the key is being released 
     * on the frame which the method is called.
     * 
     * @param keyCode
     *      The key code of the key to be checked.
     * 
     * @return
     *      True if key is being released on the current frame, False otherwise.
     */
    public boolean getKeyUp(KeyCode keyCode) {
        return keysUp.remove(keyCode);
    }

    
    /** 
     * Gets an array of keys that are currently down.
     * 
     * @return 
     *      An array of KeyCodes that are currently pressed down.
     */
    public KeyCode[] getKeyStates() {
        KeyCode[] keyCodes = new KeyCode[this.keysPress.size()];
        this.keysPress.keySet().toArray(keyCodes);
        return keyCodes;
    }
}