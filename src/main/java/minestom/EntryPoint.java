package minestom;

import lua.plugin.LuaPluginSupport;
import minestom.api.Autogeneration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;

public class EntryPoint extends Extension {

	@Override 
	public void preInitialize() {
		// Get extension
		LuaPluginSupport extension = (LuaPluginSupport) MinecraftServer.getExtensionManager().getExtension("LuaPluginSupport");
		
		// Add API to plugins
		extension.addAPI((plugin) -> {Autogeneration.Autogenerate(plugin);});
	}
	
	@Override
	public void initialize() {
		
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}

}
