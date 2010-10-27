package com.osp.ide.resource.string;

public class STRING_INFO 
{// 스트링 테이블 정보
	// 해당하는 스트링 아이디 목록
	// |IDS_ITEM||IDS_ITEM0|...
	public String items = "";
	// 해당하는 스트링 개수
	public int itemNum;

	public boolean include;
	public RefInt hex;
	
	public STRING_INFO()
	{
		hex = new RefInt();
	}
}
