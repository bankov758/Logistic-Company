import React from "react";
import {item} from "@/components/home/Table";

const EditOfficeForm: React.FC<{ selectedItem: item }> = ({selectedItem}) => {

    const submitForm = (e:any) => {
        const formData = new FormData(e.target);
        const data = Object.fromEntries(formData);
    }

    return (
        <form onSubmit={submitForm}>
            <h3 className="flex justify-center">Edit an office:</h3><br/>
            <div className="order-div">
                <label className="block  text-gray-500">Address:</label>
                <input type="text" id="adress" className="input-info-dialog"/>
            </div>
            <div className="order-div">
                <label className="flex text-gray-500">Company name:</label>
                <input type="text" id="companyName" className="input-info-dialog justify-center"/>
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
}

export default EditOfficeForm;