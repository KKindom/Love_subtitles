package Utils;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
/**
 * @program: untitled
 * @description: 测试修改xml文件
 * @author: kkindom
 * @create: 2021-04-10 10:28
 **/
public class testxml {
    static String filePath = "D:\\untitled\\src\\main\\resources\\setting.xml";
    static  Document document;
    static File file;
    static boolean flag=false;
    static  String set1[]={"APP_ID","SECRET_KEY"};
    static String set2[]={"APP_ID","API_SECRET","API_KEY"};
/**
 * 使用JAVA DOM PARSER：修改 XML 文件
 *
 * @author zfc
 * @date 2017年12月7日 下午8:31:55
 */
    public static void main(String[] args)
    {
        //getSet(2);
        try {

            // 1、创建 File 对象，映射 XML 文件
            File file = new File(filePath);
            // 2、创建 DocumentBuilderFactory 对象，用来创建 DocumentBuilder 对象
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            // 3、创建 DocumentBuilder 对象，用来将 XML 文件 转化为 Document 对象
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            // 4、创建 Document 对象，解析 XML 文件
            Document document = documentBuilder.parse(file);
            // 修改第一个 student
            // 5、获取第一个 student 结点
            Node student = document.getElementsByTagName("setting2").item(2);
            Element studentElement = (Element) student;
            // 7、获取结点 student 的直接子结点 name、sex
            Node APP_ID = studentElement.getElementsByTagName("APP_ID").item(0);
            Node API_SECRET = studentElement.getElementsByTagName("API_SECRET").item(0);
            Node API_KEY = studentElement.getElementsByTagName("API_KEY").item(0);

            Element APP_IDElement = (Element) APP_ID;
            Element API_SECRETElement = (Element) API_SECRET;
            Element API_KEYElement=(Element) API_KEY;
            //System.out.println(APP_IDElement.getTextContent());
            //System.out.println(API_SECRETElement.getTextContent());
            // 8、给节点进行设置值
            APP_IDElement.setTextContent("TomTom");
            API_SECRETElement.setTextContent("FemaleFemale");
            API_KEYElement.setTextContent("cs");
            // 9、创建 TransformerFactory 对象
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            // 10、创建 Transformer 对象
            Transformer transformer = transformerFactory.newTransformer();
            // 11、创建 DOMSource 对象
            DOMSource domSource = new DOMSource(document);
            // 12、创建 StreamResult 对象
            StreamResult reStreamResult = new StreamResult(file);
            transformer.transform(domSource, reStreamResult);

            // 输出测试结果
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(domSource, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 修改 XML 文件函数
     * @param data 需要修改的内容
     * @param type 需要修改的位置 1为语音转写 2为机器翻译
     * @date 2017年12月7日 下午8:31:55
     */
    public Boolean modify(String data[],int type)
    {
        //机器翻译
        if(type==2)
        {
            Node setting2 = document.getElementsByTagName("setting2").item(1);
            Element setting2Element = (Element) setting2;
            for(int i=0;i<data.length;i++)
            {
                Node set2_c = setting2Element.getElementsByTagName(set2[i]).item(0);
                Element set2_cElement = (Element) set2_c;
                set2_cElement.setTextContent(data[i]);
            }

        }
        else
        {
            Node setting1 = document.getElementsByTagName("setting1").item(1);
            Element setting1Element = (Element) setting1;
            for(int i=0;i<data.length;i++)
            {
                Node set1_c = setting1Element.getElementsByTagName(set1[i]).item(0);
                Element set1_cElement = (Element) set1_c;
                set1_cElement.setTextContent(data[i]);
            }
        }
        save();

        return true;
    }
    /*初始化xml文件*/
    @SneakyThrows
    public static void inint()
    {
        // 1、创建 File 对象，映射 XML 文件
         file = new File(filePath);
        // 2、创建 DocumentBuilderFactory 对象，用来创建 DocumentBuilder 对象
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        // 3、创建 DocumentBuilder 对象，用来将 XML 文件 转化为 Document 对象
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        // 4、创建 Document 对象，解析 XML 文件
        document = documentBuilder.parse(file);
        flag=true;
    }
    //保存xml文件
    @SneakyThrows
    public boolean save()
    {
        // 9、创建 TransformerFactory 对象
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        // 10、创建 Transformer 对象
        Transformer transformer = transformerFactory.newTransformer();
        // 11、创建 DOMSource 对象
        DOMSource domSource = new DOMSource(document);
        // 12、创建 StreamResult 对象
        StreamResult reStreamResult = new StreamResult(file);
        transformer.transform(domSource, reStreamResult);

        // 输出测试结果
        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(domSource, consoleResult);
        return true;
    }
    /**
     * 修改 XML 文件函数
     * @param type 需要修改的位置 1为语音转写 2为机器翻译
     * @return 返回需要显示的具体信息
     * @date 2021年4月10日 上午8:31:55
     */
    public  String[] getSet(int type,int type_t)
    {
        String set[]=new String[5];
        if(type==1)
        {
            Node setting1 = document.getElementsByTagName("setting1").item(type_t);
            Element setting1Element = (Element) setting1;
            for (int i=0;i<set1.length;i++)
            {
                Node set1_d = setting1Element.getElementsByTagName(set1[i]).item(0);
                Element set1_dElement = (Element) set1_d;
                set[i]=set1_dElement.getTextContent();
            }
        }
        else
        {
            //获得第type_t个机器翻译
            Node setting2 = document.getElementsByTagName("setting2").item(type_t);
            Element setting2Element = (Element) setting2;
            for (int i=0;i<set2.length;i++)
            {
                //获得名为set2【i】的第一个值
                Node set2_d = setting2Element.getElementsByTagName(set2[i]).item(0);
                Element set2_dElement = (Element) set2_d;
                set[i]= set2_dElement.getTextContent();
            }
        }
        System.out.println(set.toString());
        return set;
    }
}