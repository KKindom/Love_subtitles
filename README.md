# Love_subtitles (i 字幕)

## 技术及环境

本程序使用JavaFx写界面、JavaCv处理音视频、htmlunit爬虫、讯飞语音转写和自然语言翻译API。

字幕来源网址：http://zimuku.org/

开发环境为：JDK1.8.241+IntelliJ IDEA 2021.1 (Ultimate Edition)+Scenebulider(Javafxgui编写插件)

**注意：JDK1.8.241自带JavaFx，JDK版本小于等于8自带JavaFx无需配置，JDK8以上请自行下载JavaFx的jar包。**

SceneBulider下载参考：https://blog.csdn.net/u011781521/article/details/86632482

## 主要功能实现

1. 利用爬虫下载字幕
2. 字幕文件转译
3. 字幕文件生成
4. 字幕视频文件生成

## 注意

- 程序可能很卡，因为这完全取决于你的网络和你的讯飞账号是否有钱。
- 程序可能在你的电脑上无法运行，**请首先考虑路径是否有中文。**
- 程序打包后会很大，因为我也不知道有哪些jar是多余的。
- 如果出现Bug，请自行解决，不行请提issue。
- 如果你的Scenebulider无法打开，请注意是否导入了jfoenix和bootstrapfx的jar包否则无法正常显示
- 最后有问题先百度，百度不行就谷歌，我觉得你问我不如问他们来的快。

## 部署过程

**注意：此部署教程为JDK1.8.241下的JavaFx打包成exe/jar，其他版本可能无法适用，要自行探索。**

### 0 前提条件

首先你要有一个**讯飞开放平台账号**，其次，你还要有语音转写和自然语音翻译的**剩余时长/额度**。否则请退出这个界面谢谢！

### 1外部文件配置

因为我不知道怎么样把文件打包到jar中，所以直接简单粗暴放绝对路径。 

**setting.xml文件**：文件存放讯飞API密匙。具体的你自己看setting.xml

**位置：C:\setting.xml**

### 2 拉代码

确保你安装了git

然后 打开 Git Bash 输入下面命令：

git clone git@github.com:KKindom/Love_subtitles.git

### 3 打开项目

配置Jdk为1.8.241，重新编译项目

### 4 部署项目

参照 https://blog.csdn.net/wangyinlon/article/details/79247606

注意：

Application class：**sample.Host_interface**

**导入resourse文件夹**

![1.PNG](D:\pro\Love_subtitles\image\1.PNG.jpg)

![2](D:\pro\Love_subtitles\image\2.jpg)

导入jar包：

![3](D:\pro\Love_subtitles\image\3.jpg)

包括以下jar包：

**虽然我也不知道那些还可以删除 但是 我会更新最简版 。。。**

