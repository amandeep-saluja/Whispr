import { over } from 'stompjs';
import SockJS from 'sockjs-client';

var stompClient;
const Connector = (user, history, setHistory) => {
    const makeConnection = () => {
        if (!stompClient) {
            const sock = new SockJS('http://localhost:8800/stomp-endpoint');
            stompClient = over(sock);
            stompClient.connect({}, onConnected, onError);
        } else {
            console.log('WS Connection request raises, but already connected...');
        }
    };

    const onConnected = () => {
        //stompClient.subscribe('/topic/message', onBroadcastMessage);
        stompClient.subscribe(`/user/${user.id}/message`, onChatHistory);
        stompClient.subscribe(`/user/${user.id}/private`, onGroupChatMessage);
        //stompClient.subscribe(`/user/${user.id}/message-read`, onReadHandler);
        //stompClient.subscribe(`/user/${user.id}/message-received`,onReceivedHandler);
        fetchAllMessages();
    };

    const sendReceivedAck = (msgHistory, chatId) => {
        /*user.chats.map((chat) => {
            if (!chat.receivedAck) {
                msgHistory[chat.id]
                    .filter((m) => m.isReceived == true)
                    .map((m) => m.id);

                if (stompClient && msgIds.length > 0) {
                    stompClient.send(
                        '/app/received',
                        {},
                        JSON.stringify(msgIds)
                    );
                    chat.receivedAck = true;
                }
            }
            return chat;
        });*/

        const msgIds = msgHistory
            .filter((m) => m.received == false)
            .map((m) => m.id);

        const dataToSend = {
            messageIds: msgIds,
            userId: user.id,
            chatId: chatId,
        };

        if (stompClient && msgIds.length > 0) {
            stompClient.send('/app/received', {}, JSON.stringify(dataToSend));
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
            stompClient.send('/app/read', {}, JSON.stringify(dataToSend));
        }
    };

    const onReadHandler = (payload) => {
        //console.log(`on read hander: ${payload}`);
        const body = JSON.parse(payload.body);
        const header = payload.headers;
        const id = Array.isArray(body) ? body[0]?.chatId : body.chatId;

        // setHistory((history) => [
        //     ...history[id].map((m) => {
        //         if (Array.isArray(body) && body.includes(m.id)) {
        //             m.read = true;
        //         } else if (body == m.id) {
        //             m.read = true;
        //         }
        //         return m;
        //     }),
        // ]);
    };

    const onReceivedHandler = (payload) => {
        //console.log(`on received hander: ${payload}`);
        const body = JSON.parse(payload.body);
        const header = payload.headers;
        const id = Array.isArray(body) ? body[0]?.chatId : body.chatId;

        // setHistory((history) => [
        //     ...history[id].map((m) => {
        //         if (Array.isArray(body) && body.includes(m.id)) {
        //             m.received = true;
        //         } else if (body == m.id) {
        //             m.received = true;
        //         }
        //         return m;
        //     }),
        // ]);
    };

    const onGroupChatMessage = (payload) => {
        //console.log(payload);
        const body = JSON.parse(payload.body);
        const header = payload.headers;
        const id = Array.isArray(body) ? body[0]?.chatId : body.chatId;

        //console.log('msg: ', body);
        //console.log('header: ', header);
        if (id) {
            if (Array.isArray(body)) {
                setHistory((history) => ({ ...history, [id]: body }));
            } else {
                setHistory((history) => ({
                    ...history,
                    [id]: [...(history[id] ? history[id] : []), body],
                }));
            }

            //console.log(`history after set: ${history}`);
        }
    };

    const fetchAllMessages = () => {
        user.chats.map((chat) => {
            if (stompClient) {
                const messageDTO = {
                    id: '',
                    body: '',
                    isRead: false,
                    isReceived: false,
                    creationDateTime: '',
                    chatId: chat.id,
                    userId: user.id,
                };
                stompClient.send('/app/all', {}, JSON.stringify(messageDTO));
            }
        });
    };

    const onChatHistory = (payload) => {
        //console.log(payload);
        const body = JSON.parse(payload.body);
        const header = payload.headers;
        const id = Array.isArray(body) ? body[0]?.chatId : body.chatId;
        //console.log('msg: ', body);
        //console.log('header: ', header);

        if (id) {
            /*if (Array.isArray(body)) {
                setHistory((history) => [...history, ...body]);
            } else {
                setHistory((history) => [...history, body]);
            }*/
            if (Array.isArray(body)) {
                setHistory((history) => ({ ...history, [id]: body }));
            } else {
                setHistory((history) => ({
                    ...history,
                    [id]: [...(history[id] ? history[id] : []), body],
                }));
            }
            //setHistory((history) => ({ ...history, [id]: body }));

            //console.log(`history after set: ${history}`);

            //sendReceivedAck(body, id);

            //console.log(`received: history after set: ${history}`);
        }
    };

    const onMessageReceived = (payload) => {
        const payloadData = JSON.parse(payload);
        //console.log(payloadData);
    };

    const onError = (err) => {
        //console.log(err);
    };

    const sendValue = (message, chatId, id) => {
        if (stompClient) {
            const messageDTO = {
                id: '',
                body: message,
                isRead: false,
                isReceived: false,
                creationDateTime: '',
                chatId: chatId,
                userId: id,
            };
            stompClient.send(
                '/app/private-message',
                {},
                JSON.stringify(messageDTO)
            );
            messageDTO['type'] = 'send';

            // setHistory((history) => [...history, messageDTO]);
        }
    };

    return {
        connect: makeConnection,
        fetch: fetchAllMessages,
        send: sendValue,
        read: sendReadAck,
    };
};

export default Connector;
