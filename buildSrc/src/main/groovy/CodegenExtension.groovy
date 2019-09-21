import org.gradle.api.*
import org.gradle.api.artifacts.*

class CodegenExtension {
	final Project project
	CodegenExtension (Project project) {
		this.project = project
	}
	List<Project> extendsFrom = []
	String packageName
	List<String> classNames

	void extendsFrom(Project extendsFrom) {
		project.evaluationDependsOn extendsFrom.path
		if (!extendsFrom.plugins.hasPlugin(CodegenPlugin)) {
			throw new Exception("${extendsFrom} does not have plugin $CodegenPlugin")
		}
		['api', 'implementation'].each { configName ->
			Configuration sourceConfig = extendsFrom.configurations[configName]
			Configuration targetConfig = project.configurations[configName]
			sourceConfig.allDependencies.all { Dependency dependency ->
				targetConfig.dependencies.add(dependency)
			}
		}
		this.extendsFrom << extendsFrom
	}
}