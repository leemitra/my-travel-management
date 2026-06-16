# Travel Management API - Curl Examples

Base URL: `http://localhost:8080/api`

## Health Check

```bash
# Check API status
curl -X GET http://localhost:8080/api/trips/health/status \
  -H "Content-Type: application/json"
```

## Create a Trip

```bash
# Create a new trip
curl -X POST http://localhost:8080/api/trips \
  -H "Content-Type: application/json" \
  -d '{
    "destination": "Paris",
    "description": "Spring vacation in Paris",
    "startDate": "2026-07-01",
    "endDate": "2026-07-15",
    "travelerName": "John Doe",
    "budget": 5000,
    "status": "PLANNED"
  }'
```

Response:
```json
{
  "id": 1,
  "destination": "Paris",
  "description": "Spring vacation in Paris",
  "startDate": "2026-07-01",
  "endDate": "2026-07-15",
  "travelerName": "John Doe",
  "budget": 5000.0,
  "status": "PLANNED",
  "createdAt": "2026-06-16"
}
```

## Get All Trips

```bash
# Get all trips
curl -X GET http://localhost:8080/api/trips \
  -H "Content-Type: application/json"
```

Response:
```json
[
  {
    "id": 1,
    "destination": "Paris",
    "description": "Spring vacation in Paris",
    "startDate": "2026-07-01",
    "endDate": "2026-07-15",
    "travelerName": "John Doe",
    "budget": 5000.0,
    "status": "PLANNED",
    "createdAt": "2026-06-16"
  }
]
```

## Get Trip by ID

```bash
# Get specific trip by ID
curl -X GET http://localhost:8080/api/trips/1 \
  -H "Content-Type: application/json"
```

Response:
```json
{
  "id": 1,
  "destination": "Paris",
  "description": "Spring vacation in Paris",
  "startDate": "2026-07-01",
  "endDate": "2026-07-15",
  "travelerName": "John Doe",
  "budget": 5000.0,
  "status": "PLANNED",
  "createdAt": "2026-06-16"
}
```

## Get Trips by Traveler Name

```bash
# Get all trips for a specific traveler
curl -X GET "http://localhost:8080/api/trips/traveler/John%20Doe" \
  -H "Content-Type: application/json"
```

Or:
```bash
curl -X GET "http://localhost:8080/api/trips/traveler/John Doe" \
  -H "Content-Type: application/json"
```

Response:
```json
[
  {
    "id": 1,
    "destination": "Paris",
    ...
  },
  {
    "id": 2,
    "destination": "London",
    ...
  }
]
```

## Get Trips by Status

```bash
# Get all trips with a specific status
curl -X GET http://localhost:8080/api/trips/status/PLANNED \
  -H "Content-Type: application/json"
```

Possible statuses: `PLANNED`, `CONFIRMED`, `ONGOING`, `COMPLETED`, `CANCELLED`

Response:
```json
[
  {
    "id": 1,
    "destination": "Paris",
    "status": "PLANNED",
    ...
  }
]
```

## Update a Trip

```bash
# Update existing trip
curl -X PUT http://localhost:8080/api/trips/1 \
  -H "Content-Type: application/json" \
  -d '{
    "destination": "London",
    "description": "Summer vacation in London",
    "startDate": "2026-08-01",
    "endDate": "2026-08-15",
    "travelerName": "John Doe",
    "budget": 6000,
    "status": "CONFIRMED"
  }'
```

Response:
```json
{
  "id": 1,
  "destination": "London",
  "description": "Summer vacation in London",
  "startDate": "2026-08-01",
  "endDate": "2026-08-15",
  "travelerName": "John Doe",
  "budget": 6000.0,
  "status": "CONFIRMED",
  "createdAt": "2026-06-16"
}
```

## Delete a Trip

```bash
# Delete a trip
curl -X DELETE http://localhost:8080/api/trips/1 \
  -H "Content-Type: application/json"
```

Response: HTTP 204 No Content

## Error Responses

### Validation Error (400 Bad Request)

```bash
# Missing required field
curl -X POST http://localhost:8080/api/trips \
  -H "Content-Type: application/json" \
  -d '{
    "destination": "Paris"
  }'
```

Response:
```json
{
  "timestamp": "2026-06-16T10:30:00",
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "description": "Description is required",
    "startDate": "Start date is required",
    "endDate": "End date is required",
    "travelerName": "Traveler name is required"
  }
}
```

### Not Found (404)

```bash
# Get non-existent trip
curl -X GET http://localhost:8080/api/trips/999 \
  -H "Content-Type: application/json"
```

Response: HTTP 404 Not Found

### Internal Server Error (500)

Response:
```json
{
  "timestamp": "2026-06-16T10:30:00",
  "status": 500,
  "message": "An unexpected error occurred",
  "error": "Error message details"
}
```

## Using PowerShell (Windows)

```powershell
# Create a trip
$tripData = @{
    destination = "Paris"
    description = "Spring vacation in Paris"
    startDate = "2026-07-01"
    endDate = "2026-07-15"
    travelerName = "John Doe"
    budget = 5000
    status = "PLANNED"
} | ConvertTo-Json

$response = Invoke-WebRequest -Uri "http://localhost:8080/api/trips" `
    -Method POST `
    -ContentType "application/json" `
    -Body $tripData

$response.Content | ConvertFrom-Json | Format-Table
```

## Using Python

```python
import requests
import json

# Create a trip
url = "http://localhost:8080/api/trips"
headers = {"Content-Type": "application/json"}
data = {
    "destination": "Paris",
    "description": "Spring vacation in Paris",
    "startDate": "2026-07-01",
    "endDate": "2026-07-15",
    "travelerName": "John Doe",
    "budget": 5000,
    "status": "PLANNED"
}

response = requests.post(url, headers=headers, json=data)
print(response.status_code)
print(json.dumps(response.json(), indent=2))

# Get all trips
response = requests.get(url, headers=headers)
print(json.dumps(response.json(), indent=2))
```

## Using JavaScript/Node.js

```javascript
// Create a trip
const trip = {
    destination: "Paris",
    description: "Spring vacation in Paris",
    startDate: "2026-07-01",
    endDate: "2026-07-15",
    travelerName: "John Doe",
    budget: 5000,
    status: "PLANNED"
};

fetch('http://localhost:8080/api/trips', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(trip)
})
.then(response => response.json())
.then(data => console.log(data));

// Get all trips
fetch('http://localhost:8080/api/trips')
    .then(response => response.json())
    .then(data => console.log(data));
```

## Testing with Postman

1. Import the collection or create requests manually
2. Set `{{base_url}}` variable to `http://localhost:8080`
3. Use the examples above for request bodies

Common variables:
- `{{base_url}}` = `http://localhost:8080`
- `{{tripId}}` = ID from response (e.g., 1)
- `{{travelerName}}` = Traveler name (e.g., John Doe)
- `{{status}}` = Trip status (e.g., PLANNED)

## Response Headers

All successful responses include:
- `Content-Type: application/json`
- `Content-Length: [size]`
- `Date: [timestamp]`

## Status Codes

- `200 OK` - Successful GET/PUT/PATCH request
- `201 Created` - Successful POST request
- `204 No Content` - Successful DELETE request
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

## Rate Limiting

No rate limiting is currently implemented. Add Spring Cloud Gateway if needed.

## Authentication

No authentication is currently required. Add Spring Security if needed.
