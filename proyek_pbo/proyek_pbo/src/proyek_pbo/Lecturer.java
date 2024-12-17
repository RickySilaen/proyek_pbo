package proyek_pbo;

public class Lecturer {
    private String name;
    private String id;

    public Lecturer(String name, String id) {
        this.name = name;
        this.id = id;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
