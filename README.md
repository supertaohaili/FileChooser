# FileChooser介绍

选择文件路径的的插件，一般运用于设置缓存路径、选择文件等地方，代码简单，几个类，库没有导入其他的第三方包，干净整洁。




# 效果图

<img src="https://github.com/supertaohaili/FileChooser/blob/master/S80110-113111.jpg" width="300"> <img src="https://github.com/supertaohaili/FileChooser/blob/master/S80110-113122.jpg" width="300">
<img src="https://github.com/supertaohaili/FileChooser/blob/master/S80110-113143.jpg" width="300">



#apk下载链接

<a href="https://github.com/supertaohaili/FileChooser/blob/master/app-debug.apk">https://github.com/supertaohaili/FileChooser/blob/master/app-debug.apk</a></p>


# 引入Gradle
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
     compile 'com.github.supertaohaili:FileChooser:1.0.0'
}
```


#示例代码:
``` java
  FileChooser fileChooser = new FileChooser(MainActivity.this, new FileChooser.FileChoosenListener() {
                            @Override
                            public void onFileChoosen(String filePath) {
                                ((TextView) findViewById(R.id.tv_msg)).setText(filePath);
                            }
                        });

                        fileChooser.setBackIconRes(R.drawable.icon_arrow);
                        fileChooser.setTitle("选择文件路径");
                        fileChooser.setDoneText("确定");
                        fileChooser.setThemeColor(R.color.colorAccent);
                        fileChooser.setChooseType(FileChooser.FOLDER);
                        fileChooser.showFile(false);
                        fileChooser.open();
```

### Known Issues
If you have any questions/queries/Bugs/Hugs please mail @
taohailili@gmail.com
