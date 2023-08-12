import React from 'react';
import './Chats.css';
import USER from '../../assets/man.svg';
import { transformTime } from '../../utils/Helper';
import useFetchChatUsers from '../../hooks/useFetchChatUsers';

const Chats = ({ user, markChatActive, history, activeChatUsers }) => {
    const { id, chats, name, active } = user;
    return (
        <div className="chats-container">
            {chats?.map((chat) => (
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
                                {chat?.groupName == ''
                                    ? useFetchChatUsers(chat?.id)?.filter(
                                          (u) => u.id != id
                                      )[0]?.name
                                    : chat.groupName}
                            </div>
                            <div className="chat-user-last-msg">
                                {history[chat?.id]?.slice(-1)[0].body}
                            </div>
                        </div>
                        <div className="chat-last-msg-timestamp">
                            <span>
                                {transformTime(
                                    history[chat?.id]?.slice(-1)[0]
                                        .creationDateTime
                                )}
                            </span>
                            {history[chat?.id]?.filter((m) => !m?.isRead)
                                .length > 0 && (
                                <span className="chat-unread-count">
                                    {
                                        history[chat?.id]?.filter(
                                            (m) => !m?.isRead
                                        ).length
                                    }
                                </span>
                            )}
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default Chats;
