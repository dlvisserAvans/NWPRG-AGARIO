package data;

public class Player extends GameObject {

    private String name;

    public Player(String name) {
        super(0,40,40);
        this.name = name;
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
