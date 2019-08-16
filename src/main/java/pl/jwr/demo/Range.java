package pl.jwr.demo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Range {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "range")
    List<Mount> mountains;

    public Range() {
    }

    public Range(String name, String description, List<Mount> mountains) {
        this.name = name;
        this.description = description;
        this.mountains = mountains;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Mount> getMountains() {
        return mountains;
    }

    public void setMountains(List<Mount> mountains) {
        this.mountains = mountains;
    }
}
