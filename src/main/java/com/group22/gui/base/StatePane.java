package com.group22.gui.base;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * 
 * The class {@code StatePane} extends StackPane and is an abstraction that
 * allows different panes to be shown based on a given state.
 * 
 * @author Liam Clegg
 * @version 1.1
 */
public class StatePane<T extends Enum<T>> extends StackPane {
    /** Value of the current state of the StatePane. */
    private T currentState;
    /** Hash map which maps a state to the panes which are visible 
     * with the given state. */
    private HashMap<T, ArrayList<Integer>> activationMap;

    /**
     * Creates a StatePane with an initial state value.
     * 
     * @param initialState The stating state of the pane.
     */
    public StatePane(T initialState) {
        this.currentState = initialState;
        this.activationMap = new HashMap<>();
    }

    /**
     * Resets the {@link #currentState} to make sure it is up to date.
     */
    public void refreshState() {
        this.setState(this.currentState);
    }

    /**
     * Sets the {@link #currentState} of the pane.
     * 
     * @param state Value of the new state.
     */
    public void setState(T state) {
        this.currentState = state;

        for (Node child : this.getChildren()) {
            child.setVisible(false);
            child.setManaged(false);
        }

        for (int i : this.getActivationList(state)) {
            this.getChildren().get(i).setVisible(true);
            this.getChildren().get(i).setManaged(true);
        }
    }

    /**
     * Gets the {@link #currentState}.
     * 
     * @return Current panes state.
     */
    public T getCurrentState() {
        return currentState;
    }

    /**
     * Adds a pane and its visible states to the {@code StatePane}.
     * 
     * @param pane Element that is added.
     * @param activateOnStates States which the pane will be visible.
     */
    @SafeVarargs
    public final void addPane(Pane pane, T ...activateOnStates) {
        if (!this.getChildren().add(pane)) {
            return;
        }

        int i = this.getChildren().size() - 1;

        for (T state : activateOnStates) {
            this.getActivationList(state).add(i);
        }

        this.refreshState();
    }

    /**
     * Gets the indexes of the panes that are visible with a given state.
     * 
     * @param state State to check against panes.
     * @return An arraylist of indexes of panes that are visible with the state.
     */
    public ArrayList<Integer> getActivationList(T state) {
        ArrayList<Integer> activations = this.activationMap.get(state);

        if (activations == null) {
            this.activationMap.put(state, new ArrayList<>());
            activations = this.activationMap.get(state);
        }

        return activations;
    }
}
