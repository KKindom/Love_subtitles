package sample;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

import com.jfoenix.controls.JFXComboBox;
import it.sauronsoftware.jave.EncoderException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

/**
 * @program: untitled
 * @description: 主界面控制器
 * @author: kkindom
 * @create: 2021-02-28 18:49
 **/
public class Host_controller_video
{

    @FXML
    Button button1;
    //设置下拉列表
    @FXML
    JFXComboBox setting;
    @FXML
    MediaView video;
    MediaPlayer mediaPlayer;
    @FXML
    private void initialize()
    {

        System.out.println("hello");
        System.out.println(video.getFitHeight());
        setting.getItems().addAll(
                "语音转写设置",
                "机器翻译设置"
        );
    }


    public void start_video(ActionEvent actionEvent)
    {
        String path="C:\\au_result\\1.mp4";

        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        video.setMediaPlayer(mediaPlayer);
        System.out.println(media.getWidth());

        get_vodeo();
        mediaPlayer.setAutoPlay(true);
    }

    public void puse_video(ActionEvent actionEvent)
    {
        mediaPlayer.pause();
    }

    public void restart_video(ActionEvent actionEvent)
    {
        mediaPlayer.play();
    }

    void get_vodeo()
    {
        File source = new File("C:\\au_result\\1.mp4");

        Encoder encoder = new Encoder();
        MultimediaInfo m = null;
        try {
            m = encoder.getInfo(source);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        // 视频帧宽高
        int hight=m.getVideo().getSize().getHeight();
        int width=m.getVideo().getSize().getWidth();
        Double real_h=560/(width/(double)hight);
        real_h=(460-real_h)/2;
        video.setY(real_h);
    }
}




