package com.mgr.interf;

public interface GameConstants {
	// 炉石英雄、职业
	static final String HS_OCCUPATION_HUNTER = "hunter"; // 猎人
	static final String HS_OCCUPATION_MAGE = "mage"; // 法师
	static final String HS_OCCUPATION_PALADIN = "paladin";// 圣骑士
	static final String HS_OCCUPATION_PREIST = "preist"; // 牧师
	static final String HS_OCCUPATION_ROUGE = "rouge"; // 盗贼
	static final String HS_OCCUPATION_WARLOCK = "warlock";// 术士
	static final String HS_OCCUPATION_DRUID = "druid"; // 德鲁伊
	static final String HS_OCCUPATION_SHAMAN = "shaman"; // 萨满
	static final String HS_OCCUPATION_WARRIOR = "warrior";// 战士
	static final String HS_OCCUPATION_ALL = "all";
	static final String[] HS_OCCUPATION_LIST = { HS_OCCUPATION_DRUID,
			HS_OCCUPATION_HUNTER, HS_OCCUPATION_MAGE, HS_OCCUPATION_PALADIN,
			HS_OCCUPATION_PREIST, HS_OCCUPATION_ROUGE, HS_OCCUPATION_SHAMAN,
			HS_OCCUPATION_WARLOCK, HS_OCCUPATION_WARRIOR };
	static final Integer HS_MAX_WINS = 12;
}
