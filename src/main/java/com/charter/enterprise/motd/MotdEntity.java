package com.charter.enterprise.motd;

import javax.persistence.*;

@Entity
@Table(name = "Message")
public class MotdEntity {

    @Id
    @GeneratedValue
    private int id;
    private String motd;

    public MotdEntity() {

    }

    public MotdEntity(int id, String motd) {
        this.id = id;
        this.motd = motd;
    }

    public int getId() {
        return id;
    }

    public String getMotd() {
        return motd;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    @Override
    public String toString() {
        return "MotdEntity{" +
                "id=" + id +
                ", motd='" + motd + '\'' +
                '}';
    }
}
