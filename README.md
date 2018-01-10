
gradle引入
dependencies {
	        compile 'compile 'com.github.supertaohaili:FileChooser:1.0.0'
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
