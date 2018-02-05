package com.thl.filechooser;

import android.content.Context;
import android.os.storage.StorageManager;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileTourController {

    private File currentFile;
    private File rootFile;
    private List<FileInfo> currenFileInfoList;
    private List<File> currentFolderList = new ArrayList<>();
    private boolean isRootFile = true;
    private boolean showFile = true;
    private boolean showHideFile = false;
    private int sdcardIndex;


    private Context mContext;

    public FileTourController(Context context, String currentPath) {
        this.currentFile = new File(currentPath);
        this.mContext = context;
        rootFile = getRootFile();
        System.out.println("FileTourController.getRootFile " + rootFile.getAbsolutePath());
        if (currentFile == null) {
            this.currentFile = rootFile;
        } else if (!currentFile.exists()) {
            this.currentFile = rootFile;
        } else
            isRootFile = false;

        if (!currentFile.getAbsolutePath().equals(getRootFile().getAbsolutePath())) {
            currentFolderList.add(rootFile);
            ArrayList<File> fileList = new ArrayList<>();
            File tempFile = currentFile;
            while (!tempFile.getParent().equals(rootFile.getAbsolutePath())) {
                fileList.add(tempFile.getParentFile());
                tempFile = tempFile.getParentFile();
            }
            for (int i = fileList.size() - 1; i >= 0; i--) {
                currentFolderList.add(fileList.get(i));
            }
        }

        currenFileInfoList = searchFile(this.currentFile);
        currentFolderList.add(this.currentFile);
    }

    public FileTourController(Context context) {
        this.mContext = context;
        rootFile = getRootFile();
        this.currentFile = rootFile;
        currenFileInfoList = searchFile(this.currentFile);
        currentFolderList.add(this.currentFile);
    }

    public boolean isShowFile() {
        return showFile;
    }

    public void setShowFile(boolean showFile) {
        this.showFile = showFile;
    }

    public boolean isShowHideFile() {
        return showHideFile;
    }

    public void setShowHideFile(boolean showHideFile) {
        this.showHideFile = showHideFile;
    }

    public List<File> getCurrentFolderList() {
        return currentFolderList;
    }

    public List<FileInfo> getCurrenFileInfoList() {
        return currenFileInfoList;
    }

    public File getRootFile() {
        if (sdcardIndex == 1) {
            return getSDcard1();
        } else {
            return getSDcard0();
        }
    }

    public void switchSdCard(int sdcardIndex) {
        if (sdcardIndex == 0) {
            rootFile = getSDcard0();
        } else {
            rootFile = getSDcard1();
        }
        this.currentFile = rootFile;
        currenFileInfoList = new ArrayList<>();
        currentFolderList = new ArrayList<>();
        currenFileInfoList = searchFile(this.currentFile);
        currentFolderList.add(this.currentFile);
    }

    public File getSDcard0() {
        return new File(getStoragePath(mContext, false));
    }

    public File getSDcard1() {
        if (getStoragePath(mContext, true) == null)
            return new File(getStoragePath(mContext, false));
        return new File(getStoragePath(mContext, true));
    }

    public static String getStoragePath(Context mContext, boolean is_removale) {

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isRootFile() {
        if (isRootFile(currentFile))
            isRootFile = true;
        else
            isRootFile = false;
        return isRootFile;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public List<FileInfo> addCurrentFile(File file) {
        List<FileInfo> fileInfoList = new ArrayList<>();
        currentFile = file;
        currentFolderList.add(file);
        fileInfoList = searchFile(file);
        this.currenFileInfoList = fileInfoList;
        return fileInfoList;
    }

    public List<FileInfo> resetCurrentFile(int position) {
        List<FileInfo> fileInfoList = new ArrayList<>();
        while (currentFolderList.size() - 1 > position) {
            currentFolderList.remove(currentFolderList.size() - 1);
        }
        if (currentFolderList.size() != 0)
            currentFile = new File(currentFolderList.get(currentFolderList.size() - 1).getAbsolutePath());
        else
            currentFile = rootFile;
        fileInfoList = searchFile(currentFile);
        this.currenFileInfoList = fileInfoList;
        return fileInfoList;
    }

    public List<FileInfo> searchFile(File file) {
        this.currentFile = file;
        List<FileInfo> fileInfoList = new ArrayList<>();
        File childFiles[] = file.listFiles();
        if (childFiles != null)
            for (int i = 0; i < childFiles.length; i++) {
                FileInfo fileInfo = new FileInfo();
                File childFile = childFiles[i];
                String name = childFile.getName();
                if (name.length()>0&& String.valueOf(name.charAt(0)).equals(".")&&!showHideFile){
                    continue;
                }
                fileInfo.setFileName(name);
                String time = new SimpleDateFormat("yyyy年MM月dd日").format(new Date(childFile.lastModified()));
                fileInfo.setCreateTime(time);
                fileInfo.setFilePath(childFile.getAbsolutePath());
                if (childFile.isDirectory()) {
                    fileInfo.setFolder(true);
                    fileInfo.setFileType(FileInfo.FILE_TYPE_FOLDER);
                } else {
                    fileInfo.setFolder(false);
                    if ("mp4".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "3gp".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "wmv".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "ts".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "rmvb".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "mov".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "m4v".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "mkv".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "avi".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "3gpp".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "3gpp2".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "flv".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "divx".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "f4v".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "rm".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "asf".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "ram".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "mpg".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "mpeg".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "dat".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "webm".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "qsv".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "v8".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "swf".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "m2v".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "asx".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "ra".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "ndivx".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "xvid".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "m3u8".equals(getFileTypeName(childFile.getAbsolutePath())))
                        fileInfo.setFileType(FileInfo.FILE_TYPE_VIDEO);
                    else if ("mp3".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "aac".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "amr".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "ogg".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "wma".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "wav".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "flac".equals(getFileTypeName(childFile.getAbsolutePath())) ||
                            "ape".equals(getFileTypeName(childFile.getAbsolutePath())))
                        fileInfo.setFileType(FileInfo.FILE_TYPE_AUDIO);
                    else if ("apk".equals(getFileTypeName(childFile.getAbsolutePath())))
                        fileInfo.setFileType(FileInfo.FILE_TYPE_APK);
                    else if ("zip".equals(getFileTypeName(childFile.getAbsolutePath())))
                        fileInfo.setFileType(FileInfo.FILE_TYPE_ZIP);
                    else if ("rar".equals(getFileTypeName(childFile.getAbsolutePath())))
                        fileInfo.setFileType(FileInfo.FILE_TYPE_RAR);
                    else if ("jpeg".equals(getFileTypeName(childFile.getAbsolutePath())))
                        fileInfo.setFileType(FileInfo.FILE_TYPE_JPEG);
                    else if ("jpg".equals(getFileTypeName(childFile.getAbsolutePath())))
                        fileInfo.setFileType(FileInfo.FILE_TYPE_JPG);
                    else if ("png".equals(getFileTypeName(childFile.getAbsolutePath())))
                        fileInfo.setFileType(FileInfo.FILE_TYPE_PNG);
                    else
                        fileInfo.setFileType(FileInfo.FILE_TYPE_FILE);
                }
                if (this.showFile) {
                    fileInfoList.add(fileInfo);
                } else {
                    if (fileInfo.isFolder())
                        fileInfoList.add(fileInfo);
                }
            }
        return fileInfoList;
    }

    public List<FileInfo> backToParent() {
        currentFile = currentFile.getParentFile();
        if (isRootFile(currentFile))
            isRootFile = true;
        else
            isRootFile = false;
        currentFolderList.remove(currentFolderList.size() - 1);
        return resetCurrentFile(currentFolderList.size());
    }

    public boolean isRootFile(File file) {
        return rootFile.getAbsolutePath().equals(file.getAbsolutePath());
    }

    private String getParentName(String path) {
        int end = path.lastIndexOf("/") + 1;
        return path.substring(0, end);
    }

    private String getFileTypeName(String path) {
        int start = path.lastIndexOf(".") + 1;
        if (start == -1)
            return "";
        return path.substring(start);
    }

}
