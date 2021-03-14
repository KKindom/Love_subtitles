package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Controller
{
    int i=1;
    public  Controller()
    {

    }
    @FXML
    private Button button1;
    @FXML
    private MenuBar menubar1;
    @FXML
    private AnchorPane pane1;
    @FXML
    private void initialize()
    {
       System.out.println(button1.getText());
        //menubar1.setPrefWidth(pane1.getWidth());
    }
    @FXML
    private  void  action()
    {
//        button1.setText(i+"");
//        i++;
        //System.out.println(button1.getText());
    }


    public void clicktest(MouseEvent mouseEvent)
    {
        System.out.println("撒大苏打");
        if(mouseEvent.getClickCount()%3==0)
        {
            System.out.println("666");
        }

    }
}
