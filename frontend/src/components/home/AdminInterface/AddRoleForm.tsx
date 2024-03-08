import React from "react";
import {item} from "@/components/home/Table";
import BaseDialog from "@/components/UI/BaseDialog";

const AddRoleForm: React.FC<{ selectedItem: item }> = ({selectedItem}) => {

    return (

        <form>
            <h3 className="flex justify-center mb-4">Add Role in the company:</h3>
            <td className="flex gap-x-4 items-center justify-center">
                <button className="base-btn-blue px-8 " >Office employee</button>
                <button className="base-btn-blue px-14" >Courier</button>
            </td>
        </form>
    )
}

export default AddRoleForm;