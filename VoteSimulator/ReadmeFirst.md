#Vote Simulator
####Christopher Liu
##1. Programme
###*1.1 The User Controller*
    1. Join in.(Post)
    2. Log in Check.(Post, boolean)
    3. Get All User.(Get, Manager Required, /user)
    4. Delete a user.(Delete, Manager Required, /user)
    5. Update a User's Name and Password.(Put, both newName 
        and newCode Required, /user)
    6. Find User's code.(Post, Manager Required, /findCode)
###*1.2 The Vote Controller*
    1. Publish a Vote.(Post, /vote)
    2. Participate a Vote.(Put, /vote)
    3. Get All Vote.(Get, /vote)
    4. Delete a Vote.(Delete, /vote)
    6. Delete My Participation.(Delete, /deVote)
    6. Check the Result Via
        6.1 Vote Id. (Get, /result)
        6.2 User Id. (Get)
            6.2.1 All Votes Published By Him. (/myVote)
            6.2.2 All Votes Submited By Him. (/myParticipation)
    7. ReVote a Vote.(Put, /reVote)
###*1.3 Others*
    1. APIResponse
    2. Try and Lock
    3. Log-in Check.
##2. Usage
###*2.1 Test Connection*
####*/testConnection*
    Get
        To Test Connection with the Return as "Hello World".
###*2.2 User Controller*
####*/user*
    Get
        Get All users' name and code.
        <managerName> and <managerCode> are required.
    Post
        Sign up as a freshman.
        <name> and <code> are required.
    Put
        Update a User's name and code.
        <name>, <code>, <newName> and <newCode> are required.
    Delete
        Delete a User.
        <name>, <code>, <managerName> and <managerCode> are required.
        !!Danger
            All Votes Published by him
            All his Participations
            Are deleted.
####*/findCode*
    Get
        To find a User's code.
        <name>, <managerName> and <managerCode> are required.
####*/userName*
    Get
        To get All users' name(s);
###*2.3 Vote Controller*
####*/question*
    Get
        To get All Votes' questions. Including
        voteId, question, userId(Publisher)
####*/choice*
    Get
        To get All Votes' choices. Including
        voteId, choiceId, choice, times.
####*/vote*
    Get
        Get All Votes, including its Question, Choices, User(Publisher)
    Post
        Publish a Vote
        <name>, <code>, <question> and a list of <choice> are required.
    Put
        Participate in a Vote
        <name>, <code>, <voteId> and <choiceId> are required.
    Delete
        Delete a Vote which has to be Yours.
        <name>, <code> and <voteId> are required.
####*/deVote*
    Delete
        Delete My Participation in a Vote.
        <name>, <code> and <voteId> are required.
####*/reVote*
    Put
        ReVote in a Vote.
        <name>, <code>, <voteId> and <choiceId> are required.
####*/result*
    Get
        Get a Result to a Vote.
        <voteId> is required.
####*/myParticipation*
    Get
        Get Results to all Votes that I have Participated.
        <name> and <code> are required.
####*/myVote*
    Get
        Get Result to all Votes that I have Published.
        <name> and <code> are required.
##3. About Mysql Tables
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
    | question | varchar(500) | NO   | UNI | NULL    |                |
    | user_id  | int          | NO   |     | NULL    |                |
    +----------+--------------+------+-----+---------+----------------+
###*choice*
    +-----------+--------------+------+-----+---------+-------+
    | Field     | Type         | Null | Key | Default | Extra |
    +-----------+--------------+------+-----+---------+-------+
    | vote_id   | int          | NO   |     | NULL    |       |
    | choice_id | int          | NO   |     | NULL    |       |
    | choice    | varchar(500) | NO   |     | NULL    |       |
    | times     | int          | NO   |     | NULL    |       |
    +-----------+--------------+------+-----+---------+-------+
###*answer*
    +-----------+------+------+-----+---------+----------------+
    | Field     | Type | Null | Key | Default | Extra          |
    +-----------+------+------+-----+---------+----------------+
    | id        | int  | NO   | PRI | NULL    | auto_increment |
    | vote_id   | int  | NO   |     | NULL    |                |
    | choice_id | int  | NO   |     | NULL    |                |
    | user_id   | int  | NO   |     | NULL    |                |
    +-----------+------+------+-----+---------+----------------+
###*manager*
    +------------+-------------+------+-----+---------+----------------+
    | Field      | Type        | Null | Key | Default | Extra          |
    +------------+-------------+------+-----+---------+----------------+
    | manager_id | int         | NO   | PRI | NULL    | auto_increment |
    | NAME       | varchar(50) | NO   | UNI | NULL    |                |
    | CODE       | varchar(50) | NO   |     | NULL    |                |
    +------------+-------------+------+-----+---------+----------------+
