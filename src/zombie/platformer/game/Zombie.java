package zombie.platformer.game;

public class Zombie {

    public enum ZombieState {INACTIVE, MOVING, ATTACKING, DEAD}

    private float x;            // Current x-position in the game world
    private float y;            // Current y-position in the game world
    private float width;        // Zombie's width (for collision/visual)
    private float height;       // Zombie's height (for collision/visual)
    private float speed;        // Movement speed
    private int health;         // Current health
    private int maxHealth;      // Maximum health
    private int damage;         // Damage zombie inflicts on player
    private boolean facingRight; // True if facing right, false if facing left

    private ZombieState currentState;

    private float detection = 150.0f;
    private float wanderChance = 0.01f;

    public Zombie (float x, float y, float width, float height, float speed, int maxHealth, int damage) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.facingRight = true;
        this.currentState = ZombieState.INACTIVE;
    }

    // Getters

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
    public int getDamage() {
        return damage;
    }
    public boolean isFacingRight() {
        return facingRight;
    }
    public ZombieState getCurrentState() {
        return currentState;
    }

    // Setters

    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }
    public void getCurrentState(ZombieState newState) {
        if (this.currentState != ZombieState.DEAD) {
            this.currentState = newState;
        }
    }

    // core methods

    public void update (float deltaTime, Player player) {
        switch (currentState) {

            case INACTIVE:
                if (player != null) {
                    float dx = player.getX() - x;
                    float dy = player.getY() - y;
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    if (distance < detection) {
                        currentState = ZombieState.ATTACKING;
                        break;
                    }
                }

                if (Math.random() < wanderChance) {
                    currentState = ZombieState.MOVING;
                } else {
                    inactiveBehavior(deltaTime);
                }
                break;


            case MOVING:
                moveBehavior(deltaTime);
                break;

            case ATTACKING:
                attackBehavior(deltaTime);
                break;

            case DEAD:
                deadBehavior(deltaTime);
                break;

            default:
                break;
        }
    }

    private void inactiveBehavior (float deltaTime) {
        // smth
    }

    private void moveBehavior (float deltaTime) {
        if (facingRight) {
            x += speed * deltaTime;
        } else {
            x -= speed * deltaTime;
        }
    }

    private void attackBehavior (float deltaTime) {
        // smth
    }

    private void deadBehavior (float deltaTime) {
        // smth
    }
}