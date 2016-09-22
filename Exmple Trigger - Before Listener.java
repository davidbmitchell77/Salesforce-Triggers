trigger AccountBeforeEventListener on Account (before insert, before update) {  
    if (trigger.isInsert) {
        AccountUtil.setDefaultValues(trigger.new);
        AccountUtil.setIndustryCode(trigger.new);
    }
}

public class AccountUtil {  
    public static void setDefaultValues(List<Account> listAccounts) {
        for (Account oAccount : listAccounts) {
            if (oAccount.Industry == null) {
                oAccount.Industry = ‘Cloud Computing’;
            }
        }
    }

    public static void setIndustryCode(List<Account> listAccounts) {
        for (Account oAccount : listAccounts) {
            if (oAccount.Industry == ‘Cloud Computing’) {
                oAccount.Industry_Code__c = ‘CC’;
            }
        }
    }
}