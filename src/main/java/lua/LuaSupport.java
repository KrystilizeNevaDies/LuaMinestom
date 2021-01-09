package lua;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import lua.api.LuaBoolean;
import lua.api.LuaFunction;
import lua.api.LuaJavaBinding;
import lua.api.LuaNumber;
import lua.api.LuaObject;
import lua.api.LuaString;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;
import script.plugin.Plugin;
import script.plugin.ScriptSupport;
import script.plugin.language.LanguageAPI;
import script.plugin.language.LanguageBoolean;
import script.plugin.language.LanguageFunction;
import script.plugin.language.LanguageJavaBinding;
import script.plugin.language.LanguageNumber;
import script.plugin.language.LanguageObject;
import script.plugin.language.LanguageString;

public class LuaSupport extends Extension implements LanguageAPI {

	Map<Plugin, Globals> pluginGlobals = new HashMap<Plugin, Globals>();
	
	public static LuaSupport get() {
		return (LuaSupport) MinecraftServer.getExtensionManager().getExtension("LuaSupport");
	}
	
	private static Boolean debug = false;
	
	@Override 
	public void preInitialize() {
		// Get extension
		ScriptSupport extension = (ScriptSupport) MinecraftServer.getExtensionManager().getExtension("ScriptSupport");
		
		// Add API to plugins
		extension.addLanguage(this);
	}
	
	@Override
	public void initialize() {
		
	}

	@Override
	public void terminate() {
	}

	@Override
	public LanguageBoolean booleanOf(Boolean bool) {
		return new LuaBoolean(bool, this);
	}

	@Override
	public LanguageJavaBinding classOf(Class<?> clazz) {
		return new LuaJavaBinding(this, clazz);
	}

	@Override
	public LanguageObject getGlobal(Plugin plugin, String str) {
		return luaToAPIValue(pluginGlobals.get(plugin).get(str));
	}

	@Override
	public Boolean initializePlugin(Plugin plugin) {
		pluginGlobals.put(plugin, JsePlatform.standardGlobals());
		return true;
	}

	@Override
	public Boolean isScript(File file, String fileContents) {
		return file.getName().endsWith(".lua");
	}

	@Override
	public void loadScript(Plugin plugin, File file, String fileContents) {
		pluginGlobals.get(plugin).load(fileContents).call();
	}

	@Override
	public LanguageNumber numberOf(Number num) {
		return new LuaNumber(num, this);
	}

	@Override
	public LanguageObject objectOf(Object obj) {
		LanguageJavaBinding binding = new LuaJavaBinding(this, obj);
		
		return binding; 
	}

	@Override
	public void setGlobal(Plugin plugin, String str, LanguageObject obj) {
		log("LuaSupport -> Setting global: " + str);
		pluginGlobals.get(plugin).set(str, ((LuaObject) obj).value);
	}

	@Override
	public LanguageString stringOf(String str) {
		return new LuaString(this, str);
	}
	
	public LanguageObject luaToAPIValue(LuaValue value) {
		if (value.isnumber()) {return numberOf(value.todouble());};
		if (value.isstring()) {return stringOf(value.tojstring());};
		if (value.isboolean()) {return booleanOf(value.toboolean());};
		if (value.istable()) {return LuaJavaBinding.getValueOf(this, value.get("ID").toint());};
		if (value.isfunction()) {return new LuaFunction(this, value);};
		return null;
	}
	
	@Override
	public Object getJavaValue(LanguageObject object) {
		if (object instanceof LuaValue) return getJavaValue(luaToAPIValue((LuaValue) object));
		if (object instanceof LanguageNumber) return ((LanguageNumber) object).getJavaValue();
		if (object instanceof LanguageString) return ((LanguageString) object).getJavaValue();
		if (object instanceof LanguageBoolean) return ((LanguageBoolean) object).getJavaValue();
		if (object instanceof LanguageFunction) return ((LanguageFunction) object).getJavaValue();
		if (object instanceof LanguageJavaBinding) return ((LanguageJavaBinding) object).getJavaValue();
		return object.toString();
	}

	@Override
	public LanguageFunction functionOf(Function<ArrayList<LanguageObject>, ArrayList<LanguageObject>> arg) {
		return new LuaFunction(this, arg);
	}
	
	public static void log(String str) {
		if (debug) System.out.println("DEBUG: " + str);
	}
}
