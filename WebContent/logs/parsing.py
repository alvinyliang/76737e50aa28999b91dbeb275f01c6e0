
'''
Test config id sample: 11111
Position| Representation 		| (0 for general case, 1 for special case)
0		| Scaling 				| 0 with, 1 without
1		| protocol	 			| 0 for HTTP, 1 for HTTPS
2		| number of threads 	| 0 for 10, 1 for 1
3		| prepared statement 	| 0 with, 1 without
4		| connection pooling 	| 0 with, 1 without



All test cases


Single-instance cases 
	(via ​http://INSTANCE1_PUBLIC_IP:8080/fabflix):

id: 10000
	Use HTTP, 10 threads in JMeter.
id: 10100
	Use HTTP, 1 thread in JMeter.
id: 11000
	Use HTTPS, 10 threads in JMeter.
id: 10010
	Use HTTP, without using prepared statements, 10 threads in JMeter.
id: 10001
	Use HTTP, without using connection pooling, 10 threads in JMeter.

Scaled-version cases 
	(via ​http://INSTANCE1_PUBLIC_IP:80/fabflix):

id: 00000
	Use HTTP, 10 threads in JMeter.
id: 00100
	Use HTTP, 1 thread in JMeter.
id: 00010
	Use HTTP, without using prepared statements, 10 threads in JMeter.
id: 00001
	Use HTTP, without using connection pooling, 10 threads in JMeter.


'''
'''
title: search.
auto_complete: .
year: .
director: .
star: .
pageId: 1.
sort: title.
order: asc.
limit: 50.
config: 10100

jmeter -n -t my_test.jmx -l log.jtl

'''






record = []
for lines in logFile:
	if not "INFO: " in lines:
		continue
	else: 
		rec = dict( element.split(":") for element in lines[6:].strip().split("\t"))
		
		record.append(rec)

logFile.close()



for rec in record:
	print(rec)
