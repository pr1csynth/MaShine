
package mashine.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import javax.script.ScriptEngineManager;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ScriptManager{

	private Map<Script,String> scriptChilds;   // script name by Script object
	private Map<String,String> scriptSources;  // script source by script name
	private Map<String,String> scriptFilePath; // script filepath by script name
	private List<String> scripts;
	private String preprocessorSource;
	private ScriptEngineManager engineManager;
	
	public ScriptManager(String[] packagedScriptList, String preprocessor){

		scriptChilds = new HashMap<Script,String>();
		scriptSources = new HashMap<String,String>();
		scriptFilePath = new HashMap<String,String>();


		preprocessorSource =  new Scanner(getClass().getResourceAsStream(preprocessor), "utf-8")
		.useDelimiter("\\A")
		.next();

		for(int i = 0; i < packagedScriptList.length; i++){
			if(!packagedScriptList[i].equals("")){
				String source = new Scanner(getClass().getResourceAsStream(packagedScriptList[i]), "utf-8")
				.useDelimiter("\\A")
				.next();
				scriptSources.put(getFileName(packagedScriptList[i]), source);
			}
		}
		genScriptList();
		engineManager = new ScriptEngineManager();
	}

	public Script getNewScript(String scriptName, Object parent) {
		if(scriptSources.containsKey(scriptName)){
			Script script = new Script(
				engineManager.getEngineByName("nashorn"),
				preprocessorSource,
				scriptSources.get(scriptName),
				parent
				);
			scriptChilds.put(script, scriptName);
			return script;
		}
		return null;
	}

	public List<String> getScripts(){
		return scripts;
	}

	private void genScriptList(){
		scripts = new ArrayList<String>(scriptSources.keySet());
		Collections.sort(scripts);
	}

	public void reloadScripts(){
		// for each script child, get source, if external, refresh script with reread data
		for(Script script : scriptChilds.keySet()){
			String scriptName = scriptChilds.get(script);
			if(scriptFilePath.containsKey(scriptName)){
				try{
					scriptSources.put(scriptName, readFile(scriptFilePath.get(scriptName)));
					script.reload(
						engineManager.getEngineByName("nashorn"),
						preprocessorSource,
						scriptSources.get(scriptName)
						// blocks.reloadNodes(); TODO
					);
				}catch(Exception e){e.printStackTrace();}
			}
		}
	}

	public void importFolder(String folderPath){
		try{
			File folder = new File(folderPath);
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".js")) {
					try{
						scriptFilePath.put(folderPath+listOfFiles[i].getName(), listOfFiles[i].getName());
						scriptSources.put(listOfFiles[i].getName(), readFile(folderPath+listOfFiles[i].getName()));
					}catch(Exception e){e.printStackTrace();}
				}
			}
			genScriptList();
			reloadScripts();
		}catch (Exception e) {e.printStackTrace();}
	}

	private String getFileName(String filepath){
		return filepath.substring(filepath.lastIndexOf('/')+1);
	}

	public static String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, StandardCharsets.UTF_8);
	}

}