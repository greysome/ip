#!/usr/bin/env python3
import argparse
import random
import subprocess
import sys


def command(rng):
    words = ["todo", "deadline", "event", "mark", "unmark", "delete", "find", "list", "wat", ""]
    choice = rng.choice(words)
    if choice in ("", "list", "wat"):
        return rng.choice([choice, choice + " extra"])
    if choice == "todo":
        return rng.choice(["todo", "todo buy milk", "todo " + "x" * 200])
    if choice == "deadline":
        return rng.choice(["deadline", "deadline submit", "deadline submit /by", "deadline submit /by friday"])
    if choice == "event":
        return rng.choice(["event", "event meeting /from", "event meeting /from 2pm", "event meeting /from 2pm /to 3pm"])
    return choice + " " + rng.choice(["", "0", "1", "999999999999999999999999", "abc", "1 2"])


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--classpath", required=True)
    parser.add_argument("--main", required=True)
    parser.add_argument("--iterations", type=int, default=100)
    parser.add_argument("--seed", type=int)
    parser.add_argument("--timeout", type=float, default=3)
    args = parser.parse_args()
    seed = args.seed if args.seed is not None else random.randrange(2**32)
    rng = random.Random(seed)
    inputs = [command(rng) for _ in range(args.iterations)] + ["bye"]
    data = "\n".join(inputs) + "\n"
    try:
        result = subprocess.run(["java", "-cp", args.classpath, args.main], input=data,
                                text=True, capture_output=True, timeout=args.timeout)
    except subprocess.TimeoutExpired:
        print(f"FAIL: timeout (seed={seed})\n{data}")
        return 1
    output = result.stdout + result.stderr
    if result.returncode != 0 or "Exception" in output or "Error:" in output or "at Puke." in output:
        print(f"FAIL: crash or stack trace (seed={seed}, exit={result.returncode})\n{data}\n--- output ---\n{output}")
        return 1
    print(f"PASS: {args.iterations} generated commands (seed={seed})")
    return 0


if __name__ == "__main__":
    sys.exit(main())
