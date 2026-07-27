# AI-Powered Job Application Tracker

## Tech Stack
- Spring Boot 3.3.x
- PostgreSQL
- Redis
- Docker Compose
- Spring AI + Groq LLM

## Features
- CRUD APIs for job applications
- Redis caching with TTL
- Natural language job search using AI

## How to Run
docker-compose up --build

## API Endpoints
POST /api/jobs
GET /api/jobs
GET /api/jobs/{id}
GET /api/jobs/ai/search?query=...