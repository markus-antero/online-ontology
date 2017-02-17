'''
Created on 16.2.2017

@author: Markus.Walden
'''
import datetime as dt
import matplotlib.pyplot as plt
from matplotlib import style
import pandas as pd
import pandas_datareader.data as web
import sqlalchemy as sqla
from yahoo_finance import Share
import re
import sys

style.use('ggplot')

class LoadDataFromSQL(object):
    '''
    classdocs
    Main queries
        retrieve_company_IDs = id query
        retrieve_company_data = data query
        missing_records = records not working
        
    Main goal is to load Id and data to pandas objects from SQL datasource
    
    visualize
        Correlation 
        Covariance 
    
    Linking 
        retrieve_company_IDs = self._df_symbols (dataFrame)
        retrieve_company_data = self.ts_Stockdata (timeSeries)
        companyStatistics = self.df_company (dataFrame)
         
    on the stocks data
    '''
    retrieve_company_IDs = ''' SELECT top(10) [symbol], [dateAdded], [CIK], [security] FROM [dbo].[SandP500Index] '''
    
    retrieve_company_data = '''SELECT [Date], [Open], [High], [Low], [Close], [Volume], [Adj Close], [100ma] 
                            FROM [dbo].[SandP500_index_data] as S_data 
                                    inner join [dbo].[SandP500Index] as S  on S_data.symbol = S.symbol 
                            where S_data.symbol like :symbol order by S_data.[symbol], [Date] ''' # S_data.[symbol], 

    companyStatistics = ''' SELECT [marketCapital], [bookValue], [ebitda], [dividentShare], [DividentYield], [earningShare]
                            ,[BookPrice], [SalesPrice], [earningsGrowth], [earningsRatio], [symbol], [date]
                            FROM [finance].[dbo].[companyStatistics]
                            where marketCapital > -1 '''

    
    missing_records = '''SELECT c.symbol, c.security
                        FROM [finance].[dbo].[companyStatistics] as d 
                                right join [finance].[dbo].[SandP500Index] as c on d.symbol = c.symbol 
                        where c.symbol not in (select symbol from [finance].[dbo].[companyStatistics]) '''

    sql = sqla.create_engine('mssql+pyodbc://markus:Kukkuu!@localhost:1433/finance?driver=SQL+Server+Native+Client+11.0')

    def __init__(self, df_symbols = pd.DataFrame(), ts_Stockdata = [], df_company = pd.DataFrame(), params=[]):
        '''
        Constructor
        '''
        self._df_symbols = df_symbols
        self.ts_Stockdata = ts_Stockdata
        self.df_company = df_company
        
    def to_dict(self):
        return {
            "symbols":self._df_symbols,
        }
    
    def __repr__(self):
        '''
        eval to turn back to dictionary
        '''
        return str(self.to_dict())    
    
    @classmethod
    def retrieve_ALLCompanyData (cls):
        '''
        Loading company data - returning data 
        for specific id, type symbol - for all, type %
        
        Make stocksData to 
        '''
        query = cls.retrieve_company_IDs
        df_companyIds = pd.read_sql_query(query, cls.sql)
        
        listStocks=[]
        query = sqla.text(cls.retrieve_company_data)
        for item in df_companyIds['symbol']:
            df_companyStock = pd.read_sql_query(query, cls.sql,  params={'symbol': item})
            ts = df_companyStock.set_index('Date')
            listStocks.append(ts)
       
        query = cls.companyStatistics
        df_companyStat = pd.read_sql_query(query, cls.sql)
        print ("statistics loaded")
        
        return df_companyIds, listStocks, df_companyStat
        
        
