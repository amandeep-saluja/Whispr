import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import {
    MESSAGE_READ,
    MESSAGE_RECEIVED,
    MESSAGE_SEND,
    WS_URL,
    MESSAGE_ALL,
    MESSAGE_LISTENER,
} from '../../Constants';

var stompClient;
const Connector = (user, history, setHistory) => {
    const makeConnection = () => {
        if (!stompClient) {
            const sock = new SockJS(WS_URL);
            stompClient = over(sock);
            stompClient.connect({}, onConnected, onError);
        } else {
            console.log(
                'WS Connection request raises, but already connected...'
            );
        }
    };

    const onConnected = () => {
        stompClient.subscribe(MESSAGE_LISTENER(user.id), onChatHistory);
        fetchAllMessages();
    };

    const sendReceivedAck = (msgHistory, chatId) => {
        const msgIds = msgHistory
            .filter((m) => m.received == false)
            .map((m) => m.id);

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
        const body = JSON.parse(payload.body);
        const header = payload.headers;
        const id = Array.isArray(body) ? body[0]?.chatId : body.chatId;
    };

    const onReceivedHandler = (payload) => {
        const body = JSON.parse(payload.body);
        const header = payload.headers;
        const id = Array.isArray(body) ? body[0]?.chatId : body.chatId;
    };

    const onGroupChatMessage = (payload) => {
        const body = JSON.parse(payload.body);
        const header = payload.headers;
        const id = Array.isArray(body) ? body[0]?.chatId : body.chatId;
        //const history = getHistory();

        if (id) {
            if (Array.isArray(body)) {
                setHistory((history) => ({ ...history, [id]: body }));
            } else {
                setHistory((history) => ({
                    ...history,
                    [id]: [...(history[id] ? history[id] : []), body],
                }));
            }
        }
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

        if (id) {
            if (Array.isArray(body)) {
                setHistory({ ...history, [id]: body });
            } else {
                setHistory({
                    ...history,
                    [id]: [...(history[id] ? history[id] : []), body],
                });
            }
        }
    };

    const onMessageReceived = (payload) => {
        const payloadData = JSON.parse(payload);
    };

    const onError = (err) => {
        console.error(err);
    };

    const sendValue = (message, chatId, id) => {
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

    return {
        connect: makeConnection,
        fetch: fetchAllMessages,
        send: sendValue,
        read: sendReadAck,
    };
};

export default Connector;
