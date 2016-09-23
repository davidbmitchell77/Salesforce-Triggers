 public with sharing class AccountTriggerHandler {
	private boolean m_isExecuting = false;
	private integer BatchSize = 0;
	
	public AccountTriggerHandler(boolean isExecuting, integer size){
		m_isExecuting = isExecuting;
		BatchSize = size;
	}
		
	public void OnBeforeInsert(Account[] newAccounts){
		//Example usage
		for(Account newAccount : newAccounts){
			if(newAccount.AnnualRevenue == null){
				newAccount.AnnualRevenue.addError('Missing annual revenue');
			}
		}
	}
	
	public void OnAfterInsert(Account[] newAccounts){
		
	}
	
	@future public static void OnAfterInsertAsync(Set<ID> newAccountIDs){
		//Example usage
		List<Account> newAccounts = [select Id, Name from Account where Id IN :newAccountIDs];
	}
	
	public void OnBeforeUpdate(Account[] oldAccounts, Account[] updatedAccounts, Map<ID, Account> accountMap){
		//Example Map usage
		Map<ID, Contact> contacts = new Map<ID, Contact>( [select Id, FirstName, LastName, Email from Contact where AccountId IN :accountMap.keySet()] );
	}
	
	public void OnAfterUpdate(Account[] oldAccounts, Account[] updatedAccounts, Map<ID, Account> accountMap){
		
	}
	
	@future public static void OnAfterUpdateAsync(Set<ID> updatedAccountIDs){
		List<Account> updatedAccounts = [select Id, Name from Account where Id IN :updatedAccountIDs];
	}
	
	public void OnBeforeDelete(Account[] accountsToDelete, Map<ID, Account> accountMap){
		
	}
	
	public void OnAfterDelete(Account[] deletedAccounts, Map<ID, Account> accountMap){
		
	}
	
	@future public static void OnAfterDeleteAsync(Set<ID> deletedAccountIDs){
		
	}
	
	public void OnUndelete(Account[] restoredAccounts){
		
	}
	
	public boolean IsTriggerContext{
		get{ return m_isExecuting;}
	}
	
	public boolean IsVisualforcePageContext{
		get{ return !IsTriggerContext;}
	}
	
	public boolean IsWebServiceContext{
		get{ return !IsTriggerContext;}
	}
	
	public boolean IsExecuteAnonymousContext{
		get{ return !IsTriggerContext;}
	}
}
