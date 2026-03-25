const API_BASE_URL =
    import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

async function request(path, method = "GET", body) {
    const response = await fetch(`${API_BASE_URL}${path}`, {
        method,
        headers: {
            "Content-Type": "application/json",
        },
        body: body ? JSON.stringify(body) : undefined,
    });

    if (!response.ok) {
        const error = await response.text();
        throw new Error(error || "Request failed");
    }

    return response.status === 204 ? null : response.json();
}

export const api = {
    createUser: (payload) => request("/users", "POST", payload),
    getUser: (id) => request(`/users/${id}`),
    createOrder: (payload) => request("/orders", "POST", payload),
    getOrder: (id) => request(`/orders/${id}`),
    createPayment: (payload) => request("/payments", "POST", payload),
};
