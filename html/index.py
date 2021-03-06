#!/usr/bin/python
import traceback
import web
import json
import os
import time
import util

urls = (
    "/", "index",
    "/api", "api",
)

app = web.application(urls, globals())
application = app.wsgifunc()
render = web.template.render('./')

class index:
    def GET(self):
        return render.home()

class api:
    def POST(self):
        data = web.data()
        print data
        req = json.loads(data)
        try:
            if "size" in req:
                putIdAns = "%013d%04d" %(time.time() * 1000, os.getpid())
                content = []
                for i in xrange(req["size"]):
                    ans = util.getAdsCandidate()
                    ans['adsId'] = i
                    ans['id'] = "%s%03d" %(putIdAns, i)
                    content.append(ans)
                if "cookieId" in req:
                    util.IsMissingCookie(req['cookieId'])
                    return json.dumps(content)
                else:
                    util.setNewCookie(putIdAns)
                    ret = {}
                    ret['AdsId'] = putIdAns
                    ret['AdsItem'] = content
                    return json.dumps(ret)
            else:
                internalerror()
        except Exception, e:
            traceback.print_exc()

    def GET(self):
        ret = {"title" : "readme", "content" : "This is a web api."}
        web.header("Content-Type", "application/json")
        return json.dumps(ret)

def notfound():
    return web.notfound("Sorry, the page you were looking for was not found.")

def internalerror():
    return web.internalerror("Bad, bad server. No donut for you.")
if __name__ == "__main__":
    app.notfound = notfound
    app.internalerror = internalerror
    app.run()
