package cli;

// Player.java
public class Player {
    private int row;
    private int col;
    private boolean hasKey = false;
    private boolean chestCollected = false;

    private int health;
    private final int maxHealth = 100;

    public boolean getHasKey() {
        return hasKey;
    }

    public boolean getChestCollected() {
        return chestCollected;
    }

    public int getHealth() {
        return health;
    }

    public Player(int startRow, int startCol) {
        this.row = startRow;
        this.col = startCol;
        this.health = maxHealth;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void collectKey() {
        System.out.println("WE GOT A KEY!");
        hasKey = true;
    }

    public void collectChest() {
        System.out.println("WIN WIN WIN");
        chestCollected = true;

    }

    public void restoreHealthIfNearWater(Map map) {
        if (isNearWater(map)) {
            health = maxHealth;
            System.out.println("You drink water");
        }
    }

    /** Checks the four adjacent tiles for water. */
    private boolean isNearWater(Map map) {
        // north
        if (map.isWater(row-1, col)) return true;
        // south
        if (map.isWater(row+1, col)) return true;
        // west
        if (map.isWater(row, col-1)) return true;
        // east
        if (map.isWater(row, col+1)) return true;
        return false;
    }

    public void move(char direction, Map map) {
        int newRow = row;
        int newCol = col;
        switch (direction) {
            case 'W': newRow--; break;
            case 'S': newRow++; break;
            case 'A': newCol--; break;
            case 'D': newCol++; break;
            default:
                System.out.println("Invalid input. Use W, A, S, or D.");
                return;
        }
        if (newRow < 0 || newRow >= map.getRows() || newCol < 0 || newCol >= map.getCols()) {
            System.out.println("Out of bounds!");
        } else if (map.isWall(newRow, newCol)) {
            System.out.println("You can't move there! It's a wall.");
        } else if (map.isDoor(newRow, newCol) && !hasKey) {
            System.out.println("Nigga what are you doing? You need a key");
        } else {
            map.updatePlayerPosition(this, newRow, newCol);
            row = newRow;
            col = newCol;

            restoreHealthIfNearWater(map);
        }
    }
}