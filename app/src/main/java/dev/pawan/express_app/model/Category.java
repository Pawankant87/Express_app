package dev.pawan.express_app.model;

public class Category {

    private String name, image, brief;
    private int id;

    public Category(String name, String image, String brief, int id) {
        this.name = name;
        this.image = image;
        this.brief = brief;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return image;
    }

    public void setIcon(String icon) {
        this.image = icon;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
