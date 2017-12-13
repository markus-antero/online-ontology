Site details
============

Wordpress team development
--------------------------
- https://gist.github.com/waqasjamal/7428185	wordpress / python - post upload
- http://spark.apache.org/docs/latest/
- http://orange-bioinformatics.readthedocs.io/en/latest/
- https://github.com/olefredrik/FoundationPress
- https://foundation.zurb.com/sites.html
- https://understrap.com/
- https://www.lyrathemes.com/bootstrap-wordpress-theme-tutorial-1/
- https://code.tutsplus.com/series/how-theme-frameworks-actually-work--cms-713
- https://wpshout.com/wordpress-theme-development/
- https://www.youtube.com/watch?v=RV68DzVu7iE

The project architecture is visualized:

.. image:: images/UI.jpg

The plugins currently in use: 
-----------------------------

- Admin Code Editor
Easily add additional HTML, CSS, and JS to a post. Features colour highlighted text, line numbers, tab indents, and more. SCSS, LESS support too.

- Contact Form 7	
Contact Form 7 - Just another contact form plugin. Simple but flexible.
	
- Disqus Comment System
The Disqus comment system replaces your WordPress comment system with your comments hosted and powered by Disqus. Head over to the Comments admin page to set up your Disqus Comment System.

- Embed Webmap
Embed a public webmap from ArcGIS Online into WordPress with a shortcode. http://gavinr.com/embed-webmap-plugin for help and details.

- FameTheme Demo Importer
Demo data import tool for FameThemes's themes.

- Guaven SQL Charts
Turn your SQL queries to Google Charts

- Inline Google Spreadsheet Viewer
Retrieves data from a public Google Spreadsheet or CSV file and displays it as an HTML table or interactive chart. Like this plugin? Please donate. ♥ Thank you!

- Really Simple SSL
Lightweight plugin without any setup to make your site SSL-proof

-  Schema
The next generation of Structured Data.

- Simple Links
Replacement for the old WordPress Links Manager with many added features.
	
- Theme Editor
create, edit, upload, download, delete Theme Files and folders

- Timeline Express
Create a beautiful vertical, CSS3 animated and responsive timeline in minutes flat without writing code.

- Vectr
Vectr description
	
- What The File
What The File adds an option to your toolbar showing what file and template parts are used to display the page you’re currently viewing. You can click the file name to directly edit it through the theme editor. Supports BuddyPress and Roots Theme. More information can be found at the WordPress plugin page.
	
- Wordfence Security
Wordfence Security - Anti-virus, Firewall and Malware Scan
	
- WP Statistics
Complete WordPress Analytics and Statistics for your site!

- WP Ultimate CSV Importer
Seamlessly create posts, custom posts, pages, media, SEO and more from your CSV data with ease.

The pluggins installed:
-----------------------

- Advanced Database Cleaner
Clean database by deleting unused data such as 'old revisions', 'old drafts', 'orphan options', etc. Optimize database and more.

- ARI Adminer
Powerful, compact and easy to use database manager plugin for WordPress.

- Black Studio TinyMCE Widget
Adds a new "Visual Editor" widget type based on the native WordPress TinyMCE editor.

- Google Analytics Dashboard for WP (GADWP)
Displays Google Analytics Reports and Real-Time Statistics in your Dashboard. Automatically inserts the tracking code in every page of your website.

- Read a Poem - Month by Month
Use this plugin to display content that changes each month. Assign each post(poem) to a month then use the shortcode [poem-current]

-  Wordpress File Upload
Simple interface to upload files from a page. 

- WP Document Revisions
A document management and version control plugin for WordPress that allows teams of any size to collaboratively edit files and manage their workflow.

- wp-linked-data
Publishing blog contents as linked data Version 0.3.

- WP REST API
JSON-based REST API for WordPress, originally developed as part of GSoC 2013.

Disqus Comment System
---------------------
Python installation 

- pip install -i https://pypi.anaconda.org/pypi/simple disqus-python

Main pages:

- https://disqus.com/api/docs/
- https://help.disqus.com/customer/portal/articles/684744
- https://help.disqus.com/customer/portal/articles/236206
- https://help.disqus.com/customer/en/portal/articles/1264625-getting-started

