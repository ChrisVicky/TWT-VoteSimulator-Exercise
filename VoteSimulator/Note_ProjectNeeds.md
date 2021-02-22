#Function
##Three Parts
    The User Controller
    The Vote Simulator
    The Alarm Part
##The User Controller
    1. Join in.(Post)
    2. Log in Check.(Post, boolean)
    3. Get All User.(Get)
    4. Delete a user.(Delete)
    5. Update a User's Name or Password.(Put)
    6. Find User's Password Via Manager's Code.(Post)
##The Vote Simulator
    1. Publish a Vote.(Post)
    2. Submit a Vote.('Ballot', Post)
    3. Get All Vote.(Get)
    4. Delete a Vote.(Delete)
    5. Update a Vote.
        5.1 Only the Description can be Updated.
        5.2 Or The Vote is Published Again.   
    6. Check the Result Via
        6.1 Vote Id.
        6.2 Question.
        6.3 User Id. --> Seperated By @Para 
            6.3.1 All Vote Published By Him.
            6.3.2 All Vote Submited By Him.
##Alarm Parts --> Things Should Have Written.
    1. APIResponse
    2. Try and Lock
    3. Check the ResponsePara
##About the Mysql Tables
###*user*
    +---------+-------------+------+-----+---------+----------------+
    | Field   | Type        | Null | Key | Default | Extra          |
    +---------+-------------+------+-----+---------+----------------+
    | user_id | int         | NO   | PRI | NULL    | auto_increment |
    | NAME    | varchar(50) | NO   | UNI | NULL    |                |
    | CODE    | varchar(50) | NO   |     | NULL    |                |
    +---------+-------------+------+-----+---------+----------------+
###*question*
    +----------+--------------+------+-----+---------+----------------+
    | Field    | Type         | Null | Key | Default | Extra          |
    +----------+--------------+------+-----+---------+----------------+
    | vote_id  | int          | NO   | PRI | NULL    | auto_increment |
    | question | varchar(500) | NO   |     | NULL    |                |
    | user_id  | int          | NO   |     | NULL    |                |
    +----------+--------------+------+-----+---------+----------------+
###*answer*
    +-----------+------+------+-----+---------+-------+
    | Field     | Type | Null | Key | Default | Extra |
    +-----------+------+------+-----+---------+-------+
    | user_id   | int  | NO   |     | NULL    |       |
    | vote_id   | int  | NO   |     | NULL    |       |
    | choice_id | int  | NO   |     | NULL    |       |
    +-----------+------+------+-----+---------+-------+
###*choice*
    +-----------+--------------+------+-----+---------+-------+
    | Field     | Type         | Null | Key | Default | Extra |
    +-----------+--------------+------+-----+---------+-------+
    | choice    | varchar(500) | NO   |     | NULL    |       |
    | choice_id | int          | NO   |     | NULL    |       |
    | vote_id   | int          | NO   |     | NULL    |       |
    | times     | int          | NO   |     | NULL    |       |
    +-----------+--------------+------+-----+---------+-------+

