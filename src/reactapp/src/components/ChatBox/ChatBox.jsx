import React, { useEffect, useRef, useState } from 'react';
import './ChatBox.css';
import USER from '../../assets/man.svg';
import SRCH from '../../assets/search.png';
import BIN from '../../assets/trash3.svg';
import TICK from '../../assets/tick.svg';
import DOUBLE_TICK from '../../assets/double-check.svg';
import RIGHT_ARROW from '../../assets/triangle.png';
import { msgReceived, objIsEmpty, transformTime } from '../../utils/Helper';
import { useDispatch, useSelector } from 'react-redux';
import { initializeMessages } from '../../store/messageSlice';
import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import {
    DELETE_CHAT,
    MESSAGE_ALL,
    MESSAGE_LISTENER,
    MESSAGE_READ,
    MESSAGE_READ_RECEPIENT_LISTENER,
    MESSAGE_RECEIEVED_RECEPIENT_LISTENER,
    MESSAGE_RECEIVED,
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
        stompClient.subscribe(MESSAGE_LISTENER(user.id), onChatHistory);
        stompClient.subscribe(
            MESSAGE_RECEIEVED_RECEPIENT_LISTENER(user.id),
            onReceivedHandler
        );
        stompClient.subscribe(
            MESSAGE_READ_RECEPIENT_LISTENER(user.id),
            onReadHandler
        );
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
        //console.log(latestMsg.msgs);

        if (id) {
            if (Array.isArray(body)) {
                const msgIds = body
                    ?.filter((m) => m?.receivedUserId?.indexOf(id) != -1)
                    ?.map((m) => m.id);
                if (msgIds.length > 0) {
                    sendReceivedAck(msgIds, id);
                }
                const receivedMessages = body?.map((msg) => {
                    if (msg?.receivedUserId?.indexOf(id) != -1) {
                        msg.receivedUserId = msg?.receivedUserId
                            ? [...msg.receivedUserId, user.id]
                            : [user.id];
                    }
                    return msg;
                });
                initMessages({ ...latestMsg.msgs, [id]: receivedMessages });
            } else {
                if (body.receivedUserId?.indexOf(user.id) == -1) {
                    sendReceivedAck([body.id], id);
                    body.receivedUserId = body?.receivedUserId
                        ? [...body.receivedUserId, user.id]
                        : [user.id];
                }
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

    const sendReceivedAck = (msgIds, chatId) => {
        // const msgIds = msgHistory
        //     .filter((m) => m.received == false)
        //     .map((m) => m.id);

        const dataToSend = {
            messageIds: msgIds,
            userId: user.id,
            chatId: chatId,
        };

        if (stompClient && msgIds.length > 0) {
            stompClient.send(MESSAGE_RECEIVED, {}, JSON.stringify(dataToSend));
        }
    };

    const sendReadAck = (chatId) => {
        const msgIds = history[chatId]
            .filter((m) => m.read == false)
            .map((m) => m.id);

        const dataToSend = {
            messageIds: msgIds,
            userId: user.id,
            chatId: chatId,
        };

        if (stompClient && msgIds.length > 0) {
            stompClient.send(MESSAGE_READ, {}, JSON.stringify(dataToSend));
        }
    };

    const onReadHandler = (payload) => {
        const msgIds = JSON.parse(payload.body);
        const header = payload.headers;

        console.log(msgIds);

        //msgMap[activeChatId]?
        msgIds?.map((mId) => {
            let flag = true;
            const msgObj = msgMap[activeChatId]?.filter((m) => {
                if (flag && m.id === mId) {
                    flag = false; // no need to filter again -> skip
                    return true; // msg found so true
                }
                return false; // not found -> skip
            });
        });
    };

    const onReceivedHandler = (payload) => {
        const body = JSON.parse(payload.body);
        const header = payload.headers;
        const id = Array.isArray(body) ? body[0]?.chatId : body.chatId;
        console.log(body, id);
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
                            {msgReceived(chats[activeChatId], msg) ? (
                                <img
                                    alt="double tick"
                                    src={DOUBLE_TICK}
                                    className="double-tick"
                                />
                            ) : (
                                <img
                                    alt="tick"
                                    src={TICK}
                                    className="single-tick"
                                />
                            )}
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
