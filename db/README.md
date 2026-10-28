# Historic MySQL store — 2016–2017
**ARCHIVED; DO NOT CONNECT TO AN ACTIVE DATABASE OR RUN THE MODEL.**

The code uses a historical MySQL JDBC connection configuration; its original
`Simulation/ConfigBD.txt` contains fields including a password and MUST NOT
be reused, copied into fresh commits, or published. Existing old Git history
is preserved unmodified and may already include old configuration; treat it
as potentially sensitive. All archival branches remain local with NO remote.

We recovered SQL schemas (6 tables) and historical CSV configurations/outputs,
but **have not recovered a restorable MySQL data dump**.
- 2016 schema: `schemas/2016/schema.sql`.
- 2017 schema: `schemas/2017/schema.sql`.
- Historical CSV 2016 March: `../historical/Eclipse-Workspace-2016-03/simulation-*.csv`.
- Historical Mathematica/Simu/Database outputs: see
  `../../../databases/computational-abe/DATABASE_MANIFEST.tsv` in the Elements archive.
- Old JDBC config: private backup, not documented with credentials here.

If revived, first verify schema versions, data types and licensing; use a
brand-new isolated MySQL instance with new credentials and never point the
historic DAO at a live DB. SQL schema-only files do NOT contain stored data.
