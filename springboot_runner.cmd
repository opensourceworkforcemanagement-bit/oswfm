@echo off
setlocal enabledelayedexpansion
REM Spring Boot Maven Runner Batch File - Multiple Projects
REM Configure your project directories below

REM === CONFIGURE YOUR PROJECT DIRECTORIES HERE ===
set PROJECT_DIRS="C:\development\TA\springbootmicroserviceswithsecurity-main\eurekaserver" "C:\development\TA\springbootmicroserviceswithsecurity-main\apigateway" "C:\development\TA\springbootmicroserviceswithsecurity-main\authservice" "C:\development\TA\springbootmicroserviceswithsecurity-main\timesheetservice" "C:\development\TA\springbootmicroserviceswithsecurity-main\userservice"

REM === Main Loop ===
echo Spring Boot Multi-Project Runner (Concurrent)
echo ===============================================

for %%d in (%PROJECT_DIRS%) do (
    echo [INFO] Opening new CLI window for: %%d
    
    REM Check if pom.xml exists in the directory before opening CLI
    if not exist "%%d\pom.xml" (
        echo [ERROR] pom.xml not found in %%d - Skipping this directory
    ) else (
        REM Open new command prompt window for each directory with JAVA_HOME set
        start "Spring Boot - %%d" cmd /k "set PATH=C:\dev_tools\apache-maven-3.9.9\bin;%PATH%  && echo [INFO] JAVA_HOME set to: %JAVA_HOME% && cd /d %%d && echo [INFO] Changed to directory: %%cd && echo [INFO] Starting Maven Spring Boot... && mvn spring-boot:run"

        echo [SUCCESS] Launched CLI window for %%d
    )
)

echo.
echo [INFO] All CLI windows launched. Each project will run in its own window.
echo [INFO] You can close this window now.
pause


