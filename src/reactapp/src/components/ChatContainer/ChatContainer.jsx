import React, { useEffect, useState } from 'react';
import './ChatContainer.css';
import Chats from '../Chats/Chats';
import USER from '../../assets/man.svg';
import GROUP from '../../assets/group.svg';
import MENU from '../../assets/three-dots.svg';
import MSG from '../../assets/message.svg';
import SRCH from '../../assets/search.png';
import NewChat from '../NewChat/NewChat';
import { useDispatch, useSelector } from 'react-redux';
import { initializeUsers } from '../../store/userSlice';
import useAllUsers from '../../hooks/useAllUsers';
import useFetchChatsForUserId from '../../hooks/useFetchChatsForUserId';
import { initializeChats } from '../../store/chatSlice';

const ChatContainer = () => {
    const user = useSelector((store) => store.user.currentUser);
    const { id, chatIds, name, isActive } = user;
    const dispatch = useDispatch();
    const users = useAllUsers();
    const chats = useFetchChatsForUserId(id);

    useEffect(() => {
        if (users != null && users.length > 0) {
            dispatch(initializeUsers(users));
        }
    }, [users]);

    useEffect(() => {
        if (chats?.length > 0) {
            dispatch(initializeChats(chats));
        }
    }, [chats]);

    const [openNewChatPage, setOpenNewChatPage] = useState(false);

    return (
        <div className="chat-container" data-id={id}>
            {openNewChatPage ? (
                <NewChat user={user} setOpenNewChatPage={setOpenNewChatPage} />
            ) : (
                <>
                    <div className="chat-header">
                        <div className="profile">
                            <img className="profile-pic" src={USER} />
                            <span className="name">{name}</span>
                            <img
                                className="new-chat"
                                src={MSG}
                                onClick={() => setOpenNewChatPage(true)}
                            />
                            <img className="new-group" src={GROUP} />
                            <img alt="Menu" className="menu" src={MENU} />
                        </div>
                        <div className="chat-search">
                            <img src={SRCH} className="search-icon" />
                            <input
                                type="text"
                                className="chat-search-input"
                                placeholder="Search or start new chat"
                            />
                        </div>
                    </div>
                    <Chats user={user} />
                </>
            )}
        </div>
    );
};

export default ChatContainer;