MongoDB, MariaDB, MSSQLDB
-------------------------

Mongo DB - conda install -c anaconda pymongo 

- Home: http://github.com/mongodb/mongo-python-driver
- Development: https://github.com/mongodb/mongo-python-driver
- Documentation: http://api.mongodb.org/python/


Embed Webmap
------------
- https://gavinr.com/
- https://www.arcgis.com/home/item.html?id=6e084bb378aa4837bee0557052fae3cb

Two options to integrate:
- Build with plugin from ARCGIS maps
- Embed html 

Embed webmap blugin (embed [webmap id="52475e6edb18471780858627b40460c2"]) from source URL ( https://developers.arcgis.com/applications/15677178af9a4808961d957cc21064d3 ).
	
1. plugin

	- id: The webmap ID of the map you wish to embed. Get this from the URL bar on arcgis.com. See the “Screenshots” page for help. Example: [webmap id=”52475e6edb18471780858627b40460c2″]
	- extent: in the “shortened” form. Use http://psstl.esri.com/apps/extenthelper/ for help. Example: [webmap extent=”-159.3635,7.093,-45.8967,63.7401″]
	- height: specify the height, in pixels. Example: [webmap height=”600″]
	- width: specify the width, in pixels. Example: [webmap width=”230″]
	- zoom: Include zoom buttons. Example: [webmap id=”52475e6edb18471780858627b40460c2″ zoom]
	- home: Include a home button. If this is included, the zoom buttons will automatically be included.  Example: [webmap id=”52475e6edb18471780858627b40460c2″ home]
	- scale: Include a scale bar. Example: [webmap scale id=”52475e6edb18471780858627b40460c2″]
	- legend: Include a legend button. Example: [webmap id=”52475e6edb18471780858627b40460c2″ legend]
	- search: Include a location search textbox. Example: [webmap id=”52475e6edb18471780858627b40460c2″ search]
	- searchextent: When using ‘search’ (above), only search the current extent. Example: [webmap id=”52475e6edb18471780858627b40460c2″ search searchextent]
	- basemap_toggle: Show a basemap toggle. Must also include ‘alt_basemap’ to indicate alternate basemap that will show when the toggle button is clicked. Example: [webmap id=”52475e6edb18471780858627b40460c2″ basemap_toggle alt_basemap=”streets”]
	- Basemap_gallery: Include a basemap menu. [webmap id=”52475e6edb18471780858627b40460c2″ basemap_gallery]
	- view-larger-link: Include a link below the map to view the map in a larger window. Example: [webmap id=”52475e6edb18471780858627b40460c2″ view-larger-link]
	- disable_scroll: When the mouse is over the map, the scroll will not zoom the map if this is added. Example: [webmap id=”52475e6edb18471780858627b40460c2″ 
	- disable_scroll]: details – Show the map details pane. Example: [webmap id=”52475e6edb18471780858627b40460c2″ details]

2. webmap as html

	<style>.embed-container {position: relative; padding-bottom: 80%; height: 0; max-width: 100%;} .embed-container iframe, .embed-container object, .embed-container iframe{position: absolute; top: 0; left: 0; width: 100%; height: 100%;} small{position: absolute; z-index: 40; bottom: 0; margin-bottom: -15px;}</style><div class="embed-container"><small><a href="//nedlaw.maps.arcgis.com/apps/Embed/index.html?webmap=21dee994a8f64f2785fbcfeae01ad99a&amp;extent=-180,-49.1324,180,82.6354&amp;home=true&amp;zoom=true&amp;scale=true&amp;search=true&amp;searchextent=true&amp;basemap_gallery=true&amp;disable_scroll=true&amp;theme=light" style="color:#0000FF;text-align:left" target="_blank">View larger map</a></small><br><iframe width="500" height="400" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" title="city_stock_country" src="//nedlaw.maps.arcgis.com/apps/Embed/index.html?webmap=21dee994a8f64f2785fbcfeae01ad99a&amp;extent=-180,-49.1324,180,82.6354&amp;home=true&amp;zoom=true&amp;previewImage=false&amp;scale=true&amp;search=true&amp;searchextent=true&amp;basemap_gallery=true&amp;disable_scroll=true&amp;theme=light"></iframe></div>
