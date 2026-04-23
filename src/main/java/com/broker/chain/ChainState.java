package com.broker.chain;

public class ChainState {
    private String jobId;
    private String jobType;
    private String data;
    private String retryUrl;
    private String emailResponse;
    private String error;

    // Getters and Setters Manuales para evitar problemas con Lombok
    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }
    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public String getRetryUrl() { return retryUrl; }
    public void setRetryUrl(String retryUrl) { this.retryUrl = retryUrl; }
    public String getEmailResponse() { return emailResponse; }
    public void setEmailResponse(String emailResponse) { this.emailResponse = emailResponse; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}
