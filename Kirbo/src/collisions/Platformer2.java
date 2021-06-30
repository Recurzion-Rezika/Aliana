package collisions;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.HashMap;

public class Platformer2 extends Application {

    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();

    private ArrayList<Shape> platforms = new ArrayList<Shape>();

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();

    private Shape player;
    private Point2D playerVelocity = new Point2D(0, 0);
    private boolean canJump = true;

    private int levelWidth;

    Image image = new Image("file:///N://Year 12//Computer Science//JetBrains//IntelliJ//Gui//Kirbo//Sprites/Metal Connector Tile.png");


    private void initContent() {
        //Rectangle bg = new Rectangle(1280, 720);


        levelWidth = LevelData.Level1[0].length() * 60;

        for (int i = 0; i < LevelData.Level1.length; i++) {
            String line = LevelData.Level1[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Shape platform = createEntity(j*32, i*32, 32, 32, Color.BROWN);
                        //platform.setFill(new ImagePattern(RuleTile.CopperTile));
                        platform.setFill(new ImagePattern(RuleTile.image));
                        platforms.add(platform);
                        /*System.out.println("x = " + (j*60));
                        System.out.println("y = " + (i*60));
                        System.out.println("");*/
                        break;
                }
            }
        }

        //Polygon polyplatform = createPlatform();
        //platforms.add(polyplatform);

        System.out.println(platforms.size());
        platforms.forEach( (platform) -> {
            System.out.println(platform.getBoundsInParent());
        });
        player = createEntity(0, 900, 20, 20, Color.TEAL);
        player.translateXProperty().addListener((obs, old, newValue) ->{
            int offset = newValue.intValue();

            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX((-(offset - 640)));
            }
        });
        //appRoot.getChildren().addAll(gameRoot, uiRoot);
        appRoot.getChildren().addAll(gameRoot, uiRoot);
    }

    double momentumX = 0.0;
    double slowCoefficient = 1;


    private void update() {


        if (isPressed(KeyCode.SPACE) && player.getTranslateY() >= 5) {
            jumpPlayer();
        }
        if (isPressed(KeyCode.UP) && player.getTranslateY() >= 5) {
            jumpPlayer();
        }

        if (isPressed(KeyCode.LEFT) && player.getTranslateX() >= 5) {
            momentumX = -6;
            double slowCoefficient = 1;

        }

        if (isPressed(KeyCode.RIGHT) && player.getTranslateX() + 20 <= levelWidth - 5) {
            momentumX = 6;
            double slowCoefficient = 1;

        }



        if (playerVelocity.getY() < 10) {
            playerVelocity = playerVelocity.add(0, 1);
        }

        momentumX = momentumX * Math.pow(0.7, slowCoefficient);
        slowCoefficient = slowCoefficient / 1.00000000000001 ;
        System.out.println(momentumX);
        if (Math.abs(momentumX)  < 0.2) {
            momentumX = 0;
        }


        if (player.getTranslateY() > 1200) {
            player.setTranslateY(900);
            player.setTranslateX(0);
            gameRoot.setLayoutX(0);
            momentumX = 50;
        }

        System.out.println(momentumX);

        movePlayerX(momentumX);
        movePlayerY((int)playerVelocity.getY());


    }

    private void movePlayerX(double value) {
        boolean movingRight = value > 0;
        boolean collisionDetected = false;

        for (int i = 0; i < Math.abs(value); i++) {
            for (Shape platform : platforms) {
                if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (player.getTranslateX() + 20 == platform.getTranslateX()) {
                            return;
                        }
                    }
                    else {
                        if (player.getTranslateX() == platform.getTranslateX() + 32) {
                            return;
                        }
                    }
                }
            }
            player.setTranslateX(player.getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    private void movePlayerY(int value) {
        boolean movingDown = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            for (Shape platform : platforms) {
                if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (player.getTranslateY() + 20 == platform.getTranslateY()) {
                            player.setTranslateY(player.getTranslateY() - 1);
                            canJump = true;
                            return;
                        }
                    }
                    else {
                        if (player.getTranslateY() == platform.getTranslateY() + 32) {
                            return;
                        }
                    }
                }
            }
            player.setTranslateY(player.getTranslateY() + (movingDown ? 1 : -1));
        }
    }

    private void jumpPlayer() {
        if (canJump) {
            playerVelocity = playerVelocity.add(0, -30);
            canJump = false;
        }
    }

    private Shape createEntity(int x, int y, int w, int h, Color color) {
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);

        gameRoot.getChildren().add(entity);
        return entity;
    }

    private Polygon createPlatform() {
        Polygon entity = new Polygon();
        entity.getPoints().addAll(420.0, 420.0,
                600.0, 380.0,
                600.0, 440.0,
                420.0, 440.0);
        entity.setFill(Color.BROWN);
        gameRoot.getChildren().add(entity);
        return entity;
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public void start(Stage primaryStage) throws Exception {
        initContent();
        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        primaryStage.setTitle("Platformer");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
