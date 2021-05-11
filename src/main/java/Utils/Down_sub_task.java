package Utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileTypeUtil;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @program: untitled
 * @description: 下载字幕线程
 * @author: kkindom
 * @create: 2021-04-01 17:39
 **/
@Data
public class Down_sub_task extends Service<Number> {
    String url,data,savepath="";
    int type;
    boolean task_result=false;
    public Down_sub_task()
    {
    }
    @Override
    protected Task<Number> createTask()
    {
        Task<Number> mytask=new Task<Number>() {
            @Override
            protected Number call() throws Exception
            {
                this.updateProgress(0.1,1);
                WebClient webClient=new WebClient();
                //设置10秒超时
                webClient.getOptions().setTimeout(10*1000);
                HtmlPage page=null;
                page = webClient.getPage(url);

                //获取字幕网搜索框 并设置搜索内容
                HtmlElement usernameEle =(HtmlElement) page.getByXPath("/html/body/div[1]/div/div[1]/div/form/div/input").get(0);
                //聚焦搜索按钮
                usernameEle.focus();
                usernameEle.type(data);
                //点击首页上的搜索按钮
                page = ((DomElement) page.getByXPath("/html/body/div[1]/div/div[1]/div/form/div/span/button").get(0)).click();
               // System.out.println(page.asXml());
                this.updateProgress(0.2,1);
                //判断是否有结果
                HtmlElement result =(HtmlElement) page.getByXPath("/html/body/div[2]/div/div/div[2]/div[2]").get(0);
                List<?> result_list=page.getByXPath("/html/body/div[2]/div/div/div[2]/div[2]/div");
               // System.out.println(result_list.size());
                //无结果
                if(result_list.size()==0)
                {
                   task_result=false;
                    this.updateProgress(0,1);
                }
                //有结果
                else
                {
                    //默认下载第一个字幕

                    if(type==1) {
//                //获取链接
                        this.updateProgress(0.4,1);
                        HtmlElement link = (HtmlElement) page.getByXPath(" /html/body/div[2]/div/div/div[2]/div[2]/div[2]/div/table/tbody/tr[1]/td[1]/a").get(0);
                        this.updateProgress(0.6,1);
                      task_result=  chose_link(page,link,data);
                        this.updateProgress(1,1);
                    }
                    //下载推荐项
                    else
                    {
                        this.updateProgress(0.3,1);
                        //拿到更多字幕按钮
                        List<HtmlElement> more_link=page.getByXPath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/div/table/tbody/tr[6]/td/a");
                       // System.out.println(more_link.size());
                        if(more_link.size()==0)
                        {
                            //分析仅有字幕下载量最大的
                            List<HtmlElement> Best_List = page.getByXPath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/div/table/tbody/tr");
                            //获取下载量最大的链接下标
                            int index=Best_link_index(Best_List,1);
                            //获取下载量最大的链接
                            //获取链接
                            HtmlElement link = (HtmlElement) page.getByXPath(" /html/body/div[2]/div/div/div[2]/div[2]/div[2]/div/table/tbody/tr["+index+"]/td[1]/a").get(0);
                            this.updateProgress(0.5,1);
                           task_result= chose_link(page,link,data);
                            this.updateProgress(1,1);


                        }
                        //分析所有字幕中下载量最大的
                        else
                        {
                            this.updateProgress(0.3,1);
                            //拿到最匹配的首选项的字幕列表
                            HtmlElement More_link = (HtmlElement) page.getByXPath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/div/table/tbody/tr[6]/td/a").get(0);
                            page= More_link.click();
                            List<HtmlElement> num_index=page.getByXPath("/html/body/div[2]/div/div[2]/div/table/tbody/tr[1]/td");
                            int num=num_index.size()-1;
                            List<HtmlElement> More_linklist=page.getByXPath("//*[@id=\"subtb\"]/tbody/tr/td["+num+"]");
                            //获取下载量最大的链接下标
                            int index= Best_link_index(More_linklist,2);
                          //  System.out.println(index);
                            this.updateProgress(0.5,1);
                            //获取下载量最大的链接
                            //获取链接
                            HtmlElement link = (HtmlElement) page.getByXPath("//*[@id=\"subtb\"]/tbody/tr["+index+"]/td[1]/a ").get(0);
                            task_result=chose_link(page,link,data);
                            this.updateProgress(1,1);
                            System.out.println("尝试");
                        }
                    }
                }

                String meg=task_result==true?"成功下载":"下载失败";
                this.updateMessage(meg);
                if (task_result==true)
                    this.updateProgress(0,1);
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


    //保存文件
    public  Boolean saveFile(Page pages, String data,String houzhui) throws Exception
    {

        InputStream is = pages.getWebResponse().getContentAsStream();
        FileOutputStream output = new FileOutputStream(savepath+"\\"+houzhui);
        IOUtils.copy(is, output);
        output.close();
        return true;
    }

    //进入详情下载页面
    @SneakyThrows
    public Boolean chose_link(HtmlPage page, HtmlElement link, String data)
    {
        //点击进入链接
        page = link.click();
        System.out.println(page.asXml());

        //进入最终下载界面
        link = (HtmlElement) page.getElementById("down1");
        page = link.click();
        System.out.println(page.asXml());
        System.out.println("进行这步");
        //使用备用下载链接
        link = (HtmlElement) page.getByXPath("/html/body/main/div/div/div/table/tbody/tr/td[1]/div/ul/li[1]/a").get(0);

        Page page1 = link.click();

        //获取后缀
        String a= String.valueOf(page1.getUrl());
        String b[]=a.split("/");
        a=b[b.length-1];
        String decodeStr = Base64.decodeStr(a);
        decodeStr.replaceAll(" ","");
        b=decodeStr.split("\\|");
        String  Filename_Extension=b[1];

        System.out.println(page.asXml());
        System.out.println("进行最后一步");
        if (page1.getWebResponse().getContentAsStream() == null) {
            System.out.println("测试");
            page1 = link.click();

        }
        saveFile(page1, data,Filename_Extension);
        return true;
    }
    //获取最多下载量的链接下标
    public static int Best_link_index(List<HtmlElement> Best_List,int type)
    {
        if(type==1) {
            String[] at = null;
            int num = 0, index = 0, Max = 0;
            //获取最大的下载量
            for (int i = 0; i < Best_List.size(); i++) {
                at = Best_List.get(i).getTextContent().split("\n");
                if (num < Integer.parseInt(at[at.length - 1])) {
                    num = Integer.parseInt(at[at.length - 1]);
                    index = i + 1;
                }
                System.out.println(num);
            }
            return index;
        }
        else
        {
            String num_s=null;
            Double Max= 0.0,num=0.0;
            int index=0;
            //获取最大的下载量
            for(int i=0;i<Best_List.size();i++)
            {
                num_s=Best_List.get(i).getTextContent();
                num=num_s.substring(num_s.length()-1,num_s.length()).equals("万")? Double.parseDouble(num_s.substring(0,num_s.length()-1))*10000:Integer.parseInt(num_s);
                if(Max<num)
                {
                    Max=num;
                    index=i+1;
                }
                System.out.println(Max);
            }
            return index;
        }
    }

}
