import React, {useEffect} from 'react';
import './App.css';

function App() {

    const fetchUsers = async () => {
        const response = await fetch("http://localhost:8080/api/users", {
            method: "GET",
            headers: {
                Authorization: 'antoan'
            }
        });

        console.log('response >>> ', response);

        const data = await response.json();

        console.log('data >>> ', data);
    }

    useEffect(() => {
        fetchUsers()
    }, []);

    return (
        <div className="App">

        </div>
    );
}

export default App;
