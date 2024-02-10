package com.mesabrook.ib.util.apiaccess;

public class PutData extends DataAccess {

	public PutData(API api, String resource, Object objectToPut, Class<?>... returnTypes) {
		super(api, resource, returnTypes);
		sendableObject = objectToPut;
	}

	@Override
	protected String getMethod() { return "PUT"; }
}
