import React, { useEffect, useRef, useState } from 'react';
import './ChatRoom.css';
import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import { transformTime } from '../utils/Helper';

var stompClient;
const ChatRoom = () => {
    const messagesEndRef = useRef(null);
    const [message, setMessage] = useState('');
    const [history, setHistory] = useState([]);
    const [connection, setConnection] = useState(false);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    };

    useEffect(() => {
        scrollToBottom();
    }, [history]);

    const makeConnection = () => {
        const sock = new SockJS('http://localhost:8800/stomp-endpoint');
        stompClient = over(sock);
        stompClient.connect({}, onConnected, onError);
    };

    const onConnected = () => {
        setConnection(true);
        //stompClient.subscribe('/', onMessageReceived);
        stompClient.subscribe('/topic/message', onPrivateMessage);
        fetchAllMessages();
    };

    const fetchAllMessages = () => {
        if (stompClient) {
            const messageDTO = {
                id: '',
                body: message,
                isRead: false,
                isReceived: false,
                creationDateTime: '',
                chatId: '100',
                userId: '1',
            };
            stompClient.send('/app/all', {}, JSON.stringify(messageDTO));
        }
        setMessage('');
    };

    const onPrivateMessage = (payload) => {
        console.log(payload);
        const body = JSON.parse(payload.body);
        const header = payload.headers;
        body['type'] = 'received';
        console.log('msg: ', body);
        console.log('header: ', header);
        console.log(
            `history before set: ${history} now setting this ${[
                ...history,
                body,
            ]}`
        );
        if (Array.isArray(body)) {
            setHistory((history) => [...history, ...body]);
        } else {
            setHistory((history) => [...history, body]);
        }

        console.log(`history after set: ${history}`);
    };

    const onMessageReceived = (payload) => {
        const payloadData = JSON.parse(payload);
        console.log(payloadData);
    };

    const onError = (err) => {
        console.log(err);
    };

    const sendValue = () => {
        if (stompClient) {
            const messageDTO = {
                id: '',
                body: message,
                isRead: false,
                isReceived: false,
                creationDateTime: '',
                chatId: '100',
                userId: '1',
            };
            stompClient.send('/app/send', {}, JSON.stringify(messageDTO));
            messageDTO['type'] = 'send';
            console.log(
                `history before set: ${history} now setting this ${[
                    ...history,
                    messageDTO,
                ]}`
            );
            console.log(setHistory, history);
            setHistory((history) => [...history, messageDTO]);
            console.log(`history after set: ${history}`);
        }
        setMessage('');
    };
    console.log(history);

    // const sendMessage = () => {
    //     sendValue();
    //     setMessage('');
    // };

    return (
        <div className="container">
            {!connection ? (
                <>
                    <button
                        className="connect-btn"
                        onClick={() => {
                            makeConnection();
                        }}
                    >
                        Connect
                    </button>
                </>
            ) : (
                <>
                    <div className="message-container">
                        <div className="message-box">
                            <input
                                className="message-input"
                                type="text"
                                placeholder="Enter a message...."
                                onChange={(e) => setMessage(e.target.value)}
                                value={message}
                            />
                            <button onClick={sendValue} className="message-btn">
                                Send Message
                            </button>
                        </div>
                        <div className="messages">
                            {history?.length === 0 ? (
                                'No msg sent'
                            ) : (
                                <>
                                    {history?.map((m, idx) => (
                                        <div
                                            className={
                                                m?.type == 'send'
                                                    ? 'msg-sent message'
                                                    : 'msg-received message'
                                            }
                                            key={idx}
                                        >
                                            {m?.body}
                                            <span className="timestamp">
                                                {transformTime(
                                                    m?.creationDateTime
                                                )}
                                            </span>
                                        </div>
                                    ))}
                                </>
                            )}
                            <div ref={messagesEndRef} />
                        </div>
                    </div>
                </>
            )}
        </div>
    );
};

export default ChatRoom;
