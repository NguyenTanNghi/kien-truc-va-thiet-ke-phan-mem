import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'
import tsconfigPaths from 'vite-tsconfig-paths'

export default defineConfig({
  plugins: [react(), tsconfigPaths()],
  server: {
    host: '0.0.0.0',
    port: 3000,
    proxy: {
      '/api/users': {
        target: 'http://192.168.137.103:8081',
        changeOrigin: true
      },
      '/foods': {
        target: 'http://192.168.137.203:8082',
        changeOrigin: true
      },
      '/orders': {
        target: 'http://192.168.137.1:8083',
        changeOrigin: true
      },
      '/payments': {
        target: 'http://192.168.137.126:8084',
        changeOrigin: true
      }
    }
  }
})