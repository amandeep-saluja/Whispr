import React from 'react';
import './Chats.css';
import USER from '../../assets/man.svg';
import { transformTime } from '../../utils/Helper';
import useFetchChatUsers from '../../hooks/useFetchChatUsers';
import { useDispatch, useSelector } from 'react-redux';
import { setActiveChat } from '../../store/chatSlice';

const Chats = ({ user }) => {
    const { id, chatIds, name, isActive } = user;
    const { chats, activeChatId } = useSelector((store) => store.chat);
    const msgMap = useSelector((store) => store.message.chatMsgMap);

    const dispatch = useDispatch();

    const markChatActive = (selectedChatId) => {
        if (selectedChatId) {
            dispatch(setActiveChat(selectedChatId));
        }
    };

    return (
        <div className="chats-container">
            {chatIds?.map((chatId) => (
                <div
                    className={
                        chatId === activeChatId ? 'chat-row opened' : 'chat-row'
                    }
                    key={chatId}
                    data-chat-id={chatId}
                    onClick={(e) => {
                        const chatId = e.currentTarget.dataset['chatId'];
                        markChatActive(chatId);
                        //call all the message read which are unread
                        const msgIds = msgMap[chatId]
                            ?.filter((m) => m?.readUserId?.indexOf(id) != -1)
                            ?.map((m) => m.id);
                        console.log(msgIds);
                    }}
                >
                    {/* <div className="border-top"></div> */}
                    <img className="chat-user-icon" src={USER} />
                    <div className="chat-user-box">
                        <div className="user-mid">
                            <div className="chat-user-name">
                                {chats[chatId]?.isGroup
                                    ? chats[chatId]?.name
                                    : chats[chatId]?.userDetails.filter(
                                          (u) => u.userId != id
                                      )[0]?.userName}
                            </div>
                            <div className="chat-user-last-msg">
                                {msgMap[chatId]?.slice(-1)[0]?.content}
                            </div>
                        </div>
                        <div className="chat-last-msg-timestamp">
                            <span>
                                {transformTime(
                                    msgMap[chatId]?.slice(-1)[0]
                                        ?.creationDateTime
                                )}
                            </span>
                            {msgMap[chatId]?.filter((m) => !m?.isRead).length >
                                0 && (
                                <span className="chat-unread-count">
                                    {
                                        msgMap[chatId]?.filter(
                                            (m) =>
                                                m?.readUserId?.indexOf(id) != -1
                                        ).length
                                    }
                                </span>
                            )}
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default Chats;
