Guidance for AI coding agents working on this repository

Purpose
- This repo contains Advent of Code solutions written in C#. Each puzzle is implemented as a `Day` subclass and run via the small `Program` reflection harness.

Quick architecture summary
- **Entry point:** `src/Program.cs` — expects two args: `<year>` and `<day>` (e.g. `2024 1`). It constructs a type name `aoc.y{year}.Day{DD}` and instantiates it via reflection.
- **Base class:** `src/Day.cs` — abstract `Day` with two abstract methods `PartOne()` and `PartTwo()`. It provides `Input()` helpers which read from `dat/{classname}.txt` (classname is `GetType().Name.ToLower()`, e.g. `day01`), a `Run(Func<string>)` timing wrapper, and `ToCharMatrix` extension.
- **Solutions:** `src/y2024/DayXX.cs` — per-day implementations live in `src/y{year}/` and use namespace `aoc.y{year}`. New days must follow this pattern so the reflection lookup works.
- **Inputs:** `dat/` contains per-day input files named `day01.txt`, `day02.txt`, etc. `Day`'s constructor derives the path from the class name, so keep filenames lowercase and prefixed `day`.

How to run and build (Windows PowerShell)
- Build the project from repository root:
  - `dotnet build csharp.csproj`
- Run a specific day (from repository root):
  - `dotnet run --project csharp.csproj -- 2024 1`
  - This runs `aoc.y2024.Day01` and prints `P1:` and `P2:` results with timings.

How to add or update a solution
- Add a file `src/y{year}/Day{DD}.cs` with `namespace aoc.y{year}; public class Day{DD} : Day`.
- Implement `public override string PartOne()` and `public override string PartTwo()` returning the printed answer as a string.
- Add or update input data at `dat/day{dd}.txt` (lowercase, two-digit day).
- Ensure the namespace and class name exactly match the reflection pattern: `aoc.y{year}.Day{DD}`.

Project-specific patterns and gotchas
- Reflection is used to locate day classes — mismatched namespaces, class names, or file locations will cause `Day not found.` errors at runtime.
- `Day`'s constructor computes `fileName` from the class name lowercased. Use `Day01` -> `dat/day01.txt` naming.
- Two `Input` helpers exist: `Input()` returning `string[]`, and `Input(Action<string>)` iterating lines. Many solutions use the `Input(Action<string>)` pattern.
- There's a timing wrapper `Day.Run(Func<string>)` used to format results as `'{result,-16}[{ms:F2} ms]'`. Keep return strings concise (no newlines) so output aligns nicely.
- The `InputExtensions.ToCharMatrix` helper is available for grid-based puzzles.

Code quality notes for agents
- Many solution files currently contain non-compiling or pseudo-C# idioms (e.g., `List<int> left = [];`, `Order()` vs `OrderBy()`). Before making broad edits or PRs, run the build to verify compile success.
- Prefer minimal, local fixes that preserve the author's original intent (variable names, algorithmic approach) rather than wholesale rewrites.

Examples (concrete)
- Running Day 1 (PowerShell):
  - `dotnet build csharp.csproj; dotnet run --project csharp.csproj -- 2024 1`
- Adding Day 9 for 2024:
  - Create `src/y2024/Day09.cs` with `namespace aoc.y2024; public class Day09 : Day { ... }`
  - Add input file `dat/day09.txt`
  - Run: `dotnet run --project csharp.csproj -- 2024 9`

Notes for PRs and editing
- Run `dotnet build` after edits and prefer small commits focused on one day or one cross-cutting fix.
- If updating `README.md`, note that it currently links to `src/aoc/y2024/...` (outdated); actual implementation files live under `src/y2024/` and use the `aoc.y2024` namespace.
 - **Commit messages:** keep commits very simple and to the point. Omit trivial, self-evident details (this is a personal project — no need for elaborate messages).

If anything here is unclear, ask the repo owner which conventions to preserve (naming, string formatting, input parsing) before making large automated changes.
