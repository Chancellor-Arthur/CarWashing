package ru.koigerov.carwashing.entities;

import java.time.LocalDate;

public final class Record {
    private final int id;
    private final int userId;
    private final String car;
    private final String service;
    private final LocalDate date;
    private final String time;

    public Record(int id, int userId, String car, String service, LocalDate date, String time) {
        this.id = id;
        this.userId = userId;
        this.car = car;
        this.service = service;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getCar() {
        return car;
    }

    public String getService() {
        return service;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