class LoadDataFromYahoo(LoadDataFromSQL):
    '''
    main metrics 
        Start time
        End time
        List to transfer (ID)
    Main goal is to update data in the SQL datasource
    
    SELECT distinct year =  DATEPART (yyyy,DATEADD(day,1 , max([Date]))),
                     month =  DATEPART (mm,DATEADD(day,1 , max([Date]))),
                     day =  DATEPART (dd,DATEADD(day,1 , max([Date]))),
                     date = DATEADD(day,1 , max([Date]))
                     FROM [finance].[dbo].[SandP500_index_data] where symbol like '%'
    '''
    
    latestUpload = '''SELECT distinct date = DATEADD(day,1 , max([Date]))
                     FROM [finance].[dbo].[SandP500_index_data] where symbol like '%'  '''
    
    symbols = pd.DataFrame() 
    start = dt.datetime(2000,1,1) 
    end = dt.datetime(2016,12,31)   
        
    def __init__(self, symbol ='', marketCap = 0.0, bookValue = 0.0, ebitda = 0.0, dividentShare = 0.0, dividentYield = 0.0, earningShare = 0.0,
                 bookPrice = 0.0, salesPrice = 0.0, earningsGrowth = 0.0, earningRatio = 0.0, date = dt.datetime.now().strftime('%Y-%m-%d %H:%M:%S'), params=[]): 
        '''
        Constructor
        '''
        super().__init__()
        self.symbol = symbol
        self.marketCap = marketCap
        self.bookValue = bookValue
        self.ebitda = ebitda
        self.dividentShare = dividentShare
        self.dividentYield = dividentYield
        self.earningShare = earningShare
        self.bookPrice = bookPrice
        self.salesPrice = salesPrice
        self.earningGrowth = earningsGrowth
        self.earningRation = earningRatio
        self.date = date

    def to_dict(self):
        '''
        Returns dictionary object
        '''
        return {
            'marketCapital': self.markeCap,        
            'bookValue': self.bookValue,
            'ebitda': self.ebitda,                                                                      # yahoo.get_ebitda(),
            'dividentShare': self.dividentShare,
            'DividentYield': self.dividentYield,
            'earningShare': self.earningsShare,
            'BookPrice': self.bookPrice,
            'SalesPrice': self.salesPrice,
            'earningsGrowth': self.earningGrowth,
            'earningsRatio': self.earningRatio,
            'symbol': self.symbol,
            'date': self.date,
        }
    
    def __repr__(self):
        '''
        eval to turn back to dictionary
        '''
        return str(self.to_dict())    
    
    @classmethod
    def getDataFromYahoo(cls, load_csv = False, reload = False, tableNameA = "SandP500_index_data", 
                         start = dt.datetime(2000,1,1), end = dt.datetime.today()):
        ''' 
            * read symbols from SQL 
            * Create a virtual table using sql alchemy
            * form into class
        '''
        cls.start = start
        cls.end = end
        
        if load_csv :
            df_smp = pd.read_csv('sAndP500.csv', sep = ';', encoding='latin-1') # (df_smp['symbol']  
        else:
            df_smp = cls.retrieveCompanyIds()
        
        if reload:
            conn = cls.sql.connect()
            trans = conn.begin()
            conn.execute("truncate table [dbo].[SandP500_index_data]")
            trans.commit()
            conn.close()
            print ('Truncate done, reloading table:')
         
        for item in df_smp['symbol']:
            df = web.DataReader(item, 'yahoo', cls.start, cls.end)
            df ['100ma'] = df['Adj Close'].rolling(window = 100, min_periods = 0).mean()
            df ['symbol'] = item
            df.to_sql(tableNameA, cls.sql, if_exists='append')   
            print (item)         
        print ('done - stocks data loaded')  

    @classmethod
    def retrieveCompanyIds (cls ):
        '''
        Load company Id:s
        Runs a query: 'SELECT [symbol],[dateAdded], [CIK] FROM [dbo].[SandP500Index]' to pandas dataFrame
        
        remaining fields: [security], [GICS_Sector], [GICS_subIndustry], [address], 
        '''
        query = cls.retrieve_company_IDs
        df_company = pd.read_sql_query(query, cls.sql)
        return df_company
    
    @classmethod        
    def loadAllCompanyDetailsToSQL(cls, reload = False, databaseTable = 'companyStatistics', errorTable = 'errorStatistics'):
        df = cls.retrieveCompanyIds()
        
        companies = []
        df_errorCompanies = {}
        for item in df['symbol']:
            c, df_error, e = cls.loadKeyStatistics(companyID = item)
            if e == None:
                companies.append(c)
            else:
                df_errorCompanies.update({item: df_error})
            print ('Symbol: ' + item)
        print ('done')
        
        if reload:
            conn = cls.sql.connect()
            trans = conn.begin()
            conn.execute("truncate table [dbo].[" + databaseTable + "]")
            trans.commit()
            conn.close()
        
        df_c = pd.DataFrame.from_records([l.to_dict() for l in companies])
        df_c.to_sql(databaseTable, cls.sql, if_exists='append')
        
        return df_errorCompanies
    
    @classmethod
    def loadKeyStatistics (cls, companyID = 'A'):
        '''
        dataset 
        df= pd.DataFrame(columns=['marketCapital','bookValue','ebitda','dividentShare','DividentYield','earningShare',
                                  'BookPrice','SalesPrice','earningsGrowth','earningsRatio', 'symbol', 'date'])
        '''
        yahoo = Share(companyID)
        print (type (yahoo))
        yahoo.refresh()
       
        try:    
            a = re.search('[a-zA-Z]+', yahoo.get_market_cap())
            b = re.search('[a-zA-Z]+', yahoo.get_ebitda())
    
            if a.group(0) is not None:
                p = re.split('[a-zA-Z]+', yahoo.get_market_cap())
                if a.group(0) in 'B':
                    marketCap = float(p[0]) * 10 ** 9
                elif a.group(0) in 'M':
                    marketCap = float(p[0]) * 10 ** 6
                else: 
                    marketCap = -1
                print ('Market cap: ' + yahoo.get_market_cap())
            else:
                marketCap = yahoo.get_market_cap()
        
            if b.group(0) is not None:    
                p = re.split('[a-zA-Z]+', yahoo.get_ebitda())
                if b.group(0) in 'B':
                    ebitda = float(p[0]) * 10 ** 9
                elif b.group(0) in 'M':
                    ebitda = float(p[0]) * 10 ** 6
                else: 
                    ebitda = -1
                
                print ('Ebitda: ' +yahoo.get_ebitda())
            else:
                ebitda =  yahoo.get_ebitda()
    
        except (TypeError, AttributeError):
            print ('Missing :' + companyID)
            e = sys.exc_info()[0]
            print( "<p>Error: %s</p>" % e )
            ebitda = -1.0
            marketCap = -1.0
        
        try:
            company = LoadDataFromYahoo(symbol = companyID, marketCap = marketCap, bookValue = float(yahoo.get_book_value()), ebitda = ebitda, 
                                   dividentShare = float(yahoo.get_dividend_share()), dividentYield = float(yahoo.get_dividend_yield()), earningShare = float(yahoo.get_earnings_share()),
                                   bookPrice = float(yahoo.get_price_book()), salesPrice = float(yahoo.get_price_sales()), earningsGrowth = float(yahoo.get_price_earnings_growth_ratio()),
                                   earningRatio = float(yahoo.get_price_earnings_ratio()))
            
            return company, yahoo, None
        except :
            print ('Missing :' + companyID)
            e = sys.exc_info()[0]
            print( "<p>Error: %s</p>" % e )
            return None, yahoo, e
      
