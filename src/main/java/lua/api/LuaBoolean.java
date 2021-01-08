package lua.api;

import org.luaj.vm2.LuaValue;

import script.plugin.language.LanguageAPI;
import script.plugin.language.LanguageBoolean;

public class LuaBoolean extends LuaObject implements LanguageBoolean {
	
	public LuaBoolean(Boolean bool, LanguageAPI langAPI) {
		super(langAPI);
		value = LuaValue.valueOf(bool);
	}

	@Override
	public Boolean getJavaValue() {
		return value.toboolean();
	}
}