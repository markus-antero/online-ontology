# -*- coding: latin-1 -*-
'''
Created on 29.11.2017
@author: Markus.Walden
'''

from disqus import (
    engineString_finance,
    engineString_disqus,
    ssh_server,
    ssh_mysql,
    ssh_user,
    ssh_password,
    mysqlUser,
    mysqlPass,
    mysqldb,
    db_root,
    db_finance_cities,
    db_finance_companyStatistics,
    db_finance_GeographicNE,
    db_finance_geographicNE_Stat,
    db_finance_SandP500_index_data,
    db_finance_SandP500GigsClassifier,
    db_finance_SandP500history,
    db_finance_SandP500index,
    qUserAssoc,
    qUser,
    qFilterPosts,
    qForumDetails,
    qThreads,
    qCompanyStatistics
)

import sqlalchemy as sqla
import pandas as pd
import sys
import pymysql
from sshtunnel import SSHTunnelForwarder, create_logger
import paramiko
from pandas.io import sql

class PopulateFinanceDatabase(object):
    '''
    classdocs
    Link SQL to Wordpress
    '''
    def __init__(self, setRemotePipe = False):
        '''
        Constructor
        manual: https://pypi.python.org/pypi/sshtunnel
        '''
        if setRemotePipe :
            self.server = SSHTunnelForwarder(
                (ssh_server, 22)
                ,ssh_username = ssh_user 
                ,ssh_password = ssh_password
                ,remote_bind_address=(ssh_mysql, 3306)
                ,logger=create_logger(loglevel=1)
                )    
            self.server.start()
            connection_data = 'mysql+pymysql://{user}:{password}@{host}:{port}/{db}'.format(user=mysqlUser,
                                                                                     password=mysqlPass,
                                                                                     host='127.0.0.1',
                                                                                     port= self.server.local_bind_port,
                                                                                     db=mysqldb)
            self.sqlRemote = sqla.create_engine(connection_data)
        
        self.sqlLocal_finance = sqla.create_engine(engineString_finance)
        self.sqlLocal_disqus = sqla.create_engine(engineString_disqus)
    
    def SQLQueryToDataframe (self, query, engine, schemaName):
        df = pd.read_sql_query(query, engine)
        return df, sql.get_schema(df, name = schemaName)
    
    def SQLTableToDataframe (self, table, engine, schemaName):
        df = pd.read_sql_table(table, engine)
        return df, sql.get_schema(df, name = schemaName)
    
    def storeStatisticsToSQL(self, dfToStore, tableName = '[geographicNEStat]', reload = False ):
        if reload:
            conn = self.sqlRemote.connect()
            trans = conn.begin()
            conn.execute("truncate table [dbo]." + tableName )
            trans.commit()
            conn.close()
            print ('Truncate done, reloading table:')       
        try:
            dfToStore.to_sql(tableName , self.sqlRemote, if_exists='append')
        except:
            print ('Exception type:', sys.exc_info()[0])
            print ('Exception value:', sys.exc_info()[1])

def main ():
    '''
    The remote DB connection is not available
    info.storeStatisticsToSQL(df_statistics, tableName = 'CompanyStatistics', reload = False)
    '''
#    client = testSSH()
#    client.close()

    setRemotePipe = True
    info = PopulateFinanceDatabase(setRemotePipe)
    
    df_1, _ = info.SQLQueryToDataframe(query = qUserAssoc, engine = info.sqlLocal_disqus, schemaName = 'UserAssoc')
    info.storeStatisticsToSQL(df_1, 'user', False)
    
    if setRemotePipe:
        info.server.stop()

#    generateCSVData()
    
