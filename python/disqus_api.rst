Disqus
======

Main and available public data
- https://disqus.com/api/docs/
- https://help.disqus.com/customer/portal/articles/1104788-web-integration
- https://help.disqus.com/customer/portal/articles/1131785-available-public-api-data

youtube / wordpress
- https://www.youtube.com/watch?v=rGObWtjxGBc
- https://www.youtube.com/channel/UCHRp19HU7Y2LwfI0Ai6WAGQ

- https://www.youtube.com/watch?v=amF7LLlHfgc

Connection Strings
------------------

- https://disqus.com/api/3.0/users/listActiveForums.json
- https://disqus.com/api/3.0/threads/list.json
- https://disqus.com/api/3.0/users/listPosts.json
	- https://disqus.com/api/3.0/users/listPosts.json
		since = 2016-01-08T21:45:56 + 1
		since = 2016-03-24T10:29:57 + 1
		since = 2017-01-07T21:46:46 + 1
	    
- https://disqus.com/api/3.0/threads/list.json
- https://disqus.com/api/3.0/categories/list.json
- https://disqus.com/api/3.0/users/details.json
- https://disqus.com/api/3.0/users/listFollowing.json
- https://disqus.com/api/3.0/users/listFollowers.json
- https://disqus.com/api/3.0/users/listFollowingForums.json
- https://disqus.com/api/3.0/forums/listThreads.json
	
examples
--------
Returns details of a user.
	
- https://disqus.com/api/docs/users/details/ - https://disqus.com/api/3.0/users/details.json
- Accessibility: public key, secret key
- Supported request methods: GET
- Supported formats: json, jsonp
- Requires authentication: no
- Rate limited: inherits global rate limit
- Arguments / Optional: attach (allows multiple), user
			
Returns a list of posts made by the user.

- User / List posts https://disqus.com/api/docs/users/listPosts/ - https://disqus.com/api/3.0/users/listPosts.json
- Accessibility: public key, secret key
- Supported request methods: GET
- Supported formats: json, jsonp, rss
- Requires authentication: no
- Rate limited: inherits global rate limit	
- arguments / Optional: since. related, cursor, limit, user, filters, include, order.
