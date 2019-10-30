package com.models;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "location") // This tells Hibernate to make a table out of this class
public class locationOfUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long coordinateX;

    private long coordinateY;


    public long getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(long coordinateX) {
        this.coordinateX = coordinateX;
    }

    public long getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(long coordinateY) {
        this.coordinateY = coordinateY;
    }

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }


}
