package com.zhy.Share;

import com.dawnlightning.ucqa.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.wechat.utils.WechatHelper;

/**
 * 分享工具
 * 
 * @author xiaojun
 * 
 */
public class ShareTool
{
    private Context context;
    private PlatformActionListener platformActionListener;
    private ShareParams shareParams;
    private ProgressDialog pd;

    public ShareTool(Context context)
    {
        this.context = context;
        pd = ProgressDialog.show(context, null , "页面加载中...", true, true); 
    }

    /**
     * 隐藏加载提示
     */
    public void dismiss()
    {
        if (pd != null)
        {
            pd.dismiss();
        }
    }

    public PlatformActionListener getPlatformActionListener()
    {
        return platformActionListener;
    }

    public void setPlatformActionListener(PlatformActionListener platformActionListener)
    {
        this.platformActionListener = platformActionListener;
    }

    public void showShareWindow()
    {
        View view = LayoutInflater.from(context).inflate(R.layout.share_layout, null);
        GridView gridView = (GridView) view.findViewById(R.id.share_gridview);
        ShareAdapter adapter = new ShareAdapter(context);
        gridView.setAdapter(adapter);

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setView(view);
        AlertDialog adialog = dialog.show();
        gridView.setOnItemClickListener(new ShareItemClickListener(adialog));
    }

    private class ShareItemClickListener implements OnItemClickListener
    {
        private AlertDialog dialog;

        public ShareItemClickListener(AlertDialog dialog)
        {
            this.dialog = dialog;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            pd.show();
            share(position);
            dialog.dismiss();
        }

    };

    /**
     * 分享
     * 
     * @param position
     */
    private void share(int position)
    {

        if (position == 3)
        {
            qzone();
        }else if(position==0){
        	weiXinWebShare(true);
        }else if(position==1){
        	weiXinWebShare(false);
        }
        else
        {
        	qq();
            
        }
    }

    /**
     * 初始化分享参数
     * 
     * @param shareModel
     */
    public void initShareParams(ShareModel shareModel)
    {
        if (shareModel != null)
        {
            ShareParams sp = new ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);

            sp.setTitle(shareModel.getText());
            sp.setText(shareModel.getText());
            sp.setUrl(shareModel.getUrl());
            sp.setImageUrl(shareModel.getImageUrl());
            shareParams = sp;
        }
    }

    /**
     * 获取平台
     * 
     * @param position
     * @return
     */
    private String getPlatform(int position)
    {
        String platform = "";
        switch (position)
        {
            case 0:
                platform = "Wechat";
                break;
            case 1:
                platform = "WechatMoments";
                break;
            case 2:
                platform = "QQ";
                break;
            case 3:
                platform = "QZone";
                break;
            case 4:
                platform = "SinaWeibo";
                break;

        }
        return platform;
    }

    /**
     * 分享到QQ空间
     */
    private void qzone()
    {
        ShareParams sp = new ShareParams();
        sp.setTitle(shareParams.getTitle());
        sp.setTitleUrl(shareParams.getUrl()); // 标题的超链接
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setComment("我对此分享内容的评论");
        sp.setSite(shareParams.getTitle());
        sp.setSiteUrl(shareParams.getUrl());
        Platform qzone = ShareSDK.getPlatform(context, "QZone");

        qzone.setPlatformActionListener(platformActionListener); // 设置分享事件回调 // 执行图文分享
        qzone.share(sp);
    }
    private void weiXinWebShare(boolean isWeixin)
    
    {
   
    WechatHelper.ShareParams sp = null;
    
    if (isWeixin)
   
    {
   
    sp = new Wechat.ShareParams();
   
    }
   
    else
   
    {
   
    sp = new WechatMoments.ShareParams();
   
    }
    sp.setShareType(Platform.SHARE_TEXT);
    sp.title = shareParams.getTitle();
    sp.text = shareParams.getText();
   sp.imageData=((BitmapDrawable)context.getResources().getDrawable(R.drawable.mylogo)).getBitmap();
    //sp.imageData = 
   sp.imageUrl="http://www.wyl.cc/wp-content/uploads/2014/02/10060381306b675f5c5.jpg";
    sp.url = shareParams.getUrl(); 
   
   
    Platform plat = null;
    
    if (isWeixin)
    
    {
   
    plat = ShareSDK.getPlatform(Wechat.NAME);
    
    }
    
    else
   
    {
 
    plat = ShareSDK.getPlatform(WechatMoments.NAME);
    
    }
    
    plat.setPlatformActionListener(platformActionListener);
    
    plat.share(sp);
    
    }
   
    private void qq() {
        ShareParams sp = new ShareParams();
        sp.setTitle(shareParams.getTitle());
        sp.setTitleUrl(shareParams.getUrl()); // 标题的超链接
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setComment("我对此分享内容的评论");
        sp.setSite(shareParams.getTitle());
        sp.setSiteUrl(shareParams.getUrl());
        Platform qq = ShareSDK.getPlatform(context, "QQ");
        qq.setPlatformActionListener(platformActionListener);
        qq.share(sp);
    }
    
}
