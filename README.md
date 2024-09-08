This is the backend component of my final project after a year training at Intec Brussels as a Java Enterprise Developer.
It integrates most of the stuff we learned, as well as a few new tricks such as using mokito (not a fan :-(), CORS configuration, some basic authentification. I also learned about DTOs and 
how this can belved with simple but nifty Spring annotations that let me simplify my code a bit.

The basic technology is Java Spring, MySQL and an Angular-based frontend.

What is the purpose of this app? 

Basically, to let patient record some basic parameters such as weight, blood pressure, pulse and sense of balance as a neurological parameter. 
The frontend displays a few monthly/annual averages to detect trends.
Patients can do most CRUD operations. 


Doctors have access to these patient's lists, but cannot delete patient's data.

The admins can delete patients and doctors, but do not access their data.





