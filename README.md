# Minnesota-Income-Tax-Calculation-Project
## Project Goal
The goal of this project is to reengineer a legacy Java application. At a glance, serves for the income tax calculation of the Minnesota state citizens. The tax calculation accounts for the marital status of a given citizen, his income, and the amount of money that he has spend, as witnessed by a set of receipts declared along with the income. The legacy application takes as input txt or xml files that contain the necessary data for each citizen. The tax calculation is based on a complex algorithm provided by the Minnesota state. The application further produces graphical representation s of the data in terms of bar and pie charts. Finally. the application produces respective output reports in txt or xml.

### Reengineering
- We fixed all the Smells based on project backlog guidelines.
- We redesigned InputSystem, OutputSystem, Database to 2 key manager classes names FilesManager and ChartsManager
- We introduced model, view, manager packages for the 3 key modules of our project
- We used java 8 streams and lambdas for recoding some old-fashioned fors and ifs
- Models were refactored based on simple POJO's philosophy. Removed heave logic from models
- Static code analysis tool ( sonarLint ) was used to fix many common java mistakes

## Contributors
| Name  | AM | Email |
| ------------- | ------------- | ------------- |
| Apostolis Kasselouris  | 2994 | kasseluris.apostolos@gmail.com |
| Todhri Angjelo | 3090 | todhri_angjelo@outlook.com.gr |

## Reengineering the legacy code
The goal of this project is to reengineer a legacy Java application. At a glance, serves for the 
income tax calculation of the Minnesota state citizens. The tax calculation accounts for the marital
status of a given citizen, his income, and the amount of money that he has spend, as witnessed by a
set of receipts declared along with the income. The legacy application takes as input txt or xml 
files that contain the necessary data for each citizen. The tax calculation is based on a complex 
algorithm provided by the Minnesota state. The application further produces graphical representation
s of the data in terms of bar and pie charts. Finally. the application produces respective output 
reports in txt or xml.

Legacy code Smells:
- The Taxpayer class has a lot of code duplication.

- The Taxpayer class suffers from [primitive obsession](https://refactoring.guru/smells/primitive-obsession). 
A lot of constants for the income limits and income tax rates, are used to calculate the tax of the Taxpayer. 
The values of these constants depend on the value of the family status attribute. Essentially the family status acts 
as a typecode for different kinds of Taxpayers.

- The different subclasses of Receipt are [lazy classes](https://refactoring.guru/smells/lazy-class). 

- The Database, the InputSystem and the OutputSystem classes do not really follow the object-oriented style. 
The main problem is that all of them just define a bunch of static methods that operate on static class attributes. 
All these static methods and attributes prevent further refactoring that will allow to separate the class responsibilities 
into smaller classes and get rid of code duplication by extracting abstract classes and template methods.

- The InputSystem class has too many responsibilities. It is used to parse two different formats (XML and TXT).

- The OutputSystem class has too many responsibilities. It is used to generate reports in two different formats. 
Moreover, it creates the graphical representation of the data (pie charts, bar charts and so on).

## Todos and future enhancements

- **Remove the dependency of FileManager to the User Interface classes**. We don’t want the FileManager to alter the UI. Only the view classes should do this. The FIleManager is called from theUI classes. The UI classes just need to know thatthe operation was successful. For example,if we have requested to create some files given some input, the UI just needs to know SUCCESS OR FAILURE in order to display the propermodal. FileManager methodsshould be refactored to return an integerwhich the UI classes will have to handle.
- **The parsing mechanismwhich we implemented was draft and still a little bit of complicated**. We should make the parsing mechanism way more abstract and exploit a ready method from some external library if that exists.
- There is dependency at the View classes(TaxPayerLoadDataJDialog) with the Java IO library. This is not agood technique as we want the UI classes to be responsible only for proper display and managing the AWTand swing modals. FileManager should be the one tohave this logic that the UI classes will request for
- **FileManager started as a helper classin order we remove away Input and Outputsystem. It tends to be a GOD** classwhich holds a lot of logic and it could possibly be refactored into smaller andmore readable units
- **External libraries like Junit and JFreeChart should not be committedinto the code base** and we shouldn’t have to worry about adding each external dependency in our application separately when a tool like **Maven can do this for us**. 
- Java is moving fast and beautiful things are comingin **later versions(we used java 8) and in Micromanagement frameworks like Spring Boot**. Things like dependency injections and inversion ofcontrolare super trendy nowadays and we would like our application to exploit those features that are offeredwithout having to reinvent the wheel like we do in some cases inside our application (singletonand getInstance())

## Note
Initial code before refactoring at **master branch**.
