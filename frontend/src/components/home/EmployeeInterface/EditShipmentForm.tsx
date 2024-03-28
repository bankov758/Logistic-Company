"use client";

import React, {useEffect, useState} from "react";
import {item} from "@/components/home/Table";
import RadioButton from "@/components/UI/RadioButton";
import SubmitButton from "@/components/UI/SubmitButton";
import {useFormState} from "react-dom";
import DataSelectorWrapper, {selectorItem} from "@/components/UI/DataSelectorWrapper";
import {getSession, Session} from "@/lib/auth";
import {editOrder, getCompanies, getCouriers, getUsers} from "@/lib/actions";

// Example usage:
const options = [
    {value: "Active", placeholder: "Active"},
    {value: "Closed", placeholder: "Closed"},
];

const EditShipmentForm: React.FC<{ selectedItem: item }> = ({selectedItem}) => {
    const [session, setSession] = useState<null | Session>(null);
    const [error, setError] = useState<Error | null | string>(null);

    const [companies, setCompanies] = useState<selectorItem[]>([]);
    const [couriers, setCouriers] = useState<selectorItem[]>([]);
    const [users, setUsers] = useState<selectorItem[]>([]);
    const [selectedCompany, setSelectedCompany] = useState<selectorItem | null>(null);
    const [selectedCourier, setSelectedCourier] = useState<selectorItem | null> (null);
    const [selectedSender, setSelectedSender] = useState<selectorItem | null>(null);
    const [selectedReceiver, setSelectedReceiver] = useState<selectorItem | null>(null);



    useEffect(() => {
        getSession()
            .then( async (response) => {
                setSession(response)
                try {
                    const companies = await getCompanies();
                    const users = await getUsers();
                    const couriers = await getCouriers();
                    if( companies ) {
                        setCompanies(companies);
                    } else {
                        setError("Something went wrong when requesting companies!")
                    }
                    if( users ) {
                        setUsers(users);
                    }else {
                        setError("Something went wrong when requesting users!")
                    }
                    if( couriers ) {
                        setCouriers(couriers);
                    }else {
                        setError("Something went wrong when requesting users!")
                    }
                } catch (err) {
                    if( err instanceof Error ) {
                        setError(err)
                    }
                }
            });
    }, []);


    function handleSelect(data:selectorItem, targetState:string) {
        switch (targetState) {
            case 'sender' : setSelectedSender(data); break;
            case 'receiver' : setSelectedReceiver(data); break;
            case 'courier' :  setSelectedCourier(data); break;
           // case 'company' : setSelectedCompany(data); break;
            default: break;
        }
    }
    const[editShipmentState, editShipmentAction] = useFormState(editOrder.bind(null,
        session,
        selectedSender?.id,
        selectedReceiver?.id,
        selectedCourier?.id,
            selectedItem.id),
        {message: null, errors: ''})

    return (
        <form action={editShipmentAction}>
            <h3 className="flex justify-center">Edit the shipment</h3>
            <div className="order-div">
                <label className="block text-gray-500">Sender:</label>
                <DataSelectorWrapper
                    hasInitialPlaceholderValue
                    placeholderValue={selectedSender && Object.keys(selectedSender).length > 0 ? selectedSender.title : "Select user"}
                    selectorData={users}
                    onResubForNewData={(data) => handleSelect(data, 'sender')}
                />
            </div>
            <div className="order-div">
                <label className="block  text-gray-500">Receiver:</label>
                <DataSelectorWrapper
                    hasInitialPlaceholderValue
                    placeholderValue={selectedReceiver && Object.keys(selectedReceiver).length > 0 ? selectedReceiver.title : "Select user"}
                    selectorData={users}
                    onResubForNewData={(data) => handleSelect(data, 'receiver')}
                />
            </div>
            <div className="order-div">
                <label className="block  text-gray-500">Courier:</label>
                <DataSelectorWrapper
                    hasInitialPlaceholderValue
                    placeholderValue={selectedCourier && Object.keys(selectedCourier).length > 0 ? selectedCourier.title : "Select courier"}
                    selectorData={couriers}
                    onResubForNewData={(data) => handleSelect(data, 'courier')}
                />
            </div>
            <div className="order-div">
                <label htmlFor="departureAddress" className="block  text-gray-500">Departure location:</label>
                <input type="text" id="departureAddress" name="departureAddress" className="input-info-dialog"/>
            </div>
            <div className="order-div">
                <label htmlFor="arrivalAddress" className="block  text-gray-500">Arrival location:</label>
                <input type="text" id="arrivalAddress" name="arrivalAddress" className="input-info-dialog"/>
            </div>
            <div className="order-div">
                <label htmlFor="sentDate" className="block  text-gray-500">Send date:</label>
                <input
                    type='date'
                    id="sentDate" name="sentDate"
                    className="block text-gray-500 rounded-xl border-2 py-1.5 px-1 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-base sm:leading-6 whitespace-pre-line"
                />
            </div>
            <div className="order-div">
                <label htmlFor="receivedDate" className="block  text-gray-500">Received date:</label>
                <input
                    type='date'
                    id="receivedDate" name="receivedDate"
                    className="block text-gray-500 rounded-xl border-2 py-1.5 px-1 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-base sm:leading-6 whitespace-pre-line"
                />
            </div>

            {/*<div className="order-div">*/}
            {/*    <label className="block  text-gray-500">Status:</label>*/}
            {/*    <div className="rounded-xl border-2 py-1 px-3">*/}
            {/*        <RadioButton options={options} />*/}
            {/*    </div>*/}
            {/*</div>*/}
            <div className="order-div">
                <label htmlFor="weight" className="block  text-gray-500">Weight (in kilogram):</label>
                <input type="text" id="weight" name="weight" className="input-info-dialog"/>
            </div>
            <div className='flex justify-center py-3 text-gray-500'>
                <div className='flex justify-center py-3 text-gray-500'>
                    <SubmitButton formState={editShipmentState}/>
                </div>
            </div>
        </form>
    )
};

export default EditShipmentForm;