## How to replicate bug

-  Login (copy the `access_token` for next request)
```
curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{"username":"tester@gmail.com", "password": "password123!"}' "http://localhost:8080/api/login"
```

- View all keywords (GET)
```
curl -X GET -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTTVVcL2JRQmo5SElLb1FDb0JpVW9kNkZLNlZZNEVZeFlJcFJYSURWVkRGaW9WWGV3UGMzQytNM2RuU0JhVWlRNE1vTFpJbGZvWCtDZnQwaCtBMnFFck15dmZHWUlEQytJbSs3dm45OTczbnM4dVlOaG9tSXMxNDhMNHFjaGlMbjJUYWk1amcyR211ZTM2bVVFZG9jMFI3M0pnaXlad2Zid1NlQUdVZUdSaE10aG1lNndxbUl5cnErMXRERzJ0bzJGVzZmaUdjVk96QlBlVjN2RnZ1VU9sOFk1QVFlMmRsR0JrSFNaWUdLcE0yb2FTUzUyVWE0eldvVkxNQWhYdXVORlVTRGNvTFdmQ0RFSkhVTEsyd0NpQU1aYlpMVVdxSEkyRjhXdXptZVdpMmtSYkMrQkp5b3doZFwvYzJhVnBuM2QwN201STIySVVES0hkU2p3NWw5OHBCZmNmakx5b2hhR3V1cEpscHlVUkZmSk03Y2VMdlRYXC85Y1wveXoxeW9CVUNhdkhcLzZtbUQrdlErXC9YNThzWGVkQmVhT0haZ1BVQ1Z1dWs1R2FpWUY3VDZKVFBmM3o0ZG5yeDVkTVFLVHZFMjhmM01iTndrMXgzVVNVcDA4eXFnWTZJZHJcL3Nub204XC9qQjV2NFd1MytSSktwRCtLR2t4dXBVb2lHbmRzbGFpbjdlRjBZK3J3ZExHd3B2M3l3MzNXckZVSk9yNU9DR25SSjZRZ2FmNVwvcTQ0UDFCVTI5SFwvazlcL0hMXC84UzJRb003ekdSSWNWZktVQ05MR21qUGp3N25SNzdcL3U4b1g2YlwvWTE4QmpFQUtDQndEQUFBPSIsInN1YiI6InRlc3RlckBnbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImV4cCI6MTQ1ODAwNzUwNSwiaWF0IjoxNDU4MDAzOTA1fQ.5oJS_HFD-Y9hg7d5veiztS0x7k8A7IZs9ftJfvc4O5g" -H "Cache-Control: no-cache"  "http://localhost:8080/api/keywords"
```
- **BUG** Go to `views/keyword/_keywordenclave.gson` and uncomment the line `enclaves tmpl....` and comment the line `enclave g.render....` and make the same curl request as above
```
curl -X GET -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTTVVcL2JRQmo5SElLb1FDb0JpVW9kNkZLNlZZNEVZeFlJcFJYSURWVkRGaW9WWGV3UGMzQytNM2RuU0JhVWlRNE1vTFpJbGZvWCtDZnQwaCtBMnFFck15dmZHWUlEQytJbSs3dm45OTczbnM4dVlOaG9tSXMxNDhMNHFjaGlMbjJUYWk1amcyR211ZTM2bVVFZG9jMFI3M0pnaXlad2Zid1NlQUdVZUdSaE10aG1lNndxbUl5cnErMXRERzJ0bzJGVzZmaUdjVk96QlBlVjN2RnZ1VU9sOFk1QVFlMmRsR0JrSFNaWUdLcE0yb2FTUzUyVWE0eldvVkxNQWhYdXVORlVTRGNvTFdmQ0RFSkhVTEsyd0NpQU1aYlpMVVdxSEkyRjhXdXptZVdpMmtSYkMrQkp5b3doZFwvYzJhVnBuM2QwN201STIySVVES0hkU2p3NWw5OHBCZmNmakx5b2hhR3V1cEpscHlVUkZmSk03Y2VMdlRYXC85Y1wveXoxeW9CVUNhdkhcLzZtbUQrdlErXC9YNThzWGVkQmVhT0haZ1BVQ1Z1dWs1R2FpWUY3VDZKVFBmM3o0ZG5yeDVkTVFLVHZFMjhmM01iTndrMXgzVVNVcDA4eXFnWTZJZHJcL3Nub204XC9qQjV2NFd1MytSSktwRCtLR2t4dXBVb2lHbmRzbGFpbjdlRjBZK3J3ZExHd3B2M3l3MzNXckZVSk9yNU9DR25SSjZRZ2FmNVwvcTQ0UDFCVTI5SFwvazlcL0hMXC84UzJRb003ekdSSWNWZktVQ05MR21qUGp3N25SNzdcL3U4b1g2YlwvWTE4QmpFQUtDQndEQUFBPSIsInN1YiI6InRlc3RlckBnbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImV4cCI6MTQ1ODAwNzUwNSwiaWF0IjoxNDU4MDAzOTA1fQ.5oJS_HFD-Y9hg7d5veiztS0x7k8A7IZs9ftJfvc4O5g" -H "Cache-Control: no-cache"  "http://localhost:8080/api/keywords"
```
Notice the `{"message":"Internal server error","error":500}` response and stacktrace in console
