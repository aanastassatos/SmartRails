package GUI;

import SmartRails.*;

import Train_and_Track.Train;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class SmartRailsWindow extends Application
{
  static final int WINDOW_WIDTH = 1400;
  static final int WINDOW_HEIGHT = 700;
  static Random rand = new Random();
  private static final String font = "Monospaced";
  private static final int fontsize = 20;
  private static ArrayList<Train> trains = new ArrayList<>();
  private ChoiceBox<String> destination = new ChoiceBox<>();

  private static int NUM_TRAINS = 1;

  /**
   * main method of project SmartRails
   * @param args
   */
  public static void main(String[] args)
  {
    //System.out.println(Font.getFamilies());
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
    //makeTrains(track);

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
    openingScene(stage, scene, track);
    stage.setOnCloseRequest(e -> System.exit(0));
    stage.show();
  }

  /**
   * private method openingScene
   * @param stage: stage of SmartRails
   * @param scene: Main scene with trains and stations
   *
   *             Creates opening scene with title,
   *             and option to choose train and destination
   */
  private void openingScene(Stage stage, Scene scene, TrackMaker track)
  {
    VBox vbox = new VBox();
    int vertInset = 100;
    int horizInset = 70;
    int spacing = 20;
    Color textColor = Color.ORANGERED;

    Background bkgd = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));

    Label pickTrain = new Label("How many trains would you like?");
    pickTrain.setTextFill(textColor);
    pickTrain.setFont(Font.font(font, 20));

    ChoiceBox<Integer> numOfTrains = new ChoiceBox<>();
    numOfTrains.getItems().addAll(1, 2);

    Image title = res.ResourceLoader.getTitle("SmartRailsTitle", (int)vbox.getWidth(), (int)vbox.getHeight());
    ImageView imageView = new ImageView(title);

    Button go = new Button();
    formatButton(go, "Set The Schedule", bkgd);
    go.setOnAction(e -> {
      NUM_TRAINS = numOfTrains.getValue();
      makeTrains(track);
      scheduleScene(stage, scene, bkgd, track);
    });

    vbox.setBackground(bkgd);
    vbox.setAlignment(Pos.CENTER);
    vbox.setSpacing(spacing);
    vbox.setPadding(new Insets(vertInset, horizInset, vertInset, horizInset));
    vbox.getChildren().addAll(imageView, pickTrain, numOfTrains, go);
    Scene opener = new Scene(vbox);
    stage.setScene(opener);
  }

  private void scheduleScene(Stage stage, Scene scene, Background bkgd, TrackMaker track)
  {
    int vertInset = 100;
    int horizInset = 70;
    int spacing = 20;

    VBox vBox = new VBox();
    vBox.setPadding(new Insets(vertInset, horizInset, vertInset, horizInset));
    vBox.setSpacing(spacing);
    vBox.setAlignment(Pos.CENTER);
    vBox.setBackground(bkgd);
    for(int i = 0; i < NUM_TRAINS; i++)
    {
      vBox.getChildren().add(trainSelection(i+1, bkgd, track));
    }

    Button sendTrain = new Button();
    formatButton(sendTrain, "Release the Trains.", bkgd);
    sendTrain.setOnAction(e -> {
      stage.setScene(scene);
      SmartRails smartRails = new SmartRails(trains);
      smartRails.setDestination(destination.getValue());
      new Thread(smartRails).start();
    });

    vBox.getChildren().add(sendTrain);
    Scene schedScene = new Scene(vBox);
    stage.setScene(schedScene);
  }

  private void setTrainAtStation(ChoiceBox<String> choice, Train train, TrackMaker track)
  {
    char sideCharacter = choice.getValue().charAt(0);
    int lineNumber = choice.getValue().charAt(1) - '0';
    if(sideCharacter == 'A') track.getLines().get(lineNumber).addTrainToStartPoint(train);
    else track.getLines().get(lineNumber).addTrainToEndPoint(train);
  }

  /**
   * private method formatButton
   * @param b: button to be formatted
   * @param text: text to go onto button
   * @param background: background color of button
   *
   *                  formats button to match
   */
  private void formatButton(Button b, String text, Background background)
  {
    b.setText(text);
    b.setFont(Font.font(font, fontsize));
    b.setTextFill(Color.ORANGERED);
    b.setBackground(background);
  }

  private HBox trainSelection(int trainNumber, Background bkgd, TrackMaker track)
  {
    int spacing = 20;
    HBox hBox = new HBox();
    hBox.setSpacing(spacing);
    hBox.setAlignment(Pos.CENTER);

    Image titleImage = res.ResourceLoader.getTitle("Train" + trainNumber + "_Title", (int)hBox.getWidth(), (int)hBox.getHeight());
    ImageView title = new ImageView(titleImage);
    ArrayList<ChoiceBox<String>> destinations = new ArrayList<>();
    for(int i = 0; i < 4; i++) destinations.add(new ChoiceBox<>());
    Button chooseLeft = new Button("LEFT");
    Button chooseRight = new Button("RIGHT");
    chooseLeft.setOnAction(e -> {
      chooseRight.setDisable(true);
      int i = 0;
      for(ChoiceBox<String> choice : destinations)
      {
        if(i % 2 == 0) choice.getItems().addAll("A0", "A1", "A2", "A3");
        else choice.getItems().addAll("B0", "B1", "B2", "B3");
        i++;
      }
    });

    chooseRight.setOnAction(e -> {
      chooseLeft.setDisable(true);
      int i = 0;
      for(ChoiceBox<String> choice : destinations)
      {
        if(i % 2 != 0) choice.getItems().addAll("A0", "A1", "A2", "A3");
        else choice.getItems().addAll("B0", "B1", "B2", "B3");
        i++;
      }
    });

    Button setSchedule = new Button();
    formatButton(setSchedule, "Set the Schedule.", bkgd);
    LinkedList<String> schedule = new LinkedList<>();
    setSchedule.setOnAction(e -> {
      setTrainAtStation(destinations.get(0), trains.get(trainNumber-1), track);
      for(int i = 1; i < destinations.size(); i++)
      {
        schedule.add(destinations.get(i).getValue());
      }
      trains.get(trainNumber-1).setSchedule(schedule);
    });

    hBox.getChildren().addAll(title, chooseLeft, chooseRight);
    for(ChoiceBox<String> choice : destinations) hBox.getChildren().add(choice);
    hBox.getChildren().add(setSchedule);
    return hBox;
  }

  private void makeTrains(TrackMaker track)
  {
    Train train;
    for(int i = 0; i < NUM_TRAINS; i++)
    {
      for (TrainView trainView : track.getTrainViews())
      {
        train = new Train(String.valueOf(i));
        train.setTrainView(trainView);
        trains.add(train);
      }
    }
  }
}
