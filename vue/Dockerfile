# Node 22 + Alpine 3.21
FROM node:22-alpine3.21

# Install dependencies needed to compile native packages
RUN apk add --no-cache python3 make g++ bash

# Set working directory
WORKDIR /app

# Copy dependency files first for caching
COPY package*.json ./

# Install npm dependencies and clean cache
RUN npm install \
 && npm cache clean --force

# Copy the rest of the application code
COPY . .

# Expose Vite port
EXPOSE 5173

# Default command
CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0"]
