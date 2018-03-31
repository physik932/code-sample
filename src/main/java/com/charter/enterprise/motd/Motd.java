package com.charter.enterprise.motd;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Message")
public class Motd {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    private String motd;

    public Motd() {}

    public Motd(int id, String motd) {
        this.id = id;
        this.motd = motd;
    }

    public int getId() {
        return id;
    }

    String getMotd() {
        return motd;
    }

    public void setId(int id) {
        this.id = id;
    }

    void setMotd(String motd) {
        this.motd = motd;
    }

    @Override
    public String toString() {
        return "Motd{" +
                "id=" + id +
                ", motd='" + motd + '\'' +
                '}';
    }
}
