package com.mesabrook.ib.util.apiaccess;

public class GetData extends DataAccess {

	public GetData(API api, String resource) {
		super(api, resource);
	}
	
	public GetData(API api, String resource, Class<?>... returnTypes)
	{
		super(api, resource, returnTypes);
	}

	@Override
	protected String getMethod() { return "GET"; }
}
