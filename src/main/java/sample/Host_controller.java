package sample;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import com.jfoenix.controls.JFXButton;
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
    //相关显示控制
    @FXML
    Pane h_setting,h_sub,h_subchange,h_makesub,h_subvideo;
    @FXML
    private void initialize()
    {

        System.out.println("hello");

    }

    //点击字幕文件转译 监听
    public void TEST(ActionEvent actionEvent)
    {


        h_subchange.setVisible(true);
        h_sub.setVisible(false);
        h_setting.setVisible(false);
        h_makesub.setVisible(false);
        h_subvideo.setVisible(false);
        System.out.println("字幕文件转译按钮");
    }

    //点击设置 按钮监听
    public void c_setting(ActionEvent actionEvent)
    {

        h_setting.setVisible(true);
        h_sub.setVisible(false);
        h_subchange.setVisible(false);
        h_makesub.setVisible(false);
        h_subvideo.setVisible(false);
        System.out.println("设置按钮");
    }
    //点击下载字幕文件监听
    public void down_sub(ActionEvent actionEvent)
    {

        h_setting.setVisible(false);
        h_subchange.setVisible(false);
        h_sub.setVisible(true);
        h_makesub.setVisible(false);
        h_subvideo.setVisible(false);
        System.out.println("搜索字幕按钮");
    }
    //点击生成字幕监听
    public void makesub(ActionEvent actionEvent)
    {

        h_setting.setVisible(false);
        h_subchange.setVisible(false);
        h_sub.setVisible(false);
        h_makesub.setVisible(true);
        h_subvideo.setVisible(false);
        System.out.println("生成字幕按钮");
    }
    //点击生成字幕视频监听
    public void makesubvideo(ActionEvent actionEvent)
    {
        h_subchange.setVisible(false);
        h_sub.setVisible(false);
        h_setting.setVisible(false);
        h_makesub.setVisible(false);
        h_subvideo.setVisible(true);
    }

    public void test(ActionEvent actionEvent)
    {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/KKindom/Love_subtitles/issues"));
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
    }
    }






