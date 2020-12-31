package lua.api;

import script.plugin.language.LanguageAPI;
import script.plugin.language.LanguageObject;

public class LuaObject implements LanguageObject {
	
	LanguageAPI langAPI;
	
	public LuaObject(LanguageAPI langAPI) {
		this.langAPI = langAPI;
	}
	
	@Override
	public LanguageAPI getAPI() {
		return langAPI;
	}

}
