package Utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
/**
 * @program: untitled
 * @description: 将语音转写结果合成srt字幕文件
 * @author: kkindom
 * @create: 2021-02-25 14:02
 **/


public class Change_SRT {


    //配置时间格式
    public static final String date  = "HH:mm:ss,SSS";
    public static final SimpleDateFormat sdf = new SimpleDateFormat(date, Locale.ROOT);

    public static void main(String[] args) throws Throwable
    {
        //XF_SRT("C:\\au_result\\au_result.txt", "C:\\au_result\\字幕.srt",null);
        //XF_ARR("C:\\au_result\\au_result.txt");
        List<sub_base> subBaseList= SRT_SUBBASE("E:\\桌面\\测试视频\\字幕2.srt");
    }

    /**
     * @description: 讯飞结果转str字幕文件
     * @author: kkindom
     * @create: 2021-02-25 14:02
     * @xunfeiFilePath： 讯飞结果保存文件
     * @srtFilePath： 处理结果srt文件
     * @param type 1为单语 2 为双语
     **/
    public static void XF_SRT(String xunfeiFilePath, String srtFilePath,List<sub_base> Data,int type) throws Throwable{
        StringBuilder sb = new StringBuilder();
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));

        if(!xunfeiFilePath.equals("")) {
            FileInputStream fis = new FileInputStream(xunfeiFilePath);

            byte[] bytes = new byte[9999999];
            fis.read(bytes);

            String data = new String(bytes);


            JSONArray array = JSONArray.parseArray(data.toString());
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = array.getJSONObject(i);
                String bg = object.getString("bg");
                String ed = object.getString("ed");

                long bgg = Long.valueOf(bg);
                long edd = Long.valueOf(ed);
                String startTime = sdf.format(bgg);
                String endTime = sdf.format(edd);

                String msg = object.getString("onebest");

                sb.append(i + 1).append("\n")
                        .append(startTime)
                        .append(" --> ")
                        .append(endTime).append("\n")
                        .append(msg)
                        .append("\n").append("\n");
            }

            BufferedWriter out = new BufferedWriter(new FileWriter(srtFilePath));
            out.write(sb.toString());
            out.close();
            fis.close();
        }
        else
        {
            for (int i = 0; i < Data.size(); i++) {
                String bg = Data.get(i).start_t+"";
                String ed =Data.get(i).end_t+"";

                long bgg = Long.valueOf(bg);
                long edd = Long.valueOf(ed);
                String startTime = sdf.format(bgg);
                String endTime = sdf.format(edd);

                String msg = type==1?Data.get(i).data:Data.get(i).data+"\n"+Data.get(i).data2;
                    sb.append(i + 1).append("\n")
                            .append(startTime)
                            .append(" --> ")
                            .append(endTime).append("\n")
                            .append(msg)
                            .append("\n").append("\n");


            }

            BufferedWriter out = new BufferedWriter(new FileWriter(srtFilePath));
            out.write(sb.toString());
            out.close();
        }
    }


    /**
     * @description: 讯飞结果转字幕数组
     * @author: kkindom
     * @create: 2021-02-25 14:02
     * @xunfeiFilePath： 讯飞结果保存文件
     **/
    public static List<sub_base> XF_ARR(String xunfeiFilePath,int type) throws Throwable
    {
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = new FileInputStream(xunfeiFilePath);

        byte[] bytes = new byte[9999999];
        fis.read(bytes);

        String data = new String(bytes);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        List<sub_base> arr=new ArrayList<sub_base>();
        JSONArray array = JSONArray.parseArray(data.toString());
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            int bg = Integer.parseInt(object.getString("bg"));
            int ed = Integer.parseInt(object.getString("ed"));
            String msg = object.getString("onebest");
            //写入字幕数组
            arr.add(new sub_base(bg,ed,msg));

        }
        System.out.println(arr.toString());
        //返回字幕数组
        return arr;
    }


    /**
     * @description: Srt字幕转字幕数组
     * @author: kkindom
     * @create: 2021-03-3 21:02
     * @xunfeiFilePath： Srt字幕文件位置
     **/
    public static List<sub_base> SRT_SUBBASE(String srtFilePath) {

        List<sub_base> srtList = new ArrayList<sub_base>();
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    srtFilePath),"UTF-8"));
            int pos = 0;
            String readline = null;
            sub_base subBase=null;
            StringBuffer buffer = new StringBuffer();
            while ((readline = br.readLine())!=null)
            {
                pos++;
                switch (pos) {
                    case 1:
                        break;
                    case 2:
                        subBase=convert(readline);
                        break;
                    case 3:
                        buffer.append(readline);
                        break;
                    default:
                        if(readline.equals("")){
                            pos = 0;
                            subBase.setData(buffer.toString());
                            srtList.add(subBase);
                            buffer.setLength(0);
                        } else
                            {
                            buffer.append("<br>"+readline);
                        }
                        break;
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("对不起没有字幕");
        }
        for (int i = 0; i < srtList.size(); i++) {
            System.out.println(srtList.get(i).getStart_t() + "="
                    + srtList.get(i).getEnd_t() + "\n"
                    + srtList.get(i).getData());
        }



        File file=new File("E:\\桌面\\au_result");
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
        try{//异常处理
            //如果Qiju_Li文件夹下没有Qiju_Li.txt就会创建该文件
            BufferedWriter bw=new BufferedWriter(new FileWriter("E:\\桌面\\au_result\\au_result.txt"));
            bw.write(srtList.toString());//写入切片后文件路径
            bw.close();//一定要关闭文件
        }catch(IOException e){
            e.printStackTrace();
        }



        return srtList;

    }
    //时间戳转化
    private static sub_base convert(String line) {
        if (line.indexOf("-->") > 0)
        {
            String[] tmp = line.split("-->");
            String t0 = conv(tmp[0]);
            String t1 = conv(tmp[1]);
            return new sub_base(Integer.parseInt(t0),Integer.parseInt(t1),"");
        }
        return  null;
    }
    //时间戳转化2
    private static String conv(String str) {
        String[] tmp = str.split(",");
        String times = tmp[0].trim();
        String ms = tmp[1].trim();
        if (ms.substring(0, 1).equals("0")) {
            if (ms.substring(1, 2).equals("0")) {
                if (ms.substring(2, 3).equals("0")) {
                    ms = "0";
                } else {
                    ms = ms.substring(2);
                }
            } else {
                ms = ms.substring(1);
            }
        }
        int w = Integer.parseInt(ms);
        String[] tm = times.split(":");
        String hh = tm[0];
        String mm = tm[1];
        String ss = tm[2];
        if (hh.substring(0, 1).equals("0")) {
            if (hh.substring(1, 2).equals("0")) {
                hh = "0";
            } else {
                hh = hh.substring(1);
            }
        }
        if (mm.substring(0, 1).equals("0")) {
            if (mm.substring(1, 2).equals("0")) {
                mm = "0";
            } else {
                mm = mm.substring(1);
            }
        }
        if (ss.substring(0, 1).equals("0")) {
            if (ss.substring(1, 2).equals("0")) {
                ss = "0";
            } else {
                ss = ss.substring(1);
            }
        }
        int h = Integer.parseInt(hh) * 60 * 60 * 1000;
        int m = Integer.parseInt(mm) * 60 * 1000;
        int s = Integer.parseInt(ss) * 1000;
        return String.valueOf(h + m + s + w);
    }
}