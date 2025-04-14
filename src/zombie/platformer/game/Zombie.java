package zombie.platformer.game;

public class Zombie extends Entity {

    public enum ZombieState {INACTIVE, MOVING, ATTACKING, DEAD}

    private int damage;         // Damage zombie inflicts on player
    private boolean facingRight; // True if facing right, false if facing left

    private ZombieState currentState;

    private float detection = 150.0f;
    private float wanderChance = 0.01f;

    public Zombie (float x, float y, float width, float height, float speed, int maxHealth, int damage) {
        super(x, y, width, height, speed, maxHealth);
        this.damage = damage;
        this.facingRight = true;
        this.currentState = ZombieState.INACTIVE;
    }

    // Getters

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

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }
    public void getCurrentState(ZombieState newState) {
        if (this.currentState != ZombieState.DEAD) {
            this.currentState = newState;
        }
    }

    public void draw() {}

    public void onCollide(Entity other) {
        if (other instanceof Zombie) {
            Zombie zombie = (Zombie)other;


        } else if (other instanceof Player) {
            Player player = (Player)other;

            // what happens to the zombie that collided with player
        }
    }
    // core methods

    public void update () {
        float deltaTime = 0.0f;
        Player player = null;

        switch (currentState) {

            case INACTIVE:
                if (player != null) {
                    float dx = player.getX() - getX();
                    float dy = player.getY() - getY();
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
            setX(getSpeed() * deltaTime + getX());
        } else {
            setX(-getSpeed() * deltaTime + getX());
        }
    }

    private void attackBehavior (float deltaTime) {
        // smth
    }

    private void deadBehavior (float deltaTime) {
        // smth
    }
}