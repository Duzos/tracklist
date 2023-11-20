package com.duzo.tracklist;

import com.duzo.tracklist.util.SoundManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.image.Image;
public class Main extends Application {
    private Text NowPlayingText;
    private String dir;

    public void SetPlayingText(String name) {
        if (NowPlayingText == null) return;

        NowPlayingText.setText("Now Playing: " + name);
    }

    public Image GetCoverImage() {
        if (this.dir == null) return null;

        File cover = new File(this.dir + "\\cover.png");
        if (!cover.exists() || cover.isDirectory()) return null;

        try {
            return new Image(new FileInputStream(this.dir + "\\cover.png"));
        } catch (Exception e) {
            return null;
        }
    }

    public Scene CreateStartupScene(Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        Text title = new Text("duzos tracklist!!");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(title,0,0,2,1);


        Label pathPrompt = new Label("Music Path:");
        grid.add(pathPrompt, 0, 1);

        TextField pathTextField = new TextField();
        grid.add(pathTextField, 1, 1);

        Button btn = new Button("Start");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 3);
        btn.setOnAction(event -> {
            stage.setScene(SwitchToPlayerScene(stage,pathTextField.getText()));
        });

        SoundManager.setOnSongChange(() -> {
            SetPlayingText(SoundManager.getSongName());
        });

        return new Scene(grid, 500, 275);
    }

    public Scene SwitchToPlayerScene(Stage stage,String dir) {
        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        SoundManager.queueAllInFolder(dir);
        this.dir = dir;

        if (this.GetCoverImage() != null) {
            ImageView imageView = new ImageView(this.GetCoverImage()    );

            imageView.setX(50);
            imageView.setY(25);

            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            imageView.setPreserveRatio(true);

            grid.add(imageView,0,0);
        }

        this.NowPlayingText = new Text("Now Playing: ");
        this.NowPlayingText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(this.NowPlayingText,grid.getColumnCount() + 1,0);

        Button btn = new Button("pause");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER_LEFT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, grid.getColumnCount() - 1, 1);
        btn.setOnAction(event -> {
            if (SoundManager.isPlayerPaused()) {
                SoundManager.unpause();
                btn.setText("pause");
            } else {
                SoundManager.pause();
                btn.setText("play");
            }
        });

        Button skipBtn = new Button("skip");
        HBox skipHbBtn = new HBox(10);
        skipHbBtn.setAlignment(Pos.CENTER_LEFT);
        skipHbBtn.getChildren().add(skipBtn);
        grid.add(skipHbBtn, grid.getColumnCount(), 1);
        skipBtn.setOnAction(event -> {
            SoundManager.skip();
        });

        return new Scene(grid, 500, 275);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("duzos tracklist");

        stage.setScene(CreateStartupScene(stage));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}