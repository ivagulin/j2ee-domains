import urllib2, json
import uuid
import time
import optparse
from multiprocessing import Pool

def f(x):
	data = json.dumps({"domainName":"ivagulin-set5.%s" % str(uuid.uuid4()),
		"email":"root@ivagulin-set5-mn.qa.sw.ru",
		"expire":2419200,"refresh":14400,"retry":7200,"serial":1,"minTtl":3600})
	req = urllib2.Request('http://%s:8081/j2ee-domains/rest/domains/create' % opts.host , data, {'Content-Type': 'application/json'})
	urllib2.urlopen(req).read()

parser = optparse.OptionParser()
parser.add_option('--host', default='127.0.0.1')
parser.add_option('--iterations', type=int, default=32)
parser.add_option('--concurrency', type=int, default=8)
opts, args = parser.parse_args()

p = Pool(opts.concurrency)
start = time.time()
p.map(f, range(opts.iterations))
finish = time.time()
print '%s r/sec' % ( (opts.iterations * 10) / (finish-start) )
