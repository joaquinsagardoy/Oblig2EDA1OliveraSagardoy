$root = $PSScriptRoot
$testsDir = Join-Path $root "tests"
$pass = 0
$fail = 0
$error_count = 0

$ejercicios = 1..5

foreach ($n in $ejercicios) {
    $javaFile = Join-Path $root "Ejercicio$n.java"
    $testDir  = Join-Path $testsDir "ejercicio$n"

    Write-Host "`n=== Ejercicio $n ===" -ForegroundColor Cyan

    # Compilar
    $compileOut = & javac $javaFile 2>&1
    if ($LASTEXITCODE -ne 0) {
        Write-Host "  [ERROR] No compila: $compileOut" -ForegroundColor Red
        continue
    }

    # Buscar casos de prueba
    $inputs = Get-ChildItem -Path $testDir -Filter "*.in.txt" | Sort-Object Name

    foreach ($inFile in $inputs) {
        $baseName   = $inFile.BaseName -replace '\.in$', ''
        $outFile    = Join-Path $testDir "$baseName.out.txt"

        if (-not (Test-Path $outFile)) {
            Write-Host "  [SKIP] $baseName - sin archivo .out.txt" -ForegroundColor Yellow
            continue
        }

        $expected = (Get-Content $outFile -Raw).Trim()

        try {
            $actual = (Get-Content $inFile.FullName -Raw | & java -cp $root "Ejercicio$n" 2>&1)
            $actual = ($actual -join "`n").Trim()
        } catch {
            Write-Host "  [ERROR] $baseName - excepcion al ejecutar" -ForegroundColor Red
            $error_count++
            continue
        }

        if ($actual -eq $expected) {
            Write-Host "  [OK]   $baseName" -ForegroundColor Green
            $pass++
        } else {
            Write-Host "  [FAIL] $baseName" -ForegroundColor Red
            Write-Host "         Esperado : $($expected -replace "`n",' | ')" -ForegroundColor DarkRed
            Write-Host "         Obtenido : $($actual  -replace "`n",' | ')" -ForegroundColor DarkRed
            $fail++
        }
    }
}

Write-Host "`n=============================" -ForegroundColor Cyan
Write-Host "Resultados: $pass OK  |  $fail FAIL  |  $error_count ERROR" -ForegroundColor White
