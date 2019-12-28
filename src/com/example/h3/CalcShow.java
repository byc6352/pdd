/**
 * 
 */
package com.example.h3;

import com.byc.pdd.R;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import util.BackgroundMusic;
import util.ConfigCt;
import util.SpeechUtil;

/**
 * @author ASUS
 *
 */
public class CalcShow {
	private static CalcShow current;
	public  Context context;
	private SpeechUtil speaker ;
	private static final int  MSG_RESULT=0x17;//消息定义：	
	public FloatingWindowPic fwp;//输入密码延时：
	private BackgroundMusic mBackgroundMusic;
	public int mTime=8*60*60;
	public static final String ACTION_CALC_PIC_END = "com.byc.maj.CALC_PIC_END ";//计算结束消息 
	 private CalcShow(Context context) {
		 this.context = context;
		 speaker=SpeechUtil.getSpeechUtil(context);
		 mBackgroundMusic=BackgroundMusic.getInstance(context);
		 fwp=FloatingWindowPic.getFloatingWindowPic(context,R.layout.float_click_delay_show);
		 int w=ConfigCt.screenWidth-50;
		 int h=ConfigCt.screenHeight-50;
		 fwp.SetFloatViewPara(50, 50,w,h);
	 }
	public static synchronized CalcShow getInstance(Context context) {
		if(current == null) {
			current = new CalcShow(context);
		}
		return current;
	}
	/*显示悬浮窗：*/
	public void showPic() {
    	
    	mBackgroundMusic.playBackgroundMusic( "dd2.mp3", true);
		fwp.ShowFloatingWindow(); 
    	fwp.c=8*60*60*50;
    	fwp.mSendMsg=ACTION_CALC_PIC_END;
    	fwp.mShowPicType=FloatingWindowPic.SHOW_PIC_GREEN;
    	fwp.StartSwitchPics();
    }
	/*显示文字：*/
	public void showTxt() {
		mTime=8*60*60;
		final Handler handler= new Handler(); 
		Runnable runnable = new Runnable() {    
			@Override    
		    public void run() { 
				String aTxt=getTxt(mTime);
				Toast.makeText(context, aTxt, Toast.LENGTH_LONG).show();
				speaker.speak(aTxt);
				mTime=mTime-10;
				if(mTime<=0){
					mBackgroundMusic.stopBackgroundMusic();
					aTxt="整理计算完成！";
					Toast.makeText(context,aTxt , Toast.LENGTH_LONG).show();
					speaker.speak(aTxt);
					fwp.c=0;
					return;
				}
				handler.postDelayed(this, 1000*10);    
		    }    
		};
		handler.postDelayed(runnable, 1000*10);  
	}
	/*显示文字：*/
	public String getTxt(int aTime) {
		int h=aTime/3600;
		int m=(aTime%3600)/60;
		int s=(aTime%3600)%60;
		String aTxt="正在整理计算"+ConfigCt.AppName+"数据！请不要锁屏！剩余时间："+h+"小时"+m+"分钟"+s+"秒！";
		return aTxt;
	}
}
