hogwarts
========

Hogwarts has many features, but the goal of this library is to support code decoupling for android.

Service-UI communication
========================
It is possible to run user interfaces in one process (Main UI process), and backend processing logic in service process. This is nice because backend service and UI process won't mess with each other (example, GC)
But we also need ways to communicate between the 2 process. With hogwarts, it is just very easy to do so.
