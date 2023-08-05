import React from 'react';
import './ChatContainer.css';
import Chats from '../Chats/Chats';
import USER from '../../assets/man.svg';
import GROUP from '../../assets/group.svg';
import MSG from '../../assets/message.svg';
import SRCH from '../../assets/search.png';

const ChatContainer = ({ user }) => {
    return (
        <div className="chat-container">
            <div className="chat-header">
                <div className="profile">
                    <img className="profile-pic" src={USER} />
                    <span className="name">Amandeep</span>
                    <img className="new-chat" src={MSG} />
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
            <Chats user={user} />
        </div>
    );
};

export default ChatContainer;
