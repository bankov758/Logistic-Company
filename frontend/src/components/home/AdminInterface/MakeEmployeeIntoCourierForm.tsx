import DataSelectorWrapper, {selectorItem} from "@/components/UI/DataSelectorWrapper";
import React, {useEffect, useState} from "react";
import {getCompanyId, getCouriers, getUsers} from "@/lib/actions";
import {getOffices, makeCourier, promoteCourier} from "@/lib/adminActions";
import {item} from "@/components/home/Table";
import SubmitButton from "@/components/UI/SubmitButton";
import {useFormState} from "react-dom";
import {AxiosError} from "axios";

const MakeEmployeeIntoCourierForm:React.FC<{selectedItem: item; selectedCompanyId: number}> = ({selectedItem, selectedCompanyId}) => {

    const [error, setError] = useState<Error | null | string>(null);
    const [offices, setOffices] = useState<selectorItem[]>([]);
    const [selectedOffice, setSelectedOffice] = useState<selectorItem> (offices[0]);
    const [makeCourierState, makeCourierAction] = useFormState(promoteCourier.bind(null, selectedItem.id, selectedOffice?.id as number), {message: '', errors: ''})

    useEffect(() => {
        console.log(makeCourierState)
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
            <h3>Choose witch office you would like the courier to work in:</h3>
            <div className="order-div">
                <label className="block  text-gray-500">Courier:</label>
                {offices &&
                    <DataSelectorWrapper
                        hasInitialPlaceholderValue
                        placeholderValue={selectedOffice && Object.keys(selectedOffice).length > 0 ? selectedOffice.title : "Select offices"}
                        selectorData={offices}
                        onResubForNewData={(data) => setSelectedOffice(data)}
                    />
                }
                <SubmitButton formState={makeCourierState}>

                </SubmitButton>
            </div>
        </form>
    </>

}

export default MakeEmployeeIntoCourierForm;