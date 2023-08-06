import React, { useEffect, useState } from 'react';
import './style.css';
import ReactDOM from 'react-dom/client';
import ChatRoom from './components/ChatRoom/ChatRoom';
import Login from './components/Login/Login';
import Home from './components/Home/Home';
import Connector from './components/WebSocketConnection/Connector';

const AppLayout = () => {
    const [user, setUser] = useState({});
    const [activeChatId, setActiveChatId] = useState('');
    const [history, setHistory] = useState([]);

    const markChatActive = (chatId) => {
        const activeChat = user?.chats?.map((chat) => {
            if (chat.id === chatId) {
                chat.active = true;
                setActiveChatId(chatId);
                return chat;
            }
            chat.active = false;
            return chat;
        });
        setUser({ ...user, chats: activeChat });
        setHistory([]);
    };

    useEffect(() => {
        if (Object.keys(user).length !== 0) {
            Connector(user.id, '', history, setHistory).connect();
        }
    }, [user]);

    useEffect(() => {
        console.log('History: ', history);
    }, [history]);

    return (
        <>
            {Object.keys(user).length === 0 ? (
                <Login setUser={setUser} />
            ) : (
                <Home
                    user={user}
                    markChatActive={markChatActive}
                    activeChatId={activeChatId}
                    history={history}
                    setHistory={setHistory}
                />
            )}
        </>
    );
};

const app = document.getElementById('app');

const root = ReactDOM.createRoot(app);

root.render(<AppLayout />);
