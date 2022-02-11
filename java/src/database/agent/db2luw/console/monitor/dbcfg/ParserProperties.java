package com.easydataservices.db2admintool.database.agent.db2luw.console.monitor.dbcfg;

import java.util.Properties;
import com.easydataservices.db2admintool.database.agent.db2luw.console.monitor.Db2pdParserProperties;

//------------------------------------------------------------------------------
// File:         ParserProperties.java
// Licence:      Apache License 2.0
// Description:  
/**
 * Constants for parsing output from {@code db2pd -dbcfg}.
 * @author jeremy.rickard@easydataservices.com
 * @version 2022.01.03
 */ 
//------------------------------------------------------------------------------
public class ParserProperties extends Db2pdParserProperties {
  private Properties parameterProperties = new Properties();
  private Properties sectionRegexProperties = new Properties();

  /**
   * Constructor.
   */
  public ParserProperties() {
    super();
    setSectionRegexProperties();
    setParameterProperties();
  }

  /**
   * Return properties for each configuration parameter.
   */
  public Properties getParameterProperties() {
    return parameterProperties;
  }

  /**
   * Return regex properties for section header.
   */
  public Properties getSectionRegexProperties() {
    return sectionRegexProperties;
  }

  /**
   * Set properties for each configuration parameter.
   */
  protected void setParameterProperties() {   
    parameterProperties.setProperty("DB configuration release level", "DB_CFG_RELEASE_LEVEL");
    parameterProperties.setProperty("Database release level", "DB_RELEASE_LEVEL");
    parameterProperties.setProperty("Database territory", "DB_TERRITORY");
    parameterProperties.setProperty("Database code page", "DB_CODE_PAGE");
    parameterProperties.setProperty("Database code set", "DB_CODE_SET");
    parameterProperties.setProperty("Database country/region code", "DB_REGION_CODE");
    parameterProperties.setProperty("Database collating sequence", "DB_COLLATE_SEQUENCE");
    parameterProperties.setProperty("ALT_COLLATE", "ALT_COLLATE");
    parameterProperties.setProperty("NUMBER_COMPAT", "NUMBER_COMPAT");
    parameterProperties.setProperty("VARCHAR2_COMPAT", "VARCHAR2_COMPAT");
    parameterProperties.setProperty("DATE_COMPAT", "DATE_COMPAT");
    parameterProperties.setProperty("Database page size", "DB_PAGE_SIZE");
    parameterProperties.setProperty("Statement concentrator", "STMT_CONC");
    parameterProperties.setProperty("DISCOVER_DB", "DISCOVER_DB");
    parameterProperties.setProperty("Restrict access", "RESTRICT_ACCESS");
    parameterProperties.setProperty("DFT_QUERYOPT", "DFT_QUERYOPT");
    parameterProperties.setProperty("DFT_DEGREE", "DFT_DEGREE");
    parameterProperties.setProperty("DFT_SQLMATHWARN", "DFT_SQLMATHWARN");
    parameterProperties.setProperty("DFT_REFRESH_AGE", "DFT_REFRESH_AGE");
    parameterProperties.setProperty("DFT_MTTB_TYPES", "DFT_MTTB_TYPES");
    parameterProperties.setProperty("NUM_FREQVALUES", "NUM_FREQVALUES");
    parameterProperties.setProperty("NUM_QUANTILES", "NUM_QUANTILES");
    parameterProperties.setProperty("DECFLT_ROUNDING", "DECFLT_ROUNDING");
    parameterProperties.setProperty("Backup pending", "BACKUP_PENDING");
    parameterProperties.setProperty("Database is consistent", "DATABASE_CONSISTENT");
    parameterProperties.setProperty("Rollforward pending", "ROLLFORWARD_PENDING");
    parameterProperties.setProperty("Restore pending", "RESTORE_PENDING");
    parameterProperties.setProperty("Multi-page File alloc enabled", "MULTIPAGE_FILE_ALLOC_ENABLED");
    parameterProperties.setProperty("SELF_TUNING_MEM", "SELF_TUNING_MEM");
    parameterProperties.setProperty("ENABLE_XMLCHAR", "ENABLE_XMLCHAR");
    parameterProperties.setProperty("WLM_COLLECT_INT (mins)", "WLM_COLLECT_INT");
    parameterProperties.setProperty("DATABASE_MEMORY (4KB)", "DATABASE_MEMORY");
    parameterProperties.setProperty("DB_MEM_THRESH", "DB_MEM_THRESH");
    parameterProperties.setProperty("LOCKLIST (4KB)", "LOCKLIST");
    parameterProperties.setProperty("MAXLOCKS", "MAXLOCKS");
    parameterProperties.setProperty("PCKCACHESZ (4KB)", "PCKCACHESZ");
    parameterProperties.setProperty("SHEAPTHRES_SHR (4KB)", "SHEAPTHRES_SHR");
    parameterProperties.setProperty("SORTHEAP (4KB)", "SORTHEAP");
    parameterProperties.setProperty("DBHEAP (4KB)", "DBHEAP");
    parameterProperties.setProperty("CATALOGCACHE_SZ (4KB)", "CATALOGCACHE_SZ");
    parameterProperties.setProperty("LOGBUFSZ (4KB)", "LOGBUFSZ");
    parameterProperties.setProperty("UTIL_HEAP_SZ (4KB)", "UTIL_HEAP_SZ");
    parameterProperties.setProperty("APPL_MEMORY (4KB)", "APPL_MEMORY");
    parameterProperties.setProperty("STMTHEAP (4KB)", "STMTHEAP");
    parameterProperties.setProperty("APPLHEAPSZ (4KB)", "APPLHEAPSZ");
    parameterProperties.setProperty("STAT_HEAP_SZ (4KB)", "STAT_HEAP_SZ");
    parameterProperties.setProperty("DLCHKTIME (ms)", "DLCHKTIME");
    parameterProperties.setProperty("LOCKTIMEOUT (secs)", "LOCKTIMEOUT");
    parameterProperties.setProperty("CHNGPGS_THRESH", "CHNGPGS_THRESH");
    parameterProperties.setProperty("NUM_IOCLEANERS", "NUM_IOCLEANERS");
    parameterProperties.setProperty("NUM_IOSERVERS", "NUM_IOSERVERS");
    parameterProperties.setProperty("SEQDETECT", "SEQDETECT");
    parameterProperties.setProperty("DFT_PREFETCH_SZ (pages)", "DFT_PREFETCH_SZ");
    parameterProperties.setProperty("TRACKMOD", "TRACKMOD");
    parameterProperties.setProperty("Default number of containers", "DFT_NUM_CONTAINERS");
    parameterProperties.setProperty("DFT_EXTENT_SZ (pages)", "DFT_EXTENT_SZ");
    parameterProperties.setProperty("MAXAPPLS", "MAXAPPLS");
    parameterProperties.setProperty("AVG_APPLS", "AVG_APPLS");
    parameterProperties.setProperty("MAXFILOP", "MAXFILOP");
    parameterProperties.setProperty("LOGFILSIZ (4KB)", "LOGFILSIZ (4KB)");
    parameterProperties.setProperty("LOGPRIMARY", "LOGPRIMARY");
    parameterProperties.setProperty("LOGSECOND", "LOGSECOND");
    parameterProperties.setProperty("NEWLOGPATH", "NEWLOGPATH");
    parameterProperties.setProperty("Path to log files", "LOGPATH");
    parameterProperties.setProperty("OVERFLOWLOGPATH", "OVERFLOWLOGPATH");
    parameterProperties.setProperty("MIRRORLOGPATH", "MIRRORLOGPATH");
    parameterProperties.setProperty("First active log file", "FIRST_ACTIVE_LOG_FILE");
    parameterProperties.setProperty("BLK_LOG_DSK_FUL", "BLK_LOG_DSK_FUL");
    parameterProperties.setProperty("BLOCKNONLOGGED", "BLOCKNONLOGGED");
    parameterProperties.setProperty("MAX_LOG", "MAX_LOG");
    parameterProperties.setProperty("NUM_LOG_SPAN", "NUM_LOG_SPAN");
    parameterProperties.setProperty("SOFTMAX", "SOFTMAX");
    parameterProperties.setProperty("PAGE_AGE_TRGT_MCR", "PAGE_AGE_TRGT_MCR");
    parameterProperties.setProperty("PAGE_AGE_TRGT_GCR", "PAGE_AGE_TRGT_GCR");
    parameterProperties.setProperty("HADR database role", "HADR_ROLE");
    parameterProperties.setProperty("HADR_LOCAL_HOST", "HADR_LOCAL_HOST");
    parameterProperties.setProperty("HADR_LOCAL_SVC", "HADR_LOCAL_SVC");
    parameterProperties.setProperty("HADR_REMOTE_HOST", "HADR_REMOTE_HOST");
    parameterProperties.setProperty("HADR_TARGET_LIST", "HADR_TARGET_LIST");
    parameterProperties.setProperty("HADR_REMOTE_SVC", "HADR_REMOTE_SVC");
    parameterProperties.setProperty("HADR_REMOTE_INST", "HADR_REMOTE_INST");
    parameterProperties.setProperty("HADR_TIMEOUT", "HADR_TIMEOUT");
    parameterProperties.setProperty("HADR_SYNCMODE", "HADR_SYNCMODE");
    parameterProperties.setProperty("HADR_PEER_WINDOW (secs)", "HADR_PEER_WINDOW");
    parameterProperties.setProperty("LOGARCHMETH1", "LOGARCHMETH1");
    parameterProperties.setProperty("LOGARCHCOMPR1", "LOGARCHCOMPR1");
    parameterProperties.setProperty("LOGARCHOPT1", "LOGARCHOPT1");
    parameterProperties.setProperty("LOGARCHMETH2", "LOGARCHMETH2");
    parameterProperties.setProperty("LOGARCHCOMPR2", "LOGARCHCOMPR2");
    parameterProperties.setProperty("LOGARCHOPT2", "LOGARCHOPT2");
    parameterProperties.setProperty("OPT_DIRECT_WRKLD", "OPT_DIRECT_WRKLD");
    parameterProperties.setProperty("FAILARCHPATH", "FAILARCHPATH");
    parameterProperties.setProperty("NUMARCHRETRY", "NUMARCHRETRY");
    parameterProperties.setProperty("ARCHRETRYDELAY (secs)", "ARCHRETRYDELAY");
    parameterProperties.setProperty("VENDOROPT", "VENDOROPT");
    parameterProperties.setProperty("AUTORESTART", "AUTORESTART");
    parameterProperties.setProperty("INDEXREC", "INDEXREC");
    parameterProperties.setProperty("LOGINDEXBUILD", "LOGINDEXBUILD");
    parameterProperties.setProperty("DFT_LOADREC_SES", "DFT_LOADREC_SES");
    parameterProperties.setProperty("NUM_DB_BACKUPS", "NUM_DB_BACKUPS");
    parameterProperties.setProperty("REC_HIS_RETENTN (days)", "REC_HIS_RETENTN");
    parameterProperties.setProperty("AUTO_DEL_REC_OBJ", "AUTO_DEL_REC_OBJ");
    parameterProperties.setProperty("TSM_MGMTCLASS", "TSM_MGMTCLASS");
    parameterProperties.setProperty("TSM_NODENAME", "TSM_NODENAME");
    parameterProperties.setProperty("TSM_OWNER", "TSM_OWNER");
    parameterProperties.setProperty("TSM_PASSWORD", ""); // Not captured
    parameterProperties.setProperty("AUTO_MAINT", "AUTO_MAINT");
    parameterProperties.setProperty("AUTO_DB_BACKUP", "AUTO_DB_BACKUP");
    parameterProperties.setProperty("AUTO_TBL_MAINT", "AUTO_TBL_MAINT");
    parameterProperties.setProperty("AUTO_RUNSTATS", "AUTO_RUNSTATS");
    parameterProperties.setProperty("AUTO_STMT_STATS", "AUTO_STMT_STATS");
    parameterProperties.setProperty("AUTO_STATS_VIEWS", "AUTO_STATS_VIEWS");
    parameterProperties.setProperty("AUTO_SAMPLING", "AUTO_SAMPLING");
    parameterProperties.setProperty("AUTO_REORG", "AUTO_REORG");
    parameterProperties.setProperty("CUR_COMMIT", "CUR_COMMIT");
    parameterProperties.setProperty("AUTO_REVAL", "AUTO_REVAL");
    parameterProperties.setProperty("DBOKFORSD", "DBOKFORSD");
    parameterProperties.setProperty("DEC_TO_CHAR_FMT", "DEC_TO_CHAR_FMT");
    parameterProperties.setProperty("MON_REQ_METRICS", "MON_REQ_METRICS");
    parameterProperties.setProperty("MON_ACT_METRICS", "MON_ACT_METRICS");
    parameterProperties.setProperty("MON_OBJ_METRICS", "MON_OBJ_METRICS");
    parameterProperties.setProperty("MON_RTN_DATA", "MON_RTN_DATA");
    parameterProperties.setProperty("MON_RTN_EXECLIST", "MON_RTN_EXECLIST");
    parameterProperties.setProperty("MON_UOW_DATA", "MON_UOW_DATA");
    parameterProperties.setProperty("MON_UOW_PKGLIST", "MON_UOW_PKGLIST");
    parameterProperties.setProperty("MON_UOW_EXECLIST", "MON_UOW_EXECLIST");
    parameterProperties.setProperty("MON_LOCKTIMEOUT", "MON_LOCKTIMEOUT");
    parameterProperties.setProperty("MON_DEADLOCK", "MON_DEADLOCK");
    parameterProperties.setProperty("MON_LOCKWAIT", "MON_LOCKWAIT");
    parameterProperties.setProperty("MON_LW_THRESH", "MON_LW_THRESH");
    parameterProperties.setProperty("SMTP Server", "SMTP_SERVER");
    parameterProperties.setProperty("CF_SELF_TUNING_MEM", "CF_SELF_TUNING_MEM");
    parameterProperties.setProperty("CF_DB_MEM_SZ", "CF_DB_MEM_SZ");
    parameterProperties.setProperty("CF_GBP_SZ", "CF_GBP_SZ");
    parameterProperties.setProperty("CF_LOCK_SZ", "CF_LOCK_SZ");
    parameterProperties.setProperty("CF_SCA_SZ", "CF_SCA_SZ");
    parameterProperties.setProperty("CF_CATCHUP_TRGT", "CF_CATCHUP_TRGT");
    parameterProperties.setProperty("MON_PKGLIST_SZ", "MON_PKGLIST_SZ");
    parameterProperties.setProperty("MON_LCK_MSG_LVL", "MON_LCK_MSG_LVL");
    parameterProperties.setProperty("SQL_CCFLAGS", "SQL_CCFLAGS");
    parameterProperties.setProperty("SECTION_ACTUALS", "SECTION_ACTUALS");
    parameterProperties.setProperty("SYSTIME_PERIOD_ADJ", "SYSTIME_PERIOD_ADJ");
    parameterProperties.setProperty("LOG_DDL_STMTS", "LOG_DDL_STMTS");
    parameterProperties.setProperty("LOG_APPL_INFO", "LOG_APPL_INFO");
    parameterProperties.setProperty("DFT_SCHEMAS_DCC", "DFT_SCHEMAS_DCC");
    parameterProperties.setProperty("DFT_TABLE_ORG", "DFT_TABLE_ORG");
    parameterProperties.setProperty("STRING_UNITS", "STRING_UNITS");
    parameterProperties.setProperty("NCHAR_MAPPING", "NCHAR_MAPPING");
    parameterProperties.setProperty("DBCFD_CFG_SIGNATURE", "DBCFD_CFG_SIGNATURE");
    parameterProperties.setProperty("DBCFD_GLOB_CFG_SIGNATURE", "DBCFD_GLOB_CFG_SIGNATURE");
    parameterProperties.setProperty("HADR_SPOOL_LIMIT", "HADR_SPOOL_LIMIT");
    parameterProperties.setProperty("HADR_REPLAY_DELAY", "HADR_REPLAY_DELAY");
    parameterProperties.setProperty("Database is in write suspend state", "DB_WRITE_SUSPEND");
    parameterProperties.setProperty("CONNECT_PROC", "CONNECT_PROC");
    parameterProperties.setProperty("EXTENDED_ROW_SZ", "EXTENDED_ROW_SZ");
    parameterProperties.setProperty("ENCRLIB", "ENCRLIB");
    parameterProperties.setProperty("ENCROPTS", "ENCROPTS");
    parameterProperties.setProperty("ENCRYPTED_DATABASE", "ENCRYPTED_DATABASE");
    parameterProperties.setProperty("EXTBL_STRICT_IO", "EXTBL_STRICT_IO");
    parameterProperties.setProperty("EXTBL_LOCATION", "EXTBL_LOCATION");
    parameterProperties.setProperty("PL_STACK_TRACE", "PL_STACK_TRACE");
    parameterProperties.setProperty("HADR_SSL_LABEL", "HADR_SSL_LABEL");
    parameterProperties.setProperty("WLM_AGENT_LOAD_TRGT", "WLM_AGENT_LOAD_TRGT");
    parameterProperties.setProperty("WLM_ADMISSION_CTRL", "WLM_ADMISSION_CTRL");
    parameterProperties.setProperty("WLM_DISP_ADAP_SHARES", "WLM_DISP_ADAP_SHARES");
    parameterProperties.setProperty("WLM_CPU_SHARES", "WLM_CPU_SHARES");
    parameterProperties.setProperty("WLM_CPU_SHARE_MODE", "WLM_CPU_SHARE_MODE");
    parameterProperties.setProperty("WLM_CPU_LIMIT", "WLM_CPU_LIMIT");
    parameterProperties.setProperty("DEC_ARITHMETIC", "DEC_ARITHMETIC");
  }

  /**
   * Set regex properties for section header.
   */
  protected void setSectionRegexProperties() {
    sectionRegexProperties.setProperty("SECTION", "Database Configuration Settings:\\s*$");
    sectionRegexProperties.setProperty("COLS", "Description\\s+Memory Value\\s+Disk Value\\s*$");
    sectionRegexProperties.setProperty("COL_DESCRIPTION", "Description\\s+");
    sectionRegexProperties.setProperty("COL_MEMORY_VALUE", "Memory Value\\s+");
    sectionRegexProperties.setProperty("TAG_MEMORY", ".* \\(memory\\)\\s*");
    sectionRegexProperties.setProperty("TAG_DISK", ".* \\(disk\\)\\s*");
  }
}
