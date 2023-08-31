import React from 'react';
import './Home.css';
import ChatContainer from '../ChatContainer/ChatContainer';
import ChatBox from '../ChatBox/ChatBox';

const Home = () => {
    return (
        <div className="home">
            <div className="header"></div>
            <div className="body"></div>
            <ChatContainer className={'chat-container-home'} />
            <ChatBox />
        </div>
    );
};

export default Home;
