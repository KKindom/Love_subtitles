package sample;

import Utils.testxml;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * @program: untitled
 * @description:
 * @author: kkindom
 * @create: 2021-03-25 10:10
 **/
public class Host_Interface_Setting
{
    static testxml myfile=new testxml();
    @FXML
    JFXTextField set_1_1,set_1_2,set_2_1,set_2_2,set_2_3;

    @FXML
    private void initialize()
    {
        //初始化
        myfile.inint();
        String data1[]=myfile.getSet(1,1);
        if(data1[0].equals("null"))
            data1=myfile.getSet(1,0);
            set_1_1.setText(data1[0]);
            set_1_2.setText(data1[1]);

        String data2[]=myfile.getSet(2,1);
        if(data2[0].equals("null"))
            data2=myfile.getSet(2,0);
            set_2_1.setText(data2[0]);
            set_2_2.setText(data2[1]);
            set_2_3.setText(data2[2]);

    }

    public void default1(ActionEvent actionEvent)
    {
        String data[]=myfile.getSet(1,0);
            set_1_1.setText(data[0]);
            set_1_2.setText(data[1]);
    }
    public void default2(ActionEvent actionEvent)
    {
        String data[]=myfile.getSet(2,0);
        set_2_1.setText(data[0]);
        set_2_2.setText(data[1]);
        set_2_3.setText(data[2]);
    }

    public void save1(ActionEvent actionEvent)
    {
        String data[]=new String[2];
        data[0]=set_1_1.getText();
        data[1]=set_1_2.getText();
        myfile.modify(data,1);
    }
    public void save2(ActionEvent actionEvent)
    {
        String data[]=new String[3];
        data[0]=set_2_1.getText();
        data[1]=set_2_2.getText();
        data[2]=set_2_3.getText();
        myfile.modify(data,2);
    }

}

