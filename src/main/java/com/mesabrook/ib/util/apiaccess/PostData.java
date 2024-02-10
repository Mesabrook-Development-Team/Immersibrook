package com.mesabrook.ib.util.apiaccess;

public class PostData extends DataAccess {

	public PostData(API api, String resource, Object objToPost, Class<?>... returnTypes) {
		super(api, resource, returnTypes);
		sendableObject = objToPost;
	}

	@Override
	protected String getMethod() { return "POST"; }
}
