trigger updateContactOtherAddress on Account(after insert, after update) {  
    if (trigger.isUpdate) {
        //Identify Account Address Changes
        Set<Id> setAccountAddressChangedIds = new Set<Id>();

        for (Account oAccount : trigger.new) {
            Account oOldAccount = trigger.oldMap.get(oAccount.Id);

            boolean bIsChanged = (oAccount.BillingStreet != oOldAccount.BillingStreet || oAccount.BillingCity != oOldAccount.BillingCity);
            if (bIsChanged) {
                setAccountAddressChangedIds.add(oAccount.Id);
            }
        }

        //If any, get contacts associated with each account
        if (setAccountAddressChangedIds.isEmpty() == false) {
            List<Contact> listContacts = [SELECT Id, AccountId FROM Contact WHERE AccountId IN :setAccountAddressChangedIds];
            for (Contact oContact : listContacts) {
                //Get Account
                oAccount = trigger.newMap.get(oContact.AccountId);

                //Set Address
                oContact.OtherStreet = oAccount.BillingStreet;
                oContact.OtherCity = oAccount.BillingCity;
                oContact.OtherState = oAccount.BillingState;
                oContact.OtherPostalCode = oAccount.BillingPostalCode;
            }

            //If any, execute DML command to save contact addresses
            if (listContacts.isEmpty() == false) {
                update listContacts;
            }
        }
    }
}