- Maven: cn.hutool:hutool-all:4.1.0  
- Maven: com.alibaba:fastjson:1.2.67  
- Maven: com.google.code.gson:gson:2.8.5  
- Maven: com.jfoenix:jfoenix:8.0.10  
- Maven: commons-codec:commons-codec:1.9  
- Maven: commons-io:commons-io:2.5  
- Maven: commons-logging:commons-logging-api:1.1  
- Maven: commons-logging:commons-logging:1.2  
- Maven: log4j:log4j:1.2.17  
- Maven: net.sourceforge.cssparser:cssparser:0.9.23  
- Maven: net.sourceforge.htmlunit:htmlunit-core-js:2.27  
- Maven: net.sourceforge.htmlunit:htmlunit:2.27  
- Maven: net.sourceforge.htmlunit:neko-htmlunit:2.27  
- Maven: org.bytedeco.javacpp-presets:ffmpeg:3.4.2-1.4.1  
- Maven: org.bytedeco.javacpp-presets:ffmpeg:windows-x86_64:3.4.2-1.4.1  
- Maven: org.bytedeco:artoolkitplus:2.3.1-1.5.4  
- Maven: org.bytedeco:artoolkitplus:windows-x86_64:2.3.1-1.5.4  
- Maven: org.bytedeco:ffmpeg:4.3.1-1.5.4  
- Maven: org.bytedeco:ffmpeg:windows-x86_64:4.3.1-1.5.4  
- Maven: org.bytedeco:flandmark:1.07-1.5.4  
- Maven: org.bytedeco:flandmark:windows-x86_64:1.07-1.5.4  
- Maven: org.bytedeco:flycapture:2.13.3.31-1.5.4  
- Maven: org.bytedeco:flycapture:windows-x86_64:2.13.3.31-1.5.4  
- Maven: org.bytedeco:javacpp:1.5.4  
- Maven: org.bytedeco:javacpp:windows-x86_64:1.5.4  
- Maven: org.bytedeco:javacv:1.5.4  
- Maven: org.bytedeco:leptonica:1.80.0-1.5.4  
- Maven: org.bytedeco:leptonica:windows-x86_64:1.80.0-1.5.4  
- Maven: org.bytedeco:libdc1394:2.2.6-1.5.4  
- Maven: org.bytedeco:libdc1394:windows-x86_64:2.2.6-1.5.4  
- Maven: org.bytedeco:libfreenect2:0.2.0-1.5.4  
- Maven: org.bytedeco:libfreenect2:windows-x86_64:0.2.0-1.5.4  
- Maven: org.bytedeco:libfreenect:0.5.7-1.5.4  
- Maven: org.bytedeco:libfreenect:windows-x86_64:0.5.7-1.5.4  
- Maven: org.bytedeco:librealsense2:2.29.0-1.5.4  
- Maven: org.bytedeco:librealsense2:windows-x86_64:2.29.0-1.5.4  
- Maven: org.bytedeco:librealsense:1.12.4-1.5.4  
- Maven: org.bytedeco:librealsense:windows-x86_64:1.12.4-1.5.4  
- Maven: org.bytedeco:openblas:0.3.10-1.5.4  
- Maven: org.bytedeco:openblas:android-x86_64:0.3.10-1.5.4  
- Maven: org.bytedeco:openblas:windows-x86_64:0.3.10-1.5.4  
- Maven: org.bytedeco:opencv:4.4.0-1.5.4  
- Maven: org.bytedeco:opencv:windows-x86_64:4.4.0-1.5.4  
- Maven: org.bytedeco:tesseract:4.1.1-1.5.4  
- Maven: org.bytedeco:tesseract:windows-x86_64:4.1.1-1.5.4  
- Maven: org.bytedeco:videoinput:0.200-1.5.4  
- Maven: org.bytedeco:videoinput:windows-x86_64:0.200-1.5.4  
- Maven: org.kordamp.bootstrapfx:bootstrapfx-core:0.4.0  
- Maven: org.openjfx:javafx-base:11  
- Maven: org.openjfx:javafx-base:win:11  
- Maven: org.openjfx:javafx-graphics:11  
- Maven: org.openjfx:javafx-graphics:win:11  
- Maven: org.projectlombok:lombok:1.18.10  
- Maven: org.w3c.css:sac:1.3  
- Maven: ws.schild:jave-core:2.4.6  
- Maven: ws.schild:jave-native-win64:2.4.6  
- Maven: xalan:serializer:2.7.2  
- Maven: xalan:xalan:2.7.2  
- Maven: xerces:xercesImpl:2.11.0  

- untitled.jar  下
- Maven: com.google.code.gson:gson:2.8.5  
- Maven: com.iflytek.msp.lfasr:lfasr-sdk:3.0.0  
- Maven: com.jfoenix:jfoenix:8.0.10  
- Maven: org.bytedeco:javacv:1.5.4  
- Maven: xml-apis:xml-apis:1.4.01

按照教程后 你会得到一个很大的文件夹

![4](D:\pro\Love_subtitles\image\4.jpg)

可仅保留该文件，其余文件夹均删除即可

**最后双击exe即可**

只要有这个untitled文件夹 然后c盘下有配置文件，即可运行该程序。

## 温馨提示

- 可先利用Jar包运行，查看错误信息，根据错误信息增删Jar包
- 仅供学习

