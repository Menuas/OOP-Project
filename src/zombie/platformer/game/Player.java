package zombie.platformer.game;

public class Player {
    private Inventory inventory;
    private int health;
    private int speed;

    public Player() {
        this.inventory = new Inventory();
        this.health = 100;
        this.speed = 0;
    }

    public Player(Inventory inventory, int health, int speed) {
        if (health <= 0 || health > 100 || speed <= 0 || speed > 100) {
            throw new IllegalArgumentException();
        }
        this.inventory = inventory;
        this.health = health;
        this.speed = speed;
    }

    public void setHealth(int health) {
        if (health <= 0 || health > 100) {
            this.health = health;
        }
    }

    public void setSpeed(int speed) {
        if (speed <= 0 || speed > 100) {
            this.speed = speed;
        }
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public void run() {
        while (true) {
            // while user hold button the player coordinates change
        }
    }
}

