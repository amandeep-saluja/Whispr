import React, { useEffect, useRef, useState } from 'react';
import './ChatBox.css';
import USER from '../../assets/man.svg';
import SRCH from '../../assets/search.png';
import RIGHT_ARROW from '../../assets/triangle.png';
import Connector from '../WebSocketConnection/Connector';
import { transformTime } from '../../utils/Helper';

const ChatBox = ({ user, activeChatId, history, setHistory }) => {
    const { id, chats, name, active } = user;

    const messagesEndRef = useRef(null);

    const [message, setMessage] = useState('');

    useEffect(() => {
        scrollToBottom();
        console.log(history);
    }, [history]);

    useEffect(() => {
        Connector(id, activeChatId, history, setHistory).fetch();
    }, [activeChatId]);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    };

    return (
        <div className="chat-box-container">
            <div className="chat-box-header">
                <img className="chat-box-profile-pic" src={USER} />
                <span className="chat-box-name">
                    {chats?.filter((chat) => chat?.id === activeChatId)
                        ?.length > 0
                        ? chats?.filter((chat) => chat?.id === activeChatId)[0]
                              ?.groupName
                        : ''}
                </span>
                <img className="chat-box-search" src={SRCH} />
            </div>
            <div className="chat-box-body">
                {history?.map((msg, i) => (
                    <div
                        className={
                            msg?.userId === id
                                ? 'sent message-box'
                                : 'received message-box'
                        }
                        key={i}
                    >
                        <div className="message">{msg?.body}</div>
                        <div className="time">
                            {transformTime(new Date(msg?.creationDateTime))}
                        </div>
                    </div>
                ))}
                {/* {Array(10)
                    .fill(1)
                    .map((x, i) => (
                        <div className="message-box received" key={i}>
                            <div className="message">Hi How are you</div>
                            <div className="time">9:00 pm</div>
                        </div>
                    ))}
                {Array(10)
                    .fill(1)
                    .map((x, i) => (
                        <div className="message-box sent" key={i}>
                            <div className="message">Hi How are you</div>
                            <div className="time">9:00 pm</div>
                        </div>
                    ))} */}
                <div ref={messagesEndRef} />
            </div>
            <div className="chat-box-footer">
                <input
                    type="text"
                    className="chat-box-message-input"
                    placeholder="Type a message"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                />
                <img
                    className="chat-box-message-send"
                    src={RIGHT_ARROW}
                    onClick={() => {
                        Connector(id, activeChatId, history, setHistory).send(
                            message
                        );
                        setMessage('');
                    }}
                />
            </div>
        </div>
    );
};

export default ChatBox;
