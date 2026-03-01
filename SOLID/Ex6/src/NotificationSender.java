public abstract class NotificationSender {
    protected final AuditLog audit;
    protected NotificationSender(AuditLog audit) { this.audit = audit; }

    /**
     * Sends a notification via the appropriate channel.
     * Implementation must NOT:
     * - Throw exceptions for valid input (validation is caller's responsibility)
     * - Silently truncate or modify content
     * - Ignore documented fields in the request
     */
    public abstract void send(Object request);
}