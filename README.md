# Dragonfly Webserver

This repository includes the Tomcat RESTfull Dragonfly server.

## Rest API

All REST operations can be found on the
[REST API File](docs/REST.md)

## What is Dragonfly?

Dragonfly is an intelligent domestic watering system that allows for an autonomous and efficient way of watering plants, by knowing what a plant needs and when it needs.

The system works by disposing plants in a circular pattern and the dragonfly watering unit on the center. A motor rotates the water pipe, allowing each plant to be watered individually. Each vase has its own humidity sensor and the system has a record of which plant is sowed on which vase.

<br>
<div style="text-align: center;">
<img src="docs/system.png" width=400>
</div>
<br>

This allows a custom watering regime for different plants. All plants are also registered in Dragonfly's databases, which allows the system to know what that plant's specific water and lighting requirements.
