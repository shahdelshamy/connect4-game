package connect4;
import java.io.File;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox; 
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class connect4 extends Application {
    private static final int TILE_SIZE = 80;
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;

    private boolean redMove = true;
    private Disc[][] grid = new Disc[COLUMNS][ROWS];

    private Pane discRoot = new Pane();
    private Stage stage;
       
    
     private Parent createContent() {
        Pane root = new Pane();
        root.getChildren().add(discRoot);

        Shape gridShape = makeGrid();
        root.getChildren().add(gridShape);
        root.getChildren().addAll(makeColumns());

        return root;
    }
 
    
  private Parent createWelcomePage() {
    
    Pane start = new Pane(); 
    Image image = new Image("photo.png");
            
       BackgroundImage backgroundImage = new BackgroundImage(image,
       BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
       BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT); 
       Background background = new Background(backgroundImage);
 
       start.setBackground(background);
       Button b1 = new Button("Play With Computer");  
       b1.setTranslateX(140);
       b1.setTranslateY(130);
       b1.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-font-size: 25px; -fx-padding: 10px 20px; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,1), 20, 0, 0, 0);");
  
       b1.setOnMouseEntered(event -> {
       b1.setStyle("-fx-background-color: #666; -fx-text-fill: white; -fx-font-size: 25px; -fx-padding: 10px 20px; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,1), 20, 0, 0, 0);");
  });
       b1.setOnMouseExited(event -> {
       b1.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-font-size: 25px; -fx-padding: 10px 20px; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,1), 20, 0, 0, 0);");
  });
          
        Button b2 = new Button("2 Players");
        b2.setTranslateX(200);
        b2.setTranslateY(210);
        b2.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-font-size: 25px; -fx-padding: 10px 20px; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,1), 20, 0, 0, 0);");
  
        b2.setOnMouseEntered(event -> {
        b2.setStyle("-fx-background-color: #666; -fx-text-fill: white; -fx-font-size: 25px; -fx-padding: 10px 20px; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,1), 20, 0, 0, 0);");
  });
        b2.setOnMouseExited(event -> {
        b2.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-font-size: 25px; -fx-padding: 10px 20px; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,1), 20, 0, 0, 0);");
  });
      Button exit=new Button ("Exit");
      exit.setTranslateX(230);
      exit.setTranslateY(290);
  
      exit.setStyle("-fx-background-color: #333; -fx-text-fill: red; -fx-font-size: 30px; -fx-padding: 10px 20px; -fx-background-radius: 50%;");
    
      exit.setOnMouseEntered(event -> {
      exit.setStyle("-fx-background-color: #666; -fx-text-fill: red; -fx-font-size: 30px; -fx-padding: 10px 20px; -fx-background-radius: 50%;");
     });   
      exit.setOnMouseExited(event -> {
      exit.setStyle("-fx-background-color: #333; -fx-text-fill: red; -fx-font-size: 30px; -fx-padding: 10px 20px; -fx-background-radius: 50%;");
 });

      exit.setOnAction(e->{
      stage.close();
});
        
      b1.setOnAction(e -> {
      vsComputer = true;
      showGame();
 }); 

     
    b2.setOnAction(e -> {
    vsComputer = false;
    showGame();
    });

     
    
    start.setPrefSize( 514, 514);
   
    start.getChildren().addAll(b1,b2,exit);
   return start;
    
 
  }
private void showGame() {
    stage.setScene(new Scene(createContent()));

}
  
    // Creates a grid shape using Rectangle and Circle objects
// The grid is formed by subtracting circles from the rectangle
private Shape makeGrid() {
    Shape shape = new Rectangle((COLUMNS + 1) * TILE_SIZE, (ROWS + 1) * TILE_SIZE);

    for (int y = 0; y < ROWS; y++) {
        for (int x = 0; x < COLUMNS; x++) {
            Circle circle = new Circle(TILE_SIZE / 2);
            circle.setCenterX(TILE_SIZE / 2);
            circle.setCenterY(TILE_SIZE / 2);
            circle.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
            circle.setTranslateY(y * (TILE_SIZE + 5) + TILE_SIZE / 4);

            shape = Shape.subtract(shape, circle);
        }
    }

    // Adds lighting effects and fills the grid with blue color
    Light.Distant light = new Light.Distant();
    light.setAzimuth(45.0);
    light.setElevation(30.0);

    Lighting lighting = new Lighting();
    lighting.setLight(light);
    lighting.setSurfaceScale(5.0);

    shape.setFill(Color.BLUE);
    shape.setEffect(lighting);

    return shape;
}

