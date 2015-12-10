package com.zhy.Adapter;



import java.util.ArrayList;

import com.dawnlightning.Frag.ConsultFrag;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TabAdapter extends FragmentPagerAdapter
{


	public static final String[] TITLES = new String[] { "咨询", "问诊", "个人" };
	private Context context;
	private ArrayList<String> userinfo;
	public TabAdapter(FragmentManager fm,Context context,ArrayList<String> userinfo)
	{	
		super(fm);
		this.context=context;
		this.userinfo=userinfo;
	}


	@Override
	public Fragment getItem(int arg0)
	{
		//MainFragment fragment = new MainFragment(arg0);
		//return fragment;
		ConsultFrag  consult = null;
		// consult=new ConsultFrag("咨询", context,userinfo);
		 return consult;
		/*switch(arg0){
    	case 0:
    	
    		break;
    	default:
    		
    		break;
    	}
        return consult;*/
	}


	@Override
	public CharSequence getPageTitle(int position)
	{
		return TITLES[position % TITLES.length];
	}


	@Override
	public int getCount()
	{
		return TITLES.length;
	}


}



