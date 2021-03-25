package Utils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Data;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @program: untitled
 * @description: 预览视频时字幕线程
 * @author: kkindom
 * @create: 2021-03-21 19:38
 **/
@Data
public class My_task_sub extends Service<Number> {
    //获取字幕数组地址
    List<sub_base> sub_list=null;
    Boolean pause_type=false;
    //赋值
   public My_task_sub(List<sub_base> list)
    {
        sub_list=list;
    }
    //赋值
    public My_task_sub()
    {
        sub_list=null;
    }
    @Override
    protected Task<Number> createTask() {
            Task<Number> mytask=new Task<Number>() {
                @Override
                protected Number call() throws Exception
                {
                    int time=0,list_time=0;
                    System.out.println("运行自定义任务");
                    for (int i=0;i<sub_list.size();i++)
                    {
                        //获取每个字幕持续时间
                        sub_list.get(i).time=(sub_list.get(i).end_t-sub_list.get(i).start_t);

                        //System.out.println(sub_list.get(i).time+" "+i);
                    }
                    System.out.println("12345");
                    for (int i=0;i<sub_list.size();i++)
                    {
                        if(pause_type==true)
                        {
                            wait();
                        }
                        else {
                            running();
                        }

                        list_time=sub_list.get(i).getTime();
                        while(time<sub_list.get(i).getStart_t())
                        {
                            time+=10;
                            Thread.sleep(10);
                        }
                        while(list_time>0)
                        {
                            time+=10;
                            list_time-=10;
                            Thread.sleep(10);
                            this.updateMessage(i+"");
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
        System.out.println("ready"+Platform.isFxApplicationThread());
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


