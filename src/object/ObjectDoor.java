package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjectDoor extends SuperObject{
    public ObjectDoor() {
        name = "Door";
        collision = true;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
