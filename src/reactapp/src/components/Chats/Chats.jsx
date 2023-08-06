import React from 'react';
import './Chats.css';
import USER from '../../assets/man.svg';

const Chats = ({ user, markChatActive }) => {
    const { id, chats, name, active } = user;
    return (
        <div className="chats-container">
            {chats.map((chat) => (
                <div
                    className={
                        chat?.active === true ? 'chat-row opened' : 'chat-row'
                    }
                    key={chat?.id}
                    data-chat-id={chat?.id}
                    onClick={(e) => {
                        const chatId = e.currentTarget.dataset['chatId'];
                        markChatActive(chatId);
                    }}
                >
                    {/* <div className="border-top"></div> */}
                    <img className="chat-user-icon" src={USER} />
                    <div className="chat-user-box">
                        <div className="user-mid">
                            <div className="chat-user-name">
                                {chat?.groupName}
                            </div>
                            {/* <div className="chat-user-last-msg">
                                How are you?
                            </div> */}
                        </div>
                        <div className="chat-last-msg-timestamp">Yesterday</div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default Chats;
