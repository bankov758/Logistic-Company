import React from "react";

import FilterOrders from "./EmployeeInterface/FilterOrders";
import Button from "../UI/BaseButton";

const EmployeeInterface: React.FC = () => {

    return (
        <section className="flex flex-col justify-start items-start gap-y-6 w-full">
            <h1>Welcome, Antoan</h1>
            <div className="flex gap-x-4">
                <Button fill>Create an order</Button>
                <Button secondary fill>Close an order</Button>
            </div>
            <FilterOrders />
        </section>
    )
};

export default EmployeeInterface;