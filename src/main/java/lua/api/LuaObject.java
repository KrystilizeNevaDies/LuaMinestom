package lua.api;

import org.luaj.vm2.LuaValue;

import script.plugin.language.LanguageAPI;
import script.plugin.language.LanguageObject;

public class LuaObject implements LanguageObject {
	
	LanguageAPI langAPI;
	
	public LuaValue value = LuaValue.NIL;
	
	public LuaObject(LanguageAPI langAPI) {
		this.langAPI = langAPI;
	}
	
	public LuaObject(LanguageAPI langAPI, LuaValue value) {
		this.langAPI = langAPI;
		this.value = value;
	}
	
	@Override
	public LanguageAPI getAPI() {
		return langAPI;
	}

}
