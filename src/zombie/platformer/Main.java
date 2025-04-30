package zombie.platformer;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // create a window
        // initialize graphics
        // create the game (create entities, create obstacles, create the level)

        int rows = 11, cols = 11;
        int playerRow = rows / 2;
        int playerCol = cols / 2;

        Entity[][] map = new Entity[rows][cols];
        Player p = new Player(100);
        map[playerRow][playerCol] = p;

        Scanner sc = new Scanner(System.in);
        String input = null;

        // Game
        while (true) {
            printMap(map);
            System.out.print("Move (w/a/s/d), Quit (q): ");
            input = sc.nextLine();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("Game ended.");
                break;
            }

            map[playerRow][playerCol] = null;

            switch (input.toLowerCase()) {
                case "w":
                    if (playerRow > 0) playerRow--;
                    break;
                case "s":
                    if (playerRow < rows - 1) playerRow++;
                    break;
                case "a":
                    if (playerCol > 0) playerCol--;
                    break;
                case "d":
                    if (playerCol < cols - 1) playerCol++;
                    break;
                default:
                    System.out.println("Invalid key!");
            }

            map[playerRow][playerCol] = p;
        }

    }

    public static void printMap(Entity[][] map) {
        int rows = map.length;
        int cols = map[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print("+---");
            }
            System.out.println("+");

            for (int col = 0; col < cols; col++) {
                Entity cell = map[row][col];
                System.out.print("| " + (cell == null ? " " : cell.draw()) + " ");
            }
            System.out.println("|");
        }

        for (int col = 0; col < cols; col++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }
}
