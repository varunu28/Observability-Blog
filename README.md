# Observability Blog
Code for the blog series exploring various areas of Observability

 - [Observability: A Primer](https://distributed-computing-musings.com/2025/01/observability-a-primer/)
 - [Observability: Unlocking the Power of Monitoring](https://distributed-computing-musings.com/2025/01/observability-unlocking-the-power-of-monitoring/)
 - [Observability: OpenTelemetry – Decoupled by design](https://distributed-computing-musings.com/2025/02/observability-opentelemetry-decoupled-by-design/)
 - [Observability: Following the Breadcrumbs – A Journey Through Distributed Tracing](https://distributed-computing-musings.com/2025/02/observability-following-the-breadcrumbs-a-journey-through-distributed-tracing/)

## How to run?
Spin up the containers for setting up infrastructure
`docker compose up -d`

Start both `booking-service` & `booking-id-service`

## Observability stack
 - Prometheus: `http://localhost:9090`
 - Grafana: `http://localhost:3000`
 - Zipkin: `http://localhost:9411`
 - Jaeger: `http://localhost:16686`
