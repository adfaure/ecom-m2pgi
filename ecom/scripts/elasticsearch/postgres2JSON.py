#!/usr/bin/python
# -*- coding: utf-8 -*-

import psycopg2
import sys


con = None

try:
     
    con = psycopg2.connect(database='ecom', user='postgres', password="postgres", host='localhost', port='5432')
    cur = con.cursor()
    cur.execute('SELECT * FROM photo')          

    while True:
      
        row = cur.fetchone()
        
        if row == None:
            break
            
        print "{\"index\":{\"_index\":\"ecom\", \"_type\":\"photo\", \"_id\":\"%s\"}}\n{\"description\":\"%s\", \"name\":\"%s\", \"location\":\"%s\"}" % (row[0], row[2], row[3], row[4])

except psycopg2.DatabaseError, e:
    print 'Error %s' % e    
    sys.exit(1)
    
    
finally:
    
    if con:
        con.close()
