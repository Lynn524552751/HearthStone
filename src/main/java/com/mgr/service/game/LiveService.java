package com.mgr.service.game;

import java.util.ArrayList;

import com.mgr.bean.LiveHost;
import com.mgr.bean.ServiceResult;

public interface LiveService {
	public ServiceResult<ArrayList<LiveHost>> loadLiveHosts(String typr, int per);

}
