import React, {useEffect} from "react";
import {item} from "@/components/home/Table";
import {useFormState} from "react-dom";
import {editOffice} from "@/lib/adminActions";
import SubmitButton from "@/components/UI/SubmitButton";

const EditOfficeForm: React.FC<{ selectedItem: item, onActionSuccess: () => void }> = ({selectedItem, onActionSuccess}) => {
    const [editOfficeState, editOfficeAction] = useFormState(editOffice.bind(null, selectedItem.id, selectedItem.companyId), { message: '', errors: '' });

    useEffect(() => {

        if( editOfficeState.message ) onActionSuccess();

    }, [editOfficeState]);

    return (
        <form action={editOfficeAction}>
            <h3 className="flex justify-center mb-4">Edit an office:</h3>

            <div className="order-div">
                <label className="block  text-gray-500">Address:</label>
                <input type="text" id="address" name='address' className="input-info-dialog"/>
            </div>

            <div className='flex justify-center py-3 text-gray-500'>
                <SubmitButton formState={editOfficeState}/>
            </div>
        </form>
    )
}

export default EditOfficeForm;