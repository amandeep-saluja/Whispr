-- Table: Message
CREATE TABLE "Message" (
                         id VARCHAR(255) PRIMARY KEY,
                         body VARCHAR(1000),
                         isRead BOOLEAN,
                         isReceived BOOLEAN,
                         creationDateTime TIMESTAMP,
                         chatId VARCHAR(255),
                         userId VARCHAR(255)
);

-- Table: User
CREATE TABLE "Users" (
                      id VARCHAR(255) PRIMARY KEY,
                      name VARCHAR(255),
                      isActive BOOLEAN
);

-- Table: Chat
CREATE TABLE "Chat" (
                      id VARCHAR(255) PRIMARY KEY,
                      groupName VARCHAR(255)
);

-- Table: UserChat (Junction Table)
CREATE TABLE "UserChat" (
                          chatId VARCHAR(255),
                          userId VARCHAR(255),
                          PRIMARY KEY (chatId, userId),
                          FOREIGN KEY (chatId) REFERENCES "Chat"(id),
                          FOREIGN KEY (userId) REFERENCES "Users"(id)
);