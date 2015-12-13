package com.zhy.Util;

import java.util.List;

import org.apache.http.NameValuePair;

public class AjustUrl {
	public String ReturnUrl(String Url,final List<NameValuePair> params)
	{	
		String NewUrl=Url;
		switch(Url)
		{
		case HttpConstants.HTTP_LOGIN:
			String url=HttpConstants.HTTP_LOGIN;
			NewUrl=url.replace("@", params.get(0).getValue());
			NewUrl=NewUrl.replace("*", params.get(1).getValue());
		break;
		
		case HttpConstants.HTTP_FRIST_REGISTER:
			NewUrl = HttpConstants.HTTP_FRIST_REGISTER;
		break;
		case HttpConstants.HTTP_REGISTER:
			NewUrl=HttpConstants.HTTP_REGISTER
			.replace("!", params.get(0).getValue()).replace("@", params.get(1).getValue())
			.replace("#", params.get(2).getValue()).replace("$", params.get(3).getValue())
			.replace("%", params.get(4).getValue());
		break;
		case HttpConstants.HTTP_CONSULT:
			NewUrl=HttpConstants.HTTP_CONSULT.replace("!", params.get(0).getValue()).replace("@",params.get(1).getValue()).replace("#", params.get(2).getValue());
			break;
		case HttpConstants.HTTP_FORMHASH:
			NewUrl=HttpConstants.HTTP_FORMHASH.replace("!", params.get(0).getValue());
			break;
		case HttpConstants.HTTP_SELECTION:
			NewUrl=HttpConstants.HTTP_SELECTION;
			break;
		case HttpConstants.HTTP_CONSULT_DETAIL:
			NewUrl=HttpConstants.HTTP_CONSULT_DETAIL.replace("!", params.get(0).getValue()).replace("@", params.get(1).getValue()).replace("#", params.get(2).getValue());
			break;
		case HttpConstants.HTTP_CONSULT_ALL:
			NewUrl=HttpConstants.HTTP_CONSULT_ALL.replace("#", params.get(0).getValue());
			break;
		case HttpConstants.HTTP_CONSULT_ALL_CLASS:
			NewUrl=HttpConstants.HTTP_CONSULT_ALL_CLASS.replace("!",  params.get(0).getValue()).replace("#", params.get(1).getValue());
			break;
		case HttpConstants.HTTP_SELECTION_EDITOR:
			NewUrl=HttpConstants.HTTP_SELECTION_EDITOR.replace("!",  params.get(0).getValue());
			break;
		case HttpConstants.HTTP_DELETE_CONSULT:
			NewUrl=HttpConstants.HTTP_DELETE_CONSULT.replace("!",  params.get(0).getValue()).replace("@", params.get(1).getValue());
			break;
		case HttpConstants.HTTP_GET_AVATAR:
			NewUrl=HttpConstants.HTTP_GET_AVATAR.replace("!",  params.get(0).getValue());
			break;
		case HttpConstants.HTTP_UNREAD_MESSAGE:
			NewUrl=HttpConstants.HTTP_UNREAD_MESSAGE.replace("!",  params.get(0).getValue()).replace("@", params.get(1).getValue());
			break;
		
			
		}
		return NewUrl;
	}
}
