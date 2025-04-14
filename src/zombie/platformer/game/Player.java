package zombie.platformer.game;

public class Player extends Entity {
    private Inventory inventory;

    public Player(float x, float y, float width, float height, float speed, int maxHealth) {
        super(x, y, width, height, speed, maxHealth);
        this.inventory = new Inventory();
    }

    public Player(Inventory inventory, float x, float y, float width, float height, float speed, int maxHealth) {
        super(x, y, width, height, speed, maxHealth);
        this.inventory = inventory;
    }

    public void onCollide(Entity other) {
        if (other instanceof Zombie) {
            Zombie zombie = (Zombie)other;

            // zombie collided with player, lose health and shit
        }
    }

    public void draw() {}

    public void update() {}
}

