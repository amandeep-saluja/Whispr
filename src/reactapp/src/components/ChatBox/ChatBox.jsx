import React, { useEffect, useRef, useState } from 'react';
import './ChatBox.css';
import USER from '../../assets/man.svg';
import SRCH from '../../assets/search.png';
import BIN from '../../assets/trash3.svg';
import RIGHT_ARROW from '../../assets/triangle.png';
import { objIsEmpty, transformTime } from '../../utils/Helper';
import { useDispatch, useSelector } from 'react-redux';
import { initializeMessages } from '../../store/messageSlice';
import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import {
    CHAT_METADATA_CHANNEL,
    DELETE_CHAT,
    MESSAGE_ALL,
    MESSAGE_CHANNEL,
    MESSAGE_SEND,
    WS_URL,
} from '../../Constants';
import { removeChat } from '../../store/chatSlice';
import { removeChatId } from '../../store/userSlice';

var stompClient;
const ChatBox = () => {
    const user = useSelector((store) => store.user.currentUser);
    const { activeChatId, chats } = useSelector((store) => store.chat);
    const msgMap = useSelector((store) => store.message.chatMsgMap);

    const [myMsgs, setMyMsgs] = useState({});

    const dispatch = useDispatch();
    const initMessages = (msgArray) => {
        setMyMsgs(msgArray);
        dispatch(initializeMessages(msgArray));
    };
    const messagesEndRef = useRef(null);
    const latestMsg = useRef();
    latestMsg.msgs = msgMap;

    const [message, setMessage] = useState('');
    const { id, name } = user;

    useEffect(() => {
        if (!objIsEmpty(user)) {
            makeConnection();
        }
    }, [user]);

    useEffect(() => {
        console.log('activeChatId: ', activeChatId);
    }, [activeChatId]);

    const makeConnection = () => {
        if (!stompClient) {
            const sock = new SockJS(WS_URL);
            stompClient = over(sock);
            stompClient.connect({}, onConnected, (err) => {
                console.error(err);
                stompClient == undefined;
            });
        } else {
            console.log(
                'WS Connection request raises, but already connected...'
            );
        }
    };

    const onConnected = () => {
        stompClient.subscribe(MESSAGE_CHANNEL(user.id), onChatHistory);
        stompClient.subscribe(CHAT_METADATA_CHANNEL(user.id), onChatHistory);
        fetchAllMessages();
    };

    const fetchAllMessages = () => {
        user?.chatIds?.map((chat) => {
            if (stompClient) {
                const messageDTO = {
                    chatId: chat,
                    senderId: user.id,
                };
                stompClient.send(MESSAGE_ALL, {}, JSON.stringify(messageDTO));
            }
        });
    };

    const onChatHistory = (payload) => {
        const body = JSON.parse(payload.body);
        const header = payload.headers;
        const id = Array.isArray(body) ? body[0]?.chatId : body.chatId;
        console.log(latestMsg.msgs);

        if (id) {
            if (Array.isArray(body)) {
                const receivedMessages = body.map((msg) => {
                    msg.receivedUserId = msg?.receivedUserId
                        ? [...msg.receivedUserId, user.id]
                        : [user.id];
                    return msg;
                });
                initMessages({ ...latestMsg.msgs, [id]: receivedMessages });
            } else {
                body.receivedUserId = body?.receivedUserId
                    ? [...body.receivedUserId, user.id]
                    : [user.id];
                initMessages({
                    ...latestMsg.msgs,
                    [id]: [
                        ...(latestMsg.msgs[id] ? latestMsg.msgs[id] : []),
                        body,
                    ],
                });
            }
        }
    };

    const sendMessage = (message, chatId, id) => {
        if (stompClient) {
            const messageDTO = {
                content: message,
                chatId: chatId,
                senderId: id,
            };
            stompClient.send(MESSAGE_SEND, {}, JSON.stringify(messageDTO));
            messageDTO['type'] = 'send';
        }
    };

    useEffect(() => {
        scrollToBottom();
        console.log('Messages: ', msgMap);
    }, [msgMap]);

    useEffect(() => {
        //getAllUsersOfChatRoom();
        //Connector(id, activeChatId, msgMap, initMessages).fetch();
        scrollToBottom();
        if (activeChatId != '') {
            //Connector(user, msgMap, initMessages).read(activeChatId);
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
                    {chats[activeChatId]?.isGroup
                        ? chats[activeChatId]?.name
                        : chats[activeChatId]?.userDetails.filter(
                              (u) => u.userId != id
                          )[0]?.userName}
                </span>
                <img className="chat-box-search" src={SRCH} />
                <img
                    className="chat-box-delete"
                    src={BIN}
                    onClick={() => {
                        fetch(DELETE_CHAT + activeChatId, {
                            method: 'DELETE',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                        })
                            .then((response) => {
                                if (response.status == '200')
                                    return response.json();
                                return response;
                            })
                            .catch((err) => console.error(err))
                            .then((data) => {
                                console.log(
                                    `DELETE API FOR CHAT ID: ${activeChatId} RESPONSE: ${data}`
                                );
                                dispatch(removeChat(activeChatId));
                                dispatch(removeChatId(activeChatId));
                            });
                    }}
                />
            </div>
            <div className="chat-box-body">
                {msgMap &&
                    msgMap[activeChatId]?.map((msg, i) => (
                        <div
                            className={
                                msg?.senderId === id
                                    ? 'sent message-box'
                                    : 'received message-box'
                            }
                            key={i}
                        >
                            {chats[msg?.chatId]?.isGroup &&
                                msgMap[activeChatId][i - 1]?.senderId !=
                                    msg.senderId &&
                                msg.senderId != id && (
                                    <div className="user-name">
                                        {
                                            chats[
                                                activeChatId
                                            ]?.userDetails?.filter(
                                                (u) => u.userId == msg.senderId
                                            )[0]?.userName
                                        }
                                    </div>
                                )}
                            <div className="message">
                                {msg?.content?.split('\n').map((line, idx) => (
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
                            sendMessage(message, activeChatId, id);
                            setMessage('');
                        }
                        if (e.target.value.trim() == '') setMessage('');
                    }}
                />
                <img
                    className="chat-box-message-send"
                    src={RIGHT_ARROW}
                    onClick={() => {
                        sendMessage(message, activeChatId, id);
                        setMessage('');
                    }}
                />
            </div>
        </div>
    );
};

export default ChatBox;
