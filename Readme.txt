###########################################
Author: Qian Tang & Ching Man Lee
Application: Game of Emotion
###########################################

Game of Emotion application is made up of the 14 class modules and they are explained in the following:

-----------------------------------------Login:------------------------------------------

Page  for  Login  and  requiring  user  input  of  Username  and  Password.


Consists  of  class  to  deal  with  result  after  calling  Login web  service  from AsyncTaskCallWS.java  Class  connecting  with  the  server.  After  successful login,  it  redirects  to  the AffectiveCircumplexModel.java or BodyMap_New activity  page. Otherwise, restart  the Login.java activity  page  for  re-login. 

If  error  occurs,  it  will  be  handled  by ErrorHandling.java Class.


-----------------------------------------Register:-----------------------------------------


Page  for  Register  and  requiring  user  input  of  Username  and  Password.


Consists  of  class  to  deal  with  result  after  calling Register web  service  from AsyncTaskCallWS.java  Class  connecting  with  the  server.  After  successful registration,  it  redirects  to  the Login.java activity  page.

If  error  occurs,  it  will  be  handled  by ErrorHandling.java Class.

-----------------------------------------CircumplexData:-----------------------------------------

Class  for  affective circumplex data structure.

Consists of data types to represent the position or the size of the data in affective circumeplex model.

It will be implemented by AffectiveCircumplexView.java class.

-----------------------------------------AffectiveCircumplexView-----------------------------------------

Class  for  affective circumplex view.

Control the movement of components on the affective circumplex view

It will be implemented by AffectiveCircumplexModel.java class.

-----------------------------------------AffectiveCircumplexModel:-----------------------------------------

Class  for  affective circumplex model.

Consists of data types to represent the position or the size of the data in affective circumeplex model.

Consists  of  class  to  deal  with  result  after  calling AffectiveCircumplex web  service  from AsyncTaskCallWS.java  Class  connecting  with  the  server.  After  successful submission,  it  redirects  to  the YouTubeActivity.java activity  page.

If  error  occurs,  it  will  be  handled  by ErrorHandling.java Class.


-----------------------------------------IconData:-----------------------------------------

Class  for body map data structure.

Consists of data types to represent the position, view and icon type of the data in body map model.

It will be implemented by BodyMap_New.java class.

-----------------------------------------BodyMap_New:-----------------------------------------

Class  for  body map  model.

Consists  of  class  to  deal  with  result  after  calling body map web  service  from AsyncTaskCallWS.java  Class  connecting  with  the  server.  After  successful submission,  it  redirects  to  the YouTubeActivity.java activity  page.

If  error  occurs,  it  will  be  handled  by ErrorHandling.java Class.

-----------------------------------------EmotionRating:-----------------------------------------

Class  for  initialize the emotion rating window.

Consists  of  class  to  deal  with  result  after  calling emotionRating web  service  from AsyncTaskCallWS.java  Class  connecting  with  the  server.  After  successful submission,  it  redirects  to  the self-diagnostic model or YouTubeActivity.java activity  page.

If  error  occurs,  it  will  be  handled  by ErrorHandling.java Class.

-----------------------------------------YouTubeActivity:-----------------------------------------

Class represents  for Youtube video displaying

Play the type of video in according to the model_num as assigned to the user.

If  error  occurs,  it  will  be  handled  by ErrorHandling.java Class.


-----------------------------------------HTTPURLConnection:-----------------------------------------

Class  to  create  HTTPUrl connection. 

Set  parameters  and  post  to  perform  the  request. Handling  JSONObject returned from the  HttpUrl. 

With  function  to  check  the  connectionless  of  network


-----------------------------------------AsyncTaskCallWS-----------------------------------------

Class  to  execute  web  service  request  by  extending AsyncTask Class and running in background. When  request  is  making  to  the  server, check  the availability  of  network  and  the  establishment  of  HTTP  connection  by  the function  calls  from  the HTTPURLConnection.java Class.

For disconnected network or failed HTTPS connection, setting up corresponding error  numbers.

With function for setting parameters on Post method for HTTP connection to make request to web services. 

If error occurs, it will be handled by ErrorHandling.java Class.

-----------------------------------------VariablePool:-----------------------------------------

Class which is a container of shared variables for all classes.

With  functions  of  variable  setters  and  getters  for  other  classes  to  access local  shared  variables  in  this  class. 

With  shared  functions  used  by  all  classes  and  functions  to  deal  with  the content  of local  variables  in VariablePool.java Class.

-----------------------------------------JsonHandling:-----------------------------------------

Class represents  for all the Json handling function

Consists  of  function  to  check  the  validation  of  Json  content.  It  is  invalid if the  string  is  neither  Json  object  nor  Json  array.

-----------------------------------------ErrorHandling:-----------------------------------------

Class as error  Handler  for  every  error  occurred  in  each  class  with error  number  of 0  to  11.   

Dealing  with  Alert  Dialog  and  Toast  Make  to  notify  the  user of  the  happen of  errors.

Redirect  the  user  to  target  activity  page  or  clear  the  account  information if  needed.



