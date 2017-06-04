package com.erthenticate.app.erthenticate;

public class State {
    public static final short STATE_READY = 0;
    public static final short STATE_RECORDING = 1;

    private int state;

    public State(short state) {
        this.state = state;
    }

    public State() {
        this.state = STATE_READY;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
