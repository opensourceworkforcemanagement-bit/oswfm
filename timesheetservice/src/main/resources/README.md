# Timesheet Normalized Strategy

This package contains a fully normalized implementation for managing timesheet entries with flexible code and hour storage.

## Architecture

### Database Design (Option 1 - Fully Normalized)

The system uses three main tables:

1. **timesheet_entries** - Main entry table
2. **timesheet_entry_codes** - Stores arbitrary codes (work code, account code, project code, etc.)
3. **timesheet_entry_hours** - Stores hours by date

### Package Structure

```
org.oswfm.timesheetservice
├── model
│   ├── entity.timesheetstrategynormalized
│   │   ├── TimesheetEntry.java
│   │   ├── TimesheetEntryCode.java
│   │   └── TimesheetEntryHours.java
│   └── timesheet.timesheetstrategynormalized.dto
│       ├── TimesheetEntryDTO.java
│       ├── CodeDTO.java
│       ├── CodeRequest.java
│       ├── CreateTimesheetEntryRequest.java
│       └── UpdateTimesheetEntryRequest.java
├── repository
│   ├── TimesheetEntryRepository.java
│   ├── TimesheetEntryCodeRepository.java
│   └── TimesheetEntryHoursRepository.java
├── service.timesheetstrategynormalized
│   ├── TimesheetEntryService.java
│   └── impl
│       └── TimesheetEntryServiceImpl.java
├── controller
│   └── TimesheetEntryController.java
└── exception
    ├── ResourceNotFoundException.java
    ├── ErrorResponse.java
    └── GlobalExceptionHandler.java
```

## Features

### Flexibility
- **Arbitrary Codes**: Store any number of codes (work code, account code, project code, task code, etc.)
- **Arbitrary Weeks**: Store hours for any number of days/weeks without column limitations

### Capabilities
- CRUD operations for timesheet entries
- Query by code type and code ID
- Query by date range
- Calculate total hours per entry
- Calculate total hours per timesheet
- Comprehensive validation and error handling

## API Endpoints

### Create Entry
```http
POST /api/v1/timesheet-entries
Content-Type: application/json

{
  "timesheetId": 1,
  "codes": {
    "WORK_CODE": {
      "codeId": 101,
      "codeValue": "Development"
    },
    "ACCOUNT_CODE": {
      "codeId": 202,
      "codeValue": "Client A"
    }
  },
  "hours": {
    "2025-01-06": 8.0,
    "2025-01-07": 7.5,
    "2025-01-08": 8.0
  }
}
```

### Get Entry by ID
```http
GET /api/v1/timesheet-entries/{id}
```

### Get Entries by Timesheet ID
```http
GET /api/v1/timesheet-entries?timesheetId=1
```

### Update Entry
```http
PUT /api/v1/timesheet-entries/{id}
Content-Type: application/json

{
  "codes": {
    "PROJECT_CODE": {
      "codeId": 303,
      "codeValue": "Website Redesign"
    }
  },
  "hours": {
    "2025-01-09": 6.5
  }
}
```

### Delete Entry
```http
DELETE /api/v1/timesheet-entries/{id}
```

### Query by Code
```http
GET /api/v1/timesheet-entries/by-code?codeType=WORK_CODE&codeId=101
```

### Query by Date Range
```http
GET /api/v1/timesheet-entries/by-date-range?startDate=2025-01-01&endDate=2025-01-31
```

### Get Total Hours for Entry
```http
GET /api/v1/timesheet-entries/{id}/total-hours
```

### Get Total Hours for Timesheet
```http
GET /api/v1/timesheet-entries/timesheet/{timesheetId}/total-hours
```

## Database Schema

Run the SQL script located at:
```
src/main/resources/db/migration/V1__Create_Timesheet_Normalized_Tables.sql
```

This will create:
- Tables with proper constraints and foreign keys
- Indexes for query performance
- Triggers for automatic timestamp updates

## Dependencies

Required in your `pom.xml` or `build.gradle`:

```xml
<!-- Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

## Configuration

Add to your `application.properties` or `application.yml`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/timesheetdb
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Logging
logging.level.org.oswfm.timesheetservice=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

## Benefits of This Approach

### Scalability
- No schema changes needed to add new code types
- No column limit for storing weeks/days
- Efficient querying with proper indexes

### Maintainability
- Clean separation of concerns
- Type-safe entities and DTOs
- Comprehensive validation

### Performance
- Indexed columns for fast lookups
- Optimized queries with JOIN FETCH
- Aggregate functions for totals

### Flexibility
- Easy to add new code types without migration
- Support for non-contiguous date ranges
- Dynamic hour entries per timesheet entry

## Testing

Example test data:

```sql
-- Create a timesheet entry
INSERT INTO timesheet_entries (timesheet_id) VALUES (1);

-- Add codes
INSERT INTO timesheet_entry_codes (timesheet_entry_id, code_type, code_id, code_value) 
VALUES 
    (1, 'WORK_CODE', 101, 'Development'),
    (1, 'ACCOUNT_CODE', 202, 'Client A'),
    (1, 'PROJECT_CODE', 303, 'Website Redesign');

-- Add hours
INSERT INTO timesheet_entry_hours (timesheet_entry_id, date, hours) 
VALUES 
    (1, '2025-01-06', 8.0),
    (1, '2025-01-07', 7.5),
    (1, '2025-01-08', 8.0),
    (1, '2025-01-09', 6.5),
    (1, '2025-01-10', 8.0);
```

## License

[Your License Here]
