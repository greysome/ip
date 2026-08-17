---
name: cli-stress-test
description: Stress-test a Java command-line application with malformed, boundary, and randomized input, detecting crashes, hangs, stack traces, and unexpected exits.
---

# CLI stress testing

Use `scripts/fuzz_cli.py` for a Java CLI that reads commands from standard input.

1. Compile the application first and identify its main class.
2. Run the fuzzer with the compiled classpath, for example:
   `python3 skills/cli-stress-test/scripts/fuzz_cli.py --classpath out --main Puke --iterations 200`
3. Treat a non-zero exit, timeout, Java exception/stack trace, or missing normal termination as a failure.
4. Re-run a failing seed with `--seed` and inspect the minimized command sequence printed by the script.

The corpus covers blank input, missing arguments, malformed task IDs, missing `/by`, `/from`, and `/to` markers, unknown commands, very long text, and valid commands mixed with invalid ones. Keep the test deterministic by recording the seed.
