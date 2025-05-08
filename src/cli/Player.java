package cli;

// Player.java
public class Player {
    private int row;
    private int col;
    private boolean hasKey = false;
    private boolean chestCollected = false;

    public boolean getHasKey() {
        return hasKey;
    }
    public boolean getChestCollected() {
        return chestCollected;
    }

    public Player(int startRow, int startCol) {
        this.row = startRow;
        this.col = startCol;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void collectKey() {
        System.out.println("WE GOT A KEY YOU MF");
        hasKey = true;
    }

    public void collectChest() {
        System.out.println("WIN WIN WIN");
        chestCollected = true;

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
            System.out.println("Nigga what are u doing??? U need a key");
        } else {
            map.updatePlayerPosition(this, newRow, newCol);
            row = newRow;
            col = newCol;
        }
    }
}