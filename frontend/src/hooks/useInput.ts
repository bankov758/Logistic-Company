import { ChangeEvent, FocusEvent, useState } from "react";

const useInput = (validateInputHandler: (value: any) => boolean, initialInputValue: any = null) => {
    const [inputValue, setInputValue] = useState<string>
        (initialInputValue ?? "");//using nullish operator
    const [isTouched, setIsTouched] = useState<boolean>(false);
    
    const valueIsValid:boolean = validateInputHandler(inputValue);
    const hasError:boolean = !valueIsValid && isTouched;

    //this method triggers when the user modifies the element's value
    const valueChangeHandler = (event:ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setInputValue(event.target?.value);
    };
    
    //this method triggers when an element has lost focus
    const inputBlurHandler = (event: FocusEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setIsTouched(true);
    };
    
    const reset = (): void => {
        setInputValue("");
        setIsTouched(false);
    };
    
    return {
        value: inputValue,
        isValid: valueIsValid,
        hasError,
        valueChangeHandler,
        inputBlurHandler,
        reset
    };
    
};

export default useInput;