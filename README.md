Riemann-to-Alerta
=================

Forward Riemann events to Alerta for a consolidated view and improved visualisation.

Installation
------------

To install Riemann on Debian/Ubuntu...

    $ sudo apt-get install openjdk-7-jre
    $ wget http://aphyr.com/riemann/riemann_0.2.2_all.deb
    $ sudo dpkg -i riemann_0.2.2_all.deb

Replace the default riemann.config file with the example...

    $ wget https://raw.github.com/alerta/riemann-alerta/master/riemann.config

Add the alerta integration thing...

    $ wget https://raw.github.com/alerta/riemann-alerta/master/alerta.clj

Configuration
-------------

Modify the alert and heartbeat endpoints depending on where the alerta API is located...

```
(def alerta-endpoints
        {:alert "http://localhost:8080/alerta/api/v2/alerts/alert.json"
        :heartbeat "http://localhost:8080/alerta/api/v2/heartbeats/heartbeat.json"})
```

Start Riemann...
    
    $ sudo service riemann start


Testing
-------

Generate some test alerts by generating metrics using `riemann-health`...

    $ sudo gem install riemann-health
    $ riemann-health

License
-------

Copyright (c) 2013 Nick Satterly. Available under the MIT License.
