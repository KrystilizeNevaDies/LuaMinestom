package lua.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import lua.LuaSupport;
import script.plugin.api.ScriptSupportAPI;
import script.plugin.language.LanguageAPI;
import script.plugin.language.LanguageFunction;
import script.plugin.language.LanguageObject;

public class LuaFunction extends LuaObject implements LanguageFunction {

	Function<ArrayList<LanguageObject>, ArrayList<LanguageObject>> func;
	
	public LuaFunction(LanguageAPI langAPI, @NotNull Function<ArrayList<LanguageObject>, ArrayList<LanguageObject>> func) {
		super(langAPI, LuaValue.NIL);
		this.value = new JavaFunction();
		this.func = func;
	}

	public LuaFunction(LanguageAPI langAPI, LuaValue func) {
		super(langAPI, func);
		this.value = func;
		this.func = javaLuaFunction(value);
	}

	private Function<ArrayList<LanguageObject>, ArrayList<LanguageObject>> javaLuaFunction(LuaValue func) {
		return (arr) -> {
			
			LuaValue[] args = new LuaValue[arr.size()];
			
			for (int i = 0; i < args.length; i++) {
				args[i] = ((LuaObject) arr.get(i)).value;
			}
			
			Varargs returns = func.invoke(args);
			
			ArrayList<LanguageObject> returnObjects = new ArrayList<LanguageObject>();
			
			for (int i = 1; i < returns.narg() + 1; i++) {
				returnObjects.add(LuaSupport.get().luaToJavaValue(returns.arg(i)));
			}
			
			return returnObjects;
		};
	}
	
	@Override
	public Function<ArrayList<LanguageObject>, ArrayList<LanguageObject>> getJavaValue() {
		return func;
	}
	
	private class JavaFunction extends VarArgFunction {
		@Override
		public Varargs invoke(Varargs varargs) {
			
			ArrayList<LanguageObject> list = new ArrayList<LanguageObject>();
			
			for (int i = 1; i < varargs.narg() + 1; i++) {
				LuaValue arg = varargs.arg(i);
				LuaSupport api = ((LuaSupport) getAPI());
				
				list.add(api.luaToJavaValue(arg));
			}
			
			List<LanguageObject> returnArgs = callFunction(list);
			
			LuaValue[] valueArray = new LuaValue[returnArgs.size()];
			
			for (int i = 0; i < returnArgs.size(); i++) {
				
				LuaObject object = ((LuaObject) returnArgs.get(i));
				
				if (object instanceof LuaJavaBinding)
				ScriptSupportAPI.addChildren((LuaJavaBinding) object, getAPI());
				
				valueArray[i] = object.value;
			}
			
			return LuaValue.varargsOf(valueArray);
		}
	}

	@Override
	public List<LanguageObject> callFunction(ArrayList<LanguageObject> args) {
		return func.apply(args);
	}
}
