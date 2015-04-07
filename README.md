#Testing in local
```
mvn clean package
foreman start web
```
http://localhost:5000

# Deploy to heroku
```
git push heroku master
heroku ps:scale web=1
heroku open
heroku logs --tail
```

# How to build a war

```
mvn clean package -Pwar
```

# Mandatory properties
`DATABASE_URL`: The postgresql database URL in the the following format: postgres://&lt;username&gt;:&lt;password&gt;@&lt;host&gt;/&lt;dbname&gt;
