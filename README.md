# DevicesCRUD
CRUD example for devices model

## Requirements

- Requires Java JDK 21 and MariaDB 11.4.4

## Commands
### How to run locally

```
./gradlew bootRun
```

### How to run tests only

```
./gradlew clean test
```

### How to access API documentation

```
localhost:8080/api/v1/swagger-ui.html
```

### How to build project

```
./gradlew clean build
```

### How to build docker image after building Java project

```
docker build --build-arg JAR_FILE=./build/libs/devices-0.0.1-SNAPSHOT.jar -t devices/devices-crud-docker .
```

### How to run docker container

```
docker run -p 8080:8080 -d devices/devices-crud-docker:latest
```

## Endpoints

### Create a new device

```
POST localhost:8080/api/v1/devices
```
#### Request parameters

| Parameter name | Value type  | Example                         | Optional |
|----------------|-------------|---------------------------------|----------|
| name           | String      | "Device Name"                   | No       |
| brand          | String      | "Device Brand"                  | No       |
| state          | String/Enum | "AVAILABLE"/"IN_USE"/"INACTIVE" | No       |
| creationTime   | Timestamp   | "2025-03-03T01:02:03Z"          | No       |

#### Request example

```
{
    "name": "Device Name",
    "brand": "Device Brand",
    "state": "AVAILABLE",
    "creationTime": "2025-03-03T01:02:03Z"
}
```

### Update a device

```
PUT localhost:8080/api/v1/devices
```
#### Request parameters

| Parameter name | Value type  | Example                         | Optional |
|----------------|-------------|---------------------------------|----------|
| id           | Number      | 1                   | No       |
| name           | String      | "Device Name"                   | Yes       |
| brand          | String      | "Device Brand"                  | Yes       |
| state          | String/Enum | "AVAILABLE"/"IN_USE"/"INACTIVE" | Yes       |

#### Request example

```
{
    "id": 1,
    "name": "Device Name",
    "brand": "Device Brand",
    "state": "AVAILABLE"
}
```

#### Restrictions

- A device can't be updated if it's in use (State = IN_USE)

### Find a device by ID

```
GET localhost:8080/api/v1/devices/{deviceId}
```

#### Path parameters

| Parameter name | Value type  | Example                         | Optional |
|----------------|-------------|---------------------------------|----------|
| deviceId           | Number      | 1                   | No       |

#### Request example

```
GET localhost:8080/api/v1/devices/1
```

### Find all devices

```
GET localhost:8080/api/v1/devices
```

### Find devices by parameters

```
GET localhost:8080/api/v1/devices?brand={brand}&state={state}
```

#### Query parameters

| Parameter name | Value type  | Example                         | Optional |
|----------------|-------------|---------------------------------|----------|
| brand          | String      | Device Brand                  | Yes       |
| state          | String/Enum | "AVAILABLE"/"IN_USE"/"INACTIVE" | Yes       |

#### Request example

```
GET localhost:8080/api/v1/devices?brand=Device Brand&state=AVAILABLE
```

### Delete a device by ID

```
DELETE localhost:8080/api/v1/devices/{deviceId}
```

#### Path parameters

| Parameter name | Value type  | Example                         | Optional |
|----------------|-------------|---------------------------------|----------|
| deviceId           | Number      | 1                   | No       |

#### Request example

```
GET localhost:8080/api/v1/devices/1
```