"use client";

import React, {useEffect, useState} from "react";
import {item} from "@/components/home/Table";
import SubmitButton from "@/components/UI/SubmitButton";
import {useFormState} from "react-dom";
import DataSelectorWrapper, {selectorItem} from "@/components/UI/DataSelectorWrapper";
import {editShipment, getCompanyId, getCouriers, getUsers} from "@/lib/actions";

const EditShipmentForm: React.FC<{ selectedItem: item, employeeId: number; onActionSuccess: (data: {  message: string; errors: string; }) => void }> = ({selectedItem, employeeId, onActionSuccess}) => {
    const [error, setError] = useState<Error | null | string>(null);

    const [users, setUsers] = useState<selectorItem[]>([]);
    const [couriers, setCouriers] = useState<selectorItem[]>([]);

    const [selectedSender, setSelectedSender] = useState<selectorItem | null>(null);
    const [selectedReceiver, setSelectedReceiver] = useState<selectorItem | null>(null);
    const [selectedCourier, setSelectedCourier] = useState<selectorItem | null> (null);

    const [editShipmentState, editShipmentAction] = useFormState(editShipment.bind(null,
            employeeId,
            selectedSender?.id,
            selectedReceiver?.id,
            selectedCourier?.id,
            selectedItem,
            users
        ),
        {message: '', errors: ''}
    )

    useEffect(() => {

        if( editShipmentState.message ) onActionSuccess(editShipmentState as {  message: string; errors: string; });
    }, [editShipmentState]);

    useEffect(() => {
        const fetchDropdownData = async() => {
            try {
                const users = await getUsers();
                const companyId = await getCompanyId();
                const couriers = await getCouriers(companyId);

                if( users ) {
                    setUsers(users);
                }

                if( couriers ) {
                    setCouriers(couriers);
                }
            } catch (err) {
                if( err instanceof Error ) {
                    setError(err)
                }
            }
        }

        fetchDropdownData();
    }, []);


    function handleSelect(data:selectorItem, targetState:string) {
        switch (targetState) {
            case 'sender' : setSelectedSender(data); break;
            case 'receiver' : setSelectedReceiver(data); break;
            case 'courier' :  setSelectedCourier(data); break;
            default: break;
        }
    }

    return (
        <form action={editShipmentAction}>
            <h3 className="flex justify-center">Edit the shipment</h3>

            <div className="order-div">
                <label className="block text-gray-500">Sender:</label>
                {users &&
                    <DataSelectorWrapper
                        hasInitialPlaceholderValue
                        placeholderValue={selectedSender && Object.keys(selectedSender).length > 0 ? selectedSender.title : "Select user"}
                        selectorData={users}
                        onResubForNewData={(data) => handleSelect(data, 'sender')}
                    />
                }
            </div>

            <div className="order-div">
                <label className="block  text-gray-500">Receiver:</label>
                {users &&
                    <DataSelectorWrapper
                        hasInitialPlaceholderValue
                        placeholderValue={selectedReceiver && Object.keys(selectedReceiver).length > 0 ? selectedReceiver.title : "Select user"}
                        selectorData={users}
                        onResubForNewData={(data) => handleSelect(data, 'receiver')}
                    />
                }
            </div>

            <div className="order-div">
                <label className="block  text-gray-500">Courier:</label>
                {couriers &&
                    <DataSelectorWrapper
                        hasInitialPlaceholderValue
                        placeholderValue={selectedCourier && Object.keys(selectedCourier).length > 0 ? selectedCourier.title : "Select courier"}
                        selectorData={couriers}
                        onResubForNewData={(data) => handleSelect(data, 'courier')}
                    />
                }
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

            <div className="order-div">
                <label htmlFor="weight" className="block  text-gray-500">Weight:</label>
                <input
                    type='text'
                    id="weight" name="weight"
                    className="block text-gray-500 rounded-xl border-2 py-1.5 px-1 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-base sm:leading-6 whitespace-pre-line"
                />
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