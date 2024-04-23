import DataSelectorWrapper, {selectorItem} from "@/components/UI/DataSelectorWrapper";
import React, {useEffect, useState} from "react";
import {FormState} from "@/lib/actions";
import {getOffices} from "@/lib/adminActions";
import {item} from "@/components/home/Table";
import SubmitButton from "@/components/UI/SubmitButton";
import {useFormState} from "react-dom";
import {AxiosError} from "axios";

const MakeEmployeeForm:React.FC<{actionFunction: (userId: number, officeId: number, initialState: FormState) => Promise<FormState>,  selectedItem: item; selectedCompanyId: number; onActionSuccess: (data: {  message: string; errors: string; }) => void}> = ({actionFunction, selectedItem, selectedCompanyId, onActionSuccess}) => {

    const [error, setError] = useState<Error | null | string>(null);
    const [offices, setOffices] = useState<selectorItem[]>([]);
    const [selectedOffice, setSelectedOffice] = useState<selectorItem> (offices[0]);
    const [makeCourierState  , makeCourierAction] = useFormState(actionFunction.bind(null, selectedItem.id, selectedOffice?.id as number), { message: '', errors: '' })

    useEffect(() => {

        if( makeCourierState.message ) onActionSuccess(makeCourierState as  {message: string, errors: string});

    }, [makeCourierState]);

    useEffect(() => {
        const fetchDropdownData = async() => {
            try {

                const offices = await getOffices(selectedCompanyId);

                if( offices ) {
                    setOffices(offices);
                }

            } catch (err) {
                if( err instanceof AxiosError ) {
                    setError(err)
                }
            }
        }

        fetchDropdownData();
    }, []);

    return <>
        <form action={makeCourierAction}>
            <h4 className="flex justify-center p-4">Choose witch office you would like the courier to work in:</h4>
            <div className="order-div">
                <label className="block font-bold ">Office:</label>
                {offices &&
                    <DataSelectorWrapper
                        hasInitialPlaceholderValue
                        placeholderValue={selectedOffice && Object.keys(selectedOffice).length > 0 ? selectedOffice.title : "Select offices"}
                        selectorData={offices}
                        onResubForNewData={(data) => setSelectedOffice(data)}
                    />
                }
                <SubmitButton formState={makeCourierState} />
            </div>
        </form>
    </>

}

export default MakeEmployeeForm;