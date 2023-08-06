import React from 'react';
import Chats from '../Chats/Chats';
import ChatRoom from '../ChatRoom/ChatRoom';
import './Home.css';
import ChatContainer from '../ChatContainer/ChatContainer';
import ChatBox from '../ChatBox/ChatBox';

const Home = ({ user, markChatActive, activeChatId, history, setHistory }) => {
    return (
        <div className="home">
            <div className="header"></div>
            <div className="body"></div>
            <ChatContainer
                className={'chat-container-home'}
                user={user}
                markChatActive={markChatActive}
            />
            <ChatBox
                user={user}
                activeChatId={activeChatId}
                history={history}
                setHistory={setHistory}
            />
            {/* <div className="chat-box"></div> */}
            {/* <ChatRoom user={user} /> */}
        </div>
    );
};

export default Home;
