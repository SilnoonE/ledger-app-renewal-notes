import java.time.LocalDate
import java.time.YearMonth

// Standalone educational example, not production app source.
// All transactions below are fictional.
enum class EntryKind { INCOME, EXPENSE }

data class ExampleEntry(
    val date: LocalDate,
    val kind: EntryKind,
    val amount: Long
) {
    init { require(amount > 0) { "Amount must be positive." } }
}

data class MonthlySummary(val income: Long, val expense: Long) {
    val balance: Long get() = Math.subtractExact(income, expense)
}

fun summarizeMonth(entries: List<ExampleEntry>, month: YearMonth): MonthlySummary {
    var income = 0L
    var expense = 0L
    for (entry in entries) {
        if (YearMonth.from(entry.date) != month) continue
        when (entry.kind) {
            EntryKind.INCOME -> income = Math.addExact(income, entry.amount)
            EntryKind.EXPENSE -> expense = Math.addExact(expense, entry.amount)
        }
    }
    return MonthlySummary(income, expense)
}

fun main() {
    val examples = listOf(
        ExampleEntry(LocalDate.of(2025, 3, 2), EntryKind.INCOME, 120_000),
        ExampleEntry(LocalDate.of(2025, 3, 8), EntryKind.EXPENSE, 45_000),
        ExampleEntry(LocalDate.of(2025, 4, 1), EntryKind.INCOME, 80_000)
    )
    val march = summarizeMonth(examples, YearMonth.of(2025, 3))
    check(march == MonthlySummary(120_000, 45_000))
    check(march.balance == 75_000L)
    check(summarizeMonth(examples, YearMonth.of(2025, 5)) == MonthlySummary(0, 0))
    println("March income: ${march.income}, expense: ${march.expense}, balance: ${march.balance}")
}
