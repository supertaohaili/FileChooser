package com.thl.demo;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.thl.filechooser.FileChooser;
import com.thl.filechooser.FileInfo;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissins(new PermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(String[] deniedPermissions) {
                Toast.makeText(MainActivity.this, "未获取到存储权限", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissins(new PermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        FileChooser fileChooser = new FileChooser(MainActivity.this, new FileChooser.FileChoosenListener() {
                            @Override
                            public void onFileChoosen(String filePath) {
                                ((TextView) findViewById(R.id.tv_msg)).setText(filePath);
                            }
                        });
                        fileChooser.open();
                    }

                    @Override
                    public void onPermissionDenied(String[] deniedPermissions) {
                        Toast.makeText(MainActivity.this, "未获取到存储权限", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermissins(new PermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
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
                        fileChooser.open();
                    }

                    @Override
                    public void onPermissionDenied(String[] deniedPermissions) {
                        Toast.makeText(MainActivity.this, "未获取到存储权限", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermissins(new PermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        FileChooser fileChooser = new FileChooser(MainActivity.this, new FileChooser.FileChoosenListener() {
                            @Override
                            public void onFileChoosen(String filePath) {
                                ((TextView) findViewById(R.id.tv_msg)).setText(filePath);
                            }
                        });

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

                        fileChooser.setChooseType(FileInfo.FILE_TYPE_VIDEO);
                        fileChooser.open();
                    }

                    @Override
                    public void onPermissionDenied(String[] deniedPermissions) {
                        Toast.makeText(MainActivity.this, "未获取到存储权限", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermissins(new PermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        FileChooser fileChooser = new FileChooser(MainActivity.this, new FileChooser.FileChoosenListener() {
                            @Override
                            public void onFileChoosen(String filePath) {
                                ((TextView) findViewById(R.id.tv_msg)).setText(filePath);
                            }
                        });
                        fileChooser.setCurrentPath(MainActivity.this.getExternalCacheDir().getAbsolutePath());
                        fileChooser.showFile(false); //不显示文件
                        fileChooser.open();
                    }

                    @Override
                    public void onPermissionDenied(String[] deniedPermissions) {
                        Toast.makeText(MainActivity.this, "未获取到存储权限", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }

    private void requestPermissins(PermissionUtils.OnPermissionListener mOnPermissionListener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mOnPermissionListener.onPermissionGranted();
            return;
        }
        String[] permissions = { "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
        PermissionUtils.requestPermissions(this, 0
                , permissions, mOnPermissionListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


}
