package zombie.platformer;

import zombie.platformer.game.*;

public class Main {
    public static void main(String[] args) {


        // create a window
        // initialize graphics
        // create the game (create entities, create obstacles, create the level)

        Entity[] entities = new Entity[10];

        entities[0] = new Player(0, 0, 100, 100, 0, 100);
        for (int i = 1; i < 10; i++) {
            entities[i] = new Zombie(0, 0, 100, 100, 0, 100, 20);
        }

        // run the game
        while (true) {
            for (int i = 0; i < entities.length; i++) {
                entities[i].update();
            }

            // update collisions
            //      get which entities are close to each other
            //      call onCollide for each of them

            // get input

            // draw everything on window

            // present on the screen
        }
    }
}