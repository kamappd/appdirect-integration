#Testing in local
```
mvn clean package
foreman start web
```

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
