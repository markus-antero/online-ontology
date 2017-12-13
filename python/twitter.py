'''
Created on 2.12.2017
- https://marcobonzanini.com/2015/03/02/mining-twitter-data-with-python-part-1/
@author: Markus.Walden
'''
import tweepy
from tweepy import OAuthHandler
 
class MyClass(object):
    '''
    classdocs
    '''
    def __init__(self, params):
        '''
        Constructor
        '''   
        
def main():
    consumer_key = 'YOUR-CONSUMER-KEY'
    consumer_secret = 'YOUR-CONSUMER-SECRET'
    access_token = 'YOUR-ACCESS-TOKEN'
    access_secret = 'YOUR-ACCESS-SECRET'
     
    auth = OAuthHandler(consumer_key, consumer_secret)
    auth.set_access_token(access_token, access_secret)
     
    api = tweepy.API(auth)

if __name__ == '__main__':
    main()