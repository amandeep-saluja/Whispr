-- Table: Message
CREATE TABLE Message (
                         id VARCHAR(255) PRIMARY KEY,
                         body VARCHAR(1000),
                         isRead BOOLEAN,
                         isReceived BOOLEAN,
                         creationDateTime TIMESTAMP,
                         chatId VARCHAR(255),
                         userId VARCHAR(255)
);

-- Table: User
CREATE TABLE User (
                      id VARCHAR(255) PRIMARY KEY,
                      chat_id VARCHAR(255),
                      name VARCHAR(255),
                      isActive BOOLEAN,
                      FOREIGN KEY (chat_id) REFERENCES Chat(id)
);

-- Table: Chat
CREATE TABLE Chat (
                      id VARCHAR(255) PRIMARY KEY,
                      groupName VARCHAR(255)
);

