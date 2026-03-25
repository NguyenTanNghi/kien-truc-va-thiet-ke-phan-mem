import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import UserList from "./pages/UserList";
import CreateUser from "./pages/CreateUser";
import EditUser from "./pages/EditUser";

function App() {
    return (
        <Router>
            <div className="min-h-screen bg-gray-100">
                <nav className="bg-blue-600 text-white p-4 shadow-lg">
                    <div className="container mx-auto flex justify-between items-center">
                        <h1 className="text-2xl font-bold">
                            User Management System
                        </h1>
                        <div className="space-x-4">
                            <Link
                                to="/"
                                className="hover:text-blue-200 transition"
                            >
                                User List
                            </Link>
                            <Link
                                to="/create"
                                className="hover:text-blue-200 transition"
                            >
                                Create User
                            </Link>
                        </div>
                    </div>
                </nav>

                <main className="container mx-auto p-6">
                    <Routes>
                        <Route path="/" element={<UserList />} />
                        <Route path="/create" element={<CreateUser />} />
                        <Route path="/edit/:id" element={<EditUser />} />
                    </Routes>
                </main>
            </div>
        </Router>
    );
}

export default App;
