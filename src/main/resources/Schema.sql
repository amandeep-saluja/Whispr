-- Table: MESSAGE
CREATE TABLE IF NOT EXISTS MESSAGE (
    id VARCHAR(255) PRIMARY KEY,
    body VARCHAR(1000),
    is_read BOOLEAN,
    is_received BOOLEAN,
    creation_date_time TIMESTAMP,
    chat_id VARCHAR(255),
    user_id VARCHAR(255)
    );

-- Table: USERS
CREATE TABLE IF NOT EXISTS USERS (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    is_active BOOLEAN
    );

-- Table: Chat
CREATE TABLE IF NOT EXISTS Chat (
    id VARCHAR(255) PRIMARY KEY,
    group_name VARCHAR(255)
    );

-- Table: USERCHAT (Junction Table)
CREATE TABLE IF NOT EXISTS USERCHAT (
    chat_id VARCHAR(255),
    user_id VARCHAR(255),
    PRIMARY KEY (chat_id, user_id),
    FOREIGN KEY (chat_id) REFERENCES Chat (id),
    FOREIGN KEY (user_id) REFERENCES USERS (id)
    );
