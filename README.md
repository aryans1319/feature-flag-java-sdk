# Java SDK for Feature Flag Platform

This repo is:

- A library, not a service
- Used by other applications
- Independent of backend internals

It will:
- Call your backend /evaluate API
- Return true / false
- Handle failures safely

It will never:
- Store features
- Evaluate rules
- Handle rollout logic
