import React, { useState } from 'react';
import './ChatContainer.css';
import Chats from '../Chats/Chats';
import USER from '../../assets/man.svg';
import GROUP from '../../assets/group.svg';
import MSG from '../../assets/message.svg';
import SRCH from '../../assets/search.png';
import useAllUsers from '../../hooks/useAllUsers';
import NewChat from '../NewChat/NewChat';

const ChatContainer = ({ user, markChatActive, history, setUser, activeChatUsers }) => {
    const { id, chats, name, active } = user;

    const users = useAllUsers();

    const [openNewChatPage, setOpenNewChatPage] = useState(false);

    return (
        <div className="chat-container" data-id={id}>
            {openNewChatPage ? (
                <NewChat
                    id={id}
                    users={users}
                    setOpenNewChatPage={setOpenNewChatPage}
                    user={user}
                    setUser={setUser}
                />
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
                    <Chats
                        user={user}
                        markChatActive={markChatActive}
                        history={history}
                        activeChatUsers={activeChatUsers}
                    />
                </>
            )}
        </div>
    );
};

export default ChatContainer;
