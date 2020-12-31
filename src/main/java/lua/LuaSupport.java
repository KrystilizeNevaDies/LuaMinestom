package lua;

import java.io.File;

import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;
import script.plugin.Plugin;
import script.plugin.ScriptSupport;
import script.plugin.language.LanguageAPI;
import script.plugin.language.LanguageBoolean;
import script.plugin.language.LanguageJavaBinding;
import script.plugin.language.LanguageNumber;
import script.plugin.language.LanguageObject;
import script.plugin.language.LanguageString;

public class LuaSupport extends Extension implements LanguageAPI {

	public static LuaSupport get() {
		return (LuaSupport) MinecraftServer.getExtensionManager().getExtension("LuaSupport");
	}
	
	@Override 
	public void preInitialize() {
		// Get extension
		ScriptSupport extension = (ScriptSupport) MinecraftServer.getExtensionManager().getExtension("LuaPluginSupport");
		
		// Add API to plugins
		extension.addLanguage(this);
	}
	
	@Override
	public void initialize() {
		
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LanguageBoolean booleanOf(Boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LanguageJavaBinding<?> classOf(Class<?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LanguageObject getGlobal(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean initializePlugin(Plugin arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isScript(File arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadScript(File arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LanguageNumber numberOf(Number arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LanguageJavaBinding<?> objectOf(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGlobal(String arg0, LanguageObject arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LanguageString stringOf(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
