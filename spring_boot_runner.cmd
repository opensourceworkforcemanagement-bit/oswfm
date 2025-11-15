@echo off
setlocal enabledelayedexpansion
REM Spring Boot Maven Runner Batch File - Multiple Projects
REM Configure your project directory names below (relative to this script's location)

REM === CONFIGURE YOUR PROJECT DIRECTORY NAMES HERE ===
REM Just list the directory names separated by spaces (no commas needed)
set PROJECT_DIRS=eurekaserver apigateway authservice userservice timesheetservice

REM === Get the base directory (where this script is located) ===
set BASE_DIR=%~dp0
REM Remove trailing backslash
set BASE_DIR=%BASE_DIR:~0,-1%

REM === Main Loop ===
echo Spring Boot Multi-Project Runner (Concurrent)
echo ===============================================
echo Base Directory: %BASE_DIR%
echo.

for %%d in (%PROJECT_DIRS%) do (
    set "FULL_PATH=%BASE_DIR%\%%d"
    
    echo [INFO] Opening new CLI window for: %%d
    
    REM Check if pom.xml exists in the directory before opening CLI
    if not exist "!FULL_PATH!\pom.xml" (
        echo [ERROR] pom.xml not found in !FULL_PATH! - Skipping this directory
    ) else (
        REM Open new command prompt window for each directory with JAVA_HOME and MAVEN_HOME set
        start "Spring Boot - %%d" cmd /k "set PATH=%%MAVEN_HOME%%\bin;%%PATH%% && echo [INFO] MAVEN_HOME: %%MAVEN_HOME%% && echo [INFO] JAVA_HOME: %%JAVA_HOME%% && cd /d "!FULL_PATH!" && echo [INFO] Changed to directory: %%cd && echo [INFO] Starting Maven Spring Boot... && mvn spring-boot:run"

        echo [SUCCESS] Launched CLI window for %%d
        echo [INFO] Waiting 5 seconds before starting next service...
        timeout /t 5 /nobreak >nul
    )
)

echo.
echo [INFO] All CLI windows launched. Each project will run in its own window.
echo [INFO] You can close this window now.
pause