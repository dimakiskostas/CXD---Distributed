<div align="center"><img src="/image/343449988_1288841058683569_386499818967695791_n.png" width="200"></div>

<h1>Description</h1>

<p>CXD exrcise is an android app that is used to track your activity. <br> It collects as input a gpx file and each user can view different statistics such as distance, elevation , time and speed in global and personal level</p>

<div align="center"><img src="/image/20240403_214945.png" width="200">
                     <img src="/image/20240403_214959.png" width="200">
       <img src="/image/20240403_215015.png" width="200">
       
</div>

<h2>Input info</h2>
<p>Each user can send a GPX file that shows the waypoints of their workout. Each file shows:</p>

- The coordinates(latitude, longtitude)
- The elevation
- The time

  <p>The original form a the gpx file is an XML file that has the following form:</p>

```xml
<?xml version="1.0 encoding="UTF-8">
<gpx version="1.1" creator="user1">
       <wpt lat="48.325238728342" lon="4.34865347834">
              <ele>-0.43</ele>
              <time>2023-03-15T10:43:43</time>
       </wpt>
       <wpt lat="48.434957030493425" lon="4.459873498753475">
              <ele>-0.35</ele>
              <time>2023-03-15T10:47:55</time>
       </wpt>              
       
</gpx>

```


<h2>BackEnd</h2>
<p>It is based on the MarReduce framework. It allows us to have parallel processing in order to handle larde volumes of data. It contains two functions: <br></p>

- $Map(key, value)\rightarrow [key_2, value_2]$
- $Reduce(key_2, value_2)\rightarrow [value_final]$

  <p>There are three different types of nodes. A Master, a Worker and a Client(The client is used as a dummy because later we have the android app)<br>
  The master runs a TCP to listen to workers that are trying to connect to him. The master works also as a reducer and the worker works like a mapper. Both the master and the worker are multithreaded in order to complete multiple request.<br>

A client connects to the server and sends a gpx file. The file is broken to pieces and all of them go to workers, where they produce the results and later send them back to the master. He reduces them into the final results and sends in back to the client.

  </p>


  <h2>Frontend</h2>
  <p>The frontend is a simple interface created with java in Android studio. It lets the user have a simple and friendly experience with the app in order to get his results. He can select a file with a slide bar and the results show up with the press of a button. </p>
