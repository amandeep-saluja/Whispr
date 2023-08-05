import React from 'react';
import './ChatBox.css';
import USER from '../../assets/man.svg';
import SRCH from '../../assets/search.png';
import RIGHT_ARROW from '../../assets/triangle.png';

const ChatBox = () => {
    return (
        <div className="chat-box-container">
            <div className="chat-box-header">
                <img className="chat-box-profile-pic" src={USER} />
                <span className="chat-box-name">Rahul Kumar</span>
                <img className="chat-box-search" src={SRCH} />
            </div>
            <div className="chat-box-body">
                {Array(10)
                    .fill(1)
                    .map((x) => (
                        <div className="message-box received">
                            <div className="message">Hi How are you</div>
                            <div className="time">9:00 pm</div>
                        </div>
                    ))}
                {Array(10)
                    .fill(1)
                    .map((x) => (
                        <div className="message-box sent">
                            <div className="message">Hi How are you</div>
                            <div className="time">9:00 pm</div>
                        </div>
                    ))}
            </div>
            <div className="chat-box-footer">
                <input
                    type="text"
                    className="chat-box-message-input"
                    placeholder="Type a message"
                />
                <img className="chat-box-message-send" src={RIGHT_ARROW} />
            </div>
        </div>
    );
};

export default ChatBox;
