import { useState } from "react";
import { api } from "./api";

function App() {
    const [userForm, setUserForm] = useState({ name: "", email: "" });
    const [orderForm, setOrderForm] = useState({ userId: "", amount: "" });
    const [paymentForm, setPaymentForm] = useState({ orderId: "", amount: "" });
    const [queryUserId, setQueryUserId] = useState("");
    const [queryOrderId, setQueryOrderId] = useState("");
    const [result, setResult] = useState(null);
    const [error, setError] = useState("");

    const execute = async (fn) => {
        try {
            setError("");
            const data = await fn();
            setResult(data);
        } catch (e) {
            setError(e.message);
        }
    };

    return (
        <div className="container">
            <h1>Service-Based Demo</h1>
            <p>Backend: User Service, Order Service, Payment Service</p>

            <div className="grid">
                <section>
                    <h2>Create User</h2>
                    <input
                        placeholder="Name"
                        value={userForm.name}
                        onChange={(e) =>
                            setUserForm({ ...userForm, name: e.target.value })
                        }
                    />
                    <input
                        placeholder="Email"
                        value={userForm.email}
                        onChange={(e) =>
                            setUserForm({ ...userForm, email: e.target.value })
                        }
                    />
                    <button
                        onClick={() => execute(() => api.createUser(userForm))}
                    >
                        POST /users
                    </button>
                </section>

                <section>
                    <h2>Get User</h2>
                    <input
                        placeholder="User ID"
                        value={queryUserId}
                        onChange={(e) => setQueryUserId(e.target.value)}
                    />
                    <button
                        onClick={() => execute(() => api.getUser(queryUserId))}
                    >
                        GET /users/{"{id}"}
                    </button>
                </section>

                <section>
                    <h2>Create Order</h2>
                    <input
                        placeholder="User ID"
                        value={orderForm.userId}
                        onChange={(e) =>
                            setOrderForm({
                                ...orderForm,
                                userId: e.target.value,
                            })
                        }
                    />
                    <input
                        placeholder="Amount"
                        value={orderForm.amount}
                        onChange={(e) =>
                            setOrderForm({
                                ...orderForm,
                                amount: e.target.value,
                            })
                        }
                    />
                    <button
                        onClick={() =>
                            execute(() =>
                                api.createOrder({
                                    userId: Number(orderForm.userId),
                                    amount: Number(orderForm.amount),
                                }),
                            )
                        }
                    >
                        POST /orders
                    </button>
                </section>

                <section>
                    <h2>Get Order</h2>
                    <input
                        placeholder="Order ID"
                        value={queryOrderId}
                        onChange={(e) => setQueryOrderId(e.target.value)}
                    />
                    <button
                        onClick={() =>
                            execute(() => api.getOrder(queryOrderId))
                        }
                    >
                        GET /orders/{"{id}"}
                    </button>
                </section>

                <section>
                    <h2>Create Payment (Manual)</h2>
                    <input
                        placeholder="Order ID"
                        value={paymentForm.orderId}
                        onChange={(e) =>
                            setPaymentForm({
                                ...paymentForm,
                                orderId: e.target.value,
                            })
                        }
                    />
                    <input
                        placeholder="Amount"
                        value={paymentForm.amount}
                        onChange={(e) =>
                            setPaymentForm({
                                ...paymentForm,
                                amount: e.target.value,
                            })
                        }
                    />
                    <button
                        onClick={() =>
                            execute(() =>
                                api.createPayment({
                                    orderId: Number(paymentForm.orderId),
                                    amount: Number(paymentForm.amount),
                                }),
                            )
                        }
                    >
                        POST /payments
                    </button>
                </section>
            </div>

            {error && <pre className="error">{error}</pre>}
            {result && (
                <pre className="result">{JSON.stringify(result, null, 2)}</pre>
            )}
        </div>
    );
}

export default App;
