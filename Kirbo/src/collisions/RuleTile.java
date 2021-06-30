package collisions;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class RuleTile {

    Image image;



    BufferedImage bigImg = ImageIO.read(new File("file:///N://Year 12//Computer Science//JetBrains//IntelliJ//Gui//Kirbo//Sprites/Copper TileSet.png"));

    private int width = 32;
    private int height = 32;
    private int rows = 6;
    private int cols = 8;
    BufferedImage[] sprites = new BufferedImage[rows * cols];


    public void splitSprites(){


        for(int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                sprites[(i * cols) + j] = bigImg.getSubimage(
                        j * width,
                        i * height,
                        width,
                        height
                );
                image = SwingFXUtils.toFXImage(sprites[0], null);
            }

        }
    }







    public static final Image CopperTile = new Image( "file:///N://Year 12//Computer Science//JetBrains//IntelliJ//Gui//Kirbo//Sprites/Metal Connector Tile.png");


    public RuleTile() throws IOException {
    }


}
