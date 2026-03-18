import { useState } from "react";
import "./App.css";
import { login, register } from "./config/api";

export default function App() {
    const [isLogin, setIsLogin] = useState(true);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    const handleSubmit = async () => {
        try {
            if (isLogin) {
                const res = await login(email, password);
                setMessage(res.data);
            } else {
                const res = await register(email, password);
                
                setMessage(res.data);
                console.log("kt: ",res);
            }
        } catch (err: any) {
            setMessage("❌ Lỗi: " + err.response?.data || "Server error");
        }
    };

    return (
        <div className="container">
            <div className="card">
                <h2>{isLogin ? "Login" : "Register"}</h2>

                <input
                    type="email"
                    placeholder="Email"
                    onChange={(e) => setEmail(e.target.value)}
                />

                <input
                    type="password"
                    placeholder="Password"
                    onChange={(e) => setPassword(e.target.value)}
                />

                <button onClick={handleSubmit}>
                    {isLogin ? "Login" : "Register"}
                </button>

                <p className="switch" onClick={() => setIsLogin(!isLogin)}>
                    {isLogin
                        ? "Chưa có tài khoản? Đăng ký"
                        : "Đã có tài khoản? Đăng nhập"}
                </p>

                {message && <p className="message">{message}</p>}
            </div>
        </div>
    );
}
