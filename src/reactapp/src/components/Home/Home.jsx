import React from 'react';
import Chats from '../Chats/Chats';
import ChatRoom from '../ChatRoom/ChatRoom';
import './Home.css';
import ChatContainer from '../ChatContainer/ChatContainer';
import ChatBox from '../ChatBox/ChatBox';
import useFetchChatUsers from '../../hooks/useFetchChatUsers';

const Home = ({
    user,
    markChatActive,
    activeChatId,
    history,
    setHistory,
    setUser,
}) => {
    const activeChatUsers = useFetchChatUsers(activeChatId);
    return (
        <div className="home">
            <div className="header"></div>
            <div className="body"></div>
            <ChatContainer
                className={'chat-container-home'}
                user={user}
                markChatActive={markChatActive}
                history={history}
                setUser={setUser}
                activeChatUsers={activeChatUsers}
            />
            <ChatBox
                user={user}
                activeChatId={activeChatId}
                history={history}
                setHistory={setHistory}
                activeChatUsers={activeChatUsers}
            />
        </div>
    );
};

export default Home;