class StocksStatistics(object):
    index_performance = ''' SELECT [year] ,[indexChange]  ,[totalAnnualReturn] FROM [finance].[dbo].[SandP500history] '''

    def __init__(self):
        print ("undone")
        
def main ():
    '''
    Update S&p index
    
    update stocks data: 
        query latestUpload, add one day, store to datetime object, provide as start Date,
        set end date as current date 

    update company statistics, add date
    
    Load information into dataFrame and timeseries
    
    Make correlation and comparison
    '''
    runStockData = False
    runCompanyStat = False
    
    engine = LoadDataFromSQL.sql
    conn = engine.connect()
    start = conn.execute(LoadDataFromYahoo.latestUpload).fetchone()
    conn.close()
    
    yahoo = LoadDataFromYahoo()
    if runStockData:
        yahoo.getDataFromYahoo(load_csv = False, reload = False, tableNameA = "SandP500_index_data", start = start)
    
    if runCompanyStat:
        '''
        handle error records
        '''
        errorRecords = yahoo.loadAllCompanyDetailsToSQL()
        print (errorRecords) 
    
    df_companyIds, ts_companyStock, df_companyStat = LoadDataFromSQL.retrieve_ALLCompanyData()
    data = LoadDataFromSQL(df_symbols = df_companyIds, ts_Stockdata = ts_companyStock, df_company = df_companyStat)
    
    for i in ts_companyStock:
        rolmean = pd.rolling_mean(i, window=12)
        rolstd = pd.rolling_std(i, window=12)
        
       # orig = plt.plot(i, color='blue')
        mean = plt.plot(rolmean, color='red')
        std = plt.plot(rolstd, color='black')
        
        plt.legend(loc='best')
        plt.title('Rolling Mean & Standard Deviation')
        plt.show()
        print ("test")

if __name__ == '__main__':
    main()