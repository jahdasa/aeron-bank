-- wrk -t12 -c400 -d30s -s ./post.lua http://localhost:8080/bank/api/account/create
-- post.lua
wrk.method = "POST"
wrk.body   = ""
wrk.headers["Content-Type"] = "application/x-www-form-urlencoded"