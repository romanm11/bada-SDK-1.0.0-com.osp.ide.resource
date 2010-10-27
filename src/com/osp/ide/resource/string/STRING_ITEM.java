package com.osp.ide.resource.string;

public class STRING_ITEM 
{
	public String caption[] = new String[StringConst.LANG_NUM];
	public STRING_ITEM()
	{
		for(int i=0; i<StringConst.LANG_NUM; i++)
			caption[i] = "";
	}
}
