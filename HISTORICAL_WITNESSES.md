# Historical sources — pinned crosswalk

These are **distinct source witnesses**, not a single economic model.

| Witness | Identity (in immutable archive) | What is established |
| --- | --- | --- |
| W01 March 2016 Eclipse | tree 52d6a32b7dbb8f3153e1b70a564a70103ce26d7e | Independent Eclipse tree / 20 Java sources; no proven ancestor chain |
| W02 November 2016 | commit 418dc9bfbac14d3d80f55eccaacb225bea4123d5 | Historical committed November rules |
| W03 January 2017 | commit adc324c9117ad79066664d2080f3fe0cf7cdb760 | January committed rules, descends from W02 |
| W04 January local capture | commit 0281e262b083fe12d3ea78318a55a01ff2a095e5 | Child of W03, partial local source/config snapshot; 2017 code here is selected safe export |
| Archival packaging | commit b42bba0995088ad4fd127614ffe18c2e869dec58 | 2026 preservation wrapper, not another historical economy |

The immutable Git object store is on Elements at the path in README.md; it is not a configured Git remote to avoid accidental copying of old sensitive objects.

**Source evidence and decisions:** /home/matias/repos/research-group-ops/results/VRG-012/historical-migration/ includes HIST02-DOC-v0.1, crosswalk, independent reviewers, historical mechanism discriminants, and MIG-01 comparisons.

**Caution:** original 2017 local configuration files do not establish code→config→seed→run→result provenance. Simulation/schema.sql is technical DDL metadata, not proof of a database restore. Missing historical data is UNKNOWN, not zero.

This repository begins with a new sanitized Git commit. It does not pretend to reproduce the 2016/2017 commit history; full original refs are preserved only in the archival repository.