def generateCSVData(info = PopulateFinanceDatabase(setRemotePipe = False)):        
    f = open(db_root + 'workfile', 'w')
    
    df_1, schema = info.SQLQueryToDataframe(query = qUserAssoc, engine = info.sqlLocal_disqus, schemaName = 'UserAssoc')
    f.write(schema)
    df_1.to_csv(db_root + 'qUserAssoc.csv', sep = '¤')
    
    df_2, schema = info.SQLQueryToDataframe(query = qUser, engine = info.sqlLocal_disqus, schemaName = 'User')
    f.write(schema)
    df_2.to_csv(db_root + 'User.csv', sep = '¤')
    
    df_3, schema = info.SQLQueryToDataframe(query = qFilterPosts, engine = info.sqlLocal_disqus, schemaName = 'FilterPosts')
    f.write(schema)
    df_3.to_csv(db_root + 'FilterPosts.csv', sep = '¤')
    
    df_4, schema = info.SQLQueryToDataframe(query = qForumDetails, engine = info.sqlLocal_disqus, schemaName = 'ForumDetails')
    f.write(schema)
    df_4.to_csv(db_root + 'ForumDetails.csv', sep = '¤')
    
    df_5, schema = info.SQLQueryToDataframe(query = qThreads, engine = info.sqlLocal_disqus, schemaName = 'Threads')
    f.write(schema)
    df_5.to_csv(db_root + 'Threads.csv', sep = '¤')
        
    df1, schema = info.SQLTableToDataframe(table = db_finance_cities, engine = info.sqlLocal_finance, schemaName = 'cities')
    f.write(schema)
    df1.to_csv(db_root + 'finance_cities.csv', sep = '¤')
    
    df2, schema = info.SQLTableToDataframe(table = db_finance_companyStatistics, engine = info.sqlLocal_finance, schemaName = 'companyStatistics')
    f.write(schema)
    df2.to_csv(db_root + 'companyStatistics.csv', sep = '¤')
    
    df3, schema = info.SQLTableToDataframe(table = db_finance_GeographicNE, engine = info.sqlLocal_finance, schemaName = 'GeographicNE')
    f.write(schema)
    df3.to_csv(db_root + 'GeographicNE.csv', sep = '¤')
    
    df4, schema = info.SQLTableToDataframe(table = db_finance_geographicNE_Stat, engine = info.sqlLocal_finance, schemaName = 'geographicNE_Stat')
    f.write(schema)
    df4.to_csv(db_root + 'geographicNE_Stat.csv', sep = '¤')
    
    df5, schema = info.SQLTableToDataframe(table = db_finance_SandP500_index_data, engine = info.sqlLocal_finance, schemaName = 'SandP500_index_data')
    f.write(schema)
    df5.to_csv(db_root + 'SandP500_index_data.csv', sep = '¤')
    
    df6, schema = info.SQLTableToDataframe(table = db_finance_SandP500GigsClassifier, engine = info.sqlLocal_finance, schemaName = 'SandP500GigsClassifier')
    f.write(schema)
    df6.to_csv(db_root + 'SandP500GigsClassifier.csv', sep = '¤')
    
    df7, schema = info.SQLTableToDataframe(table = db_finance_SandP500history, engine = info.sqlLocal_finance, schemaName = 'SandP500history')
    f.write(schema)
    df7.to_csv(db_root + 'SandP500history.csv', sep = '¤')
    
    df8, schema = info.SQLTableToDataframe(table = db_finance_SandP500index, engine = info.sqlLocal_finance, schemaName = 'SandP500index')
    f.write(schema)
    df8.to_csv(db_root + 'SandP500index.csv', sep = '¤')
    
    f.close()  
    print ('Files are stored: ', db_root)  
    return db_root
    
def testSSH():
    client = paramiko.SSHClient()
    client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    client.connect(hostname = ssh_server, username = ssh_user, password = ssh_password)
    stdin, stdout, stderr = client.exec_command('ifconfig')
        
    print ('stdin: ', stdin)
    print ('stdout: ', stdout.readlines())
    print ('stderr: ', stderr.readlines())
    
    return client    
    
if __name__=='__main__':
    main()
      