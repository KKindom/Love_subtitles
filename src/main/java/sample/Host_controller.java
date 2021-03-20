package sample;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
/**
 * @program: untitled
 * @description: 主界面控制器
 * @author: kkindom
 * @create: 2021-02-28 18:49
 **/
public class Host_controller
{

    @FXML
    Button button1;
    //设置下拉列表
    @FXML
    JFXComboBox setting;
    @FXML
    MediaView video;
    @FXML
    private void initialize()
    {
        String path="C:\\au_result\\1.mp4";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        video.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        System.out.println("hello");
        System.out.println(video.getFitHeight()+"\n"+video.getFitWidth());
        setting.getItems().addAll(
                "语音转写设置",
                "机器翻译设置"
        );
    }



}




