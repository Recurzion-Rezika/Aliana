package collisions;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Platformer extends Application {

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();

    

    @Override
    public void start(Stage primaryStage) throws Exception {
        initContent();

        Scene scene = new Scene(appRoot);
        //scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        //scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        primaryStage.setTitle("Platformer");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private int levelWidth;

    private void initContent() {
        ArrayList<Shape> platformer = new ArrayList<>();
        //Shape platform = new Rectangle( 400,400,200, 60 );
        //gameRoot.getChildren().add(platform);
        //platformer.add(platform);

        levelWidth = LevelData.Level1[0].length() * 60;
        for (int i=0; i <LevelData.Level1.length; i++){
            String line = LevelData.Level1[i];
            for (int j = 0; j < line.length(); j++){
                switch (line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                        Shape platform = new Rectangle(j*60,i*60,  60, 60);
                        gameRoot.getChildren().add(platform);
                        platformer.add(platform);
                        break;
                }
            }
        }

        Shape player = new Circle(300,900, 40 , Color.TEAL);
        gameRoot.getChildren().add(player);

        appRoot.getChildren().add(gameRoot);
    }

    public static void main(String[] args) {
        launch(args);
    }



}