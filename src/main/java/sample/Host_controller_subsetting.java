package sample;

import Utils.AppModel;
import Utils.DialogBuilder;
import Utils.Down_sub_task;
import Utils.Mytask;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * @program: untitled
 * @description: 用于网络获取字幕控制器
 * @author: kkindom
 * @create: 2021-03-29 16:27
 **/
public class Host_controller_subsetting
{
    //fxml控件
    @FXML
    ToggleGroup toggleGroup;
    @FXML
    JFXProgressBar prbar;
    @FXML
    JFXTextField v_name;
    @FXML
    JFXButton savefilepath;

    //下载线程
    Down_sub_task down_sub_task=new Down_sub_task();
    //填充数据
    //下载类型
    int type;
    //搜索内容+保存文件路径
    String data,savesubpath;
    @FXML
    private void initialize()
    {

        System.out.println("hello");
        //单选组添加选择事件
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton r = (RadioButton) newValue;
                System.out.println("已选择:" + r.getText());
                type=r.getText().equals("默认推荐")?1:2;
            }
        });
        //设置下载线程监听
        down_sub_task.progressProperty().addListener(new ChangeListener<Number>() {
            //newValue 为 workDone/max
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
                prbar.setProgress(newValue.doubleValue());

            }
        });
        //添加任务结果消息监听
        down_sub_task.messageProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //下载提示
                if(newValue.equals("成功下载"))
                {
                    savesubpath=savesubpath.equals("")?"C:\\桌面":savesubpath;
                    new DialogBuilder(v_name).setTitle("温馨提醒").setMessage("下载成功！\n 文件保存路径为："+savesubpath).setNegativeBtn("了解","#ff3333").create();
                }
                else
                {
                    prbar.setProgress(0);
                    new DialogBuilder(v_name).setTitle("温馨提醒").setMessage("下载失败！请尝试中文+英文输入格 \n 或者网站其他方式下载: 字幕库下载").setHyperLink("http://zmk.pw/").setNegativeBtn("了解", "#ff3333").create();
                }
            }
        });


    }
    //开始下载字幕按钮监听
    public void start_downsub(ActionEvent actionEvent)
    {
       String data=v_name.getText();
        //检查信息
        if(v_name.getText().equals(""))
        {
            new DialogBuilder(v_name).setTitle("温馨提醒").setMessage("请输入你需要搜索的电影名称！").setNegativeBtn("了解", "#ff3333").create();
            return;
        }
        //填充内容
        data=v_name.getText();
        down_sub_task.setData(data);
        down_sub_task.setType(type);
        down_sub_task.setUrl("http://zmk.pw/");
        down_sub_task.start();
    }
    //取消下载字幕按钮监听
    public void cancel_downsub(ActionEvent actionEvent)
    {
        prbar.setProgress(0);
        if(down_sub_task.isRunning())
        {
            down_sub_task.cancel();
        }
        else{
            down_sub_task.cancel();
        }
    }
    //保存文件路径选择
    public void chose_path(ActionEvent actionEvent)
    {
        // 输入所选择文件的路径
        DirectoryChooser directoryChooser=new DirectoryChooser();
        Stage stage = (Stage) savefilepath.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        savesubpath=file.getPath();
        savefilepath.setText(savesubpath);
        //设置字幕文件下载位置
        down_sub_task.setSavepath(savesubpath);
        System.out.print(file.getPath());
    }


}

