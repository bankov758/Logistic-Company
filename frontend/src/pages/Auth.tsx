import React from "react";

const Auth: React.FC = () => {

    return (
        <div>
            <h1>auth page</h1>
            <h1>hi</h1>

            <label>Username: </label>
            <input type="text" className="username" id="username" name="username" value="Username"></input>
            <label>Password: </label>
            <input type="password" className="password" id="password" name="password" value="Password"></input>
            <button type="button">Log in</button>
        </div>
    )
}

export default Auth;