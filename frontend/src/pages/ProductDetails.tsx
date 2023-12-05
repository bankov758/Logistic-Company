import React, { Fragment, useEffect } from "react";
import { Link, useParams } from "react-router-dom";

const ProductDetails: React.FC = () => {
    const params = useParams();

    useEffect(() => {
      console.log('params >>> ', params)
    })
    
    return (
        <Fragment>
            <h1>Product details</h1>
            <span>ProductID: {params.productId}</span>

            {/* relative to the path, not to the route since it will go to the home page because .. is a relative path */}
            <p><Link to='..' relative='path'>Go back</Link></p>
        </Fragment>
    )
}

export default ProductDetails;