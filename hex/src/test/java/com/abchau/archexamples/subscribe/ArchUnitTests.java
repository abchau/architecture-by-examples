package com.abchau.archexamples.subscribe;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures.LayeredArchitecture;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchUnitTests {
    
    @Test
    public void input_adapter_should_only_depend_on_application_core_and_input_port() {        
        JavaClasses jc = new ClassFileImporter()
			.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
			.importPackages("com.abchau.archexamples.subscribe");
        
        ArchRule r1 = classes()
          .that()
		  	.resideInAPackage("..inputadapter..")
          .should()
		  	.onlyDependOnClassesThat()
          		.resideInAnyPackage(
					// JDK
					"java..", 
					"javax.."
					// Spring
					, "org.springframework.."
					// Apache Logging
					, "org.apache.logging.."
					// jmolecules Annotations
					, "org.jmolecules.."
					// Application Core - Domain
					, "com.abchau.archexamples.subscribe.domain.."
					// Application Core - Input Port
					, "com.abchau.archexamples.subscribe.application.inputport..");
    
		r1.check(jc);
    }

	@Test
    public void should_follow_layered_architecture() {
        
        JavaClasses jc = new ClassFileImporter()
			.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
			.importPackages("com.abchau.archexamples.subscribe");
        
        LayeredArchitecture arch = layeredArchitecture()
           // Define layers
          .layer("Input Adapter").definedBy("..inputadapter..")
          .layer("Input Port").definedBy("..inputport..")
          .layer("Use Case").definedBy("..usecase..")
          .layer("Domain").definedBy("..domain..")
          .layer("Output Port").definedBy("..outputport..")
          .layer("Output Adapter").definedBy("..outputadapter..")
          // Add constraints
          .whereLayer("Input Adapter").mayNotBeAccessedByAnyLayer()
          .whereLayer("Input Port").mayOnlyBeAccessedByLayers("Use Case", "Input Adapter")
          .whereLayer("Use Case").mayOnlyBeAccessedByLayers("Input Port")
          .whereLayer("Output Port").mayOnlyBeAccessedByLayers("Use Case", "Output Adapter")
          .whereLayer("Output Adapter").mayOnlyBeAccessedByLayers("Use Case");
        
        arch.check(jc);
    }

	@Test
    public void should_be_free_of_cycle() {
		JavaClasses jc = new ClassFileImporter()
			.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
			.importPackages("com.abchau.archexamples.subscribe");
        
		ArchRule r1 = SlicesRuleDefinition.slices()
			.matching("..com.abchau.archexamples.subscribe.(*)..")
			.should().beFreeOfCycles();
    
		r1.check(jc);
	}

}
