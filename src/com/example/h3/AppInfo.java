/**
 * 
 */
package com.example.h3;
import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
/**
 * @author ASUS
 *
 */
public class AppInfo {
	 private static AppInfo current;
	    private Context context;
	    public static final String TAG = "byc001";//调试标识：
	    
	    public List<AppsItemInfo> mAppInfos;//已安装的app信息；
	    public String[] mAppNames;//已安装的app名称；
	    public List<String> mListAppNames;//已安装的app名称；
	    
	    private AppInfo(Context context) {
	        this.context = context;
	        getAppInfos(context);
	    }
	    public static synchronized AppInfo getAppInfo(Context context) {
	        if(current == null) {
	            current = new AppInfo(context);
	        }
	        return current;
	    }
	    /*
	     * 获取app列表。
	     * 
	     * 
	     */
	    public List<String> GetListAppNames(){
	    	if(mListAppNames!=null)return mListAppNames;
	    	mListAppNames = new ArrayList<String>();
	    	mListAppNames.add("请选择牛牛游戏");
	    	for (AppsItemInfo app: mAppInfos) {
	    		mListAppNames.add(app.label);
	    	}
	    	return mListAppNames;
	    	
	    }
	    /*
	     * 获取app列表。
	     * 
	     * 
	     */
	    public String[] GetAppNames(){
	    	if(mAppNames!=null)return mAppNames;
	    	mAppNames = new String[mAppInfos.size()];
	    	int i=0;
	    	for (AppsItemInfo app: mAppInfos) {
	    		mAppNames[i]=app.getLabel();
	    		i=i+1;
	    	}
	    	return mAppNames;
	    	
	    }
	    /*
	     * 根据游戏名获取包名。
	     * GameName：游戏名称；
	     * PkgName：包名；
	     */
	    public String GetPkgName(int position){
	    	AppsItemInfo appinfo=mAppInfos.get(position);
	    	return appinfo.packageName;
	    }
	    /*
	     * 根据游戏名获取包名。
	     * GameName：游戏名称；
	     * PkgName：包名；
	     */
	    public String GetPkgName(String GameName){
	    	for(AppsItemInfo appinfo : mAppInfos){
	    		String appName=appinfo.getLabel();
	    		//Log.i(TAG, appName);
	    		if(appName.equals(GameName)){
	    			return  appinfo.getPackageName();
	    		}
	    	}
	    	return "";
	    }
	    /*
	     * 获取已安装的所有app信息 ：
	     */
	    public  void  getAppInfos(Context context){
	    	// 获取图片、应用名、包名
	    		// 用来记录应用程序的信息
	    	
	    	   PackageManager pManager = context.getPackageManager();
	    		List<PackageInfo> appList = getAllApps(context);

	    		mAppInfos = new ArrayList<AppsItemInfo>();

	    			for (int i = 0; i < appList.size(); i++) {
	    				PackageInfo pinfo = appList.get(i);
	    				AppsItemInfo shareItem = new AppsItemInfo();
	    				// 设置图片
	    				//shareItem.setIcon(pManager
	    						//.getApplicationIcon(pinfo.applicationInfo));
	    				// 设置应用程序名字
	    				shareItem.setLabel(pManager.getApplicationLabel(
	    						pinfo.applicationInfo).toString());
	    				// 设置应用程序的包名
	    				shareItem.setPackageName(pinfo.applicationInfo.packageName);

	    				mAppInfos.add(shareItem);
	    				Log.i(TAG, shareItem.getLabel());
	    				Log.i(TAG, shareItem.getPackageName());
	    			}

	       }
	    
		public static List<PackageInfo> getAllApps(Context context) {

			List<PackageInfo> apps = new ArrayList<PackageInfo>();
			PackageManager pManager = context.getPackageManager();
			// 获取手机内所有应用
			List<PackageInfo> packlist = pManager.getInstalledPackages(0);
			for (int i = 0; i < packlist.size(); i++) {
				PackageInfo pak = (PackageInfo) packlist.get(i);

				// 判断是否为非系统预装的应用程序
				// 这里还可以添加系统自带的，这里就先不添加了，如果有需要可以自己添加
				// if()里的值如果<=0则为自己装的程序，否则为系统工程自带
				if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
					// 添加自己已经安装的应用程序
					apps.add(pak);
				}

			}
			return apps;
		}
		
		// 自定义一个 AppsItemInfo 类，用来存储应用程序的相关信息
		private class AppsItemInfo {

			private Drawable icon; // 存放图片
			private String label; // 存放应用程序名
			private String packageName; // 存放应用程序包名

			public Drawable getIcon() {
				return icon;
			}

			public void setIcon(Drawable icon) {
				this.icon = icon;
			}

			public String getLabel() {
				return label;
			}

			public void setLabel(String label) {
				this.label = label;
			}

			public String getPackageName() {
				return packageName;
			}

			public void setPackageName(String packageName) {
				this.packageName = packageName;
			}

		}
}

