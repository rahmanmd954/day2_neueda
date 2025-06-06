#!/usr/bin/env python3
"""
Convert prices.csv into:
  • mysqlCreateSchema.sql – CREATE TABLE statement
  • mysqlInsertValues.sql – INSERT statements for every row

The type–inference rules match the original Perl:
  varchar  – any value containing letters or “odd” symbols
  decimal  – exactly one period, all other chars digits
  int      – digits only (unless the column was already decimal)
Lengths / precision are widened on the fly just like the Perl code.
"""
import csv
from pathlib import Path

# ---------------------------------------------------------------------------
# Tunables (same names as in the Perl)
# ---------------------------------------------------------------------------
TABLE_NAME       = "nasdaq_prices"
DATABASE_ENGINE  = "InnoDB"
DEFAULT_CHARSET  = "latin1"
CSV_FILE         = Path("prices.csv")

# ---------------------------------------------------------------------------
# Helpers
# ---------------------------------------------------------------------------
def looks_decimal(val: str) -> bool:
    """digits with a single dot → decimal"""
    if val.count(".") != 1:
        return False
    left, right = val.split(".")
    return left.isdigit() and right.isdigit()

def escape(val: str) -> str:
    """SQL‑escape single quotes and drop surrounding double quotes"""
    return val.replace("'", r"\'").strip('"')

# ---------------------------------------------------------------------------
# Main
# ---------------------------------------------------------------------------
with (
    open("mysqlCreateSchema.sql", "w", encoding="utf‑8") as schema,
    open("mysqlInsertValues.sql", "w", encoding="utf‑8") as inserts,
    open(CSV_FILE, newline='', encoding="utf‑8") as csvfile,
):
    reader = csv.reader(csvfile)
    raw_headers = next(reader)

    # ----- header cleaning --------------------------------------------------
    field_names = [
        escape(col).replace(" ", "_")              # space → underscore
        for col in raw_headers
    ]
    n_fields = len(field_names)
    columns_clause = ", ".join(field_names)

    # ----- running metadata -------------------------------------------------
    col_type   = [""]  * n_fields            # '', 'int', 'decimal', 'varchar'
    col_len    = [0]   * n_fields            # varchar / int length
    dec_left   = [0]   * n_fields            # decimal( dec_left + dec_right , dec_right )
    dec_right  = [0]   * n_fields

    row_count = 0

    # ----- iterate through CSV rows ----------------------------------------
    for row in reader:
        # pad short rows (rare but keeps logic identical to Perl)
        while len(row) < n_fields:
            row.append("")

        # analyse / mutate each cell
        cleaned = []
        for i, raw in enumerate(row):
            val = escape(raw.strip())

            # treat empty string as zero when deciding numeric widths
            test_val = val or "0"

            # --- type inference / width tracking ---------------------------
            if any(ch.isalpha() for ch in test_val):
                # contains letters → varchar
                col_type[i] = col_type[i] or "varchar"
                col_len[i] = max(col_len[i], len(test_val))

            elif looks_decimal(test_val):
                # decimal
                col_type[i] = "decimal"
                left, right = test_val.split(".")
                dec_left[i]  = max(dec_left[i],  len(left))
                dec_right[i] = max(dec_right[i], len(right))

            elif test_val.isdigit():
                # integer (unless this column was already decimal)
                if col_type[i] != "decimal":
                    col_type[i] = col_type[i] or "int"
                    col_len[i] = max(col_len[i], len(test_val))

            else:
                # fallback → varchar
                col_type[i] = "varchar"
                col_len[i] = max(col_len[i], len(test_val))

            cleaned.append(val)

        # --- write INSERT line ---------------------------------------------
        inserts.write(
            f"INSERT INTO {TABLE_NAME} ({columns_clause}) "
            f"VALUES ({', '.join(f'\'{v}\'' for v in cleaned)});\n"
        )
        row_count += 1

    # ----- emit CREATE TABLE -----------------------------------------------
    schema.write(f"CREATE TABLE `{TABLE_NAME}` (\n")
    for idx, name in enumerate(field_names):
        if col_type[idx] == "decimal":
            total = dec_left[idx] + dec_right[idx]
            col_def = f"decimal({total},{dec_right[idx]})"
        elif col_type[idx] == "int":
            col_def = f"int({max(col_len[idx],1)})"
        else:  # varchar (default)
            col_def = f"varchar({max(col_len[idx],1)})"

        comma = "," if idx < n_fields - 1 else ""
        schema.write(f"  `{name}` {col_def}{comma}\n")

    schema.write(f") ENGINE={DATABASE_ENGINE} DEFAULT CHARSET={DEFAULT_CHARSET};\n")

print(f"Processed {n_fields} columns and {row_count} data lines.")
