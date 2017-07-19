package com.howell.bean.soap;

/**
 * query request if this phone has been authenticated to server<br/>
 * uuid the unique id of this phone (in this example we used imei);
 *
 * @author howell
 */
public class QueryDeviceAuthenticatedReq {


	String UUID;

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public QueryDeviceAuthenticatedReq(String uUID) {
		super();
		UUID = uUID;
	}

	@Override
	public String toString() {
		return "QueryDeviceAuthenticatedReq [UUID=" + UUID + "]";
	}
	
}
