package ru.koigerov.carwashing.entities;

public class Service {
    private final int id;
    private final String name;
    private final int duration;

    public Service(int id, String name, int duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }
}
