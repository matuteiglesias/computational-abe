# computational-abe: three generations of historical source

**ARCHIVED / 2026-10-26.** This repository was assembled offline to preserve
a 2016–2017 research programme inspired by the Dosi–Roventini Keynes-meets-
Schumpeter family of agent-based macroeconomic models. Model equivalence has
not been audited. **Never infer that it is the authors' official C++ code.**

## Authorities and chronological sources
1. 2016-03 Eclipse workspace `/media/matias/Elements1/MATIAS_ESTATE/DURABLE_ARCHIVE/legacy-root/backup-history/Matias_Iglesias_Backup/miglesia2018/workspace/Simulation`: early Java classes and 2 CSV results.
   Independent source snapshot: `historical/Eclipse-Workspace-2016-03`.
2. 2016-11 `/media/matias/Elements1/MATIAS_ESTATE/DURABLE_ARCHIVE/legacy-root/backup-history/Matias_Iglesias_Backup/miglesia2018/git/computational-abe`: earlier repo at `418dc9b` on `margarita`;
   surviving local checkout changes are preserved in Elements record RO-037.
   Git branch `archive/2016-margarita` and earlier source snapshot.
3. 2017-01 `/media/matias/Elements1/MATIAS_ESTATE/DURABLE_ARCHIVE/legacy-root/backup-history/Matias_Iglesias_Backup/miglesia2018/gitRepoNov/computational-abe`: later version at `adc324c` on `margarita`,
   matches the public GitHub branch. Local code, schema and SimConfig CSVs
   are captured in `archive/2017-local-capture`; separate private raw
   snapshot holds *all* of the original working-tree bytes.

## Branches
- `margarita`: unchanged committed January 2017 historical reference.
- `archive/2016-margarita`: unchanged earlier November 2016 historical branch.
- `archive/2017-local-capture`: selected 2017 uncommitted local changes,
  without new commits of credential-bearing ConfigBD.txt.
- `archive/consolidated-2026-10-26`: this archival documentation and
  early-code snapshots.
- `archive/public-master-2026`: read-only snapshot of public GitHub default `master` at `31b7a71` (frozen lifecycle); its remote remains unchanged.

**Upstream:** https://github.com/matuteiglesias/computational-abe
Public default master was marked frozen in August 2026; it is preserved as local archival ref `archive/public-master-2026`. This local
consolidation does NOT update any GitHub branch or open a PR. NO remote is
configured on this archival clone.

## Database
The MySQL schema comprises `configurations`, `cycles`, `experiments`,
`labels`, `results` and `simulations`. Schema-only is not a database dump.
`db/README.md` gives safe interpretation and revival requirements.
Old raw DB credentials must be treated as sensitive and never reused.

## Evidence outside Git
`../../databases/computational-abe` (Elements archive) stores a queryable
inventory of surviving SQL schemas, configuration CSVs and simulation data.
The full 337 MiB `Documents/Economia/Simu` source is not cloned into Git.
The original two Java checkouts and Eclipse workspace are left untouched.

## Status
FROZEN / HISTORICAL. No Java, MySQL, or Mathematica execution or validation
has been attempted. This is an archival consolidation, not a revival.
