#!/usr/bin/env python3
"""
Database Cleanup Script
Drops the opensourceworkforcemanagement database and oswfm user
"""

import os
import subprocess
import sys
from pathlib import Path

# === DATABASE CONFIGURATION ===
# Database root credentials for running dropDB.sql
DB_ROOT_USER = os.environ.get('DB_ROOT_USER', 'postgres')
DB_ROOT_PASSWORD = os.environ.get('DB_ROOT_PASSWORD', 'oswfm')
DB_HOST = 'localhost'
DB_PORT = '5432'

def get_base_dir():
    """Get the directory where this script is located"""
    return Path(__file__).parent.resolve()

def run_drop_sql(drop_sql_path):
    """
    Run dropDB.sql to clean up the database and user

    Args:
        drop_sql_path: Path to the dropDB.sql file

    Returns:
        bool: True if successful, False otherwise
    """
    if not drop_sql_path.exists():
        print(f"[ERROR] dropDB.sql not found at {drop_sql_path}")
        return False

    print("=" * 60)
    print("DATABASE CLEANUP SCRIPT")
    print("=" * 60)
    print(f"[INFO] Running database cleanup script: {drop_sql_path}")
    print(f"[INFO] Using database credentials - User: {DB_ROOT_USER}, Host: {DB_HOST}, Port: {DB_PORT}")
    print()

    # Confirm before proceeding
    print("[WARNING] This will:")
    print("  1. Terminate all active connections to 'opensourceworkforcemanagement'")
    print("  2. Drop the 'opensourceworkforcemanagement' database")
    print("  3. Drop the 'oswfm' user")
    print()

    confirm = input("Are you sure you want to proceed? (yes/no): ").strip().lower()
    if confirm not in ['yes', 'y']:
        print("[INFO] Operation cancelled by user")
        return False

    print()
    print("[INFO] Proceeding with database cleanup...")
    print("-" * 60)

    try:
        # Set PGPASSWORD environment variable for psql authentication
        env = os.environ.copy()
        env['PGPASSWORD'] = DB_ROOT_PASSWORD

        # Run psql command to execute dropDB.sql
        cmd = [
            'psql',
            '-h', DB_HOST,
            '-p', DB_PORT,
            '-U', DB_ROOT_USER,
            '-d', 'postgres',  # Connect to default postgres database
            '-f', str(drop_sql_path)
        ]

        result = subprocess.run(
            cmd,
            env=env,
            capture_output=True,
            text=True
        )

        if result.returncode == 0:
            print("[SUCCESS] Database cleanup completed successfully")
            if result.stdout:
                print(f"[INFO] Output:\n{result.stdout}")
            return True
        else:
            print(f"[ERROR] Database cleanup failed with return code {result.returncode}")
            if result.stderr:
                print(f"[ERROR] Error output:\n{result.stderr}")
            if result.stdout:
                print(f"[INFO] Output:\n{result.stdout}")
            return False

    except FileNotFoundError:
        print("[ERROR] psql command not found. Please ensure PostgreSQL client tools are installed and in PATH")
        return False
    except Exception as e:
        print(f"[ERROR] Failed to run dropDB.sql: {e}")
        return False

def main():
    """Main function to run the database cleanup"""
    base_dir = get_base_dir()
    drop_sql_path = base_dir / 'dropDB.sql'

    success = run_drop_sql(drop_sql_path)

    print()
    print("=" * 60)
    if success:
        print("[COMPLETE] Database cleanup finished successfully")
    else:
        print("[FAILED] Database cleanup encountered errors")
    print("=" * 60)

    sys.exit(0 if success else 1)

if __name__ == '__main__':
    main()
