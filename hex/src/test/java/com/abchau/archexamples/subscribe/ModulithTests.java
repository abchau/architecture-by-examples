package com.abchau.archexamples.subscribe;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.moduliths.model.Modules;

import org.moduliths.docs.Documenter;
import org.moduliths.docs.Documenter.CanvasOptions;
import org.moduliths.docs.Documenter.Options;

public class ModulithTests {
	
	@Test
	void should_generate_modulith_docs() throws IOException {

		Modules modules = Modules.of(SubscribeApplication.class);

		// Generate documentation
		Documenter documenter = new Documenter(modules);

		Options options = Options.defaults() //
				.withExclusions(module -> module.getName().matches(".*core|.*support"));

		// Write overall diagram
		documenter.writeModulesAsPlantUml(options);

		// Write diagrams for each module
		modules.stream().forEach(it -> documenter.writeModuleAsPlantUml(it, options));

		// Write module canvases
		documenter.writeModuleCanvases(CanvasOptions.defaults().withApiBase("{javadoc}"));
	}

}
