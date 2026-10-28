# Computational ABE — historical Java archive (2016–2017)

**Purpose:** historical, non-executable research reference for the 2016–2017 Computational ABE Java/Eclipse simulator. This is a **sanitized working repository** with a separate new Git history, not a clone of the original private legacy Git object database.

## Repository split

| Repository | Responsibility |
| --- | --- |
| **computational-abe** (this repository) | Legacy Java sources, schema, historical configuration candidates and exact witness provenance |
| **computational-abe-research** (../computational-abe-research) | New K+S v2 simulator, tests, experiments, and current scientific development |

The other repo retains prior source commits in its *old Git history* for continuity, but legacy Simulation/ and db/ are removed from its new default branch. New v2 code is NOT developed here.

## Actual source provenance

The original immutable archive remains **the authority**, at:

/media/matias/Elements1/MATIAS_ESTATE/DURABLE_ARCHIVE/research/research-umbrella-legacy-20261026/repositories/computational-abe-consolidated

- 2016-03 independent Eclipse tree: 52d6a32b7dbb8f3153e1b70a564a70103ce26d7e
- 2016-11 margarita: 418dc9bfbac14d3d80f55eccaacb225bea4123d5
- 2017-01 committed margarita: adc324c9117ad79066664d2080f3fe0cf7cdb760
- 2017-01 local capture: 0281e262b083fe12d3ea78318a55a01ff2a095e5
- 2026 archival consolidation: b42bba0995088ad4fd127614ffe18c2e869dec58

See HISTORICAL_WITNESSES.md and PROVENANCE.md. The current source files derive from the existing previously-sanitized export 2828f89 (2026-10-27) and match the selected W04 Git blobs; **they do not contain all four full trees or verified experiment outputs**. Earlier generations remain pinned in the immutable archive, not copied into this Git repository.

## Security and scientific limits

Historical credentials (ConfigBD.txt), historic test FirstExample.java, third-party JARs, private dump data, Mathematica notebooks and large CSV results are **excluded**. Legacy DAO/controller sources, while present as historical code, **must not be executed** or connected to a database. Do not attempt SimulationController.main, JDBC, network, or historical credentials.

Config filenames are **candidates**, not evidence of executed experiments. Historical employeeGap, machine index, event ordering and other differences remain unresolved scientific questions; no K+S paper/model equivalence is implied.

GitHub remote: https://github.com/matuteiglesias/computational-abe (PUBLIC, default branch main as of 2026-10-28). The earlier public master at 31b7a719ce54f1388a10c1722c6fe0d15c35ebdc remains intact as a separate branch. Only the sanitized main source was pushed, without the private archive object history. Further releases, historical code reuse or derived executable publication require separate rights/attribution clearance and authorization; never push automatically.
