#!/usr/bin/env python3
"""
Spring Boot Maven Runner - Multiple Projects
Configure your project directory names below
"""

import os
import subprocess
import sys
import time
from pathlib import Path

# === DATABASE CONFIGURATION ===
# Database root credentials for running init.sql
DB_ROOT_USER = os.environ.get('DB_ROOT_USER', 'postgres')
DB_ROOT_PASSWORD = os.environ.get('DB_ROOT_PASSWORD', 'oswfm')
DB_CONNECTION_STRING = os.environ.get('DB_CONNECTION_STRING', 'jdbc:postgresql://localhost:5432/')
DB_HOST = 'localhost'
DB_PORT = '5432'

# === CONFIGURE YOUR PROJECT DIRECTORY NAMES HERE ===
# List the directory names for your Spring Boot projects
PROJECT_DIRS = [
    {'eurekaserver': 5},
    {'apigateway': 5},
    {'authservice': 5},
    {'userservice': 16},
    {'timesheetservice': 0},
    {'administrationservice': 0}
]

def get_base_dir():
    """Get the directory where this script is located"""
    return Path(__file__).parent.resolve()

def check_pom_exists(project_path):
    """Check if pom.xml exists in the given directory"""
    pom_file = project_path / 'pom.xml'
    return pom_file.exists()

def run_init_sql(init_sql_path):
    """
    Run init.sql to initialize the database

    Args:
        init_sql_path: Path to the init.sql file

    Returns:
        bool: True if successful, False otherwise
    """
    if not init_sql_path.exists():
        print(f"[WARNING] init.sql not found at {init_sql_path} - Skipping database initialization")
        return False

    print(f"[INFO] Running database initialization script: {init_sql_path}")
    print(f"[INFO] Using database credentials - User: {DB_ROOT_USER}, Host: {DB_HOST}, Port: {DB_PORT}")

    try:
        # Set PGPASSWORD environment variable for psql authentication
        env = os.environ.copy()
        env['PGPASSWORD'] = DB_ROOT_PASSWORD

        # Run psql command to execute init.sql
        cmd = [
            'psql',
            '-h', DB_HOST,
            '-p', DB_PORT,
            '-U', DB_ROOT_USER,
            '-d', 'postgres',  # Connect to default postgres database
            '-f', str(init_sql_path)
        ]

        result = subprocess.run(
            cmd,
            env=env,
            capture_output=True,
            text=True
        )

        if result.returncode == 0:
            print("[SUCCESS] Database initialization completed successfully")
            if result.stdout:
                print(f"[INFO] Output:\n{result.stdout}")
            return True
        else:
            print(f"[ERROR] Database initialization failed with return code {result.returncode}")
            if result.stderr:
                print(f"[ERROR] Error output:\n{result.stderr}")
            if result.stdout:
                print(f"[INFO] Output:\n{result.stdout}")
            return False

    except FileNotFoundError:
        print("[ERROR] psql command not found. Please ensure PostgreSQL client tools are installed and in PATH")
        return False
    except Exception as e:
        print(f"[ERROR] Failed to run init.sql: {e}")
        return False

def launch_spring_boot_project(project_name, project_path):
    """Launch Spring Boot project in a new terminal window"""
    
    if not check_pom_exists(project_path):
        print(f"[ERROR] pom.xml not found in {project_path} - Skipping this directory")
        return False
    
    print(f"[INFO] Opening new terminal window for: {project_name}")
    
    # Determine the OS and launch accordingly
    try:
        if sys.platform == 'win32':
            # Windows
            maven_home = os.environ.get('MAVEN_HOME', '')
            java_home = os.environ.get('JAVA_HOME', '')
            
            cmd = (
                f'start "Spring Boot - {project_name}" cmd /k '
                f'"set PATH={maven_home}\\bin;%PATH% && '
                f'echo [INFO] MAVEN_HOME: {maven_home} && '
                f'echo [INFO] JAVA_HOME: {java_home} && '
                f'cd /d "{project_path}" && '
                f'echo [INFO] Changed to directory: %cd% && '
                f'echo [INFO] Starting Maven Spring Boot... && '
                f'mvn spring-boot:run -Dmaven.test.skip=true"'
            )
            subprocess.Popen(cmd, shell=True)
            
        elif sys.platform == 'darwin':
            # macOS
            script = f'''
                tell application "Terminal"
                    do script "cd '{project_path}' && echo '[INFO] Starting {project_name}...' && mvn spring-boot:run"
                    activate
                end tell
            '''
            subprocess.Popen(['osascript', '-e', script])
            
        else:
            # Linux - try various terminal emulators
            terminals = [
                ['gnome-terminal', '--', 'bash', '-c'],
                ['xterm', '-e'],
                ['konsole', '-e'],
                ['xfce4-terminal', '-e']
            ]
            
            cmd = f"cd '{project_path}' && echo '[INFO] Starting {project_name}...' && mvn spring-boot:run; exec bash"
            
            launched = False
            for terminal in terminals:
                try:
                    subprocess.Popen(terminal + [cmd])
                    launched = True
                    break
                except FileNotFoundError:
                    continue
            
            if not launched:
                print(f"[ERROR] Could not find a suitable terminal emulator for {project_name}")
                return False
        
        print(f"[SUCCESS] Launched terminal window for {project_name}")
        return True
        
    except Exception as e:
        print(f"[ERROR] Failed to launch {project_name}: {e}")
        return False

def main():
    """Main function to run all Spring Boot projects"""
    print("Spring Boot Multi-Project Runner (Concurrent)")
    print("=" * 50)

    base_dir = get_base_dir()
    print(f"Base Directory: {base_dir}")
    print()

    # Run init.sql before launching Spring Boot projects
    init_sql_path = base_dir / 'init.sql'
    print("[STEP 1] Database Initialization")
    print("-" * 50)
    run_init_sql(init_sql_path)
    print()

    print("[STEP 2] Launching Spring Boot Projects")
    print("-" * 50)

    for project_dir_sleep in PROJECT_DIRS:

        for project_dir, sleeping_for in project_dir_sleep.items():

            full_path = base_dir / project_dir
            
            if launch_spring_boot_project(project_dir, full_path):
                print(f"[INFO] Waiting {sleeping_for} seconds before starting next service..." )
                time.sleep(sleeping_for)
            
            print()
    
    print("[INFO] All terminal windows launched. Each project will run in its own window.")
    print("[INFO] Press Enter to exit...")
    input()

if __name__ == '__main__':
    main()
