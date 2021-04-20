package sample;

import Utils.*;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Utils.Change_ASS.ASSTo_ARR;
import static Utils.Change_ASS.Requset_listToAss;
import static Utils.Change_SRT.SRT_SUBBASE;
import static Utils.Change_SRT.XF_SRT;
import static Utils.WebITS.MT;

/**
 * @program: untitled
 * @description: 转译字幕文件控制器
 * @author: kkindom
 * @create: 2021-03-30 11:50
 **/
public class Host_controller_subchange
{
    //输入字幕文件路径
    String in_savesubpath="",save_prepath;
    //输入字幕类型 默认0 1为srt 2为ass
    //选择转译方式 默认为0  单语转译 1  双语转译
    int sub_type=0,change_type=0;
    //利用hashmap存储选择语言与转译语言
    Map<Integer,String> sub_type_list=new HashMap<Integer,String>();
    //对应选择语言
    String orgin,first,second;
    @FXML
    JFXComboBox sub_orgin,sub_first,sub_second;
    @FXML
    JFXButton file_in;
    @FXML
    JFXProgressBar pbr;
    //下载线程
    Sub_change_task sub_change_task=new Sub_change_task();
    //初始化操作
    @FXML
    private void initialize()
    {
        pbr.setProgress(0);
        System.out.println("hello");
        //下拉组添加选项
        sub_orgin.getItems().addAll("不知道","中文","英文");
        sub_first.getItems().addAll("默认(原字幕)","中文","英文","日文","韩文","俄语","法语","德语","藏语","西班牙语","葡萄牙语");
        sub_second.getItems().addAll("默认(原字幕)","中文","英文","日文","韩文","俄语","法语","德语","藏语","西班牙语","葡萄牙语");
        sub_orgin.getSelectionModel().selectFirst();
        sub_first.getSelectionModel().selectFirst();
        sub_second.getSelectionModel().selectFirst();
        orgin="auto";
        second="auto";
        first="auto";
        String sub_list[]={"auto","cn","en","ja","ko","ru","fr","de","ti","es","pt"};
        //初始化
        for(int i=0;i<sub_list.length;i++)
        {
            sub_type_list.put(i,sub_list[i]);
        }
        sub_orgin.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            System.out.println(newv.toString());
            orgin=sub_type_list.get(Integer.parseInt(newv.toString()));
            System.out.println(orgin);

        });
        sub_first.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            System.out.println(newv.toString());
            first=sub_type_list.get(Integer.parseInt(newv.toString()));
            System.out.println(first);

        });
        sub_second.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            System.out.println(newv.toString());
            second=sub_type_list.get(Integer.parseInt(newv.toString()));
            //若第二转译语言为默认则为单语转译
            change_type=newv.toString().equals("0")?0:1;
            System.out.println(second);
        });
        //添加任务结果消息监听
        sub_change_task.messageProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //下载提示
                if(newValue.equals("SRT"))
                {
                    new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("转译成功！转译文件保存在:"+save_prepath+".srt下").setNegativeBtn("了解", "#ff3333").create();
                    pbr.setProgress(0);
                }
                else if(newValue.equals("ASS"))
                {
                    new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("转译成功！转译文件保存在:"+save_prepath+"生成.ass下").setNegativeBtn("了解", "#ff3333").create();
                    pbr.setProgress(0);
                }
                else
                {
                    new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("对不起，本程序仅支持SRT/ASS类型字幕文件！").setNegativeBtn("了解", "#ff3333").create();
                }
            }
        });
    }
    //取消字幕转译
    public void cancel_changesub(ActionEvent actionEvent)
    {
        pbr.setProgress(0);
        if(sub_change_task.isRunning())
        {
            sub_change_task.cancel();
        }
        else{
            sub_change_task.cancel();
        }
        sub_change_task= new Sub_change_task();

    }
    //启动字幕转译
    @SneakyThrows
    public void start_changesub(ActionEvent actionEvent)
    {
        //判断数据是否填写
        if(in_savesubpath.equals("")|first.equals("auto"))
        {
            new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("请注意未完成一下操作：\n 1.选择srt/ass字幕文件 \n 2.未选择第一转译语言").setNegativeBtn("了解", "#ff3333").create();
            return;
        }
        sub_change_task.setFirst(first);
        sub_change_task.setChange_type(change_type);
        sub_change_task.setOrgin(orgin);
        sub_change_task.setSub_type(sub_type);
        sub_change_task.setSave_prepath(save_prepath);
        sub_change_task.setIn_savesubpath(in_savesubpath);
        sub_change_task.setSecond(second);
        sub_change_task.start();
    }
    //保存文件路径选择
    public void chose_path(ActionEvent actionEvent)
    {
        // 输入所选择文件的路径
        FileChooser fileChooser=new FileChooser();
        Stage stage = (Stage) file_in.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        in_savesubpath =file.getPath();
        //检查
        if(!check_file(in_savesubpath))
        {
            new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("对不起，你选择的文件有误，本程序仅支持SRT/ASS类型字幕文件！").setNegativeBtn("了解", "#ff3333").create();
            in_savesubpath="";
            return;
        }
        file_in.setText(in_savesubpath);
        System.out.print(file.getPath());
    }
    //检查后缀是否为srt/ass
    public Boolean check_file(String file_path)
    {
        String []pre_Filename_Extension=file_path.split("\\.");
        save_prepath=pre_Filename_Extension[pre_Filename_Extension.length-2];
        System.out.println(save_prepath);
        String Filename_Extension=pre_Filename_Extension[pre_Filename_Extension.length-1];
        System.out.println(Filename_Extension);
        if(Filename_Extension.equalsIgnoreCase("ass")|Filename_Extension.equalsIgnoreCase("srt"))
        {
            sub_type=Filename_Extension.equalsIgnoreCase("ass")?2:1;
            return true;
        }
        else
        {
            sub_type=0;
        }
        return false;
    }
}
