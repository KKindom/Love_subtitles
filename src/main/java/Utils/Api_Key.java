package Utils;

import lombok.Data;

/**
 * @program: untitled
 * @description: 保存讯飞接口信息
 * @author: kkindom
 * @create: 2021-04-11 19:36
 **/
@Data
public class Api_Key
{
    public   my_Webits my_webits;
    public     lfasr my_lfasr;
    public Api_Key()
    {
        testxml myfile=new testxml();
        this.my_webits=new my_Webits();
        this.my_lfasr=new lfasr();

        //初始化
        myfile.inint();
        String data1[]=myfile.getSet(1,1);
        if(data1[0].equals("null"))
            data1=myfile.getSet(1,0);
        my_lfasr.APP_ID=data1[0];
        my_lfasr.SECRET_KEY=data1[1];
        String data2[]=myfile.getSet(2,1);
        if(data2[0].equals("null"))
            data2=myfile.getSet(2,0);
        my_webits.APP_ID=data2[0];
        my_webits.API_SECRET=data2[1];
        my_webits.API_KEY=data2[2];
        System.out.println("初始化成功！");
    }
}
//机器翻译api
@Data
class my_Webits
{
  public   String APP_ID;
    public  String API_SECRET;
    public String API_KEY;
}
//语音转写api
@Data
class lfasr
{
    public String APP_ID;
    public  String SECRET_KEY;
}
