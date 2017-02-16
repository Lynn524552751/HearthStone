package com.mgr.service.sys;

import java.util.ArrayList;

import com.mgr.bean.LiveHost;
import com.mgr.bean.ServiceResult;

public interface NavsService {
	public ServiceResult<ArrayList<LiveHost>> getLiveHosts1(String typr, int per);

}
