import org.gradle.api.*
import org.gradle.api.tasks.*

class CodegenTask extends DefaultTask {
	@OutputDirectory
	File outputDir
	
	void outputDir(Object outputDir) {
		this.outputDir = project.file(outputDir)
	}
	
	@TaskAction
	public void generate() {
		CodegenExtension codegenExtension = project.extensions['codegen']
		String packageName = codegenExtension.packageName
		List<String> classNames = codegenExtension.classNames
		File outputDir = this.outputDir
		classNames.each { className -> 
			File javaFile = project.file("$outputDir/${packageName.replace('.','/')}/${className}.java")
			javaFile.parentFile.mkdirs()
			javaFile.text = 
"""
package ${packageName};
public class $className {
	public String toString() {
		return "generated ${className} from ${project.name}";
	}
}
"""
			logger.lifecycle "Generated $javaFile"
		}
	}
}
