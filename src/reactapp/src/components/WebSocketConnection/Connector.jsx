import { over } from 'stompjs';
import SockJS from 'sockjs-client';

var stompClient;
const Connector = (userId, chatId, history, setHistory) => {
    const makeConnection = () => {
        if (!stompClient) {
            const sock = new SockJS('http://localhost:8800/stomp-endpoint');
            stompClient = over(sock);
            stompClient.connect({}, onConnected, onError);
        } else {
            console.log(
                'WS Connection request raises, but already connected...'
            );
        }
    };

    const onConnected = () => {
        //stompClient.subscribe('/', onMessageReceived);
        stompClient.subscribe('/topic/message', onPrivateMessage);
    };

    const fetchAllMessages = () => {
        if (stompClient) {
            const messageDTO = {
                id: '',
                body: '',
                isRead: false,
                isReceived: false,
                creationDateTime: '',
                chatId: chatId,
                userId: userId,
            };
            stompClient.send('/app/all', {}, JSON.stringify(messageDTO));
        }
    };

    const onPrivateMessage = (payload) => {
        console.log(payload);
        const body = JSON.parse(payload.body);
        const header = payload.headers;
        body['type'] = 'received';
        console.log('msg: ', body);
        console.log('header: ', header);

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

    const sendValue = (message) => {
        if (stompClient) {
            const messageDTO = {
                id: '',
                body: message,
                isRead: false,
                isReceived: false,
                creationDateTime: '',
                chatId: chatId,
                userId: userId,
            };
            stompClient.send('/app/send', {}, JSON.stringify(messageDTO));
            messageDTO['type'] = 'send';

            // setHistory((history) => [...history, messageDTO]);
        }
    };

    return {
        connect: makeConnection,
        fetch: fetchAllMessages,
        send: sendValue,
    };
};

export default Connector;
