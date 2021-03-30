package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;

/**
 * @program: untitled
 * @description: 转译字幕文件控制器
 * @author: kkindom
 * @create: 2021-03-30 11:50
 **/
public class Host_controller_subchange
{

    @FXML
    JFXComboBox sub_orgin,sub_first,sub_second;
    @FXML
    JFXButton file_in;
    @FXML
    private void initialize()
    {

        System.out.println("hello");
        //下拉组添加选项
        sub_orgin.getItems().addAll("不知道","中文","英文");
        sub_first.getItems().addAll("默认(原字幕语言)","中文","英文","日文","韩文");
        sub_second.getItems().addAll("默认(原字幕语言)","中文","英文","日文","韩文");

    }
    //取消字幕转译
    public void cancel_changesub(ActionEvent actionEvent)
    {

    }
    //启动字幕转译
    public void start_changesub(ActionEvent actionEvent)
    {

    }
}
