package com.gorup22;

import java.util.HashSet;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class KeyboardManager {
    private static KeyboardManager instance;
    
    private Scene scene;
    private HashSet<KeyCode> keysDown = new HashSet<>();

    private KeyboardManager() {}

    public static synchronized KeyboardManager getInstance() {
        if(instance == null)
            instance = new KeyboardManager();
        return instance;
    } 

    public void setScene(Scene scene) {
        keysDown.clear();

        if (this.scene != null) {
            this.scene.setOnKeyPressed(null);
            this.scene.setOnKeyReleased(null);
        }

        this.scene = scene;

        scene.setOnKeyPressed((e -> {
            keysDown.add(e.getCode());
        }));

        scene.setOnKeyReleased((e -> {
            keysDown.remove(e.getCode());
        }));
    }

    public boolean isKeyDown(KeyCode keyCode) {
        return keysDown.contains(keyCode);
    }

    public KeyCode[] getKeysDown() {
        KeyCode[] keyCodes = new KeyCode[this.keysDown.size()];
        this.keysDown.toArray(keyCodes);
        return keyCodes;
    }
}
