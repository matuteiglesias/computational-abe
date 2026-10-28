# Source provenance and safe export
Authoritative archive: /media/matias/Elements1/MATIAS_ESTATE/DURABLE_ARCHIVE/research/research-umbrella-legacy-20261026/repositories/computational-abe-consolidated
Source commit SHA: b42bba0995088ad4fd127614ffe18c2e869dec58 (archival consolidation)
2017 local-capture ancestor: 0281e262b083fe12d3ea78318a55a01ff2a095e5.
Export date: 2026-10-27.
Method: whitelist-only git archive <source_sha> <approved paths>; new Git repository, NO historical Git objects or original commit history, no remote. Source paths not rewritten in initial commit.
Included: Java model, engine controller/entities/parameters, DAO references (not invoked), non-secret experiment configuration CSVs, Eclipse metadata, SQL schema and public archive documentation.
Excluded intentionally: credential-bearing Simulation/ConfigBD.txt, historical JDBC test Simulation/src/engine/test/FirstExample.java, all third-party JARs, history blobs, massive notebooks and historical CSV traces. They remain in the immutable offline Elements archive. Only their provenance may be referenced; original bytes are not imported here.
Project intent: preserve the Java model, add a safe harness separately, then study model simplifications and empirical anchoring. No claim of K+S source equivalence, compilation or reproducibility by virtue of the export.

## Authorized GitHub publication — 2026-10-28

The historical repo was initially prepared locally without a remote as described above. After explicit director instruction to push both repositories, the independent sanitized Git main branch was pushed to the existing **public** https://github.com/matuteiglesias/computational-abe. The existing public master at 31b7a719ce54f1388a10c1722c6fe0d15c35ebdc was not overwritten or force-pushed; default branch was switched to main. The original Elements archival Git object store, other historical witness trees, excluded credentials, notebooks and database dumps were NOT pushed. This action does not establish license or scientific-equivalence clearance for new derived code.
