# Notes API (Express + MongoDB)

Simple REST API for a Notes app.

## Endpoints
- `GET /api/notes` — list notes
- `POST /api/notes` — create `{ text }`
- `PUT /api/notes/:id` — update `{ text }`
- `DELETE /api/notes/:id` — delete

## Quick Start

1) Install dependencies
```bash
npm install
```

2) Copy `.env.example` to `.env` and edit if needed
```bash
cp .env.example .env
# Update MONGODB_URI if using MongoDB Atlas
```

3) Run dev server
```bash
npm run dev
# or
npm start
```

By default the API runs at `http://localhost:3000/`.
