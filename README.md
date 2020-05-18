# SWEN20003-OOSD-Project2
> This is the second project of SWEN20003 yr19 sm1, this project was done based on the first project

Introduction
============
>- This project aim to create a graphical real-time strategy game in Java.
>- The game needs the player to build structures on a map and train units to mine resources.
>- This game is devided into units that can be controlled to travel to different parts of map, 
buildings that can be controlled to create new units, and resources that certain types of units can collect.
>- There are two kinds of resources, metal and unobtainium. The goal of the game is to collect unobtainiums.
Create buildings and units costs a certain amount of metal.

Design
======
>- This project has been splitted in two parts, the first was to draw a [UML dragram](https://github.com/zsqSeraphina/SWEN20003-OOSD-Project2/blob/master/OOSD%20p2A%20(2).pdf).

Implementation
==============

## [Units](https://github.com/zsqSeraphina/SWEN20003-OOSD-Project2/blob/master/src/Unit.java) #
>- [Scout](https://github.com/zsqSeraphina/SWEN20003-OOSD-Project2/blob/master/src/Scout.java): Moves the fastest, no other special attributes.
>- [Builder](https://github.com/zsqSeraphina/SWEN20003-OOSD-Project2/blob/master/src/Builder.java): Can create factories to train trucks.
>- [Engineer](https://github.com/zsqSeraphina/SWEN20003-OOSD-Project2/blob/master/src/Engineer.java): Can mine metal and unobtainium.
>- [Truck](https://github.com/zsqSeraphina/SWEN20003-OOSD-Project2/blob/master/src/Truck.java): Can create command centres.

#### Buildings #
>- [CommandCentre](https://github.com/zsqSeraphina/SWEN20003-OOSD-Project2/blob/master/src/CommandCentre.java): Can train most units when selected.
press key 1 trains a scout, press key 2 trains a builder, press key 3 trains an engineer.
>- [Factory](https://github.com/zsqSeraphina/SWEN20003-OOSD-Project2/blob/master/src/Factory.java): Can train a truck when selected and key 1 pressed.
>- [Pylon](https://github.com/zsqSeraphina/SWEN20003-OOSD-Project2/blob/master/src/Pylon.java): Already on the map, activated when a unit passes, an activated pylon will allow all engineers to carry 1 additional resource.


## [Resources](https://github.com/zsqSeraphina/SWEN20003-OOSD-Project2/blob/master/src/Resource.java) #
>- [Metal](https://github.com/zsqSeraphina/SWEN20003-OOSD-Project2/blob/master/src/Metal.java)
>- [Unobtainium](https://github.com/zsqSeraphina/SWEN20003-OOSD-Project2/blob/master/src/Unobtainium.java)

## [Camera](https://github.com/zsqSeraphina/SWEN20003-OOSD-Project2/blob/master/src/Camera.java) #
>- It allows the screen to follow the seleccted unit, or deselecte and move when one of "w, a, s, d" been pressed.
