package com.erthenticate.app.erthenticate;

public class State {
    public static final short STATE_READY = 0;
    public static final short STATE_RECORDING = 1;

    private short state;

    public State(short state) {
        this.state = state;
    }

    public State() {
        this.state = STATE_READY;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }
}
