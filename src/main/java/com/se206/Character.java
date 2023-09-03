package com.se206;

import java.io.IOException;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import nz.ac.auckland.se206.SpriteAnimation;

public class Character extends AnchorPane{
    @FXML
    private ImageView active_img;

    private int columns  =   4;
    private int count    =  13;
    private int offset_x =  18;
    private int offset_y =  25;
    private int frame_width    = 374;
    private int frame_height   = 243;

    private String sprite_sheet;

    
    public Character(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/character.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        initElements();
    }

    private void initElements() {
        Image sprite_sheet = new Image(getClass().getResource("/images/The_Horse_in_Motion.jpg").toExternalForm());
        active_img.setImage(sprite_sheet);
        active_img.setViewport(new Rectangle2D(offset_x, offset_y, frame_width, frame_height));

        final Animation animation = new SpriteAnimation(
                active_img,
                Duration.millis(1000),
                count, columns,
                offset_x, offset_y,
                frame_width, frame_height
        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }

    public void setSprite_sheet(String sprite_sheet) {
        this.sprite_sheet = sprite_sheet;
    }

    // public ImageView getActive_img() {
    //     return active_img;
    // }
    
    // public void setActive_img(ImageView active_img) {
    //     this.active_img = active_img;
    // }
    
    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOffset_x() {
        return offset_x;
    }

    public void setOffset_x(int offset_x) {
        this.offset_x = offset_x;
    }

    public int getOffset_y() {
        return offset_y;
    }

    public void setOffset_y(int offset_y) {
        this.offset_y = offset_y;
    }

    public int getFrame_width() {
        return frame_width;
    }

    public void setFrame_width(int frame_width) {
        this.frame_width = frame_width;
    }

    public int getFrame_height() {
        return frame_height;
    }

    public void setFrame_height(int frame_height) {
        this.frame_height = frame_height;
    }
}
