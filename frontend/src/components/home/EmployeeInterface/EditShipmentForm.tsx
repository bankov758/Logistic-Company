"use client";

import React, {useState} from "react";
import {item} from "@/components/home/Table";
import RadioButton from "@/components/UI/RadioButton";

// Example usage:
const options = [
    {value: "Active", placeholder: "Active"},
    {value: "Closed", placeholder: "Closed"},
];

const EditShipmentForm: React.FC<{ selectedItem: item}> = ({ selectedItem }) => {
    const [inputType, setInputType] = useState("text");

    return (
        <form>
            <h3 className="flex justify-center">Edit the shipment</h3>
            <br/>
            <div className="order-div">
                <label className="block  text-gray-500">Sender:</label>
                <input type="text" id="sender" className="input-info-dialog"
                       placeholder={selectedItem?.sender ?? "Name:"} />
            </div>
            <div className="order-div">
                <label className="block  text-gray-500">Receiver:</label>
                <input type="text" id="receiver" className="input-info-dialog"
                       placeholder={selectedItem?.receiver ?? "Name:"}></input><br/>
            </div>
            <div className="order-div">
                <label className="block  text-gray-500">Departure location:</label>
                <input type="text" id="departureAddress" className="input-info-dialog"
                       placeholder={selectedItem?.departureAddress ?? "Departure location:"}></input><br/>
            </div>
            <div className="order-div">
                <label className="block  text-gray-500">Arrival location:</label>
                <input type="text" id="arrivalAddress" className="input-info-dialog"
                       placeholder={selectedItem?.arrivalAddress ?? "Arrival location:"}></input><br/>
            </div>
            <div className="order-div">
                <label className="block  text-gray-500">Date:</label>
                <input type={inputType} onFocus={() => setInputType('date')}
                       onBlur={() => setInputType('text')} id="sentDate"
                       className="block text-gray-500 rounded-xl border-2 py-1.5 px-1 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-base sm:leading-6 whitespace-pre-line"
                       placeholder={selectedItem?.sentDate ?? "Date: "}></input><br/>
            </div>
            <div className="order-div">
                <label className="block  text-gray-500">Status:</label>
                <div className="rounded-xl border-2 py-1 px-3">
                    <RadioButton options={options}/>
                </div>
            </div>
            <div className="order-div">
                <label className="block  text-gray-500">Weight (in kilodram):</label>
                <input type="text" id="weight" className="input-info-dialog"
                       placeholder={selectedItem?.weight ?? "Weight:"}></input><br/>
            </div>
            <div className="order-div ">
                <label className="block  text-gray-500">Employee:</label>
                <input type="text" id="employee" className="input-info-dialog"
                       placeholder={selectedItem?.employee ?? "Employee:"}></input><br/>
            </div>
            <div className='flex justify-center py-3 text-gray-500'>
                <button
                    className="action_btn_green px-8 py-3"
                >
                    Edit
                </button>
            </div>
        </form>
    )
};

export default EditShipmentForm;