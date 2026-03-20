const http = require("http");

http.createServer((req, res) => {
    res.end("Hello Docker 🚀");
}).listen(3000);

console.log("Server running at http://localhost:3000");
