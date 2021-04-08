package Utils;

/**
 * @program: untitled
 * @description: 测试controller间通信
 * @author: kkindom
 * @create: 2021-03-23 18:02
 **/
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import lombok.Data;

@Data
public class AppModel
{
    //传输播放文件路径
    private StringProperty path_video;
    //传输播放字幕路径
    private StringProperty path_sub;
    public AppModel()
    {
        this.path_video = new SimpleStringProperty();
        this.path_sub=new SimpleStringProperty();
    }

    public StringProperty path_videoProperty() {
        return path_video;
    }

    public final String getpath_video() {
        return path_videoProperty().get();
    }

    public final void setpath_video(String text) {
        path_videoProperty().set(text);
    }

    public StringProperty path_subProperty() {
        return path_sub;
    }

    public final String getpath_sub() {
        return path_subProperty().get();
    }

    public final void setpath_sub(String text) {
        path_subProperty().set(text);
    }


}