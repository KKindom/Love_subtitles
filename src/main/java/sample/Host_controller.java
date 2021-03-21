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
public class Host_controller
{

    //设置下拉列表
    @FXML
    JFXComboBox setting;
    @FXML
    private void initialize()
    {

        System.out.println("hello");
        setting.getItems().addAll(
                "语音转写设置",
                "机器翻译设置"
        );
    }

}




