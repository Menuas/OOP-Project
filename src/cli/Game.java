package cli;

// Game.java
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Game {
    public static void startCLI() {
        String filename = "./res/maps/world01.txt"; // or pass as arg
        char[][] layout;
        int startRow = entity.Player.PLAYER_COORDS[1], startCol = entity.Player.PLAYER_COORDS[0];
        int exitRow = 0, exitCol = 0;

        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            int rows = lines.size();

            int cols =  lines.get(0).split(" ").length;
            layout = new char[rows][cols];

            for (int r = 0; r < rows; r++) {
                String line = lines.get(r);
                String[] tokens = line.split(" ");
                for (int c = 0; c < cols; c++) {
                    char ch = tokens[c].charAt(0);
                    layout[r][c] = ch;
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load map file: " + e.getMessage());
            return;
        }

        Map maze = new Map(layout, exitRow, exitCol);
        Player player = new Player(startRow, startCol);
        maze.updatePlayerPosition(player, startRow, startCol);
        Scanner scanner = new Scanner(System.in);

        outer: while (true) {
            maze.print(player);
            System.out.print("Enter move (W=up, S=down, A=left, D=right): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.isEmpty()) continue;

            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                player.move(ch, maze);

                if (maze.isExit(player.getRow(), player.getCol())) {
                    maze.print(player);
                    System.out.println("Congratulations! You've reached the exit.");
                    break outer;
                }
            }
        }
        scanner.close();
    }
}