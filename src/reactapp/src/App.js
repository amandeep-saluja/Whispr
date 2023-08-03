import "./App.css";
import { useState } from "react";

function App() {
  const [connection, setConnection] = useState(false);
  const [message, setMessage] = useState("");
  const [history, setHistory] = useState([]);

  const sendMessage = () => {
    setHistory([...history, message]);
    setMessage('');
  };
  return (
    <div className="App">
      {!connection ? (
        <>
          <button
            onClick={() => {
              setConnection(true);
            }}
          >
            Connect
          </button>
        </>
      ) : (
        <>
          <div className="message-container">
            <div>
              <input
                type="text"
                placeholder="Enter a message...."
                onChange={(e) => setMessage(e.target.value)}
                value={message}
              />
              <button onClick={sendMessage}>Send Message</button>
            </div>
            <div className="msg-sent">
              {history?.length === 0 ? (
                "No msg sent"
              ) : (
                <>
                  {history?.map((m, idx) => (
                    <div className="msg" key={idx}>
                      {m}
                    </div>
                  ))}
                </>
              )}
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default App;
