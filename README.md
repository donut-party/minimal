# dev

Create postgres dbs:

``` shell
createdb donut_minimal_dev
createdb donut_minimal_test
```

After starting REPL, `(dev)`

## About

This project demonstrates:

- specify endpoints
- writing endpoint tests
- creating a frontend

## migrations

from dev:

``` clojure
(migratus/migrate (db-config))
```

## starting Shadow

``` shell
npm install
npx shadow-cljs watch dev
```
