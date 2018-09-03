Riemann-to-Alerta
=================

Forward Riemann events to Alerta for a consolidated view and improved visualisation.

Transform this ...

![nagios](/docs/images/riemann.png?raw=true)

Into this ...

![alerta](/docs/images/riemann26-alerta-v3.png?raw=true)

Installation
------------

To install Riemann on Debian/Ubuntu...

    $ export VERSION=0.3.1
    $ sudo apt-get install openjdk-7-jre
    $ wget -nv https://github.com/riemann/riemann/releases/download/${VERSION}/riemann_${VERSION}_all.deb
    $ sudo dpkg -i riemann_${VERSION}_all.deb

Replace the default riemann.config file with the example...

    $ sudo mv /etc/riemann/riemann.config{,.bak}
    $ wget https://raw.github.com/alerta/riemann-alerta/master/riemann.config
    $ sudo cp riemann.config /etc/riemann/

Add the alerta clojure library...

    $ wget https://raw.github.com/alerta/riemann-alerta/master/alerta.clj
    $ sudo cp alerta.clj /etc/riemann/

Configuration
-------------

Include and require the Alerta plugin:

```clojure
(include "alerta.clj")
(:require '[alerta :refer [alert heartbeat]])
```

Set the alert and heartbeat endpoints depending on where the alerta API is located:

```clojure
; Alerta configuration
(def alert (alerta/alert {:endpoint "https://alerta-api.herokuapp.com" :api-key "demo-key"}))
(def heartbeat (alerta/heartbeat {:endpoint "https://alerta-api.herokuapp.com" :api-key "demo-key"}))
```

Start Riemann...
    
    $ sudo service riemann start


Testing
-------

Generate some test alerts by generating metrics using `riemann-health`...

    $ sudo apt-get install ruby1.9.1 ruby1.9.1-dev
    $ sudo gem install riemann-tools
    $ riemann-health

Troubleshooting
---------------

To generate debug output from the HTTP client add `debug` and/or
`debug-body` to the Alerta configuration definitions as shown:

```clojure
; Alerta configuration
(def alert (alerta/alert {:endpoint "https://alerta-api.herokuapp.com" :debug true :debug-body true}))
(def heartbeat (alerta/heartbeat {:endpoint "https://alerta-api.herokuapp.com" :debug true :debug-body true}))
```

**Example Debug output**

```
HttpRequest:
{:config nil,
 :method "POST",
 :requestLine
 #object[org.apache.http.message.BasicRequestLine 0x1eb6380d "POST https://alerta-api.herokuapp.com/alert HTTP/1.1"],
 :aborted false,
 :params
 #object[org.apache.http.params.BasicHttpParams 0x4678323f "org.apache.http.params.BasicHttpParams@4678323f"],
 :protocolVersion
 #object[org.apache.http.HttpVersion 0x389a53e1 "HTTP/1.1"],
 :URI
 #object[java.net.URI 0xede089 "https://alerta-api.herokuapp.com/alert"],
 :class org.apache.http.client.methods.HttpPost,
 :allHeaders
 [#object[org.apache.http.message.BasicHeader 0x42265e51 "Connection: close"],
  #object[org.apache.http.message.BasicHeader 0x11b5e77c "Authorization: Key demo-key"],
  #object[org.apache.http.message.BasicHeader 0x574090ad "content-type: application/json"],
  #object[org.apache.http.message.BasicHeader 0x34bc7ac8 "accept: application/json"],
  #object[org.apache.http.message.BasicHeader 0x675e94f2 "accept-encoding: gzip, deflate"]],
 :entity
 #object[org.apache.http.entity.StringEntity 0x59ee0162 "[Content-Type: text/plain; charset=UTF-8,Content-Length: 471,Chunked: false]"]}
```

References
----------

 * clj-http - https://github.com/dakrone/clj-http

License
-------

Copyright (c) 2013,2018 Nick Satterly. Available under the MIT License.
