package com.github.abchau.oss.archexamples.subscription;

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
    public void web_interface_should_only_depend_on_packages() {        
        JavaClasses jc = new ClassFileImporter()
			.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
			.importPackages("com.github.abchau.oss.archexamples.subscription");
        
        ArchRule r1 = classes()
          .that()
		  	.resideInAPackage("..userinterface..")
          .should()
		  	.onlyDependOnClassesThat()
          		.resideInAnyPackage(
					// JDK
					"java..", 
					"jakarta.."
					// Spring
					, "org.springframework.."
					// Apache Logging
					, "org.apache.logging.."
					// jmolecules Annotations
					, "org.jmolecules.."
					// Interface - Web
					, "com.github.abchau.oss.archexamples.subscription.userinterface.web.."
										, "com.github.abchau.oss.archexamples.subscription.application.."
					// Domain Model
					, "com.github.abchau.oss.archexamples.subscription.domain.."
				);
    
		r1.check(jc);
    }

	@Test
    public void should_follow_layered_architecture() {
        
        JavaClasses jc = new ClassFileImporter()
			.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
			.importPackages("com.github.abchau.oss.archexamples.subscription");
        
        LayeredArchitecture arch = layeredArchitecture()
           // Define layers
          .layer("Interface").definedBy("..userinterface..")
          .layer("Application").definedBy("..application..")
          .layer("Domain").definedBy("..domain..")
          .layer("Infrastructure").definedBy("..infrastructure..")
          // Add constraints
          .whereLayer("Interface").mayNotBeAccessedByAnyLayer()
          .whereLayer("Application").mayOnlyBeAccessedByLayers("Interface")
          .whereLayer("Domain").mayOnlyBeAccessedByLayers("Interface", "Application", "Infrastructure")
          .whereLayer("Infrastructure").mayOnlyBeAccessedByLayers("Domain");
        
        arch.check(jc);
    }

	@Test
    public void should_be_free_of_cycle() {
		JavaClasses jc = new ClassFileImporter()
			.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
			.importPackages("com.github.abchau.oss.archexamples.subscription");
        
		ArchRule r1 = SlicesRuleDefinition.slices()
			.matching("..com.github.abchau.oss.archexamples.subscription.(*)..")
			.should().beFreeOfCycles();
    
		r1.check(jc);
	}

}
