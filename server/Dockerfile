FROM node:18 AS buyer-builder
WORKDIR /app/buyer
COPY buyer/package*.json ./
RUN npm install
COPY buyer/ .

FROM node:18 AS seller-builder
WORKDIR /app/seller
COPY seller/package*.json ./
RUN npm install
COPY seller/ .

FROM node:18
WORKDIR /app

COPY --from=buyer-builder /app/buyer ./buyer
COPY --from=seller-builder /app/seller ./seller

RUN npm install -g concurrently

EXPOSE 3001
EXPOSE 3002

CMD ["concurrently", "npm start --prefix buyer", "npm start --prefix seller"]