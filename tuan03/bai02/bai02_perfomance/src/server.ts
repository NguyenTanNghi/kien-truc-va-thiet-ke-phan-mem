import { createApp } from './app';

const PORT = process.env.PORT || 8080;

const app = createApp();

app.listen(PORT, () => {
    console.log(`🚀 Server is running on http://localhost:${PORT}`);
    console.log(`📋 API endpoint: http://localhost:${PORT}/api/v1/posts`);
    console.log(`💚 Health check: http://localhost:${PORT}/health`);
});
