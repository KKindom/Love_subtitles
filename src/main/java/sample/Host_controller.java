package sample;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import Utils.AppModel;
import com.jfoenix.controls.JFXComboBox;
import it.sauronsoftware.jave.EncoderException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;

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
    JFXButton setting;
    @FXML
    JFXButton t_button;
    //设置相关信息 显示控制
    @FXML
    Pane h_setting;
    //预览视频 显示控制
    @FXML
    Pane   h_video;
    //下载字幕 显示控制
    @FXML
    Pane h_sub;
    @FXML
    private void initialize()
    {

        System.out.println("hello");

    }


    public void TEST(ActionEvent actionEvent)
    {
        // 获取结果界面控制器
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Host_interface_video.fxml"));
        Host_controller_video control = (Host_controller_video) loader.getController();
        // 设置结果界面内容
        control.model.setText(t_button.getText());
    }

    //点击设置 按钮监听
    public void c_setting(ActionEvent actionEvent)
    {

        h_setting.setVisible(true);
        h_video.setVisible(false);
        h_sub.setVisible(false);
        System.out.println("设置按钮");
    }

    public void down_sub(ActionEvent actionEvent)
    {

        h_setting.setVisible(false);
        h_video.setVisible(false);
        h_sub.setVisible(true);
        System.out.println("搜索字幕按钮");
    }
}




