import { createSlice } from '@reduxjs/toolkit';

const messageSlice = createSlice({
    name: 'message',
    initialState: {
        chatMsgMap: {},
        lastMsgMap: {},
    },
    reducers: {
        initializeMessages: (state, action) => {
            /*const msgArray = action.payload;
            msgArray.forEach(
                (msg) =>
                    (state.messageMap[msg.chatId] = [
                        ...state.messageMap[msg.chatId],
                        msg,
                    ])
            );
            const lastMsg = msgArray[msgArray.length - 1];
            state.lastMsgMap[lastMsg.chatId] = lastMsg;*/
            state.chatMsgMap = action.payload;
        },
        addMessage: (state, action) => {
            /*const msg = action.payload;
            state.messageMap[msg.chatId] = [
                ...state.messageMap[msg.chatId],
                msg,
            ];
            state.lastMsgMap[msg.chatId] = msg;
            */
            state.chatMsgMap = action.payload;
        },
    },
});

export const { addMessage, initializeMessages } = messageSlice.actions;

export default messageSlice.reducer;