// Creates a list of columns using Rectangle objects
// Each column has mouse event handlers and is added to a list
private List<Rectangle> makeColumns() {
    List<Rectangle> list = new ArrayList<>();

    for (int x = 0; x < COLUMNS; x++) {
        Rectangle rect = new Rectangle(TILE_SIZE, (ROWS + 1) * TILE_SIZE);
        rect.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
        rect.setFill(Color.TRANSPARENT);

        // Mouse event handlers for column highlighting and disc placement
        rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 50, 0.3)));
        rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

        final int column = x;
        rect.setOnMouseClicked(e -> placeDisc(new Disc(redMove), column));

        list.add(rect);
    }

    return list;
}
    private boolean vsComputer = true; // true for playing against computer, false for 2 players

// Places a disc in the specified column
// Checks for available spots, animates the disc placement, and handles game logic
private void placeDisc(Disc disc, int column) {
    // Finds the lowest available row in the column
    int row = ROWS - 1;
    do {
        if (!getDisc(column, row).isPresent())
            break;

        row--;
    } while (row >= 0);

    if (row < 0)
        return;

    // Adds the disc to the grid and animates its placement
    grid[column][row] = disc;
    discRoot.getChildren().add(disc);
    disc.setTranslateX(column * (TILE_SIZE + 5) + TILE_SIZE / 4);

    final int currentRow = row;
    TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), disc);
    animation.setToY(row * (TILE_SIZE + 5) + TILE_SIZE / 4);
    animation.setOnFinished(e -> {
        if (gameEnded(column, currentRow)) {
            gameOver(redMove);
        } else if (isBoardFull()) {
            gameDraw();
        } else {
            redMove = !redMove;

            if (vsComputer && !redMove) {
                makeAIMove();
            } else if (!vsComputer) {
                disc.setFill(redMove ? Color.RED : Color.YELLOW);
            }
        }
    });
    animation.play();

    // Sets the disc color to the opposite of the current player in two-player mode
    if (!vsComputer) {
        disc.setFill(redMove ? Color.YELLOW : Color.RED);
    }
}

// Makes a move for the AI opponent
// Randomly selects an available column and places a disc
private void makeAIMove() {
    if (isBoardFull()) {
        gameDraw();
        return;
    }

    int column = -1;
    do {
        column = (int) (Math.random() * COLUMNS);
    } while (!isColumnAvailable(column));

    placeDisc(new Disc(redMove), column);
}

// Checks if a specific column has an available spot for placing a disc
private boolean isColumnAvailable(int column) {
    for (int row = 0; row < ROWS; row++) {
        if (grid[column][row] == null) {
            return true;
        }
    }
    return false;
}

// Checks if the game board is full (all spots are occupied)
private boolean isBoardFull() {
    for (int column = 0; column < COLUMNS; column++) {
        for (int row = 0; row < ROWS; row++) {
            if (!getDisc(column, row).isPresent()) {
                return false;
            }
        }
    }
    return true;
}

