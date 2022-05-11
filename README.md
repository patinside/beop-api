# api-beop

## Description

Api-beop is a web api based on [Ring](https://github.com/ring-clojure/ring)
and [Compojure](https://github.com/weavejester/compojure).
State management is done with [Component](https://github.com/stuartsierra/component)  library
Counter status are stored in a [Datalevin](https://github.com/juji-io/datalevin) database used in a as kv-store
Data are stored with a transaction process.

## Application start
I used way to manage component as recommended in this [book](https://grishaev.me/en/clj-book-systems/)

### In the repl:
- (user/go) to start the system
- (user/reset) to restart the system
- (user/stop) to stop it.

### With clj:
``` clj -X:run```

## Configuration
Port can be configure in the `conf/dev.edn` or `conf/dev.edn` file.


## Routes usage

All routes return a JSON structure.

### GET data structure per advert-id:

Example: http://localhost:3000/question-data?advert-id=1  
Data are store in the def var name `data`, for the moment in the `core` namespace

```json
{
  "status": 200,
  "headers": {
    "Content-Type": "text/plain"
  },
  "body": {
    "accessibility": {
      "label": "Accessibility"
    },
    "inclusivity": {
      "label": "Inclusivity"
    },
    "adjustability": {
      "label": "Adjustability"
    }
  }
}
```

### Increase a counter by a pair advert-id/vote-id

Example: http://localhost:3000/count-clic?advert-id=1&vote-id=accessibility

```json
{
  "status": 200,
  "new-count": 3,
  "transaction-status": "transacted",
  "params": {
    "advert-id": "1",
    "vote-id": "adjustability"
  }
}
```

### Check the campaign status by id

Example: http://localhost:3000/advert-campaign-status?advert-id=1

```json
{
  "status": 200,
  "result": {
    "accessibility": 23,
    "inclusivity": 7,
    "adjustability": 3
  },
  "params": {
    "advert-id": "1"
  }
}
```

## TODO

- Check the validity of the options sent by the client with spec
- Make some tests
- Add the adverts data in a database
- Manage exceptions for DB
- Manage exceptions for webserver
- improve organization (routes namespace is not nice)
