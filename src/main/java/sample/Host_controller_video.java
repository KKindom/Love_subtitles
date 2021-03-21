package sample;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;

import Utils.My_task_sub;
import Utils.sub_base;
import com.jfoenix.controls.JFXComboBox;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import it.sauronsoftware.jave.EncoderException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import static Utils.Change_SRT.SRT_SUBBASE;

/**
 * @program: untitled
 * @description: 主界面视频播放控制器
 * @author: kkindom
 * @create: 2021-02-28 18:49
 **/
public class Host_controller_video
{

    List<sub_base> sub_baseList=null;

    @FXML
    MediaView video;
    MediaPlayer mediaPlayer;
    @FXML
    Label sub_txt;
    //字幕任务

    My_task_sub my_task_sub=new My_task_sub();

    @FXML
    private void initialize()
    {

        System.out.println("hello");
        System.out.println(video.getFitHeight());
    }


    public void start_video(ActionEvent actionEvent)
    {
        String path="C:\\au_result\\1.mp4";
        sub_baseList= SRT_SUBBASE("E:\\桌面\\测试视频\\字幕中文.srt");
        my_task_sub.setSub_list(sub_baseList);
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        video.setMediaPlayer(mediaPlayer);
        System.out.println(media.getWidth());
        //调整视频以及字幕位置
        get_vodeo();

        mediaPlayer.setAutoPlay(true);

        //字幕任务启动以及监听
        my_task_sub.start();
        my_task_sub.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                sub_txt.setText(sub_baseList.get(Integer.parseInt(newValue)).data);
                System.out.println("ttt");
            }

        });
    }


    public void puse_video(ActionEvent actionEvent)
    {
        mediaPlayer.pause();
    }

    public void restart_video(ActionEvent actionEvent)
    {
        mediaPlayer.play();
    }
    //调整视频以及字幕位置
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
        sub_txt.setLayoutY((int)((460-real_h)/2+real_h*0.8));
        System.out.println(hight*0.8+real_h);
        real_h=(460-real_h)/2;
        video.setY(real_h);
    }
}




