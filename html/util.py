import redis
import json

CookieTimeOut = 60 * 60 * 24 * 30
redisInstance = redis.StrictRedis(host="localhost", port=6379, db=0)
# CookieTimeOut = 10

def getAdsCandidate():
    if redisInstance.exists("ads1"):
        adsContentStr = redisInstance.get("ads1")
        adsContent = json.loads(adsContentStr)
        return adsContent
    else:
        adsContent = {}
        adsContent['content'] = "baidu"
        adsContent['url'] = "http://www.baidu.com"
        adsContent['adsId'] = 0
        return adsContent

def setNewCookie(cookieId):
    tag = {}
    tag["counter"] = {}
    tag["tag"] = {}
    print json.dumps(tag)
    redisInstance.set("CookieId" + cookieId, json.dumps(tag))
    redisInstance.expire("CookieId" + cookieId, CookieTimeOut)



def IsMissingCookie(cookieId):
    key = "CookieId" + cookieId
    if not redisInstance.exists(key):
        setNewCookie(cookieId)
        return True
    else:
        redisInstance.expire(key, CookieTimeOut)
        return False
