package org.example.gradle.internal.conventions;

import org.example.gradle.internal.conventions.info.GitInfo;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.provider.ProviderFactory;

import javax.inject.Inject;
import java.io.File;

class GitInfoPlugin implements Plugin<Project> {

    private ProviderFactory factory;
    private ObjectFactory objectFactory;

    private Provider<String> revision;
    private Property<GitInfo> gitInfo;

    @Inject
    GitInfoPlugin(ProviderFactory factory, ObjectFactory objectFactory) {
        this.factory = factory;
        this.objectFactory = objectFactory;
    }

    @Override
    public void apply(Project project) {
        File rootDir = (project.getGradle().getParent() == null) ?
                project.getRootDir() :
                project.getGradle().getParent().getRootProject().getRootDir();

        gitInfo = objectFactory.property(GitInfo.class).value(factory.provider(() ->
                GitInfo.gitInfo(rootDir)
        ));
        gitInfo.disallowChanges();
        gitInfo.finalizeValueOnRead();

        revision = gitInfo.map(info -> info.getRevision() == null ? info.getRevision() : "master");
    }

    public Property<GitInfo> getGitInfo() {
        return gitInfo;
    }

    public Provider<String> getRevision() {
        return revision;
    }
}