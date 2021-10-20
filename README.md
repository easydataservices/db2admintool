# db2admintool
Open source monitoring and administration tool for IBM Db2.

## About
The project goal is an open source set of easy-to-use monitoring and housekeeping tools for IBM Db2. This will focus on Linux, Unix and Windows (LUW) initially, but may expand in future to encompasss Db2 for z/OS (i.e. as I learn more about that) and possibly other databases.

## Status
The project is in its infancy and in active development.

## Completed milestones
* Agent to capture a snapshot type from source database, and persist it to staging area in respository database

## To dos
This is an incomplete and evolving list. Key objectives:
* ~~Capture by an agent process of one snapshot type from a source database~~
* ~~Persistence of snapshot to staging area of repository database~~
* Repository process to transform a snapshot from staging area to final target tables
* Scheduling
* Alerting
* Console facility
* Installer
* Additional snapshots (initial minimum set)

When the above basics are in place, they will constitute an initial release.

## Change log
20/10/2021: Uploaded code to date is reasonably elegant and extensible, but focuses soley on the capture by an agent of one snapshot type (database configuration) from a source DB2 for LUW database. The snapshot is persisted by the same agent into the staging area of the repository database.
