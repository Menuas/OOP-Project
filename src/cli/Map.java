package cli;
// Map.java
import main.AssetSetter;

import java.util.Arrays;

public class Map {
    private char[][] grid;
    private int exitRow, exitCol;

    public Map(char[][] grid, int exitRow, int exitCol) {
        this.grid = grid;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
    }

    public void print(Player player) {
        int x = 0, y = 0;
        for (char[] row : grid) {
            for (char c : row) {
                if(x== playerX &&
                        y== playerY) {
                    System.out.print("🤵");
                } else if(x== main.AssetSetter.KEY_COORDS[0] &&
                        y== main.AssetSetter.KEY_COORDS[1] && player.getHasKey()==false ) {
                    System.out.print("🗝️");
                } else if(x== main.AssetSetter.CHEST_COORDS[0] &&
                        y== main.AssetSetter.CHEST_COORDS[1] ) {
                    System.out.print("🧰");
                } else if(x== main.AssetSetter.DOOR_COORDS[0] &&
                        y== main.AssetSetter.DOOR_COORDS[1] && player.getHasKey()==false) {
                    System.out.print("🚪");
                } else {

                    if (c == '5')
                        System.out.print(cli.Ansi.YELLOW + "🟨" + cli.Ansi.RESET);
                    else if (c == '4')
                        System.out.print(cli.Ansi.DARK_GREEN + "🟢" + cli.Ansi.RESET);
                    else if (c == '3')
                        System.out.print(cli.Ansi.BROWN + "🟫" + cli.Ansi.RESET);
                    else if (c == '2') {
                        System.out.print(cli.Ansi.BLUE + "🟦" + cli.Ansi.RESET);

                    } else if (c == '1')
                        System.out.print(cli.Ansi.RED + "🟥" + cli.Ansi.RESET);
                    else if (c == '0') {
                        System.out.print(cli.Ansi.GREEN + "🟩" + cli.Ansi.RESET);
                    } else
                        System.out.print("#");
                }

                x++;
            }
            System.out.println();
            y++;
            x = 0;
        }

        if (player.getChestCollected()){
            System.exit(0);
        }

    }
    public boolean isChest(int row, int col) {
        return col == main.AssetSetter.CHEST_COORDS[0] &&
                row == main.AssetSetter.CHEST_COORDS[1];
    }
    public boolean isDoor(int row, int col) {
        return col == main.AssetSetter.DOOR_COORDS[0] &&
                row == main.AssetSetter.DOOR_COORDS[1];
    }
    public boolean isKey(int row, int col) {
        return  col == main.AssetSetter.KEY_COORDS[0] &&
                row == main.AssetSetter.KEY_COORDS[1];
    }

    public boolean isWall(int row, int col) {
        return grid[row][col]=='1'||grid[row][col]=='2'||grid[row][col]=='4';
    }

    public boolean isExit(int row, int col) {
        return false && row == exitRow && col == exitCol;
    }

    public boolean isWater(int row, int col) {
        // make sure you’re in‐bounds first:
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            return false;
        }
        return grid[row][col] == '2';
    }

    public void updatePlayerPosition(Player p, int newRow, int newCol) {
        if (isKey(newRow, newCol) && !p.getHasKey()) {
            p.collectKey();
        }
        if (isChest(newRow, newCol) && !p.getChestCollected()) {
            p.collectChest();
        }
        playerX = newCol;
        playerY = newRow;
    }

    public int getRows() {
        return grid.length;
    }

    public int getCols() {
        return grid[0].length;
    }

    private int playerX, playerY;
}