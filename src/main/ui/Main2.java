package ui;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.ImportantReminder;
import model.ListOfImportantReminders;
import model.ListOfReminders;
import model.Reminder;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;
import java.net.URL;

public class Main2 extends Application {

    private static Button manageReminders;
    private static Button manageGeneral;
    private static Button manageImportant;
    private static Button manageNested;
    private static Button manageShopping;
    private static Button checkLists;
    private static Button sceneRemindersBack;
    private static Button closeApp;

    private static ListOfReminders remindersList = new ListOfReminders();
    private static ListOfImportantReminders importantRemindersList = new ListOfImportantReminders();
    //private static NestedReminderMap nestedReminders;

    private static final String API_KEY = "INSERT API KEY HERE";
    private static final String WEATHER_QUERY = "http://api.openweathermap.org/data/2.5/weather?q=Vancouver&APPID=";
    private static String theUrl = WEATHER_QUERY + API_KEY;
    private static String weatherVal;

    private static final String SAVE_ERROR_MESSAGE = "Error in saving file, data may be lost on closing.";
    private static final String SAVEFILE_GENERAL = "GeneralReminders.json";
    private static final String SAVEFILE_IMPORTANT = "ImportantReminders.json";
    private AudioStream audioStream;

    private Scene sceneMain;
    private Scene sceneReminders;
    private Scene sceneGeneral;
    private Scene sceneImportant;
    //public Scene sceneNested;
    private Scene sceneDisplay;
    private Scene generalRemindersDisplay;
    private Scene importantRemindersDisplay;


    //private static TableView<ImportantReminder> importantRemindersTable;


    private Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        loadGeneral();
        loadImportant();
        loadAudio();

        //API call
        useWeatherApi();
        Label weatherLabel = weatherLabelSetup();


        // main scene
        sceneMainButtonSetup();
        sceneMainSetup(weatherLabel);

        // reminders scene
        sceneRemindersButtonSetup();
        sceneRemindersSetup();


        sceneGeneralSetup();


        sceneImportantSetup();


        sceneDisplaySetup();

