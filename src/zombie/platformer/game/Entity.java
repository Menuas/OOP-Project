package zombie.platformer.game;

public abstract class Entity {
    private float x;            // Current x-position in the game world
    private float y;            // Current y-position in the game world
    private float width;        // Zombie's width (for collision/visual)
    private float height;       // Zombie's height (for collision/visual)
    private float speed;        // Movement speed
    private int health;         // Current health
    private int maxHealth;      // Maximum health

    public Entity (float x, float y, float width, float height, float speed, int maxHealth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.maxHealth = maxHealth;

        this.health = maxHealth;
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
    public float getSpeed() {
        return speed;
    }
    public int getHealth() {
        return health;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public abstract void draw();
    public abstract void update();
    public abstract void onCollide(Entity other);
}
