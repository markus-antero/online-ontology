Python
======

Will breakdown - non stable, conda stops working.

eclipse - fix interpreter for python projects
	Install missing packages
	
python -m pip install --upgrade pip

- https://pro.arcgis.com/en/pro-app/
- http://geopandas.org/ 
- https://colorlib.com/wp/wordpress-themes-for-programmers/


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

Orange canvas
-------------

MSSQL Driver needed specifically for Orange.canvas 

- conda install -c anaconda pymssql

Related post- https://www.anaconda.com/blog/developer-blog/anaconda-easy-button-microsoft-sql-server-and-python/

Other plugins

- GEO
- TimeSeries

	(C:\python3) C:\Users\Markus.Walden>conda install -c conda-forge geojson
	The following NEW packages will be INSTALLED:

		geojson:   2.3.0-py_0            conda-forge

	The following packages will be SUPERSEDED by a higher-priority channel:

		conda:     4.3.30-py35hec795fb_0 anaconda    --> 4.3.29-py35_0 conda-forge
		conda-env: 2.6.0-h36134e3_1      anaconda    --> 2.6.0-0       conda-forge

	(C:\python3) C:\Users\Markus.Walden>conda install -c conda-forge shapely
	The following NEW packages will be INSTALLED:

		geos:    3.6.2-vc14_1 conda-forge [vc14]
		shapely: 1.6.2-py35_1 conda-forge

Orange init
- (C:\python3) C:\Users\Markus.Walden>python -m Orange.canvas

Conda installation - interesting / usable libraries
---------------------------------------------------

- anaconda                  5.0.1            py36h8316230_2				x
- anaconda-client           1.6.5            py36hd36550c_0				x
- anaconda-navigator        1.6.9            py36hc720852_0				x
- anaconda-project          0.8.0            py36h8b3bf89_0				x
- astropy                   2.0.2            py36h06391c4_4				x
- blaze                     0.11.3           py36h8a29ca5_0				x
- bleach                    2.0.0            py36h0a7e3d6_0				x
- conda                     4.3.30           py36h7e176b0_0				x
- conda-build               3.0.27           py36h309a530_0				x
- conda-env                 2.6.0                h36134e3_1				x
- conda-verify              2.0.0            py36h065de53_0				x
- cryptography              2.0.3            py36h123decb_1				x
- curl                      7.55.1           vc14hdaba4a4_3  [vc14]		x
- cython                    0.26.1           py36h18049ac_0				x
- cytoolz                   0.8.2            py36h547e66e_0				x
- datashape                 0.5.4            py36h5770b85_0				x
- docutils                  0.14             py36h6012d8f_0				x
- html5lib                  0.999999999      py36ha09b1f3_0				x
- intel-openmp              2018.0.0             hcd89f80_7				x
- ipykernel                 4.6.1            py36hbb77b34_0				x
- ipython                   6.1.0            py36h236ecc8_1				x
- ipython_genutils          0.2.0            py36h3c5d0ee_0				x
- ipywidgets                7.0.0            py36h2e74ada_0				x
- jsonschema                2.6.0            py36h7636477_0				x
- jupyter                   1.0.0            py36h422fd7e_2				x
- jupyter_client            5.1.0            py36h9902a9a_0				x
- jupyter_console           5.2.0            py36h6d89b47_1				x
- jupyter_core              4.3.0            py36h511e818_0				x
- jupyterlab                0.27.0           py36h34cc53b_2				x
- jupyterlab_launcher       0.4.0            py36h22c3ccf_0				x
- libssh2                   1.8.0            vc14hcf584a9_2  [vc14]		x
- libxml2                   2.9.4            vc14h8fd0f11_5  [vc14]		x
- libxslt                   1.1.29           vc14hf85b8d4_5  [vc14]		x
- matplotlib                2.1.0            py36h11b4b9c_0				x
- networkx                  2.0              py36hff991e3_0				x
- numpy                     1.13.3           py36ha320f96_0				x
- numpydoc                  0.7.0            py36ha25429e_0				x
- openssl                   1.0.2l           vc14hcac20b0_2  [vc14]		x
- pandas                    0.20.3           py36hce827b7_2				x
- pycodestyle               2.3.1            py36h7cc55cd_0				x
- pycurl                    7.43.0           py36h086bf4c_3				x
- pyodbc                    4.0.17           py36h0006bc2_0				x
- pyopenssl                 17.2.0           py36h15ca2fc_0				x
- scikit-image              0.13.0           py36h6dffa3f_1				x
- scikit-learn              0.19.1           py36h53aea1b_0				x
- scipy                     0.19.1           py36h7565378_3				x
- seaborn                   0.8.0            py36h62cb67c_0				x
- sphinx                    1.6.3            py36h9bb690b_0				x
- sphinxcontrib             1.0              py36hbbac3d2_1				x
- sphinxcontrib-websupport  1.0.1            py36hb5e5916_1				x
- sqlalchemy                1.1.13           py36h5948d12_0				x
- sqlite                    3.20.1           vc14h7ce8c62_1  [vc14]		x
- statsmodels               0.8.0            py36h6189b4c_0				x
- sympy                     1.1.1            py36h96708e0_0				x
- urllib3                   1.22             py36h276f60a_0				x
- yaml                      0.1.7            vc14hb31d195_1  [vc14]		x


ARCGIS
------

(C:\python) C:\Users\Markus.Walden>conda install -c esri arcgis
Fetching package metadata .................
Solving package specifications: .

Package plan for installation in environment C:\python:

The following NEW packages will be INSTALLED:

    - arcgis:                        1.2.4-py36_1           esri
    - backports.functools_lru_cache: 1.4-py36_1             conda-forge
    - kerberos-sspi:                 0.2-py36_0
    - krb5:                          1.14.2-vc14_0          conda-forge [vc14]

The following packages will be UPDATED:

    - anaconda:                      5.0.1-py36h8316230_2               --> custom-py36h363777c_0
    - hdf5:                          1.10.1-vc14hb361328_0              --> 1.10.1-vc14_1         conda-forge [vc14]
    - jpeg:                          9b-vc14h4d7706e_1                  --> 9b-vc14_2             conda-forge [vc14]
    - libxml2:                       2.9.4-vc14h8fd0f11_5               --> 2.9.5-vc14_1          conda-forge [vc14]
    - matplotlib:                    2.1.0-py36h11b4b9c_0               --> 2.1.0-py36_1          conda-forge
    - pillow:                        4.2.1-py36hdb25ab2_0               --> 4.3.0-py36_1          conda-forge
	
MongoDB, MariaDB, MSSQLDB
-------------------------

'mssql+pyodbc://markus:Kukkuu!@localhost:1433/finance?driver=SQL+Server+Native+Client+11.0'

Mongo DB - conda install -c anaconda pymongo 

	- License: Apache
	- Home: http://github.com/mongodb/mongo-python-driver
	- Development: https://github.com/mongodb/mongo-python-driver
	- Documentation: http://api.mongodb.org/python/
	- 8158 total downloads 

Disqus
------
- pip install -i https://pypi.anaconda.org/pypi/simple disqus-python
- https://disqus.com/api/3.0/trends/listThreads.json?api_key=N6T89OylJj7sP3NGjkMNtqQkbdsZT6LjXpJf4LGCbFcVros2RhaesPsZ7iPiUmgg&callback=foo
