import React from "react";
import { Link } from "react-router-dom";

const TheHeader: React.FC = () => {

    return (
        <nav>
            <ul>
                <li>
                    <Link to='/'>Home</Link>
                </li>
                <li>
                    <Link to='/auth'>Auth</Link>
                </li>
            </ul>
        </nav>
    )
}

export default TheHeader;