namespace csharp.aoc.common.models;

public record Range<T> where T : INumber<T>
{
    public T Start { get; }

    public T End { get; }

    public T Length { get; }

    public Range(T start, T end)
    {
        Start = start;
        End = end;
        Length = End - Start + T.One;
    }

    public void Deconstruct(out T start, out T end)
    {
        start = Start;
        end = End;
    }

    public Range<T> Union(Range<T> other) =>
        new(T.Min(Start, other.Start), T.Max(End, other.End));

    public bool Intersects(Range<T> other) =>
        T.Max(Start, other.Start) <= T.Min(End, other.End);

    public bool Contains(T item) =>
        Start <= item && item <= End;
}