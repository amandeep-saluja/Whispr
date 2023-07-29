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
                      name VARCHAR(255),
                      isActive BOOLEAN
);

-- Table: Chat
CREATE TABLE Chat (
                      id VARCHAR(255) PRIMARY KEY,
                      groupName VARCHAR(255)
);

-- Table: UserChat (Junction Table)
CREATE TABLE UserChat (
                          chatId VARCHAR(255),
                          userId VARCHAR(255),
                          PRIMARY KEY (chatId, userId),
                          FOREIGN KEY (chatId) REFERENCES Chat(id),
                          FOREIGN KEY (userId) REFERENCES User(id)
);

-- Insert Users
INSERT INTO User (id, name, isActive) VALUES
                                          ('user1', 'Alice', true),
                                          ('user2', 'Bob', true),
                                          ('user3', 'Charlie', true);

-- Insert Chats
INSERT INTO Chat (id, groupName) VALUES
                                     ('chat1', 'Group A'),
                                     ('chat2', 'Group B');

-- Associate Users with Chats (UserChat Junction Table)
INSERT INTO UserChat (chatId, userId) VALUES
                                          ('chat1', 'user1'),
                                          ('chat1', 'user2'),
                                          ('chat2', 'user2'),
                                          ('chat2', 'user3');
