"use client";
import React, { useState } from "react";

import FilterOrders from "./FilterOrders";
import Table from "../Table";
import Button from "../../UI/BaseButton";
import BaseDialog from "../../UI/BaseDialog";

import { categories, data, tableColumns } from "@/data/employee/ordersTableData";

const EmployeeInterface: React.FC = () => {
    const [showCreateOrderDialog, setShowCreateOrderDialog] = useState<boolean>(false)

    return (
        <section className="flex flex-col justify-start items-start gap-y-6 w-full">
            <h1>Welcome, Antoan</h1>
            <div className="flex gap-x-4">
                <Button fill
                onClick={() => setShowCreateOrderDialog(true)}>Create an order</Button>
            </div>

            {showCreateOrderDialog &&
        (<BaseDialog title="New order" tryClose={() => setShowCreateOrderDialog(false)}>
            <h3 className="flex justify-center">Create a new order:</h3><br/>
            <div className="order-div">
                <label className="block  text-gray-500">Sender:</label>
                <input type="text"  id="sender" className="input-info-dialog" placeholder="John Doe "></input><br/>
            </div>
            <div className="order-div">    
                <label className="block  text-gray-500">Receiver:</label>           
                <input type="text"  id="reveiver" className="input-info-dialog" placeholder="Jane Doe "></input><br/>
            </div>
            <div className="order-div">    
                <label className="block  text-gray-500">Departure location:</label>           
                <input type="text"  id="departure_place" className="input-info-dialog" placeholder="Sofia "></input><br/>
            </div>
            <div className="order-div">    
                <label className="block  text-gray-500">Arrival location:</label>
                <input type="text"  id="arrival_location" className="input-info-dialog" placeholder="Varna "></input><br/>
            </div>
            <div className="order-div">    
                <label className="block  text-gray-500">Date:</label>
                <input type="date"  id="date" className="block text-gray-500 rounded-xl border-2 py-1.5  focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-base sm:leading-6 whitespace-pre-line" placeholder="DD:MM:YYYY" ></input><br/>
                
            </div>
            <div className="order-div">    
                <label className="block  text-gray-500">Weight (in kg.):</label>
                <input type="text"  id="weight" className="input-info-dialog" placeholder="40 "></input><br/>
            </div>
            <div className="order-div">
                <label className="block  text-gray-500">Employee:</label>
                <input type="text"  id="employee" className="input-info-dialog" placeholder="John Doe "></input><br/>
            </div>
            <div className='flex justify-center py-3 text-gray-500'>
                <button
                    className="action_btn_green px-8 py-3"
                >
                    Create
                </button>
            </div>
        </BaseDialog>)
        }


            <FilterOrders />
            <Table
                columns={tableColumns}
                categories={categories}
                data={data}
            />
        </section>
    )
};

export default EmployeeInterface;