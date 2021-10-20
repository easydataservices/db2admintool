package com.easydataservices.db2admintool.agent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.easydataservices.db2admintool.agent.config.AgentContext;
import com.easydataservices.db2admintool.agent.db2luw.dao.monitor.DatabaseConfigSnapshotDao;

//------------------------------------------------------------------------------
// File:         Agent.java
// Licence:      GNU General Public License v3.0
// Description:  
/**
 * Main class for an agent process.
 * @author jeremy.rickard@easydataservices.com
 * @version 2021.10.17
 */
//------------------------------------------------------------------------------
public class Agent {
  public static void main(String[] args) {
    // Initialise
    ApplicationContext context = new AnnotationConfigApplicationContext(AgentContext.class);
    DatabaseConfigSnapshotDao databaseConfigSnapshotDao = context.getBean(DatabaseConfigSnapshotDao.class);

    // Get database configuation snapshot.
    databaseConfigSnapshotDao.capture();
  }
}
