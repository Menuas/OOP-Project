package zombie.platformer.game;

public class Player extends Entity{
    private Inventory inventory;

    public Player(int x, int y, float width, float height, int maxHealth) {
        super(x, y, width, height, maxHealth);
        this.inventory = new Inventory();
    }

    public Player(int maxHealth) {
        super(maxHealth);
        this.inventory = new Inventory();
    }

    public Player(float x, float y, int maxHealth) {
        super(x, y, maxHealth);
        this.inventory = new Inventory();
    }

    public Player(Inventory inventory, int x, int y, float width, float height, int maxHealth) {
        super(x, y, width, height, maxHealth);
        this.inventory = inventory;
    }

    public void onCollide(Entity other) {
        if (other instanceof Zombie) {
            Zombie zombie = (Zombie) other;
            // zombie collided with player, lose health and shit
        }
    }

    public String draw() {
//        System.out.println("Me");
        return "P";
    }
    public void update() {

    }
}