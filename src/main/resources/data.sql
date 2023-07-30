-- Insert Users
INSERT INTO "Users" (id, name, isActive) VALUES
                                          ('user1', 'Alice', true),
                                          ('user2', 'Bob', true),
                                          ('user3', 'Charlie', true);

-- Insert Chats
INSERT INTO "Chat" (id, groupName) VALUES
                                     ('chat1', 'Group A'),
                                     ('chat2', 'Group B');

-- Associate Users with Chats (UserChat Junction Table)
INSERT INTO "UserChat" (chatId, userId) VALUES
                                          ('chat1', 'user1'),
                                          ('chat1', 'user2'),
                                          ('chat2', 'user2'),
                                          ('chat2', 'user3');
