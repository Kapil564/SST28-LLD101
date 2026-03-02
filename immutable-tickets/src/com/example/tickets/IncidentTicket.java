package com.example.tickets;

import java.util.*;

/**
 * INTENTION: A ticket should be an immutable record-like object.
 *
 * CURRENT STATE (BROKEN ON PURPOSE):
 * - mutable fields
 * - multiple constructors
 * - public setters
 * - tags list can be modified from outside
 * - validation is scattered elsewhere
 *
 * TODO (student): refactor to immutable + Builder.
 */
public final class IncidentTicket {

    private final  String id;
    private final  String reporterEmail;
    private  final String title;
    private final  String description;
    private final String priority;      // LOW, MEDIUM, HIGH, CRITICAL
    private final List<String> tags;     // mutable leak
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes;    // optional
    private final String source;         // e.g. "CLI", "WEBHOOK", "EMAIL"



    private IncidentTicket(Builder builder) {
        this.id = builder.id;
        this.reporterEmail = builder.reporterEmail;
        this.title = builder.title;
        this.description = builder.description;
        this.priority = builder.priority;
        this.assigneeEmail = builder.assigneeEmail;
        this.customerVisible = builder.customerVisible;
        this.slaMinutes = builder.slaMinutes;
        this.source = builder.source;

        // Defensive copy + unmodifiable wrapper
        this.tags = Collections.unmodifiableList(new ArrayList<>(builder.tags));
    }

    // Getters
    public String getId() { return id; }
    public String getReporterEmail() { return reporterEmail; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public List<String> getTags() { return tags; } // BROKEN: leaks internal list
    public String getAssigneeEmail() { return assigneeEmail; }
    public boolean isCustomerVisible() { return customerVisible; }
    public Integer getSlaMinutes() { return slaMinutes; }
    public String getSource() { return source; }


    @Override
    public String toString() {
        return "IncidentTicket{" +
                "id='" + id + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                ", customerVisible=" + customerVisible +
                ", slaMinutes=" + slaMinutes +
                ", source='" + source + '\'' +
                '}';
    }
    public Builder toBuilder() {
        return new Builder(this.id, this.reporterEmail, this.title)
                .description(this.description)
                .priority(this.priority)
                .assigneeEmail(this.assigneeEmail)
                .customerVisible(this.customerVisible)
                .slaMinutes(this.slaMinutes)
                .source(this.source)
                .tags(this.tags);
    }
    public static class Builder{
        private final  String id;
        private final  String reporterEmail;
        private  final String title;

        private String description;
        private String priority;
        private List<String> tags = new ArrayList<>();
        private String assigneeEmail;
        private boolean customerVisible = false;
        private Integer slaMinutes;
        private String source;

        public Builder(String id, String reporterEmail, String title) {
            this.id = Objects.requireNonNull(id, "id is required");
            this.reporterEmail = Objects.requireNonNull(reporterEmail, "reporterEmail is required");
            this.title = Objects.requireNonNull(title, "title is required");
        }
        public Builder description(String description) {
            this.description=description;
            return this;
        }
        public Builder priority(String priority) {
            this.priority = priority;
            return this;
        }
        public Builder addTag(String tag) {
            this.tags.add(tag);
            return this;
        }
        public Builder tags(List<String> tags) {
            this.tags=new ArrayList<>(tags);;
            return this;
        }

        public Builder assigneeEmail(String assigneeEmail) {
            this.assigneeEmail=assigneeEmail;
            return this;
        }
        public Builder customerVisible(boolean customerVisible) {
            this.customerVisible = customerVisible;
            return this;
        }

        public Builder slaMinutes(Integer slaMinutes) {
            this.slaMinutes = slaMinutes;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public IncidentTicket build() {
            if (slaMinutes != null && slaMinutes <= 0) {
                throw new IllegalArgumentException("SLA minutes must be positive");
            }
            return new IncidentTicket(this);
         }
    }
}