        //startup main scene
        window.setTitle("ListKeeper");
        window.setScene(sceneMain);
        window.show();


    }

    public Label weatherLabelSetup() {
        Label weatherLabel = new Label("Today's weather is: " + weatherVal);
        weatherLabel.setUnderline(true);
        weatherLabel.setFont(new Font("Open Sans SemiBold", 16));
        weatherLabel.setStyle("-fx-text-fill: #1A237E; ");
        return weatherLabel;
    }


    // main menu
    private void sceneMainSetup(Label weatherLabel) {
        GridPane gridMain = new GridPane();
        gridMain.setPadding(new Insets(20,30,30,50));
        gridMain.setVgap(50);
        gridMain.setHgap(10);

        Label titleLabel = new Label("Welcome to the ListKeeper!");
        titleLabel.setFont(new Font("Open Sans SemiBold", 16));
        titleLabel.setStyle("-fx-text-fill: #1A237E; ");
        gridMain.setConstraints(titleLabel, 2,0);
        gridMain.setConstraints(manageReminders, 0,1);
        gridMain.setConstraints(manageShopping, 0, 2);
        gridMain.setConstraints(checkLists, 0, 3);
        gridMain.setConstraints(closeApp,0,4);
        gridMain.setConstraints(weatherLabel, 2, 5);

        gridMain.getChildren().addAll(titleLabel, manageReminders, manageShopping, checkLists, closeApp, weatherLabel);
        sceneMain = new Scene(gridMain, 640, 480);
    }


    private void sceneMainButtonSetup() {

        manageReminders = new JFXButton("Manage reminders");
        buttonColourSetter(manageReminders, "#BBDEFB");
        manageReminders.setOnAction(event -> window.setScene(sceneReminders));

        manageShopping = new JFXButton("Manage shopping list");
        buttonColourSetter(manageShopping, "#BBDEFB");
        //manageShopping.setOnAction(event -> window.setScene(sceneShopping));

        checkLists = new JFXButton("Check existing lists");
        buttonColourSetter(checkLists, "#BBDEFB");
        checkLists.setOnAction(event -> window.setScene(sceneDisplay));



        closeApp = new JFXButton("Exit application");
        buttonColourSetter(closeApp, "#BBDEFB");
        closeApp.setOnAction(event -> System.exit(1));
    }

    private void loadImportant() {
        try {
            importantRemindersList.load(SAVEFILE_IMPORTANT);
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found, old data may be lost");
        }
    }


    // add reminders menu
    private void sceneRemindersButtonSetup() {
        manageGeneral = new JFXButton("Check general reminders");
        buttonColourSetter(manageGeneral, "#BBDEFB");
        manageGeneral.setOnAction(event -> window.setScene(sceneGeneral));

        manageImportant = new JFXButton("Check important reminders");
        buttonColourSetter(manageImportant, "#BBDEFB");
        manageImportant.setOnAction(event -> window.setScene(sceneImportant));

        manageNested = new JFXButton("Check stacked reminders");
        buttonColourSetter(manageNested, "#BBDEFB");
        //manageNested.setOnAction(event -> window.setScene(sceneNested));

        sceneRemindersBack = new JFXButton("Go back");
        buttonColourSetter(sceneRemindersBack, "#BBDEFB");
        sceneRemindersBack.setOnAction(event -> window.setScene(sceneMain));
    }



    private void sceneRemindersSetup() {
        GridPane gridReminders = new GridPane();
        gridReminders.setPadding(new Insets(30,30,30,50));
        gridReminders.setVgap(50);
        gridReminders.setHgap(10);

        gridReminders.setConstraints(manageGeneral, 0,0);
        gridReminders.setConstraints(manageImportant, 0, 1);
        gridReminders.setConstraints(manageNested, 0, 2);
        gridReminders.setConstraints(sceneRemindersBack, 0, 3);
        gridReminders.getChildren().addAll(manageGeneral, manageImportant, manageNested, sceneRemindersBack);
        sceneReminders = new Scene(gridReminders, 640, 480);
    }


    // general reminders add menu
    private void sceneGeneralSetup() {

        Label generalReminder = new Label("General Reminders");
        TextField newReminder = new JFXTextField();
        newReminder.setPromptText("Enter General Reminder");
        Button enter = new JFXButton("Add");
        buttonColourSetter(enter, "#BBDEFB");
        enter.setOnAction(event -> remindersList.addReminder(newReminder.getText()));

        Button backGeneral = new Button("Go back");
        buttonColourSetter(backGeneral, "#BBDEFB");
        backGeneral.setOnAction(event -> window.setScene(sceneReminders));

        GridPane gridGeneral = generalGridSetup(generalReminder, newReminder, enter, backGeneral);

        gridGeneral.getChildren().addAll(generalReminder, newReminder, backGeneral, enter);
        sceneGeneral = new Scene(gridGeneral, 640, 480);
    }

    private GridPane generalGridSetup(Label generalReminder, TextField newReminder, Button enter, Button backGeneral) {
        GridPane gridGeneral = new GridPane();
        gridGeneral.setPadding(new Insets(30,30,30,50));
        gridGeneral.setVgap(10);
        gridGeneral.setHgap(10);

        gridGeneral.setConstraints(generalReminder, 0,0);
        gridGeneral.setConstraints(newReminder, 0, 1);
        gridGeneral.setConstraints(enter,0,2);
        gridGeneral.setConstraints(backGeneral, 0, 3);
        return gridGeneral;
    }



    // important reminders add menu
    private void sceneImportantSetup() {
        Label importantReminder = new Label("Important Reminders");


        TextField reminderName = new JFXTextField();
        reminderName.setPromptText("Enter Important Reminder");


        TextField reminderUrgency = new JFXTextField();
        reminderUrgency.setPromptText("Enter importance, 'high', 'medium', or 'low'");
        reminderUrgency.setPrefWidth(300);


        Button enter = new JFXButton("Add");
        buttonColourSetter(enter, "#BBDEFB");
        enter.setOnAction(event -> {
            importantReminderAdder(reminderName, reminderUrgency);
        });

        Button backImportant = new JFXButton("Go back");
        buttonColourSetter(backImportant, "#BBDEFB");
        backImportant.setOnAction(event -> window.setScene(sceneReminders));

        gridImportantSetup(importantReminder, reminderName, reminderUrgency, enter, backImportant);
    }

    private void importantReminderAdder(TextField reminderName, TextField reminderUrgency) {
        ImportantReminder nextReminder = new ImportantReminder(reminderName.getText(), reminderUrgency.getText());
        importantRemindersList.addReminder(nextReminder);
        try {
            importantRemindersList.save(SAVEFILE_IMPORTANT);
        } catch (IOException e) {
            System.out.println(SAVE_ERROR_MESSAGE);
        }
    }


    private void gridImportantSetup(Label importantReminder,
                                   TextField reminderName,
                                   TextField reminderUrgency,
                                   Button enter, Button backImportant) {
        GridPane gridImportant = new GridPane();
        gridImportant.setPadding(new Insets(30,30,30,50));
        gridImportant.setVgap(10);
        gridImportant.setHgap(10);

        gridImportant.setConstraints(importantReminder, 0,0);
        gridImportant.setConstraints(reminderName, 0, 1);
        gridImportant.setConstraints(reminderUrgency,0,2);
        gridImportant.setConstraints(enter, 0, 3);
        gridImportant.setConstraints(backImportant, 0, 4);

        gridImportant.getChildren().addAll(importantReminder, reminderName, reminderUrgency, enter, backImportant);
        sceneImportant = new Scene(gridImportant, 640, 480);
    }







    private void sceneDisplaySetup() {

        Button displayGeneral = new JFXButton("Display General Reminders");
        buttonColourSetter(displayGeneral, "#BBDEFB");
        displayGeneral.setOnAction(event -> {
            sceneGeneralDisplaySetup();
            window.setScene(generalRemindersDisplay);
        }
        );

        Button displayImportant = new JFXButton("Display Important Reminders");
        buttonColourSetter(displayImportant, "#BBDEFB");
        displayImportant.setOnAction(event -> {
            sceneImportantDisplaySetup();
            window.setScene(importantRemindersDisplay);
        });

        GridPane gridDisplay = getGridDisplay(displayGeneral, displayImportant);

        sceneDisplay = new Scene(gridDisplay, 640, 480);

    }

    private GridPane getGridDisplay(Button displayGeneral, Button displayImportant) {
        Button backDisplay = new JFXButton("Go back");
        buttonColourSetter(backDisplay, "#BBDEFB");
        backDisplay.setOnAction(event -> window.setScene(sceneMain));

        GridPane gridDisplay = new GridPane();
        gridDisplay.setPadding(new Insets(30,30,30,50));
        gridDisplay.setVgap(50);
        gridDisplay.setHgap(10);

        gridDisplay.setConstraints(displayGeneral, 0,0);
        gridDisplay.setConstraints(displayImportant, 0, 1);
        gridDisplay.setConstraints(backDisplay, 0, 2);
        gridDisplay.getChildren().addAll(displayGeneral, displayImportant, backDisplay);
        return gridDisplay;
    }


    private void sceneGeneralDisplaySetup() {

        VBox layoutGeneralDisplay = new VBox(30);
        layoutGeneralDisplay.setPadding(new Insets(60));

        for (Reminder r : remindersList.generalReminders) {
            JFXCheckBox checkbox = new JFXCheckBox(r.getName());
            checkbox.setStyle("-jfx-checked-color: #0D47A1;");

            checkbox.setSelected(statusChecker(r));
            checkbox.setOnAction(event -> reminderTicker(r));
            layoutGeneralDisplay.getChildren().add(checkbox);
        }

        Button goBack = new JFXButton("Go back");
        buttonColourSetter(goBack, "#BBDEFB");
        goBack.setOnAction(event -> {
            saveGeneral();
            window.setScene(sceneDisplay);
        });

        layoutGeneralDisplay.getChildren().add(goBack);
        generalRemindersDisplay = new Scene(layoutGeneralDisplay, 640,480);

    }

    private void reminderTicker(Reminder r) {
        if (r.getStatus().equals("done")) {
            r.undoEvent();
        } else {
            r.doEvent();
        }
    }

    private void saveGeneral() {
        try {
            remindersList.save(SAVEFILE_GENERAL);
        } catch (IOException e) {
            System.out.println("Unable to save");
        }
    }

    private void loadGeneral() {
        try {
            remindersList.load(SAVEFILE_GENERAL);
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found");
        }
    }

    private Boolean statusChecker(Reminder r) {
        if (r.getStatus().equals("not done")) {
            return false;
        } else {
            return true;
        }
    }

    // important reminders display
    private void sceneImportantDisplaySetup() {
        TableColumn<ImportantReminder, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(300);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<ImportantReminder, String> urgencyColumn = new TableColumn<>("Priority");
        urgencyColumn.setMinWidth(200);
        urgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgency"));

        TableColumn<ImportantReminder, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        TableView<ImportantReminder> importantRemindersTable = new TableView<>();


        importantRemindersTable.setItems(loadImportantReminders());
        importantRemindersTable.getColumns().addAll(nameColumn, urgencyColumn, statusColumn);

        Button goBack = new JFXButton("Go back");
        buttonColourSetter(goBack, "#BBDEFB");
        goBack.setOnAction(event -> window.setScene(sceneDisplay));

        VBox importantVbox = new VBox();
        importantVbox.getChildren().addAll(importantRemindersTable, goBack);
        importantRemindersDisplay = new Scene(importantVbox, 640, 480);
    }

    private ObservableList<ImportantReminder> loadImportantReminders() {
        ObservableList<ImportantReminder> irl = FXCollections.observableArrayList();
        irl.addAll(Main2.importantRemindersList.importantReminders);
        return irl;
    }




    //API methods
    private BufferedReader getApiInfo() throws IOException {
        URL weatherUrl = new URL(theUrl);
        return new BufferedReader(new InputStreamReader(weatherUrl.openStream()));
    }

    private StringBuilder buildRetrievedInfo(BufferedReader br) throws IOException {
        String line;
        StringBuilder retrievedWeather = new StringBuilder();

        while ((line = br.readLine()) != null) {
            retrievedWeather.append(line);
            retrievedWeather.append(System.lineSeparator());
        }
        return retrievedWeather;
    }

    private String parseRetrievedInfo(StringBuilder retrievedWeather) {
        String json = retrievedWeather.toString();
        JsonParser parser = new JsonParser();
        JsonObject jobject0 = (JsonObject) parser.parse(json);
        JsonArray jsonArray0 = (JsonArray) jobject0.get("weather");
        System.out.println(jsonArray0);

        JsonObject jobject1 = (JsonObject) jsonArray0.get(0);
        return jobject1.get("description").toString();
    }

    private void useWeatherApi() {
        try {
            BufferedReader br = getApiInfo();
            StringBuilder retrievedWeather = buildRetrievedInfo(br);
            weatherVal = parseRetrievedInfo(retrievedWeather);


        } catch (IOException e) {
            System.out.println("Couldn't access weather data");
        }
    }


    private void loadAudio() {
         String audioFile = "audio.wav";
         InputStream inputStream = null;
         try {
            inputStream = new FileInputStream(audioFile);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to load audio file");
        }


        try {
            audioStream = new AudioStream(inputStream);
        } catch (IOException e) {
            System.out.println("Unable to play audio file");;
        }

        AudioPlayer.player.start(audioStream);
    }

    private void buttonColourSetter(Button button, String colour) {
        button.setStyle("-fx-background-color: " + colour + "; ");
    }

}
