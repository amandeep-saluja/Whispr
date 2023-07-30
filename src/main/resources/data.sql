INSERT INTO USERS (id, name, is_active)
SELECT 'user1', 'Alice', true
WHERE NOT EXISTS (SELECT 1 FROM USERS WHERE id = 'user1');

INSERT INTO USERS (id, name, is_active)
SELECT 'user2', 'Bob', true
WHERE NOT EXISTS (SELECT 1 FROM USERS WHERE id = 'user2');

INSERT INTO USERS (id, name, is_active)
SELECT 'user3', 'Charlie', true
WHERE NOT EXISTS (SELECT 1 FROM USERS WHERE id = 'user3');

--

INSERT INTO CHAT (id, group_name)
SELECT 'CHAT1', 'Group A'
WHERE NOT EXISTS (SELECT 1 FROM CHAT WHERE id = 'CHAT1');

INSERT INTO CHAT (id, group_name)
SELECT 'CHAT2', 'Group B'
WHERE NOT EXISTS (SELECT 1 FROM CHAT WHERE id = 'CHAT2');

--

-- Insert into USERCHAT only if the combination of CHAT_id and user_id doesn't already exist
INSERT INTO USERCHAT (CHAT_id, user_id)
SELECT 'CHAT1', 'user1'
WHERE NOT EXISTS (SELECT 1 FROM USERCHAT WHERE CHAT_id = 'CHAT1' AND user_id = 'user1');

INSERT INTO USERCHAT (CHAT_id, user_id)
SELECT 'CHAT1', 'user2'
WHERE NOT EXISTS (SELECT 1 FROM USERCHAT WHERE CHAT_id = 'CHAT1' AND user_id = 'user2');

INSERT INTO USERCHAT (CHAT_id, user_id)
SELECT 'CHAT2', 'user2'
WHERE NOT EXISTS (SELECT 1 FROM USERCHAT WHERE CHAT_id = 'CHAT2' AND user_id = 'user2');

INSERT INTO USERCHAT (CHAT_id, user_id)
SELECT 'CHAT2', 'user3'
WHERE NOT EXISTS (SELECT 1 FROM USERCHAT WHERE CHAT_id = 'CHAT2' AND user_id = 'user3');
