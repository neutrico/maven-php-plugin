/**
 * Copyright 2010-2012 by PHP-maven.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.phpmaven.phpunit.impl;

import java.io.File;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.component.annotations.Configuration;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.configuration.PlexusConfigurationException;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.phpmaven.core.ConfigurationParameter;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.exec.IPhpExecutable;
import org.phpmaven.exec.IPhpExecutableConfiguration;
import org.phpmaven.phpunit.IPhpunitSupport;

/**
 * Abstract Phpunit support base class.
 * 
 * @author Martin Eisengardt <Martin.Eisengardt@googlemail.com>
 * @since 2.0.0
 */
public abstract class AbstractPhpunitSupport implements IPhpunitSupport {
    
    /**
     * Additional cli arguments.
     */
    @Configuration(name = "arguments", value = "")
    private String arguments;
    
    /**
     * The resulting folder.
     */
    private File resultFolder;

    /**
     * true if a single invocation for multiple tests is performed; false if
     * every test is forked.
     */
    @Configuration(name = "singleInvocation", value = "true")
    private boolean isSingleInvocation;

    /**
     * Xml result file.
     */
    private File xmlResult;

    /**
     * Coverage result file.
     */
    private File coverageResult;
    
    /**
     * The executable configuration.
     */
    @ConfigurationParameter(name = "executableConfig", expression = "")
    private Xpp3Dom execConfig;
    
    /**
     * The component factory.
     */
    @Requirement
    private IComponentFactory factory;
    
    /**
     * The maven session.
     */
    @ConfigurationParameter(name = "session", expression = "${session}")
    private MavenSession session;
    
    /**
     * Returns the maven session.
     * @return maven session.
     */
    protected MavenSession getSession() {
        return this.session;
    }
    
    /**
     * Returns the executable configuration that should be used.
     * @return executable configuration.
     */
    protected Xpp3Dom getExecConfig() {
        return this.execConfig;
    }
    
    /**
     * Setter for the configuration framework.
     * @param dom executable config.
     */
    public void setExecutableConfig(Xpp3Dom dom) {
        this.execConfig = dom;
    }
    
    /**
     * Returns the executable to be used.
     * @param log the logger.
     * @return executable to be used.
     * @throws PlexusConfigurationException thrown on configuration errors.
     * @throws ComponentLookupException thrown on configuration errors.
     */
    protected IPhpExecutable getExec(Log log) throws ComponentLookupException, PlexusConfigurationException {
        final IPhpExecutableConfiguration config = this.factory.lookup(
                IPhpExecutableConfiguration.class,
                this.getExecConfig(),
                this.session);
        return config.getPhpExecutable(log);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPhpunitArguments() {
        return this.arguments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPhpunitArguments(String args) {
        this.arguments = args;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getResultFolder() {
        return this.resultFolder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setResultFolder(File folder) {
        this.resultFolder = folder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSingleTestInvocation() {
        return this.isSingleInvocation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsSingleTestInvocation(boolean isSingle) {
        this.isSingleInvocation = isSingle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getXmlResult() {
        return this.xmlResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setXmlResult(File xmlResult) {
        this.xmlResult = xmlResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getCoverageResult() {
        return this.coverageResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCoverageResult(File coverageResult) {
        this.coverageResult = coverageResult;
    }

}