// Displays a dialog indicating the game ended in a draw
// Provides options to start a new game or return to the menu
private void gameDraw() {
    String message = "The game ended in a draw!\n\nDo you want to begin a new game?!";
    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);

        // Create a custom DialogPane with the message and buttons
        DialogPane dialogPane = new DialogPane();
        dialogPane.setContentText(message);
        dialogPane.setPrefWidth(400);
        dialogPane.setPrefHeight(200);
        dialogPane.setPadding(new Insets(10));

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType exitButton = new ButtonType("Return Menu", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialogPane.getButtonTypes().addAll(okButton, exitButton);

        alert.setDialogPane(dialogPane);

        // Handle button actions
        Button okButtonControl = (Button) alert.getDialogPane().lookupButton(okButton);
        okButtonControl.setOnAction(e1 -> {
            resetGame();
            alert.close();
        });

        Button exitButtonControl = (Button) alert.getDialogPane().lookupButton(exitButton);
        exitButtonControl.setOnAction(e2 -> {
            stage.setScene(new Scene(createWelcomePage()));
            alert.close();
            resetGame();
        });

        alert.showAndWait();
    });
}
// Checks if the game has ended by connecting four discs in a row, column, or diagonal

 private boolean gameEnded(int column, int row) {
    List<Point2D> vertical = IntStream.rangeClosed(row - 3, row + 3)
            .mapToObj(r -> new Point2D(column, r))
            .collect(Collectors.toList());
// Lists of points for horizontal, diagonal, and square connections
    List<Point2D> horizontal = IntStream.rangeClosed(column - 3, column + 3)
            .mapToObj(c -> new Point2D(c, row))
            .collect(Collectors.toList());

    Point2D topLeft = new Point2D(column - 3, row - 3);
List<Point2D> diagonal1 = IntStream.rangeClosed(0, 6)
        .mapToObj(i -> topLeft.add(i, i))
        .collect(Collectors.toList());

Point2D botLeft = new Point2D(column - 3, row + 3);
List<Point2D> diagonal2 = IntStream.rangeClosed(0, 6)
        .mapToObj(i -> botLeft.add(i, -i))
        .collect(Collectors.toList());

List<Point2D> diagonal3 = IntStream.rangeClosed(0, 6)
        .mapToObj(i -> topLeft.add(i, -i))
        .collect(Collectors.toList());

List<Point2D> diagonal4 = IntStream.rangeClosed(0, 6)
        .mapToObj(i -> botLeft.add(i, i))
        .collect(Collectors.toList());


   List<Point2D> square1 = Arrays.asList(
    new Point2D(column, row),
    new Point2D(column, row + 1),
    new Point2D(column + 1, row),
    new Point2D(column + 1, row + 1)
);

List<Point2D> square2 = Arrays.asList(
    new Point2D(column, row),
    new Point2D(column, row - 1),
    new Point2D(column + 1, row),
    new Point2D(column + 1, row - 1),
    new Point2D(column, row + 1),
    new Point2D(column, row + 2),
    new Point2D(column + 1, row + 1),
    new Point2D(column + 1, row + 2)
);

    // Check if any of the connections result in four discs of the same color
    return checkRange(vertical) || checkRange(horizontal)
            || checkRange(diagonal1) || checkRange(diagonal2)
            || checkRange(square1) || checkRange(square2);
}

  // Checks if a range of points on the game board has four discs of the same color
    private boolean checkRange(List<Point2D> points) {
        int chain = 0;

        for (Point2D p : points) {
            int column = (int) p.getX();
            int row = (int) p.getY();

            Disc disc = getDisc(column, row).orElse(new Disc(!redMove));
            if (disc.red == redMove) {
                chain++;
                if (chain == 4) {
                    return true;
                }
            } else {
                chain = 0;
            }
        }

        return false;
    }
// Handles the game over scenario when a player wins
// Displays a dialog with the winner and options to start a new game or return to the menu
private void gameOver(boolean redWon) {
    String winner;
    if (vsComputer) {
        winner = redWon ? "RED" : "YELLOW";
    } else {
        winner = redMove ? "Yellow" : "Red";
    }
    soundPlayer("win.wav");
    String message = "Congratulations, " + winner + "!\n\nYou have won the game of Connect Four.\n\nDo you want to begin a new game?!";

    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);

        // Create a custom DialogPane with the congratulatory message and buttons
        DialogPane dialogPane = new DialogPane();
        dialogPane.setContentText(message);
        dialogPane.setPrefWidth(400);
        dialogPane.setPrefHeight(200);
        dialogPane.setPadding(new Insets(10));

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType exitButton = new ButtonType("Return Menu", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialogPane.getButtonTypes().addAll(okButton, exitButton);

        alert.setDialogPane(dialogPane);

        // Handle button actions
        Button okButtonControl = (Button) alert.getDialogPane().lookupButton(okButton);
        okButtonControl.setOnAction(e1 -> {
            resetGame();
            alert.close();
        });

        Button exitButtonControl = (Button) alert.getDialogPane().lookupButton(exitButton);
        exitButtonControl.setOnAction(e2 -> {
            stage.setScene(new Scene(createWelcomePage()));
            alert.close();
            resetGame();
        });

        alert.showAndWait();
    });
}

// Retrieves the disc at a specific column and row on the game board
// Returns an Optional that may contain a Disc object
private Optional<Disc> getDisc(int column, int row) {
    if (column < 0 || column >= COLUMNS || row < 0 || row >= ROWS)
        return Optional.empty();

    return Optional.ofNullable(grid[column][row]);
}

// Inner class representing a disc (circle) on the game board
private static class Disc extends Circle {
    private final boolean red;

    public Disc(boolean red) {
        super(TILE_SIZE / 2, red ? Color.RED : Color.YELLOW);
        this.red = red;

        setCenterX(TILE_SIZE / 2);
        setCenterY(TILE_SIZE / 2);
    }
}

// Resets the game by clearing the game board and discRoot
private void resetGame() {
    grid = new Disc[COLUMNS][ROWS];
    discRoot.getChildren().clear();
}
private void soundPlayer(String soundFile) {
    try {
        String projectPath = System.getProperty("user.dir");
        File file = new File(projectPath + "/sound/" + soundFile);
        Media sound = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    } catch (Exception e) {
        
    }
        // Handle any exceptions
}
      @Override
   
   public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        stage.setScene(new Scene(createWelcomePage()));
        stage.show();
    }
  
   

    public static void main(String[] args) {
        launch(args);
    }
}