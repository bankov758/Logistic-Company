'use client';

import React, { FormEvent, useMemo, useState } from "react";

import Button from "@/components/UI/BaseButton";
import DataSelectorWrapper, { selectorItem } from "@/components/UI/DataSelectorWrapper";
import Input from "@/components/UI/Input";
import useInput from "@/hooks/useInput";

const FilterOrders: React.FC = () => {
    const [orderFilterType, setOrderFilterType] = useState<string>('Registered');

    const orderFilterTypeData = useMemo(() => {
        return [
            {
                title: 'Registered',
                code: 'registered'
            },
            {
                title: 'Sent',
                code: 'sent'
            },
            {
                title: 'Received',
                code: 'received'
            }
        ];
    }, []);

    const changeFilterType = (data: selectorItem) => {
        console.log(data);
        setOrderFilterType(data.title);
    }

    const {
        value: enteredClientName,
        valueChangeHandler: clientNameChangeHandler,
        inputBlurHandler: clientNameBlurHandler,
        reset: clientNameReset
     } = useInput(
        (value) => value.trim().length > 3,
        ''
    );

    const {
        value: enteredEmployeeName,
        valueChangeHandler: employeeNameChangeHandler,
        inputBlurHandler: employeeNameBlurHandler,
        reset: employeeNameReset
     } = useInput(
        (value) => value.trim().length > 3,
        ''
    );

    const filterOrders = async (event: FormEvent) => {
        event.preventDefault();

        console.log('enteredClientName >>> ', enteredClientName);
        console.log('enteredEmployeeName >>> ', enteredEmployeeName);
        console.log('orderFilterType >>> ', orderFilterType);
    }

    return (
        <form className="flex gap-x-6 justify-start items-center w-full" onSubmit={filterOrders}>
            <Input 
                id='client_name'
                type='text'
                name='client_name'
                placeholder="Client name"
                enteredValue={enteredClientName}
                onChangeHandler={clientNameChangeHandler}
                onBlurHandler={clientNameBlurHandler}
                reset={clientNameReset}
                iconSrc="/icons/user.svg"
                iconAlt="User icon"
                showRemoveIcon
            />
            <Input 
                id='employee_name'
                type='text'
                name='employee_name'
                placeholder="Employee name"
                enteredValue={enteredEmployeeName}
                onChangeHandler={employeeNameChangeHandler}
                onBlurHandler={employeeNameBlurHandler}
                reset={employeeNameReset}
                iconSrc="/icons/user.svg"
                iconAlt="User icon"
                showRemoveIcon
            />
            <DataSelectorWrapper 
                hasInitialPlaceholderValue
                placeholderValue={orderFilterType}
                selectorData={orderFilterTypeData}
                onResubForNewData={changeFilterType}
            />
            <Button fill type='submit'>Filter orders</Button>
        </form>
    );
};

export default FilterOrders;