package Utils;

import javafx.application.Platform;
import javafx.concurrent.Task;
import lombok.Data;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @program: untitled
 * @description: 测试工具类
 * @author: kkindom
 * @create: 2021-01-07 11:08
 **/
//中断后需要重新new 线程 和任务
   //若不想重新new 使用别的方式
@Data
public class Mytask extends Task<Number>
{
   public int kk=333;
   //都不在ui线程
   @Override
   public boolean cancel(boolean mayInterruptIfRunning) {
      return super.cancel(mayInterruptIfRunning);
   }

   @Override
   protected void updateProgress(long workDone, long max) {
      super.updateProgress(workDone, max);
   }

   //更新线程 workDone当前任务量 max 总任务量
   @Override
   protected void updateProgress(double workDone, double max) {
      super.updateProgress(workDone, max);
   }
   //更新消息
   @Override
   protected void updateMessage(String message) {
      super.updateMessage(message);
   }
   //更新标题
   @Override
   protected void updateTitle(String title) {
      super.updateTitle(title);
   }
   //这里更新的是UI线程
   //返回的值会在这里被调用
   @Override
   protected void updateValue(Number value) {

      System.out.println(value);
      System.out.println(Platform.isFxApplicationThread());
      super.updateValue(value);
   }
   //多任务处理不是UI线程
   @Override
   protected Number call() throws Exception
   {
      this.updateTitle("拷贝任务");
      FileInputStream fis=new FileInputStream("E:\\桌面\\专业\\东软\\untitled\\src\\main\\resources\\video\\123.mp4");
      FileOutputStream fos=new FileOutputStream("E:\\桌面\\专业\\东软\\untitled\\src\\main\\resources\\video\\1.mp4");
      byte []bytes= new byte[10000];
      double max=fis.available();
      double sum=0,pro;
      int i=0;
      while((i=fis.read(bytes,0,bytes.length) )!=-1)
      {
         if (this.isCancelled()) {
            break;
         }
         fos.write(bytes,0,i);
         this.updateProgress(sum,max);
         sum+=i;
         pro=sum/max;
        // Thread.sleep(10);
         if(pro<0.5)
         {
            this.updateMessage("请稍后");
         }
         else if(pro<0.8)
         {
            this.updateMessage("即将完成");
         }
         else if(pro!=1)
         {
            this.updateMessage("就差亿点点！");
         }
         else
         {
            this.updateMessage("已经完成！");
         }
         //System.out.println(pro);
      }
      fis.close();
      fos.close();
      System.out.println("测试多任务");
      System.out.println(Platform.isFxApplicationThread());
      return 123;
   }
}
