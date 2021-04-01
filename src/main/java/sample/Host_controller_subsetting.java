package sample;

import Utils.Down_sub_task;
import Utils.Mytask;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * @program: untitled
 * @description: 用于网络获取字幕控制器
 * @author: kkindom
 * @create: 2021-03-29 16:27
 **/

public class Host_controller_subsetting
{

    @FXML
    ToggleGroup toggleGroup;

    @FXML
    JFXProgressBar prbar;
    Down_sub_task down_sub_task=new Down_sub_task();
    @FXML
    JFXTextField v_name;
    int type;
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
                if(r.getText().equals("默认推荐"))
                {
                    type=1;
                }
                else
                {
                    type=2;
                }
            }
        });
        down_sub_task.progressProperty().addListener(new ChangeListener<Number>() {
            //newValue 为 workDone/max
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
                prbar.setProgress(newValue.doubleValue());

            }
        });
        //添加任务消息监听
//        down_sub_task.messageProperty().addListener(new ChangeListener<String>()
//        {
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                label_m.setText(newValue);
//            }
//        });
    }

    public void start_downsub(ActionEvent actionEvent)
    {

        down_sub_task.setData("复仇者联盟3");
        down_sub_task.setType(1);
        down_sub_task.setUrl("http://zmk.pw/");
        down_sub_task.start();
    }

    public void cancel_downsub(ActionEvent actionEvent)
    {
        if(down_sub_task.isRunning())
        {
            down_sub_task.cancel();
        }
        else{
            down_sub_task.cancel();
        }
    }
}
