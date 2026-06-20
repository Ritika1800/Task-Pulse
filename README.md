# Task Manager — Realtime Collaborative Task Board

A distributed, full-stack task management platform that enables teams to collaborate on projects in real time. Built with a microservice-inspired architecture combining **Spring Boot**, **Go**, **Redis**, and **React** to deliver seamless live task synchronization across all connected users.

---

## 🚀 What It Does

Task Manager lets multiple users work on shared project boards simultaneously. When any team member creates, updates, or moves a task, every other connected user sees the change instantly — no page refresh needed. The system is designed around an event-driven pipeline where the backend publishes task events to Redis, which a dedicated Go server picks up and broadcasts via WebSocket connections to all active clients.

---

## 🏗️ System Architecture

```
React Client (Browser)
        │
        │  HTTP REST + JWT Token
        ▼
Spring Boot API Server
        │
        │  Publishes events to Redis channel
        ▼
     Redis Broker
        │
        │  Subscribes & streams events
        ▼
  Go WebSocket Server
        │
        │  Pushes live updates over WebSocket
        ▼
React Client (Browser)
```

---

## ✨ Core Features

### 🔐 Identity & Access
- JWT-based stateless authentication
- Role-based permissions (Admin / Member)
- Protected REST endpoints via Spring Security

### 📋 Task & Project Management
- Full CRUD operations on tasks
- Organize tasks under projects
- Track task lifecycle: `TODO` → `IN_PROGRESS` → `DONE`
- Assign tasks to specific team members

### ⚡ Live Collaboration Engine
- Redis Pub/Sub as the event backbone
- Go-powered WebSocket server for high-concurrency broadcasting
- All connected clients receive instant updates when tasks change
- Real-time in-app notifications

### 🖥️ Interactive Frontend
- Kanban-style drag-and-drop board
- Live state updates using WebSocket + React Context
- Notification panel for task activity
- Per-project board views and a unified dashboard

---

## 🧩 Service Breakdown

| Service | Role |
|---|---|
| `backend-spring` | Handles REST APIs, authentication, database operations, and Redis publishing |
| `websocket-go` | Subscribes to Redis and pushes live events to connected browser clients |
| `redis` | Acts as the message broker between the backend and WebSocket layer |
| `frontend-react` | Renders the UI, connects to both the REST API and WebSocket server |

---

## 🛠️ Technology Stack

**Backend — Spring Boot**
- Java 17
- Spring Boot
- Spring Security (JWT)
- PostgreSQL (via JPA/Hibernate)
- Redis (Pub/Sub)
- Maven

**Realtime Layer — Go**
- Go 1.20+
- Gorilla WebSocket
- Redis client (go-redis)

**Frontend — React**
- React 18
- Axios (REST calls)
- Native WebSocket API
- React Context API (global state)
- Drag and Drop (react-beautiful-dnd or similar)

---

## 📁 Repository Layout

```
Task Manager/
│
├── backend-spring/       # REST API server + Redis event publisher
│   ├── src/
│   └── pom.xml
│
├── websocket-go/         # WebSocket broadcast server + Redis subscriber
│   ├── main.go
│   └── go.mod
│
├── frontend-react/       # React UI application
│   ├── src/
│   └── package.json
│
└── README.md
```

---

## ⚙️ Prerequisites

Make sure the following are installed before running the project:

- Java 17+
- Go 1.20+
- Node.js 18+
- PostgreSQL
- Redis
- Maven 3.8+

---

## 🔧 Setup & Installation

### 1. Clone the Repository

```bash
git clone https://github.com/<your-username>/Task Manager.git
cd Task Manager
```

```

---

## ▶️ Running the Application

Start each service in order:

**Step 1 — Redis**
```bash
redis-server
```

**Step 2 — Spring Boot Backend**
```bash
cd backend-spring
mvn clean install
mvn spring-boot:run
```
> Runs at: `http://localhost:8080`

**Step 3 — Go WebSocket Server**
```bash
cd websocket-go
go run .
```
> Runs at: `ws://localhost:8081/ws`

**Step 4 — React Frontend**
```bash
cd frontend-react
npm install
npm run dev
```
> Runs at: `http://localhost:3000`

<img width="3420" height="2214" alt="image" src="https://github.com/user-attachments/assets/3b34a835-d98f-4f36-9cfd-4f444369f536" />


## 📡 API Reference

### Auth
```
POST   /api/auth/register     Register a new user
POST   /api/auth/login        Authenticate and receive JWT
```

### Projects
```
POST   /api/projects          Create a new project
GET    /api/projects          List all projects for current user
```

### Tasks
```
POST   /api/tasks             Create a task
GET    /api/tasks             Fetch tasks (filter by project)
PUT    /api/tasks/{id}/status Update task status
DELETE /api/tasks/{id}        Remove a task
```

---

## 🔁 Redis Event Channel

```
Channel name: task-events
```

All task mutations (create, update, delete) publish a JSON event to this channel. The Go WebSocket server listens here and forwards events to the appropriate connected clients.

---

## 🧪 Sample API Usage

**Register a User**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "john", "email": "john@example.com", "password": "secret123"}'
```

**Login**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "john", "password": "secret123"}'
```

**Create a Task**
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Implement drag and drop",
    "description": "Add Kanban drag-and-drop support to frontend",
    "status": "TODO",
    "projectId": 1,
    "assignedUserId": 2
  }'
```

**Update Task Status**
```bash
curl -X PUT "http://localhost:8080/api/tasks/5/status?status=IN_PROGRESS" \
  -H "Authorization: Bearer $TOKEN"
```

**Delete a Task**
```bash
curl -X DELETE http://localhost:8080/api/tasks/5 \
  -H "Authorization: Bearer $TOKEN"
```

---

