package ma.shop.database.model;

public enum Role {
    USER(1, "USER"),
    ADMIN(2, "ADMIN");

    private int id;
    private String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
