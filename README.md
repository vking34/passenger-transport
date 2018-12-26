### REPORT

1. Submission:
     + who does what:
         + Dung (30%): Class Design, Guest UI, User UI, Documenting. 
         + Antonie (30%): Admin UI, Styling, Slides.
         + Vuong (40%): Architecture Design, Database Design, Back-End, Integrating.
     
     + who reviews what:
         + Dung reviews Vuong's test
         + Antonie reviews Dung's test
     + who tests what:
         + Dung test Vuong's Rest API
         + Vuong test Dung's request data
         + Antonie test User's input
2. Refactor: please check source code in folder __src__
  
3.  Documents:
    + SRS: please check __SRS - Passenger Transport Management System.docx__ in folder __Documents__
    + SDD: please check __SDD - Passenger Transport Management System.doc__ in folder __Documents_
    + Test report:
        
        + Test technique: Coverage (White Box)
        + Based on the implemented functions, we choose test data and test procedure. Then compare the actual output to the excepted output.
        + Test case: https://1drv.ms/x/s!AmxdxN1YTb5OiwGKi0bdW6LMQ2f3


    + Design Priciples & Design Patterns:
      + SOLID: Each module has a specific function and communicate with each others via interfaces. So fix inside a module without affects to other modules.
      + Design patterns:
        + Singleton.
        + Dependency Injection (implemented using Spring)

4. Video DEMO: https://www.youtube.com/watch?v=9VVvXIfos0o
5. Deploy:
    + URL: https://passenger-transport.herokuapp.com/
    + Accounts:
      + Guest: no need to login
      + User:
        + Login with Facebook / Google /
          + username: user
          + password: test
      + Admin:
        + username: admin
        + password: test