package fr.ujf.m2pgi.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import static org.junit.Assert.*;

import java.io.File;

/**
 * Created by FAURE Adrien on 03/12/15.
 */
public abstract  class AbstractTestArquillian {


    @Deployment
    public static Archive<?> createTestArchive() {

        File[] libs = Maven.resolver().loadPomFromFile("./pom.xml").importRuntimeAndTestDependencies().asFile();

        WebArchive res = ShrinkWrap.create(WebArchive.class, "test.war");
        for(File file : libs){
            res.addAsLibrary(file);
        }
        res.addPackages(true, "fr.ujf.m2pgi")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("test-ds.xml", "test-ds.xml");

        return res;
    }

}