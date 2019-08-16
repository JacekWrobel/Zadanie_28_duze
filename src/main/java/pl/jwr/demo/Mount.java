package pl.jwr.demo;

import javax.persistence.*;

@Entity
public class Mount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int altitude;
    private int likes;
    private String description;
    private String img;

    @ManyToOne
    private Range range;

    public Mount() {
    }

    public Mount(String name, int altitude, int likes, String description, String img, Range range) {
        this.name = name;
        this.altitude = altitude;
        this.likes = likes;
        this.description = description;
        this.img = img;
        this.range = range;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }
}