import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class SmartRailsWindow extends Application
{
  static final int WINDOW_WIDTH = 1400;
  static final int WINDOW_HEIGHT = 700;
  static Random rand = new Random();
  private static ArrayList<Train> trains = new ArrayList<>();
  private ChoiceBox<String> destination = new ChoiceBox<>();

  private static final int NUM_TRAINS = 3;

  /**
   * main method of project SmartRails
   * @param args
   */
  public static void main(String[] args)
  {
    launch(args);
  }

  /**
   * start() method from Application class
   * @param stage
   * @throws Exception
   */
  @Override
  public void start(Stage stage) throws Exception
  {
    MediaPlayer mediaPlayer = res.ResourceLoader.getMediaPlayer();
    mediaPlayer.play();
    Group root = new Group();
    Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    TrackMaker track = new TrackMaker(NUM_TRAINS, canvas.getGraphicsContext2D());
    makeTrains(track);

//    Button button = new Button("I Am A Button");
//    button.setOnAction(e->
//    {
//      button.setVisible(false);
//      new Thread(smartRails).start();
//    });

    root.getChildren().addAll(canvas);
    
    for(LightTrackView lightTrackView : track.getLightViews())
    {
      root.getChildren().add(lightTrackView);
    }
    
    for(TrainView trainView  : track.getTrainViews())
    {
      root.getChildren().add(trainView);
    }

    Scene scene = new Scene(root);
    openingScene(stage, scene);
    stage.setOnCloseRequest(e -> System.exit(0));
    stage.show();
  }

  private void openingScene(Stage stage, Scene scene)
  {
    VBox vbox = new VBox();
    int vertInset = 100;
    int horizInset = 70;
    int spacing = 20;
    Color textColor = Color.YELLOW;

    Background bkgd = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
    Background buttonBkgd = new Background(new BackgroundFill(Color.DARKRED, CornerRadii.EMPTY, Insets.EMPTY));

    Label pickTrain = new Label("Pick your Train");
    pickTrain.setTextFill(textColor);

    Button train1 = new Button();
    Button train2 = new Button();
    Button train3 = new Button();
    formatButton(train1, "Train 1", buttonBkgd);
    formatButton(train2, "Train 2", buttonBkgd);
    formatButton(train3, "Train 3", buttonBkgd);

    HBox trainButtons = new HBox(train1, train2, train3);
    trainButtons.setAlignment(Pos.CENTER);
    trainButtons.setSpacing(15);

    Label pickDest = new Label("Pick your Destination:");
    pickDest.setTextFill(textColor);

    destination.setBackground(buttonBkgd);

    Image title = res.ResourceLoader.getTitle((int)vbox.getWidth(), (int)vbox.getHeight());
    ImageView imageView = new ImageView(title);

    Button go = new Button("Send the Train");
    go.setBackground(buttonBkgd);
    go.setTextFill(textColor);
    go.setOnAction(e -> {
      stage.setScene(scene);
      SmartRails smartRails = new SmartRails(trains);
      smartRails.setDestination(destination.getValue());
      new Thread(smartRails).start();
    });

    vbox.setBackground(bkgd);
    vbox.setAlignment(Pos.CENTER);
    vbox.setSpacing(spacing);
    vbox.setPadding(new Insets(vertInset, horizInset, vertInset, horizInset));
    vbox.getChildren().addAll(imageView, pickTrain, trainButtons, pickDest, destination, go);
    Scene opener = new Scene(vbox);
    stage.setScene(opener);
  }

  private void formatButton(Button b, String text, Background background)
  {
    b.setText(text);
    b.setTextFill(Color.YELLOW);
    b.setBackground(background);
    b.setOnAction(e -> handleEvent(b));
  }

  private void handleEvent(Button button)
  {
    int trainNumber = 0;
    if(button.getText().equals("Train 1")) trainNumber = 1;
    else if(button.getText().equals("Train 2")) trainNumber = 2;
    else if(button.getText().equals("Train 3")) trainNumber = 3;

    switch (trainNumber)
    {
      case 1:
        destination.getItems().addAll("B0", "B1", "B2", "B3");
        destination.setValue("B0");
        break;
      case 2:
        destination.getItems().addAll("A0", "A1", "A2", "A3");
        destination.setValue("A0");
        break;
      case 3:
        destination.getItems().addAll("B1", "B2", "B3");
        destination.setValue("B1");
        break;
      default:
        break;
    }

  }
  
  private void makeTrains(TrackMaker track)
  {
    int i = 1;
    Train train;
    for(TrainView trainView : track.getTrainViews())
    {
      train = new Train(String.valueOf(i));
      train.setTrainView(trainView);
      trains.add(train);
      i++;
    }
    
    i = 0;
    
    while(i < trains.size())
    {
      for(Line line : track.getLines())
      {
        if(i < trains.size())
        {
          line.getStartPoint().addTrain(trains.get(i));
        }

        i += 1;

        if(i < trains.size())
        {
          line.getEndPoint().addTrain(trains.get(i));
        }

        i += 1;
      }
    }
  }
}
