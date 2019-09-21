import org.gradle.api.*
import org.gradle.api.tasks.*
import org.gradle.api.file.*

class CodegenPlugin implements Plugin<Project> {
	void apply(Project project) {
		project.with {
			apply plugin: 'java-library'
			project.extensions.add('codegen', new CodegenExtension(project))
			CodegenTask codegenTask = tasks.create('codegenTask', CodegenTask) {
				outputDir "$buildDir/codegen"
			}
			tasks.create('codegenExtendsFromCopy', Copy) {
				dependsOn codegenTask
				from { project.extensions['codegen'].extendsFrom.collect { it.tasks.withType(CodegenTask) } }
				exclude { FileTreeElement el ->
					return (!el.directory) && file("${codegenTask.outputDir}/${el.path}").exists()
				}
				into "$buildDir/codegenExtendsFromCopy"
			}
			sourceSets.main.java.srcDir { codegenTask.outputDir }
			sourceSets.main.java.srcDir "$buildDir/codegenExtendsFromCopy"
			compileJava.dependsOn 'codegenExtendsFromCopy'
		}
	}
}