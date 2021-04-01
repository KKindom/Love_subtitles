package sample;

import Utils.Mytask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import lombok.Data;

/**
 * @program: untitled
 * @description:
 * @author: kkindom
 * @create: 2021-01-10 19:50
 **/
@Data
public class TestTask
{
    @FXML
    Button st_bu;
    @FXML
    Button en_bu;
    @FXML
    Label label_s,label_v,label_m,label_t;
    @FXML
    ProgressBar pb;

    Mytask mytask=new Mytask();;
    Thread thread=new Thread(mytask);;
    //开始任务监听
    public void test(ActionEvent actionEvent)
    {
        thread.start();
        pb.setProgress(0.5);
    }
    //取消任务监听
    public void cencal(ActionEvent actionEvent)
    {
        if(mytask.isCancelled())
        {

        }
        else
            {
            mytask.cancel();
        }

    }
    @FXML
    private  void initialize()
    {
        System.out.println("这里是初始化");
        //添加任务进度监听
        mytask.progressProperty().addListener(new ChangeListener<Number>() {
            //newValue 为 workDone/max
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
                pb.setProgress(newValue.doubleValue());
            }
        });
        //添加任务消息监听
        mytask.messageProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                label_m.setText(newValue);
            }
        });
    }
}

