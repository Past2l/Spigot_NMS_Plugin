if (-not (Test-Path -Path ./BuildTools)) {
    New-Item -Path ./BuildTools -ItemType Directory
}
Set-Location ./BuildTools

if (-not (Test-Path -Path ./BuildTools.jar)) {
    curl.exe -fSLo ./BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
}

$ZULU_VERSIONS = @{
    8 = "zulu8.74.0.17-ca-jre8.0.392"
    16 = "zulu16.32.15-ca-jre16.0.2"
    17 = "zulu17.46.19-ca-jre17.0.9"
}

if (-not (Test-Path -Path ./java)) {
    New-Item -Path ./java -ItemType Directory
}

foreach ($JAVA_VERSION in (8, 16, 17)) {
    if (-not (Test-Path -Path ./java/$JAVA_VERSION)) {
        curl.exe -fSLo ./$JAVA_VERSION.zip https://cdn.azul.com/zulu/bin/$($ZULU_VERSIONS[$JAVA_VERSION])-win_x64.zip
        Expand-Archive -Path ./$JAVA_VERSION.zip -DestinationPath ./java -Force
        Move-Item -Path ./java/$($ZULU_VERSIONS[$JAVA_VERSION])-win_x64 -Destination ./java/$JAVA_VERSION -Force
        Remove-Item -Path ./$JAVA_VERSION.zip -Recurse -Force
    }
}

$versions = Get-Content -Path ../versions | Where-Object { $_.Trim() -ne "" }
foreach ($version in $versions) {
    $v = $version -split '\.'
    if ($v[0] -eq 1 -and $v[1] -ge 12 -and $v[1] -le 16) {
        ./java/8/bin/java.exe -jar BuildTools.jar --rev $version --remapped
    }
    elseif ($v[0] -eq 1 -and $v[1] -eq 17) {
        ./java/16/bin/java.exe -jar BuildTools.jar --rev $version --remapped
    }
    else {
        ./java/17/bin/java.exe -jar BuildTools.jar --rev $version --remapped
    }
}

pause