下载链接<a href="https://github.com/supertaohaili/FileChooser/blob/master/app-debug.apk">https://github.com/supertaohaili/FileChooser/blob/master/app-debug.apk</a></p>

## 效果
![](https://github.com/supertaohaili/FileChooser/blob/master/S80110-113111.jpg)
![](https://github.com/supertaohaili/FileChooser/blob/master/S80110-113122.jpg)
![](https://github.com/supertaohaili/FileChooser/blob/master/S80110-113143.jpg)

##
##
##
##

gradle引入
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

		dependencies {
    	        compile 'com.github.supertaohaili:FileChooser:1.0.0'
    	}

示例代码:
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
