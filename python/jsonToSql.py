'''
Created on 23.11.2017
errors
- ValueError: Mixing dicts with non-Series may lead to ambiguous ordering.


@author: Markus.Walden
'''

from disqus import (
    detailsUseru,
    listActiveForumsu,
    listCategoriesu,
    listFollowingUsersu,
    listFollowingForumsu,
    listFollowingProfilesu,
    listPostsByUseru,
    listThreadByIdu,
    listthreadsByForum,
    since0,
    since1,
    since2,
    since3,
    detailsUserf, 
    engineString_disqus,
    apiKey,
    user
    )

import json
import pandas as pd
import requests
import sqlalchemy as sqla
import sys

#import ijson
from pandas.io.json import json_normalize

class DisqusRequests(object):
    '''
    classdocs
    use json_normalize 
    '''
    def __init__(self):
        '''
        Constructor: initiate connection with the SQL server to insert the data from Disqus
        '''
        self.sql = sqla.create_engine(engineString_disqus)
        
    def detailsUser (self, user = user):
        '''
        list user details
        '''
        df_user_1 = json.loads(requests.get(detailsUseru + \
                                          'api_key='+ apiKey + '&user=username:' + user).text)
        
        df_1 = json_normalize(df_user_1   ['response'])
        return df_1
    
    def listActiveForums (self, user = user):
        '''
        list forums with comments tied to specific user
        '''
        df_forum = json.loads(requests.get(listActiveForumsu +  \
                                           'api_key='+ apiKey + '&user=username:' + user).text)
        return df_forum
    
    def listCategories (self, forum='bloombergview'):
        '''
        list categories assigned to specific forum like bloombergview
        to do visual capitalist
        '''       
        df_category = json.loads(requests.get(listCategoriesu + \
                                                'api_key='+ apiKey + '&forum=' + forum).text)
        
        df_4 = pd.DataFrame.from_dict(df_category ['response']) 
        return df_4
    
    def listFollowingForums (self, user = user):
        '''
        list following forums - forum is a container for blogs.
        '''
        df_forum_2 = json.loads(requests.get(listFollowingForumsu + \
                                                     'api_key='+ apiKey + '&user=username:' + user).text)
        
        df_5 = json_normalize(df_forum_2  ['response'])
        return df_5
    
    def listFollowsUsers (self, user = user):
        '''
        list users assigned as followers
        '''
        df_user_3 = json.loads(requests.get(listFollowingUsersu + \
                                                       'api_key='+ apiKey + '&user=username:' + user).text)
        
        df_3 = json_normalize(df_user_3   ['response'])
        return df_3
    
    def listFollowingUsers (self, user = user):
        '''
        list users assigned as following users
        '''
        df_user_2 = json.loads(requests.get(listFollowingProfilesu + \
                                                    'api_key='+ apiKey + '&user=username:' + user).text)
        
        df_2 = json_normalize(df_user_2   ['response'])
        return df_2
    
    def listPostsByUser (self, since, cursor, user = user):
        '''
        Get posts related to user using since as date.
        Problem with assigning cursor value, marked as dtype: object.
               
        since=2016-03-08T12:17:24&order=asc&limit=100
        '''
        df_posts = json.loads(requests.get(listPostsByUseru + \
                                           'api_key='+ apiKey + '&user=username:' + user + '&' \
                                           'since=' + since + '&order=asc&limit=100').text)

        df_6_cursor = json_normalize(df_posts    ['cursor'])
        df_6        = json_normalize(df_posts    ['response'])
        
        df_6.drop('isSpam', axis = 1, inplace = True)
        df_6.drop('isHighlighted', axis = 1, inplace = True)  
        df_6.drop('isFlagged', axis = 1, inplace = True) 
        df_6.drop('isApproved', axis = 1, inplace = True)
        df_6.drop('isDeleted', axis = 1, inplace = True)  
        df_6.drop('isDeletedByAuthor', axis = 1, inplace = True) 
        df_6.drop('isEdited', axis = 1, inplace = True) 
        df_6.drop('canVote', axis = 1, inplace = True) 
        df_6.drop('createdAt', axis = 1, inplace = True) 
        df_6.drop('moderationLabels', axis = 1, inplace = True) 
        df_6.drop('media', axis = 1, inplace = True) 
        
        return df_6, df_6_cursor
    
    def listThreadById (self, thread = '5874265536'):
        '''
        get threads by post related
        '''
        df_ThreadById = json.loads(requests.get(listThreadByIdu + \
                                                'api_key='+ apiKey + '&thread=' + thread).text)
        
        df_7 = json_normalize(df_ThreadById   ['response'])
        df_7.drop('identifiers', axis = 1, inplace = True)
        
        return df_7
    
    def listThreadByForum(self, forum = 'channel-modelssimulations'):
        '''
        getThreads on a channel
        '''
        df_forum = json.loads(requests.get(listthreadsByForum + \
                                                'api_key='+ apiKey + '&forum=' + forum).text)
        
        df_8 = json_normalize(df_forum   ['response'])
        df_8.drop('identifiers', axis = 1, inplace = True)
        df_8.drop('media', axis = 1, inplace = True)
        
        print (df_8)
        return df_8
    
    def storeStatisticsToSQL(self, dfToStore, tableName = '[geographicNEStat]', reload = False ):
        if reload:
            conn = self.sql.connect()
            trans = conn.begin()
            conn.execute("truncate table [dbo]." + tableName )
            trans.commit()
            conn.close()
            print ('Truncate done, reloading table:')       
        try:
            dfToStore.to_sql(tableName , self.sql, if_exists='append')  
        except:
            print ('Exception type:', sys.exc_info()[0])
            print ('Exception value:', sys.exc_info()[1])
        
