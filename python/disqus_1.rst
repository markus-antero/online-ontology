Disqus
======
Git
- https://github.com/disqus/mysql-formula
- https://docs.saltstack.com/en/latest/topics/development/conventions/formulas.html
- https://github.com/disqus/django-modeldict
- https://github.com/disqus/django-metaredirect

Main and available public data
- https://disqus.com/api/docs/
- https://help.disqus.com/customer/portal/articles/1131785-available-public-api-data

Connection Strings
------------------

- https://disqus.com/api/3.0/users/listActiveForums.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&user=username:Maquirer
- https://disqus.com/api/3.0/threads/list.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&forum=bloombergview
- https://disqus.com/api/3.0/users/listPosts.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&user=username:Maquirer
- https://disqus.com/api/3.0/threads/list.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&thread=5874265536
- https://disqus.com/api/3.0/categories/list.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&forum=bloombergview
- https://disqus.com/api/3.0/users/details.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&user=username:Maquirer
- https://disqus.com/api/3.0/users/listFollowing.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&user=username:Maquirer
- https://disqus.com/api/3.0/users/listFollowers.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&user=username:Maquirer
- https://disqus.com/api/3.0/users/listFollowingForums.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&user=username:Maquirer

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

programflow
-----------

