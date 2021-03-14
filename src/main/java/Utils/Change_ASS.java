package Utils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Change_ASS {
    public static void main(String[] args) {
        Change_ASS.Requset_listToAss("/Volumes/icybiscuit/IdolProject/danmuku/GNZ/2018_01_20_06_51.danmuku.list", "2018-01-20-19:00:00");
    }
    //字幕数组转ass字幕文件
    public static void Requset_listToAss(String filename, String VideoStartTime) {
        File in = new File(filename);
        File assFile = new File(filename + ".ass");
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        BufferedReader reader;
        BufferedWriter writer;
        Set<String> danmukulineSet = new LinkedHashSet<>();

        Change_ASS todo = new Change_ASS();


        //写ass文件
        try {
            fileWriter = new FileWriter(assFile);
            writer = new BufferedWriter(fileWriter);
            writer.write(todo.header());
            Iterator<String> danmukulist_iter = danmukulineSet.iterator();

            String writeLine = null;
            int lineNUm = 0;
            while (danmukulist_iter.hasNext()) {
                writeLine = todo.PhraseDialogue(danmukulist_iter.next(), VideoStartTime, lineNUm++);
                if (writeLine != null) {
                    writer.write(writeLine);
                }
            }
            writer.flush();

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String header() {

        SimpleDateFormat formater = new SimpleDateFormat("yyyy_MM_dd_hh_mm");

        final String scriptInfo = String.format("[Script Info]\n" +
                "Title: GNZ48_%s_BilibiliDanmuku\n" +
                "Original Script: 根据 https://live.bilibili.com/391199 的弹幕信息生成\n" +
                "ScriptType: v4.00+\n" +
                "Collisions: Normal\n" +
                "PlayResX: 1280\n" +
                "PlayResY: 720\n" +
                "Timer: 10.0000\n", formater.format(new Date()));

        final String DanmukuStyle = String.format("[V4+ Styles]\n" +
                "Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, OutlineColour, BackColour, Bold, Italic, Underline, StrikeOut, ScaleX, ScaleY, Spacing, Angle, BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV, Encoding\n" +
                "Style: Default,Microsoft YaHei,40,&H00FFFFFF,&H00FFFFFF,&H28533B3B,&H500E0A00,0,0,0,0,100.0,100.0,0.0,0.0,1,2.0,2.0,3,135,135,80,1\n" +
                "Style: Description,PingFang SC,48,&H0AB59BFF,&H000000FF,&H00424242,&H001C1C1C,-1,0,0,0,100.0,100.0,0.0,0.0,1,2.0,2.0,1,75,100,50,1");

        final String Events = String.format("[Events]\n" +
                "Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text\n" +
                "Dialogue: 2,0:00:00.00,0:00:8.00,Description,,0,0,0,,{\\\\fad(200,200)\\\\b1}压制: IcyBiscuit{\\\\b}\n" +
                "Dialogue: 2,0:00:00.00,0:00:8.00,Description,,0,0,0,,{\\\\fad(200,200)\\\\b1}视频源: 口袋48{\\\\b}\n" +
                "Dialogue: 2,0:00:00.00,0:00:8.00,Description,,0,0,0,,{\\\\fad(200,200)\\\\b1}弹幕来源: 口袋48{\\\\b}");

        StringBuilder header = new StringBuilder();

        header.append(scriptInfo);
        header.append('\n');
        header.append(DanmukuStyle);
        header.append('\n');
        header.append(Events);
        header.append('\n');

        return header.toString();

    }

    public String PhraseDialogue(String danmuku, String OpenLiveTimeStart, int lineNum) {

        String start;
        String end;
        String text;
        float pos_s;
        float pos_e;
        int pos_y;


        String danmukuStartTime = danmuku.substring(0, 19);
        String[] time = TimeCal.calTime(OpenLiveTimeStart, danmukuStartTime);
        if (time != null) {
            start = time[0];
            end = time[1];
            text = danmuku.substring(19);

            pos_s = 1280 + 20 * text.length();
            pos_e = -20 * text.length();
            pos_y = (1 + lineNum % 9) * 45;

            String line = String.format("Dialogue: 0,%s.00,%s.00,Default,,20,20,2,,{\\move(%.1f,%d,%.1f,%d)}%s\n",
                    start, end, pos_s, pos_y, pos_e, pos_y, text);

            return line;
        }

        return null;

    }


}