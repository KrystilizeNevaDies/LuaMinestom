package lua.api;

import org.luaj.vm2.LuaValue;

import script.plugin.language.LanguageAPI;
import script.plugin.language.LanguageString;

public class LuaString extends LuaObject implements LanguageString {
	
	public LuaString(LanguageAPI langAPI, String str) {
		super(langAPI);
		value = LuaValue.valueOf(str);
	}

	@Override
	public String getJavaValue() {
		return value.toString();
	}
}