def main ():
    info = DisqusRequests()
    #info.storeStatisticsToSQL(info.listThreadByForum(), 'forum_linkedToThreads', reload = False)
    info.storeStatisticsToSQL(info.listCategories(forum = 'channel-modelssimulations'), 'category', reload = False)  
    
def testing():
    info = DisqusRequests()
    queryUsers = "SELECT [username] FROM [disqus].[dbo].[userDetails_associated]"
    df_users = pd.read_sql_query(queryUsers, info.sql)
    for index, row in df_users.iterrows():
        print (index, ': ', row['username']) 
        df_posts, df_cursor = info.listPostsByUser(since=None, cursor='', user = row['username'])        
        scursor = str(df_cursor['next']).replace(' ', '')        
        
        while True:
            info.storeStatisticsToSQL(df_posts, 'posts_linkedToUser_' + row['username'], reload = False)
            df_posts, df_cursor = info.listPostsByUser(since=None, cursor=scursor[1:], user = row['username'])
            scursor = str(df_cursor['next']).replace(' ', '')
            
            if df_cursor['hasNext'] == False:
                info.storeStatisticsToSQL(df_posts, 'posts_linkedToUser_' + row['username'], reload = False)
                break
            print (df_cursor)          

    def getThreads():
        info = DisqusRequests()
        query = "SELECT distinct [forum]   ,[thread] FROM [disqus].[dbo].[posts_linkedToUser] where forum IN ('visualcapitalist', 'bloombergview')"
        df_thread = pd.read_sql_query(query, info.sql)
        for index, row in df_thread.iterrows():
            info.storeStatisticsToSQL(info.listThreadById(row['thread']), 'threads_linkedToPosts', reload = False)
            print (index, ': ', row['thread']) 
    
    def getPosts():    
        '''
        update posts, threads and forums 
        get latest post timestamp / add one
        query posts 
        '''
        info = DisqusRequests()
        df_posts = info.listPostsByUser(since3)
        print (df_posts)
        info.storeStatisticsToSQL(df_posts, 'posts_linkedToUser', reload = False)
    
    # runRecondsIntoDatabase()
    # testJSON()

def runRecondsIntoDatabase():
    info = DisqusRequests()
    info.storeStatisticsToSQL(info.detailsUser(), tableName = 'userDetails', reload = False)
    info.storeStatisticsToSQL(info.listFollowingUsers(), 'userDetails_associated', reload = False)
    info.storeStatisticsToSQL(info.listFollowsUsers(), 'userDetails_associated', reload = False)
    info.storeStatisticsToSQL(info.listCategories(), 'category', reload = False)                    # done
    info.storeStatisticsToSQL(info.listFollowingForums(), 'forums_linkedToUser', reload = False)
    info.storeStatisticsToSQL(info.listPostsByUser(), 'posts_linkedToUser', reload = False)
    info.storeStatisticsToSQL(info.listThreadById(), 'threads_linkedToPosts', reload = False)        
    info.storeStatisticsToSQL(info.listThreadByForum(), 'forum_linkedToThreads', reload = False)

def testJSON():    
    d = json.loads(requests.get(listActiveForumsu + \
                                'api_key=' + apiKey + '&user=username:' + user).text)
    
    data = pd.DataFrame(d["response"])
    df_user = pd.read_json(detailsUserf)
    
    print (list(d.keys()))
    print (data)
    print (df_user)

    #df_norm_user = json_normalize(detailsUserf)
        
if __name__ == "__main__":
    main()
