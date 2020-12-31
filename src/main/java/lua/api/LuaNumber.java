package lua.api;

import org.luaj.vm2.LuaValue;

import script.plugin.language.LanguageAPI;
import script.plugin.language.LanguageNumber;

public class LuaNumber extends LuaObject implements LanguageNumber {
	
	LuaValue value = LuaValue.valueOf(0);
	
	public LuaNumber(LanguageAPI langAPI) {
		super(langAPI);
	}

	@Override
	public Number getJavaValue() {
		// TODO Auto-generated method stub
		return null;
	}
}
