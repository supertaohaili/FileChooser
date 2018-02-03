package com.thl.filechooser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.thl.filechooser.FileChooser.*;


public class FileAdapter extends CommonAdapter<FileInfo> {

    private int sign = -1;
    private String chooseType;

    public FileAdapter(Context context, ArrayList<FileInfo> dataList, int resId, String chooseType) {
        super(context, dataList, resId);
        this.chooseType = chooseType;
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, final FileInfo data, final int position) {
        TextView textView = (TextView) holder.itemView.findViewById(R.id.fileName);
        TextView textTime = (TextView) holder.itemView.findViewById(R.id.fileTime);
        textView.setText(data.getFileName());
        textTime.setText(data.getCreateTime());

        ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.fileIcon);
        View divider = holder.itemView.findViewById(R.id.divider);
        if (FileInfo.FILE_TYPE_VIDEO.equals(data.getFileType())) {
            imageView.setImageResource(R.drawable.format_video);
        } else if (FileInfo.FILE_TYPE_AUDIO.equals(data.getFileType())) {
            imageView.setImageResource(R.drawable.format_music);
        } else if (FileInfo.FILE_TYPE_APK.equals(data.getFileType())) {
            imageView.setImageResource(R.drawable.format_app);
        } else if (FileInfo.FILE_TYPE_ZIP.equals(data.getFileType()) || FileInfo.FILE_TYPE_RAR.equals(data.getFileType())) {
            imageView.setImageResource(R.drawable.format_compress);
        } else if (FileInfo.FILE_TYPE_JPEG.equals(data.getFileType())
                || FileInfo.FILE_TYPE_JPG.equals(data.getFileType())
                || FileInfo.FILE_TYPE_PNG.equals(data.getFileType())) {
            imageView.setImageResource(R.drawable.format_picture);
        } else if (FileInfo.FILE_TYPE_FOLDER.equals(data.getFileType())) {
            imageView.setImageResource(R.drawable.format_folder);
        } else {
            imageView.setImageResource(R.drawable.format_other);
        }


        if (position != dataList.size() - 1) {
            divider.setVisibility(View.VISIBLE);
        } else {
            divider.setVisibility(View.GONE);
        }

        ImageView fileChoose = (ImageView) holder.itemView.findViewById(R.id.fileChoose);
        if (sign == position) {
            fileChoose.setImageResource(R.drawable.log_choose_checkbox_on);
        } else {
            fileChoose.setImageResource(R.drawable.log_choose_checkbox_off);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener!=null){
                    mItemClickListener.onItemClick(v,position,data);
                }
            }
        });


        if (chooseType.equals(FileInfo.FILE_TYPE_ALL)) {
            fileChoose.setVisibility(View.VISIBLE);
            fileChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyData(position);
                }
            });
        } else if (chooseType.equals(FileInfo.FILE_TYPE_FOLDER)) {
            boolean folder = data.isFolder();
            if (folder) {
                fileChoose.setVisibility(View.VISIBLE);
                fileChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyData(position);
                    }
                });
            } else {
                fileChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                fileChoose.setVisibility(View.GONE);
            }
        } else if (chooseType.equals(FileInfo.FILE_TYPE_FILE)) {
            boolean folder = data.isFolder();
            if (!folder) {
                fileChoose.setVisibility(View.VISIBLE);
                fileChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyData(position);
                    }
                });
            } else {
                fileChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                fileChoose.setVisibility(View.GONE);
            }
        } else if (chooseType.equals(FileInfo.FILE_TYPE_IMAGE)) {

            if (FileInfo.FILE_TYPE_JPEG.equals(data.getFileType())
                    || FileInfo.FILE_TYPE_JPG.equals(data.getFileType())
                    || FileInfo.FILE_TYPE_PNG.equals(data.getFileType())) {
                fileChoose.setVisibility(View.VISIBLE);
                fileChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyData(position);
                    }
                });
            } else {
                fileChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                fileChoose.setVisibility(View.GONE);
            }

        } else if (chooseType.equals(FileInfo.FILE_TYPE_PACKAGE)) {
            if (FileInfo.FILE_TYPE_ZIP.equals(data.getFileType()) || FileInfo.FILE_TYPE_RAR.equals(data.getFileType())) {
                fileChoose.setVisibility(View.VISIBLE);
                fileChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyData(position);
                    }
                });
            } else {
                fileChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                fileChoose.setVisibility(View.GONE);
            }
        } else {
            if (chooseType.equals(data.getFileType())) {
                fileChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyData(position);
                    }
                });
                fileChoose.setVisibility(View.VISIBLE);
            } else {
                fileChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                fileChoose.setVisibility(View.GONE);
            }
        }
    }

    private ItemClickListener mItemClickListener;

    public void setItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position,FileInfo data);
    }

    public void notifyClick(FileInfo data, int position) {
        Log.e("taohaili",chooseType);
        Log.e("taohaili",data.getFileType());
        if (chooseType.equals(FileInfo.FILE_TYPE_ALL)) {
            notifyData(position);
        } else if (chooseType.equals(FileInfo.FILE_TYPE_FOLDER)) {
            boolean folder = data.isFolder();
            if (folder) {
                notifyData(position);
            }
        } else if (chooseType.equals(FileInfo.FILE_TYPE_FILE)) {
            boolean folder = data.isFolder();
            if (!folder) {
                notifyData(position);
            }
        } else if (chooseType.equals(FileInfo.FILE_TYPE_IMAGE)) {
            if (FileInfo.FILE_TYPE_JPEG.equals(data.getFileType())
                    || FileInfo.FILE_TYPE_JPG.equals(data.getFileType())
                    || FileInfo.FILE_TYPE_PNG.equals(data.getFileType())) {
                notifyData(position);
            }
        } else if (chooseType.equals(FileInfo.FILE_TYPE_PACKAGE)) {
            if (FileInfo.FILE_TYPE_ZIP.equals(data.getFileType()) || FileInfo.FILE_TYPE_RAR.equals(data.getFileType())) {
                notifyData(position);
            }
        } else if (chooseType.equals(data.getFileType())) {
            notifyData(position);
        }
    }

    public int getSign() {
        return sign;
    }

    public String getChooseFilePath() {
        FileInfo fileInfo = dataList.get(sign);
        return fileInfo.getFilePath();
    }

    public void notifyData() {
        FileAdapter.this.sign = -1;
        notifyDataSetChanged();
    }

    public void notifyData(int position) {
        if (FileAdapter.this.sign == position) {
            FileAdapter.this.sign = -1;
        } else {
            FileAdapter.this.sign = position;
        }
        notifyDataSetChanged();
    }

}
