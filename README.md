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

    $ sudo apt-get install openjdk-7-jre
    $ wget http://aphyr.com/riemann/riemann_0.2.4_all.deb
    $ sudo dpkg -i riemann_0.2.4_all.deb

Replace the default riemann.config file with the example...

    $ sudo mv /etc/riemann/riemann.config{,.bak}
    $ wget https://raw.github.com/alerta/riemann-alerta/master/riemann.config
    $ sudo cp riemann.config /etc/riemann/

Add the alerta clojure library...

    $ wget https://raw.github.com/alerta/riemann-alerta/master/alerta.clj
    $ sudo cp alerta.clj /etc/riemann/

Configuration
-------------

Modify the alert and heartbeat endpoints in `aleta.clj` depending on where the alerta API is located...

```
(def alerta-endpoints
        {:alert "http://localhost:8080/api/alert"
        :heartbeat "http://localhost:8080/api/heartbeat"})
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

Copyright (c) 2013 Nick Satterly. Available under the MIT License.
