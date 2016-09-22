trigger exampleGoodButInefficientTrigger on Opportunity (before insert, after update) {  
    Map<Id, User> mapUsers = new Map<Id, User>([SELECT Id, Profile.Name FROM User]);

    for (Opportunity oOpportunity : trigger.new) {
        User oOwner = mapUsers.get(oOpportunity.OwnerId);

        if (oOwner.Profile.Name == ‘Executive’) {
            oOpportunity.IsExecutiveOpportunity = true;
        }
    }
}

trigger exampleBetterTrigger on Opportunity (before insert, after update) {  
    Set<Id> ownerIds = new Set<Id>();

    for (Opportunity oOpportunity : trigger.new) {
        ownerIds.add(oOpportunity.OwnerId);
    }

    Map<Id, User> mapUsers = new Map<Id, User>([SELECT Id, Profile.Name FROM User WHERE Id IN :ownerIds]);

    for (Opportunity oOpportunity : trigger.new) {
        User oOwner = mapUsers.get(oOpportunity.OwnerId);

        if (oOwner.Profile.Name == ‘Executive’) {
            oOpportunity.IsExecutiveOpportunity = true;
        }
    }
}

trigger exampleBestTrigger on Opportunity (before insert, after update) {  
    Set<Id> oppIds = trigger.newMap.keySet();

    Map<Id, User> mapUsers = new Map<Id, User>([SELECT Id, Profile.Name FROM User WHERE Id IN (
                                                                                               SELECT OwnerId
                                                                                                 FROM Opportunity
                                                                                                WHERE Id IN :oppIds)]);

    for (Opportunity oOpportunity : trigger.new) {
        User oOwner = mapUsers.get(oOpportunity.OwnerId);

        if (oOwner.Profile.Name == ‘Executive’) {
            oOpportunity.IsExecutiveOpportunity = true;
        }
    }
}