package com.osp.ide.resource.string;

public class STRING_INFO 
{// ��Ʈ�� ���̺� ����
	// �ش��ϴ� ��Ʈ�� ���̵� ���
	// |IDS_ITEM||IDS_ITEM0|...
	public String items = "";
	// �ش��ϴ� ��Ʈ�� ����
	public int itemNum;

	public boolean include;
	public RefInt hex;
	
	public STRING_INFO()
	{
		hex = new RefInt();
	}
}
