package com.zhy.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App {

	
	public void init(){
		
		if(SdCardUtil.checkSdCard()==true){
			SdCardUtil.createFileDir(SdCardUtil.FILEDIR);
			SdCardUtil.createFileDir(SdCardUtil.FILEDIR+"/"+SdCardUtil.FILEPHOTO);
			SdCardUtil.createFileDir(SdCardUtil.FILEDIR+"/"+SdCardUtil.FILEIMAGE);
			SdCardUtil.createFileDir(SdCardUtil.FILEDIR+"/"+SdCardUtil.FILECACHE);
			SdCardUtil.createFileDir(SdCardUtil.FILEDIR+"/"+SdCardUtil.FILEUSER+"/icon");
		}else{
		
		}
		
	}
	
	List<File> allFile=new ArrayList<File>();
	
	
}
