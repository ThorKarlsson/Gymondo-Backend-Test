## Gymondo Backend Test



###### Reflections


###### What's the problem with the GET /rest-api/api/v1/users endpoint? How can we solve it? Ignore the security topic.
	
* The call to the subscription repository happens after retrieving the list of users. This makes it inefficient as we either have to call the subscription repository for each user or retrieve all subscriptions and query them in memory. Either way it results in an additional call to the DB which could be avoided by refactoring it into a single db call that retrieves both informations at the same time e.g. via a join in a SQL context. This way only 1 call to the db is made for the API call. 


###### Imagine that for some reason we decide that we no longer want to deliver the subscriptions with the user (this means removing the "subscriptions" member from the json file). What would you do to accomplish that? Why is this such an important topic?
	
* As we would be removing a field, I would version the API and make a V2 endpoint. Typically consumers should expect fields to be added so versioning would not have been required for the task at hand. However, removing fields would break the contract so a new version should be created. This is an important topic because breaking an API contract can result in unexpected consequences in any number of consumers of the API such as javascript frontends, mobile applications, or other backend applications that rely on the API. While it is possible to do a coordinated release together with a consumer, this can complicate the release process in the event that 1 of the applications needs to be rollbacked. Versioning the API allows you to be more independent and flexible in the release process.

