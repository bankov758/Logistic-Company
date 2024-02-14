import React, { Fragment } from "react";
import { Link } from "react-router-dom";

const ProductPage: React.FC = () => {

    return (
        <Fragment>
            <h1>Product page</h1>
            <ul>
                <li>
                    <Link to='1'>Product 1</Link>
                </li>
                <li>
                    <Link to='2'>Product 2</Link>
                </li>
                <li>
                    <Link to='3'>Product 3</Link>
                </li>
            </ul>
        </Fragment>
    )
}

export default ProductPage;