import React, { useEffect, useRef, useState } from 'react';
import './ChatBox.css';
import USER from '../../assets/man.svg';
import SRCH from '../../assets/search.png';
import RIGHT_ARROW from '../../assets/triangle.png';
import Connector from '../WebSocketConnection/Connector';
import { transformTime } from '../../utils/Helper';

const ChatBox = ({
    user,
    activeChatId,
    history,
    setHistory,
    activeChatUsers,
}) => {
    const { id, chats, name, active } = user;

    const messagesEndRef = useRef(null);

    const [message, setMessage] = useState('');

    useEffect(() => {
        scrollToBottom();
        //console.log(history);
    }, [history]);

    useEffect(() => {
        //getAllUsersOfChatRoom();
        //Connector(id, activeChatId, history, setHistory).fetch();
        scrollToBottom();
        if (activeChatId != '') {
            //Connector(user, history, setHistory).read(activeChatId);
        }
    }, [activeChatId]);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'instant' });
    };

    return activeChatId == '' ? (
        <div className="chat-msg">Select a chat to start conversation</div>
    ) : (
        <div className="chat-box-container">
            <div className="chat-box-header">
                <img className="chat-box-profile-pic" src={USER} />
                <span className="chat-box-name">
                    {chats?.filter((chat) => chat?.id === activeChatId)
                        ?.length > 0
                        ? chats?.filter((chat) => chat?.id === activeChatId)[0]
                              ?.groupName != ''
                            ? chats?.filter(
                                  (chat) => chat?.id === activeChatId
                              )[0]?.groupName
                            : activeChatUsers?.filter((u) => u.id != id)[0]
                                  ?.name
                        : ''}
                </span>
                <img className="chat-box-search" src={SRCH} />
            </div>
            <div className="chat-box-body">
                {history &&
                    history[activeChatId]?.map((msg, i) => (
                        <div
                            className={
                                msg?.userId === id
                                    ? 'sent message-box'
                                    : 'received message-box'
                            }
                            key={i}
                        >
                            {chats?.filter((c) => c.id == msg?.chatId)[0]
                                ?.groupName != '' &&
                                history[activeChatId][i - 1]?.userId !=
                                    msg.userId &&
                                msg.userId != id && (
                                    <div className="user-name">
                                        {
                                            activeChatUsers?.filter(
                                                (u) => u.id === msg.userId
                                            )[0]?.name
                                        }
                                    </div>
                                )}
                            <div className="message">
                                {msg?.body?.split('\n').map((line, idx) => (
                                    <p key={idx}>{line}</p>
                                ))}
                            </div>
                            <div className="time">
                                {transformTime(new Date(msg?.creationDateTime))}
                            </div>
                        </div>
                    ))}
                <div ref={messagesEndRef} />
            </div>
            <div className="chat-box-footer">
                <textarea
                    cols={40}
                    rows={1}
                    className="chat-box-message-input"
                    placeholder="Type a message"
                    value={message}
                    onChange={(e) => {
                        setMessage(e.target.value);
                    }}
                    onKeyDown={(e) => {
                        if (e.key == 'Enter' && e.target.value.trim() != '') {
                            Connector(id, history, setHistory).send(
                                message,
                                activeChatId,
                                id
                            );
                            setMessage('');
                        }
                        if (e.target.value.trim() == '') setMessage('');
                    }}
                />
                <img
                    className="chat-box-message-send"
                    src={RIGHT_ARROW}
                    onClick={() => {
                        Connector(user, history, setHistory).send(
                            message,
                            activeChatId,
                            id
                        );
                        setMessage('');
                    }}
                />
            </div>
        </div>
    );
};

export default ChatBox;
