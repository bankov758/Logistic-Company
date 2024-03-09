import React from "react";
import {useFormState} from "react-dom";
import Notification from "@/components/UI/Notification";
import {createAnOrder} from "@/lib/actions";
import SubmitButton from "@/components/UI/SubmitButton";

const CreateAnOrderForm: React.FC = () => {
    const [createAnOrderState, createAnOrderAction] = useFormState(createAnOrder, {message: null, errors: ''})
    
    return (
        <>
            <h3 className="flex justify-center">Create a new order:</h3>
            <br/>
            {/*TODO:
                1. add htmlFor attribute to the <label>
                2. bind the htmlFor attribute to the corresponding input id
                3. add name attribute to the <input>
                4. create the actual createAnOrderAction action logic inside actions.ts including Zod validation schema
            */}
            <form action={createAnOrderAction}>
                <div className="order-div">
                    <label className="block text-gray-500">Sender:</label>
                    <input type="text" id="sender" className="input-info-dialog" placeholder="John Doe "></input><br/>
                </div>
                <div className="order-div">
                    <label className="block text-gray-500">Receiver:</label>
                    <input type="text" id="reveiver" className="input-info-dialog" placeholder="Jane Doe "></input><br/>
                </div>
                <div className="order-div">
                    <label className="block  text-gray-500">Departure location:</label>
                    <input type="text" id="departure_place" className="input-info-dialog"
                           placeholder="Sofia "></input><br/>
                </div>
                <div className="order-div">
                    <label className="block  text-gray-500">Arrival location:</label>
                    <input type="text" id="arrival_location" className="input-info-dialog"
                           placeholder="Varna "></input><br/>
                </div>
                <div className="order-div">
                    <label className="block  text-gray-500">Date:</label>
                    <input type="date" id="date"
                           className="block text-gray-500 rounded-xl border-2 py-1.5  focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-base sm:leading-6 whitespace-pre-line"
                           placeholder="DD:MM:YYYY"></input><br/>
                </div>
                <div className="order-div">
                    <label className="block  text-gray-500">Weight (in kg.):</label>
                    <input type="text" id="weight" className="input-info-dialog" placeholder="40 "></input><br/>
                </div>
                <div className="order-div">
                    <label className="block  text-gray-500">Employee:</label>
                    <input type="text" id="employee" className="input-info-dialog" placeholder="John Doe "></input><br/>
                </div>
                <div className='flex justify-center py-3 text-gray-500'>
                    <SubmitButton formState={createAnOrderState}/>
                </div>
            </form>
        </>
    )
};

export default CreateAnOrderForm;