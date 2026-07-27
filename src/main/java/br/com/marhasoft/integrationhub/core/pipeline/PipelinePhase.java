package br.com.marhasoft.integrationhub.core.pipeline;

public enum PipelinePhase {

    VALIDATION,

    DEPENDENCY_RESOLUTION,

    ENRICHMENT,

    MAPPING,

    AUTHENTICATION,

    TRANSPORT,

    POST_PROCESSING,

    AUDIT
}