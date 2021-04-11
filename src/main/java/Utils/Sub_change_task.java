package Utils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Utils.Change_ASS.ASSTo_ARR;
import static Utils.Change_ASS.Requset_listToAss;
import static Utils.Change_SRT.SRT_SUBBASE;
import static Utils.Change_SRT.XF_SRT;

/**
 * @program: untitled
 * @description: 字幕转译线程
 * @author: kkindom
 * @create: 2021-04-05 16:04
 **/
@Data
public class Sub_change_task extends Service<Number>
{
    //输入字幕文件路径
    String in_savesubpath,save_prepath;
    //输入字幕类型 默认0 1为srt 2为ass
    //选择转译方式 默认为0 单语转译 1 为双语转译
    int sub_type=0,change_type=0;
    //对应选择语言
    String orgin,first,second;

    //接口信息
    public static Api_Key api_key=new Api_Key();
    public     WebITS my_webITS=new WebITS();
    //初始化
    public Sub_change_task()
    {
        my_webITS.set_all(api_key.my_webits);
    }
    @Override
    protected Task<Number> createTask() {
        Task<Number> mytask = new Task<Number>() {
            @Override
            protected Number call() throws Exception
            {   List<sub_base> subBaseList = null;
                //单语转译
                if(change_type==0)
                {
                    //若为srt字幕文件
                    if (sub_type == 1)
                    {
                        //srt转字幕数组
                        this.updateProgress(0.1,1);
                        subBaseList = SRT_SUBBASE(in_savesubpath);
                        this.updateProgress(0.2,1);
                        //开始转译
                        subBaseList = my_webITS.MT(subBaseList, orgin, first, 1);
                        this.updateProgress(0.5,1);
                        //生成srt文件
                        try {
                            XF_SRT("",save_prepath+"生成.srt",subBaseList,1);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                        this.updateProgress(1,1);
                        this.updateMessage("SRT");
                    }
                    //若为ass字幕文件
                    else if (sub_type == 2)
                    {
                        this.updateProgress(0.1,1);
                        subBaseList=ASSTo_ARR(in_savesubpath, 1);
                        this.updateProgress(0.2,1);
                        //开始转译
                        subBaseList =my_webITS.MT(subBaseList, orgin, first, 1);
                        this.updateProgress(0.5,1);
                        Requset_listToAss(save_prepath+"生成.ass", subBaseList,1);
                        this.updateProgress(1,1);
                        this.updateMessage("ASS");
                    }
                    else
                    {
                        this.updateMessage("错误类型！");
                        return null;
                    }
                }
                //双语转译
                else
                {
                    //若为srt字幕文件
                    if (sub_type == 1)
                    {
                        //srt转字幕数组
                        this.updateProgress(0.1,1);
                        subBaseList = SRT_SUBBASE(in_savesubpath);
                        this.updateProgress(0.2,1);
                        //开始转译
                        subBaseList = my_webITS.MT(subBaseList, orgin, first, 2);
                        //若开启双语转译
                        if(!orgin.equals(second))
                            subBaseList = my_webITS.MT(subBaseList, orgin, second, 1);
                        this.updateProgress(0.5,1);
                        //生成srt文件
                        try {
                            XF_SRT("",save_prepath+"生成.srt",subBaseList,2);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                        this.updateProgress(1,1);
                        this.updateMessage("SRT");
                    }
                    //若为ass字幕文件
                    else if (sub_type == 2)
                    {
                        this.updateProgress(0.1,1);
                        subBaseList=ASSTo_ARR(in_savesubpath, 2);
                        this.updateProgress(0.2,1);
                        //开始转译
                        subBaseList =my_webITS.MT(subBaseList, orgin, first, 2);
                        //若开启双语转译
                        if(!orgin.equals(second))
                            subBaseList = my_webITS.MT(subBaseList, orgin, second, 1);
                        this.updateProgress(0.5,1);
                        Requset_listToAss(save_prepath+"生成.ass", subBaseList,2);
                        this.updateProgress(1,1);
                        this.updateMessage("ASS");
                    }
                    else
                    {
                        this.updateMessage("错误类型！");
                        return null;
                    }
                }


                return null;
            }

        };
        return mytask;
    }
    @Override
    protected void executeTask(Task<Number> task) {
        super.executeTask(task);
        task.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("executeTask valueProperty");
            }
        });
    }
    @Override
    protected void ready() {
        super.ready();
        System.out.println("ready"+ Platform.isFxApplicationThread());
    }

    @Override
    protected void scheduled() {
        super.scheduled();
        System.out.println("scheduled "+Platform.isFxApplicationThread());
    }

    @Override
    protected void running() {
        super.running();
        System.out.println("running "+Platform.isFxApplicationThread());
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("succeeded "+Platform.isFxApplicationThread());
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        System.out.println("cancelled "+Platform.isFxApplicationThread());
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("failed "+ Platform.isFxApplicationThread());
    }

}
