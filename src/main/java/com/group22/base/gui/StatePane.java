package com.group22.base.gui;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class StatePane<T extends Enum<T>> extends StackPane {
    private T currentState;
    private HashMap<T, ArrayList<Integer>> activationMap;

    public StatePane(T initialState) {
        this.currentState = initialState;
        this.activationMap = new HashMap<>();
    }

    public void refreshState() {
        this.setState(this.currentState);
    }

    public void setState(T state) {
        this.currentState = state;

        for(Node child : this.getChildren()) {
            child.setVisible(false);
            child.setManaged(false);
        }

        for(int i : this.getActivationList(state)) {
            this.getChildren().get(i).setVisible(true);
            this.getChildren().get(i).setManaged(true);
        }
    }

    public T getCurrentState() {
        return currentState;
    }

    @SafeVarargs
    public final void addPane(Pane pane, T ...activateOnStates) {
        if(!this.getChildren().add(pane)) {
            return;
        }

        int i = this.getChildren().size() - 1;

        for(T state : activateOnStates) {
            this.getActivationList(state).add(i);
        }

        this.refreshState();
    }

    public ArrayList<Integer> getActivationList(T state) {
        ArrayList<Integer> activations = this.activationMap.get(state);

        if(activations == null) {
            this.activationMap.put(state, new ArrayList<>());
            activations = this.activationMap.get(state);
        }

        return activations;
    }
}

