package com.gorup22;

import java.util.HashSet;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class KeyboardManager {
    private Scene scene;
    private HashSet<KeyCode> keysDown;
    private HashSet<KeyCode> keysPress;
    private HashSet<KeyCode> keysUp;

    public KeyboardManager(Scene scene) {
        this.keysDown = new HashSet<>();
        this.keysPress = new HashSet<>();
        this.keysUp = new HashSet<>();

        this.scene = scene;

        scene.setOnKeyPressed((e -> {
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
     
    public void nextFrame() {
        keysDown.clear();
        keysUp.clear();
    }

    // key has just been pressed 
    public boolean getKeyDown(KeyCode keyCode) {
        return keysDown.remove(keyCode);
    }

    // currently being pressed
    public boolean getKeyState(KeyCode keyCode) {
        return keysPress.contains(keyCode);
    }

    // key has just been released 
    public boolean getKeyUp(KeyCode keyCode) {
        return keysUp.remove(keyCode);
    }

    public KeyCode[] getKeyStates() {
        KeyCode[] keyCodes = new KeyCode[this.keysDown.size()];
        this.keysDown.toArray(keyCodes);
        return keyCodes;
    }
}