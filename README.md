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
(def alert (alerta/alert {:endpoint "https://alerta-api.herokuapp.com" :key "demo-key" :debug true}))
(def heartbeat (alerta/heartbeat {:endpoint "https://alerta-api.herokuapp.com" :key "demo-key"}))
```

Start Riemann...
    
    $ sudo service riemann start


Testing
-------

Generate some test alerts by generating metrics using `riemann-health`...

    $ sudo apt-get install ruby1.9.1 ruby1.9.1-dev
    $ sudo gem install riemann-tools
    $ riemann-health

License
-------

Copyright (c) 2013,2018 Nick Satterly. Available under the MIT License.
