package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * @program: untitled
 * @description: 用于设置获取字幕控制器
 * @author: kkindom
 * @create: 2021-03-29 16:27
 **/

public class Host_controller_subsetting
{

    @FXML
    ToggleGroup toggleGroup;


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
            }
        });
    }

    public void start_downsub(ActionEvent actionEvent)
    {
    }

    public void cancel_downsub(ActionEvent actionEvent)
    {
    }
}
