package eu.mikart.panoptic;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

public class PanopticLoader implements PluginLoader {

	@Override
	public void classloader(PluginClasspathBuilder classpathBuilder) {
		MavenLibraryResolver resolver = new MavenLibraryResolver();

		resolver.addDependency(new Dependency(new DefaultArtifact("org.graalvm.polyglot", "polyglot", "jar", "24.2.2"), null));
		resolver.addDependency(new Dependency(new DefaultArtifact("org.graalvm.js", "js", null, "pom", "24.2.2"), null));
		resolver.addDependency(new Dependency(new DefaultArtifact("org.graalvm.truffle", "truffle-runtime", "jar", "24.2.2"), null));
		resolver.addDependency(new Dependency(new DefaultArtifact("org.graalvm.js", "js-language", "jar", "24.2.2"), null));

		resolver.addRepository(new RemoteRepository.Builder("central", "default", "https://repo.papermc.io/repository/maven-public").build());

		classpathBuilder.addLibrary(resolver);
	}

}
