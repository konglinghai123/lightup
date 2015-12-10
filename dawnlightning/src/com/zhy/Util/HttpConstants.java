package com.zhy.Util;

public class HttpConstants {

	
	public final static String HTTP_HEAD="https://";
	
	
	
	//外网
	public final static String HTTP_IP="ucqa.dawnlightning.com/";
	
	public final static String HTTP_CONTEXT="/capi/";
	
	public final static String HTTP_REQUEST=HTTP_HEAD+HTTP_IP+HTTP_CONTEXT;
	/*获取验证码*/
	public final static String HTTP_FRIST_REGISTER=HTTP_REQUEST+"do.php?ac=register&op=seccode";
	/*登陆*/
	public final static String HTTP_LOGIN=HTTP_REQUEST+"do.php?ac=login&username=@&password=*&loginsubmit=true";//登陆
	/*注册*/
	public final static String HTTP_REGISTER=HTTP_REQUEST+"do.php?ac=register&registersubmit=true&username=!&password=@&password2=#&seccode=$&m_auth=%";
	/*获取咨询详细*/
	public final static String HTTP_CONSULT_DETAIL=HTTP_REQUEST+"space.php?do=bwzt&uid=!&id=@";
	
	/*获取防伪验证码*/
	public final static String HTTP_FORMHASH=HTTP_REQUEST+"cp.php?ac=bwztformhash&m_auth=!";
	
	/*评论*/
	public final static String HTTP_COMMENT=HTTP_REQUEST+"cp.php?ac=comment&inajax=1&m_auth=!";
	
	/*获取咨询分类*/
	public final static String HTTP_SELECTION=HTTP_REQUEST+"space.php?do=bwzt&view=class";
	
	public final static String HTTP_SELECTION_EDITOR=HTTP_REQUEST+"cp.php?ac=bwzt&m_auth=!";
	/*上传图片*/
	public final static String HTTP_UPLOAD=HTTP_REQUEST+"cp.php?ac=upload&m_auth=!";
	
	/*获取所有咨询列表*/
	public final static String HTTP_CONSULT_ALL=HTTP_REQUEST+"space.php?do=bwzt&view=all&orderby=dateline&page=#";
	
	/*获取自己的咨询列表*/
	public final static String HTTP_CONSULT=HTTP_REQUEST+"space.php?uid=!&do=bwzt&view=me&m_auth=@&page=#";
	
	/*获取某一分类的咨询列表*/
	public final static String HTTP_CONSULT_ALL_CLASS=HTTP_REQUEST+"space.php?do=bwzt&view=all&bwztclassid=!&page=#&orderby=dateline";
	
	/*发布咨询*/
	public final static String HTTP_WRITE_CONSULT=HTTP_REQUEST+"cp.php?ac=bwzt&m_auth=@";
	
	/*删除咨询*/
	public final static String HTTP_DELETE_CONSULT=HTTP_REQUEST+"cp.php?ac=bwzt&bwztid=!&op=delete&m_auth=@&deletesubmit=true";
	
	/*获取上传头像参数*/
	public final static String HTTP_GET_AVATAR=HTTP_REQUEST+"cp.php?ac=avatar&m_auth=!";
	
	/*上传用户头像*/
	public final static String HTTP_UPLOAD_AVATAR=HTTP_HEAD+HTTP_IP+"ucenter/index.php?a=uploadavatar4m&m=user&agent=!&avatartype=@&input=#&appid=$&inajax=1";
	
	/*修改用户资料*/
	public final static String HTTP_EDIT_USEINFO=HTTP_REQUEST+"cp.php?ac=profile&m_auth=!&op=base";
	
	/*改变咨询状态*/
	public final static String HTTP_EDIT_CONSULT_STAUTS=HTTP_REQUEST+"cp.php?ac=bwzt&bwztid=!&m_auth=@&bwztsubmit=true&status=1&op=alterstatus";
	
	/*获取用户头像*/
	public final static String HTTP_GET_USERICON=HTTP_REQUEST+"cp.php?ac=avatar&m_auth=!&get_avatar=true&avatar_size=middle";
}
