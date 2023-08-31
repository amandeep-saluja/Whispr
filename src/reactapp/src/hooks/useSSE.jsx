import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { addChatId } from '../store/userSlice';
import { addNewChat } from '../store/chatSlice';

const useSSE = (id) => {
    let eventSource;
    const dispatch = useDispatch();

    const establishSSEConnection = () => {
        eventSource = new EventSource(
            'http://localhost:8800/chat/register/' + id
        );
        eventSource.onmessage = (event) => {
            // Handle the incoming event data
            if (event.data?.includes('chat')) {
                const eventData = JSON.parse(event.data);
                // Update your React state or perform any other action
                console.log('SSE response: ', eventData);
                dispatch(addChatId(eventData.id));
                dispatch(addNewChat(eventData));
            } else {
                console.log('SSE response: ', event.data);
            }
        };

        eventSource.onopen = () => {
            // Connection opened
            console.log('SSE registered');
        };

        eventSource.onerror = (error) => {
            // Handle errors
            //console.log('SSE Error:', error);
            // Reconnect after a certain delay using a reconnection strategy
            const reconnectDelay = 1000; // milliseconds
            setTimeout(() => {
                establishSSEConnection(id);
            }, reconnectDelay);
        };
        eventSource.onclose = () => {
            //console.log('SSE connection closed');
            // Reconnect using a reconnection strategy
            const reconnectDelay = 1000; // milliseconds
            setTimeout(() => {
                establishSSEConnection();
            }, reconnectDelay);
        };
    };

    useEffect(() => {
        if (id) establishSSEConnection();
        return () => {
            console.log('closing SSE');
            //eventSource.close();
        };
    }, [id]);

    return;
};

export default useSSE;
