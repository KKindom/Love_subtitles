package sample;

/**
 * @program: untitled
 * @description: 测试
 * @author: kkindom
 * @create: 2021-01-07 10:38
 **/

import Utils.Mytask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lombok.Data;


@Data
public class Controller2
{
    public Mytask tt=new Mytask();
    public Double i=0.0,t=0.0;
    public  Controller2()
    {

    }
    @FXML
    private Button button1;
    @FXML
    private MenuBar menubar1;
    @FXML
    private ProgressIndicator progressIndicator1;
    @FXML
    private ProgressBar progressBar1;
    @FXML
    public ChoiceBox<String> devicesNames;


    @FXML
    private void initialize()
    {
        System.out.println(button1.getText());
        //menubar1.setPrefWidth(pane1.getWidth());
        for(int i=0;i<10;i++)
        {
            devicesNames.getItems().add("11233");
            devicesNames.getItems().add("23");
        }
        devicesNames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue+"asdas"+ oldValue);
            }
        });
    }
    @FXML
    private  void  action()
    {
//        button1.setText(i+"");
//        i++;
        //System.out.println(button1.getText());

        //进度条
//        new Thread(new Runnable() {
//
//            public void run() {
//                for (Double i=0.0;i<=1;i+=0.01)
//                {
//                    t=i;
//
//                    Platform.runLater(new Runnable() {
//                        public void run() {
//                            progressBar1.setProgress(t);
//                            System.out.println(t);
//                        }
//                    });
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("I have a great message for you!");
        //alert.getButtonTypes().remove(0);//删除按钮
        alert.getButtonTypes().add(ButtonType.NEXT);//添加按钮
        Button button= (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Mytask");
            }
        });
        alert.showAndWait();
    }

    public void clicktest(MouseEvent mouseEvent)
    {
        System.out.println("撒大苏打");
        if(mouseEvent.getClickCount()%3==0)
        {
            System.out.println("666");
        }

    }
    public void test(ActionEvent actionEvent)
    {
        System.out.println("带编程");
        System.out.println(tt.getKk()+"");
    }
}

