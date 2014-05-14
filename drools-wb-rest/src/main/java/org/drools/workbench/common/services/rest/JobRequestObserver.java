/*
* Copyright 2013 JBoss Inc
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.drools.workbench.common.services.rest;

import static org.drools.workbench.common.services.rest.cmd.AbstractJobCommand.JOB_REQUEST_KEY;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.drools.workbench.common.services.rest.cmd.AddRepositoryToOrgUnitCmd;
import org.drools.workbench.common.services.rest.cmd.CompileProjectCmd;
import org.drools.workbench.common.services.rest.cmd.CreateOrCloneRepositoryCmd;
import org.drools.workbench.common.services.rest.cmd.CreateOrgUnitCmd;
import org.drools.workbench.common.services.rest.cmd.CreateProjectCmd;
import org.drools.workbench.common.services.rest.cmd.DeployProjectCmd;
import org.drools.workbench.common.services.rest.cmd.InstallProjectCmd;
import org.drools.workbench.common.services.rest.cmd.RemoveRepositoryCmd;
import org.drools.workbench.common.services.rest.cmd.RemoveRepositoryFromOrgUnitCmd;
import org.drools.workbench.common.services.rest.cmd.TestProjectCmd;
import org.kie.internal.executor.api.CommandContext;
import org.kie.internal.executor.api.ExecutorService;
import org.kie.workbench.common.services.shared.rest.AddRepositoryToOrganizationalUnitRequest;
import org.kie.workbench.common.services.shared.rest.CompileProjectRequest;
import org.kie.workbench.common.services.shared.rest.CreateOrCloneRepositoryRequest;
import org.kie.workbench.common.services.shared.rest.CreateOrganizationalUnitRequest;
import org.kie.workbench.common.services.shared.rest.CreateProjectRequest;
import org.kie.workbench.common.services.shared.rest.DeployProjectRequest;
import org.kie.workbench.common.services.shared.rest.InstallProjectRequest;
import org.kie.workbench.common.services.shared.rest.JobRequest;
import org.kie.workbench.common.services.shared.rest.RemoveOrganizationalUnitRequest;
import org.kie.workbench.common.services.shared.rest.RemoveRepositoryFromOrganizationalUnitRequest;
import org.kie.workbench.common.services.shared.rest.RemoveRepositoryRequest;
import org.kie.workbench.common.services.shared.rest.TestProjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class observing requests for various functions of the REST service
 */
@ApplicationScoped
public class JobRequestObserver {

    private static final Logger logger = LoggerFactory.getLogger( JobRequestObserver.class );

    @Inject
    private ExecutorService executorService;

    public void onCreateOrCloneRepositoryRequest( CreateOrCloneRepositoryRequest jobRequest ) {
        logger.info( "CreateOrCloneRepositoryRequest event received." );
        executorService.scheduleRequest(CreateOrCloneRepositoryCmd.class.getName(), getContext(jobRequest));
    }

    public void onRemoveRepositoryRequest( RemoveRepositoryRequest jobRequest ) {
        logger.info( "RemoveRepositoryRequest event received." );
        executorService.scheduleRequest(RemoveRepositoryCmd.class.getName(), getContext(jobRequest));
    }

    public void onCreateProjectRequest( CreateProjectRequest jobRequest ) {
        logger.info( "CreateProjectRequest event received." );
        executorService.scheduleRequest(CreateProjectCmd.class.getName(), getContext(jobRequest));
    }

    public void onCompileProjectRequest( CompileProjectRequest jobRequest ) {
        logger.info( "CompileProjectRequest event received." );
        executorService.scheduleRequest(CompileProjectCmd.class.getName(), getContext(jobRequest));
    }

    public void onInstallProjectRequest( InstallProjectRequest jobRequest ) {
        logger.info( "InstallProjectRequest event received." );
        executorService.scheduleRequest(InstallProjectCmd.class.getName(), getContext(jobRequest));
    }

    public void onTestProjectRequest( TestProjectRequest jobRequest ) {
        logger.info( "TestProjectRequest event received." );
        executorService.scheduleRequest(TestProjectCmd.class.getName(), getContext(jobRequest));
    }

    public void onDeployProjectRequest( DeployProjectRequest jobRequest ) {
        logger.info( "DeployProjectRequest event received." );
        executorService.scheduleRequest(DeployProjectCmd.class.getName(), getContext(jobRequest));
    }

    public void onCreateOrganizationalUnitRequest( CreateOrganizationalUnitRequest jobRequest ) {
        logger.info( "CreateOrganizationalUnitRequest event received." );
        executorService.scheduleRequest(CreateOrgUnitCmd.class.getName(), getContext(jobRequest));
    }

    public void onAddRepositoryToOrganizationalUnitRequest( AddRepositoryToOrganizationalUnitRequest jobRequest ) {
        logger.info( "AddRepositoryToOrganizationalUnitRequest event received." );
        executorService.scheduleRequest(AddRepositoryToOrgUnitCmd.class.getName(), getContext(jobRequest));
    }

    public void onRemoveRepositoryFromOrganizationalUnitRequest( RemoveRepositoryFromOrganizationalUnitRequest jobRequest ) {
        logger.info( "RemoveRepositoryFromOrganizationalUnitRequest event received." );
        executorService.scheduleRequest(RemoveRepositoryFromOrgUnitCmd.class.getName(), getContext(jobRequest));
    }

    public void onRemoveOrganizationalUnitRequest( RemoveOrganizationalUnitRequest jobRequest ) {
        logger.info( "RemoveOrganizationalUnitRequest event received." );
        executorService.scheduleRequest(RemoveOrganizationalUnitRequest.class.getName(), getContext(jobRequest));
    }
            
    protected CommandContext getContext(JobRequest jobRequest) {
        CommandContext ctx = new CommandContext();
        ctx.setData(JOB_REQUEST_KEY, jobRequest);
        ctx.setData("retries", 0);

        return ctx;
    }

}
