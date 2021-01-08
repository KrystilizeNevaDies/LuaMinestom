package lua.api;

import java.util.HashMap;
import java.util.Map;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import script.plugin.language.LanguageAPI;
import script.plugin.language.LanguageJavaBinding;
import script.plugin.language.LanguageObject;

public class LuaJavaBinding extends LuaObject implements LanguageJavaBinding {
	
	private static Map<Integer, Object> objectMapping = new HashMap<Integer, Object>();
	
	private Object object;
	
	public LuaJavaBinding(LanguageAPI langAPI, Object object) {
		super(langAPI, LuaValue.NIL);
		this.object = object;
		LuaTable table = LuaTable.tableOf();
		table.set("ClassName", object.getClass().getName());
		Integer hashCode = object.hashCode();
		table.set("ID", hashCode);
		objectMapping.put(hashCode, object);
		this.value = table;
	}
	
	public LuaJavaBinding(LanguageAPI langAPI, Class<?> clazz) {
		super(langAPI, LuaValue.NIL);
		this.object = clazz;
		LuaTable table = LuaTable.tableOf();
		table.set("ClassName", clazz.getName());
		Integer hashCode = clazz.hashCode();
		table.set("ID", hashCode);
		objectMapping.put(hashCode, clazz);
		this.value = table;
	}

	@Override
	public Class<?> getJavaClass() {
		
		if (object instanceof Class) return (Class<?>) object;
		
		return object.getClass();
	}

	@Override
	public boolean isStatic() {
		return (object instanceof Class);
	}

	@Override
	public Object getJavaValue() {
		return object;
	}

	public static LanguageObject getValueOf(LanguageAPI langAPI, int ID) {
		return new LuaJavaBinding(langAPI, objectMapping.get(ID));
	}

	@Override
	public LanguageObject getChild(String idx) {
		LanguageAPI api = this.getAPI();
		LuaValue key = ((LuaObject) api.getScriptValue(idx)).value;
		LuaValue value = this.value.get(key);
		return api.getScriptValue(value);
	}

	@Override
	public void setChild(String idx, LanguageObject object) {
		LuaObject luaValue = ((LuaObject) object);
		if (luaValue != null)
		this.value.set(idx, luaValue.value);
	}
}
