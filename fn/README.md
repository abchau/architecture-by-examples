
### Getting Started

```bash
./cljw -M -m archexamples.main
```

### Folder Structure

| path       | description             |
|------------|-------------------------|
| src/       | application source code |
| lib/       | library source code     |
 | resources/ | application resources   |
| test/      | test code               |

### Source Code Organization

| namespace                                    | description                             |
|----------------------------------------------|-----------------------------------------|
| archexamples.main                            | entry point                             |
 | archexamples.infra.*,<br/>archexamples.infra | application specific framework code     |
| archexamples.adapter                         | bridge between domain and application   |
| archexamples.logic.*                         | domain. one namespace for one aggregate |
| archexamples.logic.specs                     | schema for domain entities              |  

 

