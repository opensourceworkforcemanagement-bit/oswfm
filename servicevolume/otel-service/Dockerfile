FROM otel/opentelemetry-collector-contrib:latest

COPY ../otel/otel-collector-config.yml /etc/otelcol-contrib/config.yaml

EXPOSE 4317 4318

ENTRYPOINT ["/otelcol-contrib"]
CMD ["--config=/etc/otelcol-contrib/config.yaml"]
