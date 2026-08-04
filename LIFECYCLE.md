# Lifecycle declaration

**Lifecycle:** `frozen`  
**Decision date:** 2026-08-04  
**Maintenance mode:** preserve the historical simulation artifact

## Historical role

This repository preserves a Java/MySQL agent-based economics simulator and its accompanying Mathematica analysis workflow. It documents a specific implementation period, software stack, modeling assumptions, and research framing.

## Preservation policy

- Keep the original code, schema, configuration, and explanatory material intact.
- Treat the Eclipse, MySQL, credential, branch, and local-network instructions as historical environment evidence, not as a currently supported installation contract.
- Do not upgrade Java, MySQL, Mathematica, build tooling, persistence, or simulation architecture by default.
- Do not reinterpret generated simulation outputs as currently reproduced results.

## What this repository does not claim

It is not a maintained ABM platform, production service, supported package, or verified reproduction bundle. The historical README may contain insecure example credentials and obsolete setup assumptions; those must not be reused in a real environment without redesign.

## Revival rule

Revival requires a concrete research question and a written reproduction plan covering:

1. the model version and expected output;
2. the smallest executable configuration;
3. a safe database and credential setup;
4. comparison against preserved historical outputs;
5. whether a new repository should contain the modern implementation.

Modern work should normally cite this repository as predecessor evidence rather than rewriting its historical surface.

## Verification status

This declaration was prepared from the current README and repository metadata. The Java project, MySQL schema, simulation, Mathematica notebook, and linked research document were not executed or verified.