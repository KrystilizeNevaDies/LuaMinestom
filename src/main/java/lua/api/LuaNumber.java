package lua.api;

import org.luaj.vm2.LuaValue;

import script.plugin.language.LanguageAPI;
import script.plugin.language.LanguageNumber;

public class LuaNumber extends LuaObject implements LanguageNumber {
	
	public LuaNumber(Number num, LanguageAPI langAPI) {
		super(langAPI);
		value = LuaValue.valueOf(num.doubleValue());
	}

	@Override
	public Number getJavaValue() {
		return value.todouble();
	}
}