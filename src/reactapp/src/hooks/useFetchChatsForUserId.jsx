import { useEffect, useState } from 'react';
import { CHATS_BY_USER_ID } from '../Constants';

const useFetchChatsForUserId = (id) => {
    const [chats, setChats] = useState([]);

    const fetchChatsByUserId = () => {
        fetch(CHATS_BY_USER_ID + id)
            .then((response) => response.json())
            .catch((err) => console.error(err))
            .then((data) => setChats(data));
    };

    useEffect(() => {
        fetchChatsByUserId();
    }, []);

    return chats;
};

export default useFetchChatsForUserId;
