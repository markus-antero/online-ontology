Disqus
======

twitter
- https://marcobonzanini.com/2015/03/02/mining-twitter-data-with-python-part-1/

SSH
- https://serversforhackers.com/c/ssh-tricks
- https://serversforhackers.com/c/connecting-to-mysql-via-ssh

RDF
- http://www.mkbergman.com/414/large-scale-rdf-graph-visualization-tools/
- http://philogb.github.io/jit/index.html
- http://schema.org/docs/gs.html
- https://coffeecode.net/rdfa/codelab/
- https://rdfa.info/
- https://json-ld.org/
- https://developers.google.com/search/docs/guides/intro-structured-data

Git
- https://github.com/disqus/mysql-formula
- https://docs.saltstack.com/en/latest/topics/development/conventions/formulas.html
- https://github.com/disqus/django-modeldict
- https://github.com/disqus/django-metaredirect

- https://github.com/mozilla/lightbeam/blob/master/index.html
- https://github.com/tesseract-ocr

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

- https://disqus.com/api/3.0/users/listActiveForums.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&user=username:Maquirer
- https://disqus.com/api/3.0/threads/list.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&forum=bloombergview
- https://disqus.com/api/3.0/users/listPosts.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&user=username:Maquirer
	- https://disqus.com/api/3.0/users/listPosts.json?
	  api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&user=username:Maquirer&since=2016-03-08T12:17:24&order=asc&limit=100
		- since = 2016-01-08T21:45:56 + 1
		- since = 2016-03-24T10:29:57 + 1
		- since = 2017-01-07T21:46:46 + 1
	    
- https://disqus.com/api/3.0/threads/list.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&thread=5874265536
- https://disqus.com/api/3.0/categories/list.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&forum=bloombergview
- https://disqus.com/api/3.0/users/details.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&user=username:Maquirer
- https://disqus.com/api/3.0/users/listFollowing.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&user=username:Maquirer
- https://disqus.com/api/3.0/users/listFollowers.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&user=username:Maquirer
- https://disqus.com/api/3.0/users/listFollowingForums.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&user=username:Maquirer
- https://disqus.com/api/3.0/forums/listThreads.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&forum=channel-modelssimulations

Series: 0    1347495699883391:0:0
Name: next, dtype: object
	
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

