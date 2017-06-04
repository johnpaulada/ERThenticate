package com.erthenticate.app.erthenticate;

import java.util.ArrayList;

public class App {
    private State state;
    private ArrayList<Record> recordList;
    private String name;
    private int counter;

    public App() {
        this.state      = new State(State.STATE_READY);
        this.name       = "";
        this.counter    = 0;
        this.recordList = new ArrayList<Record>();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ArrayList<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(ArrayList<Record> recordList) {
        this.recordList = recordList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
