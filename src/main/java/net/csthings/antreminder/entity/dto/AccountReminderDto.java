package net.csthings.antreminder.entity.dto;

//@Entity
//@Table(name = AccountReminderDto.TABLE_NAME)
//@Cacheable(AccountReminderDto.TABLE_NAME)
public class AccountReminderDto {
    public static final String TABLE_NAME = "account_reminders";
    // @Id
    // @Column(columnDefinition = "BINARY(16)")
    // private UUID accountId;
    //
    // @JoinColumn(table = AccountDto.TABLE_NAME)
    // private String email;
    //
    // @ManyToMany(fetch = FetchType.LAZY, targetEntity = ReminderDto.class,
    // cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    // @JoinTable(name = LinkedRemindersDto.TABLE_NAME, joinColumns = {
    // @JoinColumn(name = "accountId") },
    // inverseJoinColumns = { @JoinColumn(name = "status"), @JoinColumn(name =
    // "reminderId") })
    // private Set<ReminderDto> reminders;
    //
    // public AccountReminderDto() {
    // reminders = new HashSet<>();
    // }
    //
    // public AccountReminderDto(UUID accountId, String email) {
    // this();
    // this.accountId = accountId;
    // this.email = email;
    // }
    //
    // public AccountReminderDto(UUID accountId, ReminderDto reminder) {
    // this();
    // this.accountId = accountId;
    // reminders.add(reminder);
    // }
    //
    // public UUID getAccountId() {
    // return accountId;
    // }
    //
    // public void setAccountId(UUID accountId) {
    // this.accountId = accountId;
    // }
    //
    // public String getEmail() {
    // return email;
    // }
    //
    // public void setEmail(String email) {
    // this.email = email;
    // }
    //
    // @Cacheable(AccountReminderDto.TABLE_NAME)
    // public Set<ReminderDto> getReminders() {
    // return reminders;
    // }
    //
    // public void setReminders(Set<ReminderDto> reminders) {
    // this.reminders = reminders;
    // }
    //
    // @Override
    // public String toString() {
    // return "AccountReminderDto [accountId=" + accountId + ", email=" + email
    // + "]";
    // }

}
