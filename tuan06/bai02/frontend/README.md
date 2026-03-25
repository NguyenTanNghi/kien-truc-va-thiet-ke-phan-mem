# User Management System - Frontend

React application with TailwindCSS for managing users with partitioned backend.

## Features

- View all users in a table
- Create new users
- Edit existing users
- Delete users
- Responsive design with TailwindCSS
- Clean UI with functional components

## Tech Stack

- React 18
- Vite
- React Router DOM
- Axios
- TailwindCSS

## Setup

1. Install dependencies:

    ```bash
    npm install
    ```

2. Make sure the backend is running on `http://localhost:8080`

3. Start the development server:

    ```bash
    npm run dev
    ```

4. Open browser at `http://localhost:3000`

## Build

To build for production:

```bash
npm run build
```

To preview production build:

```bash
npm run preview
```

## Project Structure

```
src/
├── components/          # Reusable components (if any)
├── pages/              # Page components
│   ├── UserList.jsx    # List all users
│   ├── CreateUser.jsx  # Create user form
│   └── EditUser.jsx    # Edit user form
├── services/           # API services
│   └── userApi.js      # User API calls
├── App.jsx             # Main app component with routing
└── main.jsx            # Entry point
```

## API Integration

The app connects to the Spring Boot backend at:

- `http://localhost:8080/api/users`

All API calls are handled through `src/services/userApi.js`
