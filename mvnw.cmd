@echo off
setlocal

set "BASE_DIR=%~dp0"
set "WRAPPER_JAR=%BASE_DIR%.mvn\wrapper\maven-wrapper.jar"
set "WRAPPER_PROPS=%BASE_DIR%.mvn\wrapper\maven-wrapper.properties"

if not exist "%WRAPPER_JAR%" (
  for /f "usebackq tokens=1,* delims==" %%A in ("%WRAPPER_PROPS%") do (
    if "%%A"=="wrapperUrl" set "WRAPPER_URL=%%B"
  )
  if "%WRAPPER_URL%"=="" (
    echo wrapperUrl not set in %WRAPPER_PROPS%
    exit /b 1
  )
  echo Downloading Maven Wrapper...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "(New-Object Net.WebClient).DownloadFile('%WRAPPER_URL%', '%WRAPPER_JAR%')" || exit /b 1
)

set "JAVA_CMD=java"
if not "%JAVA_HOME%"=="" set "JAVA_CMD=%JAVA_HOME%\bin\java"

"%JAVA_CMD%" -jar "%WRAPPER_JAR%" %*
endlocal
