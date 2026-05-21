#!/bin/bash
ROOT="$(cd "$(dirname "$0")" && pwd)"
TESTS="$ROOT/tests"
PASS=0
FAIL=0
SKIP=0

for n in 1 2 3 4 5; do
    echo ""
    echo "=== Ejercicio $n ==="

    # Compilar
    if ! javac "$ROOT/Ejercicio$n.java" 2>/dev/null; then
        echo "  [ERROR] No compila"
        continue
    fi

    for in_file in "$TESTS/ejercicio$n/"*.in.txt; do
        base=$(basename "$in_file" .in.txt)
        out_file="$TESTS/ejercicio$n/$base.out.txt"

        [ -f "$out_file" ] || { echo "  [SKIP] $base (sin .out.txt)"; ((SKIP++)); continue; }

        actual=$(java -cp "$ROOT" "Ejercicio$n" < "$in_file" 2>/dev/null)
        expected=$(cat "$out_file")

        if [ "$actual" = "$expected" ]; then
            echo "  [OK]   $base"
            ((PASS++))
        else
            echo "  [FAIL] $base"
            diff <(echo "$expected") <(echo "$actual") | head -6 | sed 's/^/         /'
            ((FAIL++))
        fi
    done
done

echo ""
echo "============================="
echo "Resultados: $PASS OK  |  $FAIL FAIL  |  $SKIP SKIP"
