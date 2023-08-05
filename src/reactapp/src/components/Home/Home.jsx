import React from 'react';
import Chats from '../Chats/Chats';
import ChatRoom from '../ChatRoom/ChatRoom';
import './Home.css';
import ChatContainer from '../ChatContainer/ChatContainer';
import ChatBox from '../ChatBox/ChatBox';

const Home = ({ user }) => {
    return (
        <div className="home">
            <div className="header"></div>
            <div className="body"></div>
            <ChatContainer className={'chat-container-home'} />
            <ChatBox />
            {/* <div className="chat-box"></div> */}
            {/* <ChatRoom user={user} /> */}
        </div>
    );
};

export default Home;
