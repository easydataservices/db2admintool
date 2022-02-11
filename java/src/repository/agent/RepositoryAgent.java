package com.easydataservices.db2admintool.repository.agent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.easydataservices.db2admintool.repository.agent.config.RepositoryAgentContext;
import com.easydataservices.db2admintool.repository.agent.db2luw.dao.SnapshotTransformDao;

//------------------------------------------------------------------------------
// File:         RepositoryAgent.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Main class for a repository agent process.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.17
 */
//------------------------------------------------------------------------------
public class RepositoryAgent {
  public static void main(String[] args) {
    // Initialise.
    ApplicationContext context = new AnnotationConfigApplicationContext(RepositoryAgentContext.class);
    SnapshotTransformDao snapshotTransformDao = context.getBean(SnapshotTransformDao.class);

    // Execute the agent activity.
    snapshotTransformDao.exec();
  }
}
