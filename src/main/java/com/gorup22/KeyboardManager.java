package com.gorup22;

public class KeyboardManager {
    private static KeyboardManager instance;

    private KeyboardManager() {}

    public static synchronized KeyboardManager getInstance() {
        if(instance == null)
            instance = new KeyboardManager();
        return instance;
    } 
}
