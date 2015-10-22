# UCLL-ScalaTest
## TODO
### Case 1
```
Create user story

Story:
In order to limit access to the application
As an administrator
I want to register users

Scenario: the personal details of a user can be registered
Given the firstname Bert, lastname Bertels, email bert.bertels@gmail.com and password PasswordForBert
When I choose to create the person with the given data
Then a person object is created with these data
```
### Case 2
```
Scenario: the firstname of a user is not mandatory
Given the lastname Bertels, email bert.bertels@gmail.com and password PasswordForBert but no firstname
When I choose to create the person with the given data
Then a person object is created with these data and no firstname

Scenario: the lastname of a user is not mandatory
Given the firstname Bert, email bert.bertels@gmail.com and password PasswordForBert of a person but no lastname
When I choose to create the person with the given data
Then a person object is created with these data and no lastname
```
### Case 3
```
Scenario: the password cannot be stored as plain text
Given the password PasswordForBert
When I choose to create a person with this password
Then the password is stored as a digest of 40 characters

Scenario: different passwords have different hashed values
Given the password PasswordForBert
And another password OtherPasswordForJan
When I choose to create a person with the first password
And I choose to create a person with the second password
Then the stored password of the first person is different from the stored password of the second user

Scenario: identical passwords have different hashed values
Given the password PasswordForBert
When I choose to create a person with this password
And I choose to create another person with this password
Then the stored password of the first person is different from the stored password of the second user
```
### Case 4
```
Scenario: the email of a user is mandatory
Given the firstname Bert, lastname Bertels and password PasswordForBert but no email 
When I choose to create the person with the given data
Then an error is given
And the person is not created

Scenario: the password of a user is mandatory
Given the firstname Bert, lastname Bertels and email bert.bertels@gmail.com but no password
When I choose to create the person with the given data
Then an error is given
And the person is not created
```
### Case 5
```
Scenario: the email of a user should be a valid email address
Given an email addres <email>
When I choose to create a person with this email
Then a person object is created with the given email

Examples:
| email | motivation |
| bert@gmail.com | the local part can have one part |
| bert.bertels@gmail.com | the local and domain part can have two parts seperated by a dot |
| bert.bertels@g.mail.com | the domain part can have three parts seperated by a dot |
| 1-Be+rt.bertels@gmail.com | the first local part can contain upper- en lowercase characters, digits, hyphens and plus signs |
| bert.1ber-Tels@gmail.com | the second local part can contain upper- en lowercase characters, digits and hyphens |
| bert.bertels@gMail-1.com | the first domain part can contain upper- en lowercase characters, digits and hyphens |
| bert.bertels@gmail.cOm | the third domain part can contain upper- en lowercase characters |
```
### Case 6
```
Scenario: the local part of an email address can have one part
Given an email address 'bert@gmail.com'
When I choose to create a person with this email
Then a person object is created with the given email

Scenario: the local and domain part of an email address can have two parts seperated by a dot
Given an email address 'bert.bertels@gmail.com'
When I choose to create a person with this email
Then a person object is created with the given email
									
Scenario: the domain part of an email address can have three parts separated by a dot
Given an email address 'bert.bertels@g.mail.com'
When I choose to create a person with this email
Then a person object is created with the given email
```
### Case 7
```
Scenario: the email of a user should be a valid email address
Given an email addres
When I choose to create a person with this email
Then a person object is created with the given email

Examples:
( email , motivation )
( bert@gmail.com , the local part can have one part )
( bert.bertels@gmail.com , the local and domain part can have two parts seperated by a dot )
( bert.bertels@g.mail.com , the domain part can have three parts seperated by a dot )
( 1-Be+rt.bertels@gmail.com , the first local part can contain upper- en lowercase characters, digits, hyphens and plus signs )
( bert.1ber-Tels@gmail.com , the second local part can contain upper- en lowercase characters, digits and hyphens )
( bert.bertels@gMail-1.com , the first domain part can contain upper- en lowercase characters, digits and hyphens )
( bert.bertels@gmail.cOm , the third domain part can contain upper- en lowercase characters )
```