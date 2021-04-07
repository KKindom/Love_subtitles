package sample;

import Utils.DialogBuilder;
import Utils.Sub_change_task;
import Utils.Sub_make_task;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: untitled
 * @description: 生成字幕功能控制器
 * @author: kkindom
 * @create: 2021-03-30 14:38
 **/
public class Host_controller_makesub
{
    String in_savesubpath,save_prepath,orgin,first;
    //输入文件类型 选择字幕类型
    int sub_type_n,file_type;
    boolean sub_main=false;
    Map<Integer,String> sub_type_list=new HashMap<Integer,String>();
    Sub_make_task sub_make_task=new Sub_make_task();
    //绑定组件
    @FXML
    JFXComboBox sub_orgin,sub_need,sub_type;
    @FXML
    JFXButton file_in,stop_b,start_b,pre_b;
    @FXML
    JFXRadioButton sub_dou;
    @FXML
    JFXProgressBar pbr;
    //初始化操作
    @FXML
    private void initialize()
    {
        sub_orgin.getItems().addAll("默认(原字幕)","中文","英文","日文","韩文");
        sub_need.getItems().addAll("默认(原字幕)","中文","英文","日文","韩文");
        sub_type.getItems().addAll("默认(SRT)","ASS");
        sub_orgin.getSelectionModel().selectFirst();
        sub_need.getSelectionModel().selectFirst();
        sub_type.getSelectionModel().selectFirst();
        orgin="auto";
        sub_type_n=1;
        first="auto";
        String sub_list[]={"auto","cn","en","ja","ko"};
        //初始化
        for(int i=0;i<sub_list.length;i++)
        {
            sub_type_list.put(i,sub_list[i]);
        }
        //绑定监听
        sub_orgin.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            orgin=sub_type_list.get(Integer.parseInt(newv.toString()));
            System.out.println(newv.toString());
            System.out.println("原语言："+orgin);
        });
        sub_need.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            first=sub_type_list.get(Integer.parseInt(newv.toString()));
            System.out.println(newv.toString());
            System.out.println("翻译语言："+first);

        });
        sub_type.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            sub_type_n=newv.toString().equals("0")?1:2;
            System.out.println("选择字幕类型"+sub_type_n);
        });
        //添加任务结果消息监听
        sub_make_task.messageProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //下载提示
                if(newValue.equals("true"))
                {
                    new DialogBuilder(start_b).setTitle("温馨提醒").setMessage("生成字幕成功！\n ").setNegativeBtn("了解","#ff3333").create();
                }
                else
                {
                    pbr.setProgress(0);
                    new DialogBuilder(start_b).setTitle("温馨提醒").setMessage("生成字幕失败！请联系我：").setHyperLink("http://zmk.pw/").setNegativeBtn("了解", "#ff3333").create();
                }
            }
        });
        }

    //开始生成字幕
    public void start_makesub(ActionEvent actionEvent)
    {
        sub_main=sub_dou.isSelected()?true:false;
        sub_make_task.setSub_main(sub_main);
        sub_make_task.setFile_type(file_type);
        sub_make_task.setFirst(first);
        sub_make_task.setOrgin(orgin);
        sub_make_task.setIn_savesubpath(in_savesubpath);
        sub_make_task.setSave_prepath(save_prepath);
        sub_make_task.setSub_type_n(sub_type_n);
        sub_make_task.start();

    }
    //停止生成字幕
    public void cancel_makesub(ActionEvent actionEvent)
    {
        pbr.setProgress(0);
        if(sub_make_task.isRunning())
        {
            sub_make_task.cancel();
        }
        else{
            sub_make_task.cancel();
        }
        sub_make_task= new Sub_make_task();
    }
    //保存/选择文件路径选择
    public void chose_path(ActionEvent actionEvent)
    {
        // 输入所选择文件的路径
        FileChooser fileChooser=new FileChooser();
        Stage stage = (Stage) file_in.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        in_savesubpath =file.getPath();
        //检查
        if(check_file(in_savesubpath)==0)
        {
            new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("对不起，你选择的文件有误。\n 本程序仅支持mp4/avi/3gp/rmvb格式视频！\n 本程序仅支持MP3/acc/wma格式音频！").setNegativeBtn("了解", "#ff3333").create();
            in_savesubpath="";
            return;
        }
        file_in.setText(in_savesubpath);
        System.out.print(file.getPath());
    }
    //检查后缀是否为视频/音频
    public int check_file(String file_path)
    {
        String []pre_Filename_Extension=file_path.split("\\.");
        save_prepath=pre_Filename_Extension[pre_Filename_Extension.length-2];
        System.out.println(save_prepath);
        String Filename_Extension=pre_Filename_Extension[pre_Filename_Extension.length-1];
        System.out.println(Filename_Extension);
        if(Filename_Extension.equalsIgnoreCase("mp4")|Filename_Extension.equalsIgnoreCase("avi")|Filename_Extension.equalsIgnoreCase("rmvb")|Filename_Extension.equalsIgnoreCase("3gp"))
        {
            file_type=1;
            return 1;
        }
        if(Filename_Extension.equalsIgnoreCase("mp3")|Filename_Extension.equalsIgnoreCase("wma")|Filename_Extension.equalsIgnoreCase("aac"))
        {
            file_type=2;
            return 2;
        }
        save_prepath="";
        file_type=0;
        return 0;
    }
}
