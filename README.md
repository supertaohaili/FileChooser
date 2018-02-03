# FileChooser介绍

选择文件路径的的插件，一般运用于设置缓存路径、选择文件等地方，代码简单，几个类，库没有导入其他的第三方包，干净整洁。




# 效果图

 <img src="https://github.com/supertaohaili/FileChooser/blob/master/S80110-113122.jpg" width="300"><img src="https://github.com/supertaohaili/FileChooser/blob/master/S80110-113111.jpg" width="300"><img src="https://github.com/supertaohaili/FileChooser/blob/master/S80110-113143.jpg" width="300">


apk下载链接
<a href="https://github.com/supertaohaili/FileChooser/blob/master/app-debug.apk">https://github.com/supertaohaili/FileChooser/blob/master/app-debug.apk</a></p>

# 使用
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
    compile 'com.github.supertaohaili:FileChooser:1.0.9'
}
```

示例代码:
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

   //                          FILE_TYPE_FOLDER="type_folder";  //文件夹
   //                          FILE_TYPE_VIDEO="type_video";    //视频
   //                          FILE_TYPE_AUDIO="type_audio";    //音频
   //                          FILE_TYPE_FILE="type_file";      //全部文件
   //                          FILE_TYPE_APK="type_apk";        //apk
   //                          FILE_TYPE_ZIP="type_zip";        //zip
   //                          FILE_TYPE_RAR="type_rar";        //rar
   //                          FILE_TYPE_JPEG="type_jpeg";      //jpeg
   //                          FILE_TYPE_JPG="type_jpg";         //jpg
   //                          FILE_TYPE_PNG="type_png";         //png
   //
   //                          FILE_TYPE_ALL="type_all";         //所有文件
   //                           FILE_TYPE_IMAGE="type_image";    //所有图片
   //                           FILE_TYPE_PACKAGE="type_package";  //压缩包

                        fileChooser.setChooseType(FileInfo.FILE_TYPE_FILE);
                        fileChooser.showFile(false);  //是否显示文件
                        fileChooser.open();
```

### Known Issues
If you have any questions/queries/Bugs/Hugs please mail @
taohailili@gmail.com
