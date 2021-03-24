package sample;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

import Utils.AppModel;
import com.jfoenix.controls.JFXComboBox;
import it.sauronsoftware.jave.EncoderException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;

import com.jfoenix.controls.JFXButton;

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
    JFXButton t_button;
    @FXML
    private void initialize()
    {

        System.out.println("hello");
        setting.getItems().addAll(
                "语音转写设置",
                "机器翻译设置"
        );
    }


    public void TEST(ActionEvent actionEvent)
    {
        // 获取结果界面控制器
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Host_interface_video.fxml"));
        Host_controller_video control = (Host_controller_video) loader.getController();
        // 设置结果界面内容
        control.model.setText(t_button.getText());
    }
}




