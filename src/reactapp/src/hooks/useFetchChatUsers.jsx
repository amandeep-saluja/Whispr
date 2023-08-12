import React, { useEffect, useState } from 'react';

const useFetchChatUsers = (activeChatId) => {
    const [chatUsers, setChatUsers] = useState([]);

    useEffect(() => {
        getAllUsersOfChatRoom();
    }, [activeChatId]);

    const getAllUsersOfChatRoom = () => {
        if (activeChatId) {
            fetch(`http://localhost:8800/chat/${activeChatId}`)
                .then((response) => response.json())
                .catch((err) => console.error(err))
                .then((data) => setChatUsers(data));
        }
    };
    return chatUsers;
};

export default useFetchChatUsers;
