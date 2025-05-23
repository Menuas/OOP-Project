package main;

import object.ObjectChest;
import object.ObjectDoor;
import object.ObjectKey;

public class AssetSetter {
    GamePanel gp;

    public static final int[] KEY_COORDS = {23, 7};
    public static final int[] DOOR_COORDS = {10, 11};
    public static final int[] CHEST_COORDS = {10, 7};

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new ObjectKey();
        gp.obj[0].worldX = KEY_COORDS[0] * gp.tileSize;
        gp.obj[0].worldY = KEY_COORDS[1] * gp.tileSize;

        gp.obj[2] = new ObjectDoor();
        gp.obj[2].worldX = DOOR_COORDS[0] * gp.tileSize;
        gp.obj[2].worldY = DOOR_COORDS[1] * gp.tileSize;

        gp.obj[3] = new ObjectChest();
        gp.obj[3].worldX = CHEST_COORDS[0] * gp.tileSize;
        gp.obj[3].worldY = CHEST_COORDS[1] * gp.tileSize;
    }
}
