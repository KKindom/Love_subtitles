package Utils;

/**
 * @program: untitled
 * @description: 测试controller间通信
 * @author: kkindom
 * @create: 2021-03-23 18:02
 **/
import javafx.beans.property.*;
import lombok.Data;

@Data
public class AppModel
{
    //传输播放文件路径
    private StringProperty path_video;
    //传输播放字幕路径
    private StringProperty path_sub;
    //返回标志
    private BooleanProperty bakc;
    //返回类型
    private IntegerProperty back_type;
    public AppModel()
    {
        this.path_video = new SimpleStringProperty();
        this.path_sub=new SimpleStringProperty();
        this.bakc=new SimpleBooleanProperty();
        this.back_type=new SimpleIntegerProperty();
    }

    public StringProperty path_videoProperty() {
        return path_video;
    }
    public final void setpath_video(String text) {
        path_videoProperty().set(text);
    }

    public StringProperty path_subProperty() {
        return path_sub;
    }
    public final void setpath_sub(String text) {
        path_subProperty().set(text);
    }

    public BooleanProperty backProperty() {
        return bakc;
    }
    public final void setback(Boolean text) {
        backProperty().set(text);
    }
    public IntegerProperty back_typeProperty() {
        return back_type;
    }
    public final void setback_type(int text) {
        back_typeProperty().set(text);
    }
}