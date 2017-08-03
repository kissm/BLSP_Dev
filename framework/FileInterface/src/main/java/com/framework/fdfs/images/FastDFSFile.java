package com.framework.fdfs.images;

/**
 * Created by wangjunwei on 15-5-15.
 */
public abstract class FastDFSFile implements Image {
	private static final long serialVersionUID = 1L;

	public FastDFSFile(String extname) {
		this.extname = extname;
	}

	public static final String DEFALUT_GROUP_NAME = "group1";

	private String fileid;

	private String groupname = DEFALUT_GROUP_NAME;

	private String extname;

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExtname() {
		return extname;
	}

	public void setExtname(String extname) {
		this.extname = extname;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
}
