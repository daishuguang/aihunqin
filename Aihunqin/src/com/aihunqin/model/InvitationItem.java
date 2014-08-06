package com.aihunqin.model;

public class InvitationItem {
	String imguri;
	String itemdate;
	String iteminvitor;
	String itemdrink;
	String itemid;

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public InvitationItem() {
		super();
	}

	public InvitationItem(String imguri, String itemdate, String iteminvitor,
			String itemdrink, String itemid) {
		super();
		this.itemid = itemid;
		this.imguri = imguri;
		this.itemdate = itemdate;
		this.iteminvitor = iteminvitor;
		this.itemdrink = itemdrink;
	}

	public String getImguri() {
		return imguri;
	}

	public void setImguri(String imguri) {
		this.imguri = imguri;
	}

	public String getItemdate() {
		return itemdate;
	}

	public void setItemdate(String itemdate) {
		this.itemdate = itemdate;
	}

	public String getIteminvitor() {
		return iteminvitor;
	}

	public void setIteminvitor(String iteminvitor) {
		this.iteminvitor = iteminvitor;
	}

	public String getItemdrink() {
		return itemdrink;
	}

	public void setItemdrink(String itemdrink) {
		this.itemdrink = itemdrink;
	}

}
