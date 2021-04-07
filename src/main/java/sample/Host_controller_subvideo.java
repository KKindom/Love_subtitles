package sample;

import Utils.DialogBuilder;
import Utils.Sub_video_task;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
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
 * @description: 视频字幕控制器
 * @author: kkindom
 * @create: 2021-04-01 12:32
 **/
public class Host_controller_subvideo
{
    //输入文件位置，保存文件前缀，源语言，需要语言，字幕字体 字体大小
    String in_file_pat="",pre_save_path="",orgin="auto",first="cn",sub_font_type="宋体",sub_font_size="10px";
    //字幕文号，视频质量
    int video_qu=0;
    boolean flag=false;
    Map<Integer,String> sub_type_list=new HashMap<Integer,String>();
    Sub_video_task sub_video_task=new Sub_video_task();
    @FXML
    JFXButton file_in;
    @FXML
    JFXComboBox sub_first1,sub_first2,video_q,sub_orgin,sub_first;
    @FXML
    JFXProgressBar pbr;
    @FXML
    private void initialize() {
        pbr.setProgress(0);
        //初始化下拉框
        sub_orgin.getItems().addAll("默认(原字幕)", "中文", "英文", "日文", "韩文");
        sub_first.getItems().addAll("默认(原字幕)", "中文", "英文", "日文", "韩文");
        sub_first1.getItems().addAll("宋体", "行书", "楷书", "微软雅黑", "仿宋");
        sub_first2.getItems().addAll("一般", "10px", "15px", "20px", "25px");
        video_q.getItems().addAll("原画质", "高清", "标清");
        sub_orgin.getSelectionModel().selectFirst();
        sub_first.getSelectionModel().selectFirst();
        sub_first1.getSelectionModel().selectFirst();
        sub_first2.getSelectionModel().selectFirst();
        video_q.getSelectionModel().selectFirst();
        String sub[]={"auto","cn","en","ja","ko"};
        String sub_f[]={"宋体", "行书", "楷书", "微软雅黑", "仿宋"};
        String sub_s[]={"10px", "10px", "15px", "20px", "25px"};
        //绑定监听
        sub_orgin.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            orgin=sub[Integer.parseInt(newv.toString())];
            System.out.println(newv.toString());
            System.out.println("源语言："+orgin);

        });
        sub_first.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            first=sub[Integer.parseInt(newv.toString())];
            System.out.println(newv.toString());
            System.out.println("翻译语言："+first);

        });
        sub_first1.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            sub_font_type=sub_f[Integer.parseInt(newv.toString())];
            System.out.println(newv.toString());
            System.out.println("选择字幕字体："+sub_font_type);

        });
        sub_first2.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            sub_font_size=sub_s[Integer.parseInt(newv.toString())];
            System.out.println(newv.toString());
            System.out.println("选择字幕大小："+sub_font_size);

        });
        video_q.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->
        {
            video_qu=Integer.parseInt(newv.toString());
            System.out.println(newv.toString());
            System.out.println("视频质量："+video_qu);

        });
        //添加任务结果消息监听
        sub_video_task.messageProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //下载提示
                if(newValue.equals("成功下载"))
                {
                    new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("下载成功！\n 文件保存路径为："+pre_save_path).setNegativeBtn("了解","#ff3333").create();
                }
                else
                {
                    pbr.setProgress(0);
                    new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("下载失败！请尝试其他方式:仅生成字幕").setNegativeBtn("了解", "#ff3333").create();
                }
            }
        });
    }
    //开始生成字幕视频
    public void start_downsubvideo(ActionEvent actionEvent)
    {

        //检查信息是否完整

        //添加反悔按钮
        new DialogBuilder(file_in).setPositiveBtn("我在考虑", new DialogBuilder.OnClickListener() {
            @Override
            public void onClick() {
                flag=true;
            }
        }, "#ff3333").setNegativeBtn("我意已决","#ff4444").setTitle("温馨提醒").setMessage("当使用视频字幕功能时，完成速度取决于计算机运算能力，若非必要，优先级：字幕下载>仅生成字幕>生成字幕视频！").create();
       //是否返回
        if(!flag) {
            sub_video_task.setTask(1);
            sub_video_task.setVideo_qu(video_qu);
            sub_video_task.setFirst(first);
            sub_video_task.setOrgin(orgin);
            sub_video_task.setIn_file_pat(in_file_pat);
            sub_video_task.setPre_save_path(pre_save_path);
            sub_video_task.setSub_font_size(sub_font_size);
            sub_video_task.setSub_font_type(sub_font_type);
            sub_video_task.start();
        }
        else {
            return;
        }
    }
    //预览字幕视频
    public void pre_downsubvideo(ActionEvent actionEvent)
    {
        sub_video_task.setTask(2);
        sub_video_task.setVideo_qu(video_qu);
        sub_video_task.setFirst(first);
        sub_video_task.setOrgin(orgin);
        sub_video_task.setIn_file_pat(in_file_pat);
        sub_video_task.setPre_save_path(pre_save_path);
        sub_video_task.setSub_font_size(sub_font_size);
        sub_video_task.setSub_font_type(sub_font_type);
        sub_video_task.start();
    }
    //停止生成字幕视频
    public void cancel_downsubvideo(ActionEvent actionEvent)
    {
        pbr.setProgress(0);
        if(sub_video_task.isRunning())
        {
            sub_video_task.cancel();
        }
        else{
            sub_video_task.cancel();
        }
        sub_video_task=new Sub_video_task();
    }

    //保存文件路径选择
    public void chose_path(ActionEvent actionEvent)
    {
        // 输入所选择文件的路径
        FileChooser fileChooser=new FileChooser();
        Stage stage = (Stage) file_in.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        in_file_pat =file.getPath();
        //检查
        if(!check_file(in_file_pat))
        {
            new DialogBuilder(file_in).setTitle("温馨提醒").setMessage("对不起，你选择的文件有误，本程序仅支持mp4/avi/rmvb类型视频文件！").setNegativeBtn("了解", "#ff3333").create();
            in_file_pat="";
            return;
        }
        file_in.setText(in_file_pat);
        System.out.print(file.getPath());
    }
    //检查后缀是否为srt/ass
    public Boolean check_file(String file_path)
    {
        String []pre_Filename_Extension=file_path.split("\\.");
        pre_save_path=pre_Filename_Extension[pre_Filename_Extension.length-2];
        System.out.println(pre_save_path);
        String Filename_Extension=pre_Filename_Extension[pre_Filename_Extension.length-1];
        System.out.println(Filename_Extension);
        if(Filename_Extension.equalsIgnoreCase("mp4")|Filename_Extension.equalsIgnoreCase("avi")|Filename_Extension.equalsIgnoreCase("rmvb"))
        {

            return true;
        }
        else
        {

        }
        return false;
    }
}
